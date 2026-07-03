package com.naveen.bank.auth.service;

import com.naveen.bank.auth.dto.response.RoleResponse;
import com.naveen.bank.auth.entity.Role;
import com.naveen.bank.auth.enums.RoleType;

import java.util.List;
import java.util.UUID;

public interface RoleService {

    RoleResponse createRole(Role role);

    RoleResponse getRoleById(UUID id);

    RoleResponse getRoleByName(RoleType roleType);

    List<RoleResponse> getAllRoles();

    RoleResponse updateRole(UUID id, Role role);

    RoleResponse assignPermissions(UUID roleId,
                                   List<UUID> permissionIds);

    void deleteRole(UUID id);

}