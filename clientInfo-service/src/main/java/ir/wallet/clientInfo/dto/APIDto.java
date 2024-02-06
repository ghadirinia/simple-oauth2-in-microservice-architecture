package ir.wallet.clientInfo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class APIDto {
    private CardDto cardDto;
    private ClientInfoDto clientInfoDto;
}
