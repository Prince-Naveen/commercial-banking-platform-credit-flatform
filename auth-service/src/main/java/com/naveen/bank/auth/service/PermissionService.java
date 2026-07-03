package com.naveen.bank.auth.service;

import com.naveen.bank.auth.dto.response.PermissionResponse;
import com.naveen.bank.auth.entity.Permission;
import com.naveen.bank.auth.enums.PermissionType;

import java.util.List;
import java.util.UUID;

public interface PermissionService {

    PermissionResponse createPermission(Permission permission);

    PermissionResponse getPermissionById(UUID id);

    PermissionResponse getPermissionByName(PermissionType permissionType);

    List<PermissionResponse> getAllPermissions();

    PermissionResponse updatePermission(UUID id,
                                        Permission permission);

    void deletePermission(UUID id);

}