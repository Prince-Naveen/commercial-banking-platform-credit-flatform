package com.naveen.bank.auth.service.impl;

import com.naveen.bank.auth.dto.response.PermissionResponse;
import com.naveen.bank.auth.entity.Permission;
import com.naveen.bank.auth.enums.PermissionType;
import com.naveen.bank.auth.exception.PermissionNotFoundException;
import com.naveen.bank.auth.mapper.PermissionMapper;
import com.naveen.bank.auth.repository.PermissionRepository;
import com.naveen.bank.auth.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;

    @Override
    public PermissionResponse createPermission(Permission permission) {

        log.info("Creating Permission : {}",
                permission.getPermissionName());

        Permission savedPermission =
                permissionRepository.save(permission);

        log.info("Permission Created Successfully");

        return permissionMapper.toResponse(savedPermission);
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionResponse getPermissionById(UUID id) {

        log.info("Fetching Permission Id : {}", id);

        Permission permission =
                permissionRepository.findById(id)
                        .orElseThrow(() ->
                                new PermissionNotFoundException(
                                        "Permission Not Found : " + id));

        return permissionMapper.toResponse(permission);
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionResponse getPermissionByName(
            PermissionType permissionType) {

        log.info("Fetching Permission : {}", permissionType);

        Permission permission =
                permissionRepository
                        .findByPermissionName(permissionType)
                        .orElseThrow(() ->
                                new PermissionNotFoundException(
                                        "Permission Not Found : "
                                                + permissionType));

        return permissionMapper.toResponse(permission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionResponse> getAllPermissions() {

        log.info("Fetching All Permissions");

        return permissionRepository.findAll().stream().map(permissionMapper::toResponse).toList();
    }

    @Override
    public PermissionResponse updatePermission(
            UUID id,
            Permission permission) {

        log.info("Updating Permission : {}", id);

        Permission existingPermission =
                permissionRepository.findById(id)
                        .orElseThrow(() ->
                                new PermissionNotFoundException(
                                        "Permission Not Found : " + id));

        existingPermission.setPermissionName(
                permission.getPermissionName());

        existingPermission.setDescription(
                permission.getDescription());

        Permission updatedPermission =
                permissionRepository.save(existingPermission);

        log.info("Permission Updated Successfully");

        return permissionMapper.toResponse(updatedPermission);
    }

    @Override
    public void deletePermission(UUID id) {

        log.info("Deleting Permission : {}", id);

        Permission permission =
                permissionRepository.findById(id)
                        .orElseThrow(() ->
                                new PermissionNotFoundException(
                                        "Permission Not Found : " + id));

        permissionRepository.delete(permission);

        log.info("Permission Deleted Successfully");
    }

}