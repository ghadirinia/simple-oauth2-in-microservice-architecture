/*
package ir.wallet.clientInfo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class WebClientConfiguration {

    //@Value("${spring.security.oauth2.client.registration.client-internal.clientId}")
  //  String clientId="white-wallet-client-id";

   // @Value("${spring.security.oauth2.client.registration.client-internal.clientSecret}")
  //  String clientSecret;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration clientRegistration1 = ClientRegistration.withRegistrationId("client-internal")
                .clientId("white-wallet-client-id")
                .clientSecret("my-white-wallet-client-secret")
                .tokenUri("http://localhost:8080/oauth2/token")
                //.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)

                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("internal")
                .build();

        return new InMemoryClientRegistrationRepository(clientRegistration1);
    }
    @Bean
    public OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository() {
        return new HttpSessionOAuth2AuthorizedClientRepository();
    }
    @Bean
    public OAuth2AuthorizedClientService oAuth2AuthorizedClientService(){
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }


    @Bean
    public OAuth2AuthorizedClientManager auth2AuthorizedClientManager(
            ClientRegistrationRepository clientRegistrRepo,
            OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepo
    ) {
        OAuth2AuthorizedClientProvider auth2Provider =
                OAuth2AuthorizedClientProviderBuilder
                .builder().authorizationCode().refreshToken().build();
        DefaultOAuth2AuthorizedClientManager clientManager =
                new DefaultOAuth2AuthorizedClientManager(clientRegistrRepo,oAuth2AuthorizedClientRepo);
        clientManager.setAuthorizedClientProvider(auth2Provider);
        return  clientManager;
    }
    @Bean
    @LoadBalanced
    public WebClient webClient(OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauthClientFunction =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(oAuth2AuthorizedClientManager);
        return WebClient.builder()
                .apply(oauthClientFunction.oauth2Configuration())
                .build();
    }
}
*/
