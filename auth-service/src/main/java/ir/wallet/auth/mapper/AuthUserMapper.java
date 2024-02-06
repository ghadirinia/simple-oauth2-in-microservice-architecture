package ir.wallet.auth.mapper;

import ir.wallet.auth.dto.AuthUserDto;
import ir.wallet.auth.model.AuthUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthUserMapper {
    @Mapping(source="userEntity.id",target="id")
    @Mapping(source="userEntity.username",target="username")
    @Mapping(source="token",target="token")
    @Mapping(target="password",ignore = true)
    AuthUserDto toAuthUserDto(AuthUserEntity userEntity, String token);
    @Mapping(source="encodedPassword",target="password")
    AuthUserEntity toAuthUserEntity(AuthUserDto authUserDto, String encodedPassword);
}
