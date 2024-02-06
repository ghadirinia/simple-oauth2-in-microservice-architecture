package ir.wallet.card.mapper;

import ir.wallet.card.dto.CardDto;
import ir.wallet.card.model.CardEntity;

public class CardMapper {
    public static CardDto mapCardToDto(CardEntity card){
        CardDto dto = new CardDto(
                card.getId(), card.getCardNumber(), card.getIssueDate()
                , card.getExpirationDate(), card.getUser()
        );
        return dto;
    }
    public static CardEntity mapCardToDto(CardDto card){
        CardEntity cardEntity = new CardEntity(
                card.getId(), card.getCardNumber(), card.getIssueDate()
                , card.getExpirationDate(), card.getUser()
        );
        return cardEntity;
    }
}
