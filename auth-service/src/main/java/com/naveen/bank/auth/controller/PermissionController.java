package com.naveen.bank.auth.controller;

import com.naveen.bank.auth.dto.response.PermissionResponse;
import com.naveen.bank.auth.entity.Permission;
import com.naveen.bank.auth.enums.PermissionType;
import com.naveen.bank.auth.service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    /**
     * Create Permission
     */
    @PostMapping
    public ResponseEntity<PermissionResponse> createPermission(
            @Valid @RequestBody Permission permission) {

        return ResponseEntity.ok(
                permissionService.createPermission(permission));
    }

    /**
     * Get Permission By Id
     */
    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponse> getPermissionById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                permissionService.getPermissionById(id));
    }

    /**
     * Get Permission By Name
     */
    @GetMapping("/name/{permissionName}")
    public ResponseEntity<PermissionResponse> getPermissionByName(
            @PathVariable PermissionType permissionName) {

        return ResponseEntity.ok(
                permissionService.getPermissionByName(permissionName));
    }

    /**
     * Get All Permissions
     */
    @GetMapping
    public ResponseEntity<List<PermissionResponse>> getAllPermissions() {

        return ResponseEntity.ok(
                permissionService.getAllPermissions());
    }

    /**
     * Update Permission
     */
    @PutMapping("/{id}")
    public ResponseEntity<PermissionResponse> updatePermission(
            @PathVariable UUID id,
            @Valid @RequestBody Permission permission) {

        return ResponseEntity.ok(
                permissionService.updatePermission(id, permission));
    }

    /**
     * Delete Permission
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePermission(
            @PathVariable UUID id) {

        permissionService.deletePermission(id);

        return ResponseEntity.ok(
                "Permission Deleted Successfully");
    }

}