package com.naveen.bank.auth.mapper;

import com.naveen.bank.auth.dto.request.RegisterRequest;
import com.naveen.bank.auth.dto.response.UserResponse;
import com.naveen.bank.auth.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(RegisterRequest request);

    @Mapping(target = "roles", ignore = true)
    UserResponse toResponse(User user);

}