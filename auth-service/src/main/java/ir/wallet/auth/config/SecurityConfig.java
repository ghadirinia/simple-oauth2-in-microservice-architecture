package ir.wallet.auth.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;
import java.util.stream.Collectors;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    public SecurityFilterChain webFilterChainForOauth(HttpSecurity httpSecurity) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);
        httpSecurity.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());
        httpSecurity.exceptionHandling(e-> e.authenticationEntryPoint(
                new LoginUrlAuthenticationEntryPoint("/login")));
        return httpSecurity.build();
    }
    @Order(2)
    @Bean
    public SecurityFilterChain appSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request->request.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        var user1 = User.withUsername("username")
                .password("password")
                .authorities("read")
                .roles("ADMIN")
                .build();
        var user2 = User.withUsername("hai")
                .password("hai1722")
                .authorities("read")
                .roles("VIEWER")
                .build();

        var adminUser = User.withUsername("joji")
                .password("joji123")
                .authorities("read")
                .roles("VIEWER","ADMIN")
                .build();
        var hackUser = User.withUsername("GGG")
                .password("joji123")
                .authorities("read")
                .roles("HACK")
                .build();
        return new InMemoryUserDetailsManager(user1,user2,adminUser,hackUser);
    }
    //just for development;use bcryptpasswordencoder
    @Bean
    public PasswordEncoder passwordEncoder(){

        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(){
        var registerClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("white-wallet-client-id")
                .clientSecret("my-white-wallet-client-secret")
                .scopes(scope->{
                    scope.add(OidcScopes.PROFILE);
                    scope.add(OidcScopes.OPENID);
                    scope.add("read");
                    scope.add("write");
                    scope.add("internal");
                }
                )
               // .scope(OidcScopes.PROFILE)
                .redirectUri("http://localhost:9191/login/oauth2/code/api-gateway")
               // .redirectUri("http://localhost:9191/authorized")
                .clientAuthenticationMethods(s->
                {s.add(ClientAuthenticationMethod.NONE);//public client
                        s.add(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
                        s.add(ClientAuthenticationMethod.CLIENT_SECRET_POST);})
                .authorizationGrantTypes(
                        grantType->{
                            grantType.add(AuthorizationGrantType.CLIENT_CREDENTIALS);
                            grantType.add(AuthorizationGrantType.AUTHORIZATION_CODE);
                            grantType.add(AuthorizationGrantType.REFRESH_TOKEN);
                        })/*.clientSettings(ClientSettings.builder().requireProofKey(true)*/
                        .build();

        return new InMemoryRegisteredClientRepository(registerClient);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings () {
        return AuthorizationServerSettings.builder().build();
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);

        var keys = keyPairGenerator.generateKeyPair();
        var publicKey = (RSAPublicKey) keys.getPublic();
        var privateKey = (RSAPrivateKey) keys.getPrivate();
        var rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource){
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtEncodingContextOAuth2TokenCustomizer(){
        return context -> {
            if( context.getTokenType().getValue().equals(OAuth2TokenType.ACCESS_TOKEN.getValue())){
                Authentication principal = context.getPrincipal();
                System.out.println(principal);
                var authorities = principal.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet());
                context.getClaims().claim("authorities",authorities);
            }
        };
    }


    //old authorization


/*
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
                http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());
        http.exceptionHandling(e->e.authenticationEntryPoint(
                        new LoginUrlAuthenticationEntryPoint("/login")));
        return http.build();
    }
    @Bean
    @Order(2)
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

*//*    @Bean
    public UserDetailsService userDetailsService(){
        var user = User.withUsername("marool")
                .password("salam")
                .authorities("read")
                .authorities("write")
                .build();
        return new InMemoryUserDetailsManager(user);
    }*//*
    @Bean
    public RegisteredClientRepository registeredClientRepository(){
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("white-wallet-client-id")
                .clientSecret("my-white-wallet-client-secret")
                //.clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope("message.read")
                .scope("message.write")
                .redirectUri("http://127.0.0.1:9191/login/oauth2/code/api-gateway")
                .redirectUri("http://127.0.0.1:9191/authorized")
                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)//public client
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .authorizationGrantTypes(
                        grantType->{
                            grantType.add(AuthorizationGrantType.CLIENT_CREDENTIALS);
                            grantType.add(AuthorizationGrantType.AUTHORIZATION_CODE);
                            grantType.add(AuthorizationGrantType.REFRESH_TOKEN);
                        }).clientSettings(ClientSettings.builder().requireProofKey(true).build()).build();
        return new InMemoryRegisteredClientRepository(registeredClient);
    }
    //authorization server url
    @Bean
    public AuthorizationServerSettings providerSettings() {
        return AuthorizationServerSettings.builder()
                //.authorizationEndpoint("http://localhost:8080")
              //  .issuer("http://localhost:8080")
                .build();
    }*/
}
//