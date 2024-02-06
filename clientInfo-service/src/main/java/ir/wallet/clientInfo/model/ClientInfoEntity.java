package ir.wallet.clientInfo.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ClientInfoEntity {
    private Long id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String fatherName;
    private String address;
    private List<String> cardNumbers;
}
