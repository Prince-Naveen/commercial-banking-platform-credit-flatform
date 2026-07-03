package com.naveen.bank.auth.service.impl;

import com.naveen.bank.auth.dto.response.RoleResponse;
import com.naveen.bank.auth.entity.Permission;
import com.naveen.bank.auth.entity.Role;
import com.naveen.bank.auth.enums.RoleType;
import com.naveen.bank.auth.exception.PermissionNotFoundException;
import com.naveen.bank.auth.exception.RoleNotFoundException;
import com.naveen.bank.auth.mapper.RoleMapper;
import com.naveen.bank.auth.repository.PermissionRepository;
import com.naveen.bank.auth.repository.RoleRepository;
import com.naveen.bank.auth.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleResponse createRole(Role role) {

        log.info("Creating Role : {}", role.getRoleName());

        Role savedRole = roleRepository.save(role);

        log.info("Role Created Successfully");

        return roleMapper.toResponse(savedRole);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse getRoleById(UUID id) {

        log.info("Fetching Role Id : {}", id);

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new RoleNotFoundException(
                                "Role Not Found : " + id));

        return roleMapper.toResponse(role);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse getRoleByName(RoleType roleType) {

        log.info("Fetching Role : {}", roleType);

        Role role = roleRepository.findByRoleName(roleType)
                .orElseThrow(() ->
                        new RoleNotFoundException(
                                "Role Not Found : " + roleType));

        return roleMapper.toResponse(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> getAllRoles() {

        log.info("Fetching All Roles");

        return roleRepository.findAll().stream().map(roleMapper::toResponse).toList();
    }

    @Override
    public RoleResponse updateRole(UUID id, Role role) {

        log.info("Updating Role : {}", id);

        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() ->
                        new RoleNotFoundException(
                                "Role Not Found : " + id));

        existingRole.setRoleName(role.getRoleName());

        existingRole.setDescription(role.getDescription());

        Role updatedRole = roleRepository.save(existingRole);

        log.info("Role Updated Successfully");

        return roleMapper.toResponse(updatedRole);
    }

    @Override
    public RoleResponse assignPermissions(UUID roleId,
                                          List<UUID> permissionIds) {

        log.info("Assigning Permissions to Role : {}", roleId);

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() ->
                        new RoleNotFoundException(
                                "Role Not Found : " + roleId));

        Set<Permission> permissions = new HashSet<>();

        for (UUID permissionId : permissionIds) {

            Permission permission =
                    permissionRepository.findById(permissionId)
                            .orElseThrow(() ->
                                    new PermissionNotFoundException(
                                            "Permission Not Found : "
                                                    + permissionId));

            permissions.add(permission);
        }

        role.setPermissions(permissions);

        Role savedRole = roleRepository.save(role);

        log.info("Permissions Assigned Successfully");

        return roleMapper.toResponse(savedRole);
    }

    @Override
    public void deleteRole(UUID id) {

        log.info("Deleting Role : {}", id);

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new RoleNotFoundException(
                                "Role Not Found : " + id));

        roleRepository.delete(role);

        log.info("Role Deleted Successfully");
    }

}