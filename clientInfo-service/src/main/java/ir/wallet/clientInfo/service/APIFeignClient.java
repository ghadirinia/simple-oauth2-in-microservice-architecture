package ir.wallet.clientInfo.service;

import ir.wallet.clientInfo.dto.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/*
@FeignClient(url="http://localhost:8081",value="CARD-SERVICE")
*/
@FeignClient("CARD-SERVICE")
public interface APIFeignClient {
    @GetMapping("api/cards/simpleCard")
    CardDto getSimpleCard(@RequestHeader( value = "Authorization", required = true) String bearerToken);
}

/*
 @FeignClient(name="someService")
public interface ItemFeignClientService {
@RequestMapping(method = RequestMethod.GET, value = "/items/search/findByName?name={name}")
List<Item> findItemsByName(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, @PathVariable("name") String name);

 */