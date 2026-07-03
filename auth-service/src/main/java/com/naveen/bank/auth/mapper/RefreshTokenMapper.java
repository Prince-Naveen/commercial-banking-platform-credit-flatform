package com.naveen.bank.auth.mapper;

import com.naveen.bank.auth.dto.response.RefreshTokenResponse;
import com.naveen.bank.auth.entity.RefreshToken;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RefreshTokenMapper {

    @Mapping(target = "accessToken", ignore = true)
    @Mapping(target = "refreshToken", source = "token")
    RefreshTokenResponse toResponse(RefreshToken refreshToken);

}