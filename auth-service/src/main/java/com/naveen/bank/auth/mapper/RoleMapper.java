package com.naveen.bank.auth.mapper;

import com.naveen.bank.auth.dto.response.RoleResponse;
import com.naveen.bank.auth.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleResponse toResponse(Role role);

}