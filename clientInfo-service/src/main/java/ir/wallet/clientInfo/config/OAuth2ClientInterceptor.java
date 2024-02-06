package ir.wallet.clientInfo.config;

import org.apache.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.util.Objects.isNull;

@Component
public class OAuth2ClientInterceptor implements ClientHttpRequestInterceptor {

    private final ReactiveOAuth2AuthorizedClientManager manager;
    private final ClientRegistration clientRegistration;

    public OAuth2ClientInterceptor(ReactiveOAuth2AuthorizedClientManager manager
            , ReactiveClientRegistrationRepository clientRegistrationRepository){
        this.manager = manager;
        this.clientRegistration = clientRegistrationRepository.findByRegistrationId("client-internal").block();
    }
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthorizeRequest oAuth2AuthorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId(clientRegistration.getRegistrationId())
                .principal(authentication)
                .build();

        OAuth2AuthorizedClient client = manager.authorize(oAuth2AuthorizeRequest).block();
        if (isNull(client)) {
            throw new IllegalStateException("Missing Credentials");
        }
        request.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken().getTokenValue());

        return execution.execute(request, body);
    }
}
