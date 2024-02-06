package ir.wallet.card.controller;

import ir.wallet.card.dto.CardDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/cards")
public class CardController {

    @GetMapping("/checkForAuthorizationServerADMINVIEWR")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_VIEWER')")
    public String checkForAuthorizationServerADMINVIEWR(){
        return "It's working ROLE ADMIN and VIEWER!";
    }

    @GetMapping("/checkForAuthorizationServerADMIN")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String checkForAuthorizationServerADMIN(){
        return "It's working ROLE ADMIN!";
    }

    @GetMapping("/checkForAuthorizationServerSCOPE")
    @PreAuthorize("hasAuthority('SCOPE_openid')")
    public String checkForAuthorizationServerSCOPE(){
        return "It's working SCOPE openid!";
    }

    @GetMapping("/simpleCard")
    public ResponseEntity getSimpleCard(){
        CardDto card = new CardDto(1L,"5022291043153962",
                "today","2030","Marzieh");
        return new ResponseEntity(card, HttpStatus.OK);
    }
}
