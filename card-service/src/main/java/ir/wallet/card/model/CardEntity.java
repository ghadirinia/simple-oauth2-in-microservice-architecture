package ir.wallet.card.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CardEntity {
    private Long id;
    private String cardNumber;
    private String issueDate;
    private String expirationDate;
    private String user;
}
