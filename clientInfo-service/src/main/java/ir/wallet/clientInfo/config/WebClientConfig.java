package ir.wallet.clientInfo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {
    // == Oauth2 Configuration ==
    @Bean
    public WebClient webClientOldOne(){
        return WebClient.builder().build();
    }
    @Bean
    ReactiveClientRegistrationRepository clientRegistration() {
        ClientRegistration clientRegistration = ClientRegistration
                .withRegistrationId("client-internal")
                .tokenUri("http://localhost:8080/oauth2/token")
                .clientId("white-wallet-client-id")
                .clientSecret("my-white-wallet-client-secret")
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                //.clientAuthenticationMethod(ClientAuthenticationMethod.POST)
                .scope("internal")
                .build();

        return new InMemoryReactiveClientRegistrationRepository(clientRegistration);
    }

    @Bean
    ReactiveOAuth2AuthorizedClientService reactiveOAuth2AuthorizedClientService() {
        return new InMemoryReactiveOAuth2AuthorizedClientService(clientRegistration());
    }

    @Bean
    WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations,
                        ReactiveOAuth2AuthorizedClientService reactiveOAuth2AuthorizedClientService) {

        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(
                        new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrations,
                                reactiveOAuth2AuthorizedClientService));
        oauth.setDefaultClientRegistrationId("client-internal");
        //oauth.setDefaultOAuth2AuthorizedClient(true);
        return WebClient.builder()
                .filter(oauth)
                .build();
    }
  /*  @Autowired
    private ReactiveClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private ReactiveOAuth2AuthorizedClientService oAuth2AuthorizedClientService;


    @Bean
    @LoadBalanced
    public WebClient.Builder webclient() {
        WebClient.Builder builder = WebClient.builder();

        ReactiveOAuth2AuthorizedClientManager authorizedClientManager =
                authorizedClientManager(clientRegistrationRepository, oAuth2AuthorizedClientService);

        Mono<OAuth2AuthorizedClient> authorize = authorizedClientManager
                .authorize(OAuth2AuthorizeRequest.withClientRegistrationId("client-internal")
                        .principal("white-wallet-client-id").build());

        authorize.doOnNext(auth -> builder.filter(WebclientInterceptor.interceptor(auth.getAccessToken().getTokenValue())))
                .subscribe();

        return builder;
    }

    @Bean
    WebClient webClient(ReactiveOAuth2AuthorizedClientManager authorizedClientManager) {

        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth2Client =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);


        return WebClient.builder()
                .filter(oauth2Client)
                .build();

    }

    @Bean
    public ReactiveOAuth2AuthorizedClientManager authorizedClientManager(
            final ReactiveClientRegistrationRepository clientRegistrationRepository,
            final ReactiveOAuth2AuthorizedClientService authorizedClientService) {
        final ReactiveOAuth2AuthorizedClientProvider authorizedClientProvider =
                ReactiveOAuth2AuthorizedClientProviderBuilder
                        .builder()
                        .clientCredentials()
                        .build();
        final AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientService);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
        return authorizedClientManager;
    }*/
}
