package ir.wallet.clientInfo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class ClientInfoDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String fatherName;
    private String address;
    private List<String> cardNumbers;
}
