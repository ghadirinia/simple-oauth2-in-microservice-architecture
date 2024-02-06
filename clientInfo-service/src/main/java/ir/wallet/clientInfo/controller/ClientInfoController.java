package ir.wallet.clientInfo.controller;

import ir.wallet.clientInfo.dto.APIDto;
import ir.wallet.clientInfo.dto.ClientInfoDto;
import ir.wallet.clientInfo.model.ClientInfoEntity;
import ir.wallet.clientInfo.service.ClientInfoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Collections;

@RestController
@AllArgsConstructor
@RequestMapping("api/clientInfo")
public class ClientInfoController {
    private ClientInfoService clientInfoService;


    @GetMapping("/simpleInfo")
    public ResponseEntity<ClientInfoDto> getInfo(){
        ClientInfoDto clientInfo = new ClientInfoDto(1L,"Marzieh","Ghadirinia","2023","father"
                ,"home", Collections.singletonList("Pasargad Card"));
        return ResponseEntity.ok(clientInfo);
    }

    @GetMapping("/testRestTemplate")
    public ResponseEntity<APIDto> testRestTemplate(){
        APIDto apiDto = clientInfoService.testRestTemplate();
        return new ResponseEntity<APIDto>(apiDto, HttpStatus.OK);
    }
    @GetMapping("/testWebClient")
    public ResponseEntity<APIDto> testWebClient(@RegisteredOAuth2AuthorizedClient("client-internal") OAuth2AuthorizedClient authorizedClient){
        APIDto apiDto = clientInfoService.testWebClientNew(authorizedClient);
        return new ResponseEntity<APIDto>(apiDto, HttpStatus.OK);
    }
    @GetMapping("/testFeignClient")
    public ResponseEntity<APIDto> testFeignClient(@RequestHeader("Authorization")String bearerToken){
        //can write code to check bearer and its verification
        APIDto apiDto = clientInfoService.testFeignClient(bearerToken);
        return new ResponseEntity<APIDto>(apiDto, HttpStatus.OK);
    }
}
