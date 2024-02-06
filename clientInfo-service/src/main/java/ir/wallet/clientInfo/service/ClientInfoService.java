package ir.wallet.clientInfo.service;

import ir.wallet.clientInfo.dto.APIDto;
import ir.wallet.clientInfo.dto.ClientInfoDto;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;

public interface ClientInfoService {
    public APIDto testRestTemplate();
   // public APIDto testWebClient();
    public APIDto testWebClientNew(OAuth2AuthorizedClient authorizedClient);
    public APIDto testFeignClient(String bearerToken);
    }
