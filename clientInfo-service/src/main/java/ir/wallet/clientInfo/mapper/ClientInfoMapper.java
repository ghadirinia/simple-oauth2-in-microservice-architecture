package ir.wallet.clientInfo.mapper;

import ir.wallet.clientInfo.dto.ClientInfoDto;
import ir.wallet.clientInfo.model.ClientInfoEntity;

public class ClientInfoMapper {
    public static ClientInfoDto mapClientInfoToDto(ClientInfoEntity clientInfo){
        ClientInfoDto clientInfoDto = new ClientInfoDto(clientInfo.getId(),
                clientInfo.getFirstName(), clientInfo.getLastName(),
                clientInfo.getBirthDate(), clientInfo.getFatherName(),
                clientInfo.getAddress(), clientInfo.getCardNumbers()
        );
        return clientInfoDto;
    }
    public static ClientInfoEntity mapClientInfoDtoToEntity(ClientInfoDto clientInfo){
        ClientInfoEntity client = new ClientInfoEntity(clientInfo.getId(),
                clientInfo.getFirstName(), clientInfo.getLastName(),
                clientInfo.getBirthDate(), clientInfo.getFatherName(),
                clientInfo.getAddress(), clientInfo.getCardNumbers()
        );
        return client;
    }
}
