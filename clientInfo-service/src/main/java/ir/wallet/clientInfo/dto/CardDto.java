package ir.wallet.clientInfo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CardDto {
    private Long id;
    private String cardNumber;
    private String issueDate;
    private String expirationDate;
    private String user;
}
