package com.naveen.bank.auth.mapper;

import com.naveen.bank.auth.dto.response.PermissionResponse;
import com.naveen.bank.auth.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    @Mapping(target = "permissionName",
            expression = "java(permission.getPermissionName().name())")
    PermissionResponse toResponse(Permission permission);

}