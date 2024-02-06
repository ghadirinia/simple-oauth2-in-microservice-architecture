package ir.wallet.clientInfo.service;

import ir.wallet.clientInfo.dto.APIDto;
import ir.wallet.clientInfo.dto.CardDto;
import ir.wallet.clientInfo.dto.ClientInfoDto;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Service
@AllArgsConstructor
public class ClientInfoServiceImpl implements ClientInfoService {
    private RestTemplateBuilder restTemplatebuilder;
    /*private WebClient webClientOldOne;*/
    private WebClient webClient;
    private APIFeignClient apiFeignClient;
    @Override
    public APIDto testRestTemplate(){
        RestTemplate restTemplate = restTemplatebuilder.build();
       // ResponseEntity<CardDto> card = restTemplate.getForEntity("http://localhost:8081/api/cards/simpleCard", CardDto.class);
        ResponseEntity<CardDto> card = restTemplate.getForEntity("http://CARD-SERVICE/api/cards/simpleCard", CardDto.class);
        ClientInfoDto clientInfoDto = new ClientInfoDto(1L,
                "Beh","az","tomorrow morning","sal",
                "here", Collections.singletonList(card.getBody().getCardNumber()));
        APIDto apiDto = new APIDto(card.getBody(),clientInfoDto);
        return apiDto;
    }
    //old one method with out oauth call
/*    @Override
    public APIDto testWebClient(){
        CardDto card = webClientOldOne.get().uri("http://localhost:8081/api/cards/simpleCard")
      //  CardDto card = webClient.get().uri("http://CARD-SERVICE/api/cards/simpleCard")
                .retrieve().bodyToMono(CardDto.class).block();
        ClientInfoDto clientInfoDto = new ClientInfoDto(1L,
                "Beh","az","tomorrow morning","sal",
                "here", Collections.singletonList(card.getCardNumber()));
        APIDto apiDto = new APIDto(card,clientInfoDto);
        return apiDto;
    }*/
    @Override
    public APIDto testWebClientNew(OAuth2AuthorizedClient authorizedClient){
        CardDto card =
                webClient
                        .get()
                        .uri("http://localhost:8081/api/cards/simpleCard")
                        .attributes(ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(authorizedClient))
                .retrieve().bodyToMono(CardDto.class).block();
        ClientInfoDto clientInfoDto = new ClientInfoDto(1L,
                "Beh","az","tomorrow morning","sal",
                "here", Collections.singletonList(card.getCardNumber()));
        APIDto apiDto = new APIDto(card,clientInfoDto);
        return apiDto;
    }
    @Override
    public APIDto testFeignClient(String bearerToken){
        CardDto card =  apiFeignClient.getSimpleCard(bearerToken);
        ClientInfoDto clientInfoDto = new ClientInfoDto(1L,
                "Beh","az","tomorrow morning","sal",
                "here", Collections.singletonList(card.getCardNumber()));
        APIDto apiDto = new APIDto(card,clientInfoDto);
        return apiDto;
    }
}
