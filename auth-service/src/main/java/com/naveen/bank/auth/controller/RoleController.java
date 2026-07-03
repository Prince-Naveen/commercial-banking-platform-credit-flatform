package com.naveen.bank.auth.controller;

import com.naveen.bank.auth.dto.response.RoleResponse;
import com.naveen.bank.auth.entity.Role;
import com.naveen.bank.auth.enums.RoleType;
import com.naveen.bank.auth.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    /**
     * Create Role
     */
    @PostMapping
    public ResponseEntity<RoleResponse> createRole(
            @Valid @RequestBody Role role) {

        return ResponseEntity.ok(
                roleService.createRole(role));
    }

    /**
     * Get Role By Id
     */
    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                roleService.getRoleById(id));
    }

    /**
     * Get Role By Name
     */
    @GetMapping("/name/{roleName}")
    public ResponseEntity<RoleResponse> getRoleByName(
            @PathVariable RoleType roleName) {

        return ResponseEntity.ok(
                roleService.getRoleByName(roleName));
    }

    /**
     * Get All Roles
     */
    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {

        return ResponseEntity.ok(
                roleService.getAllRoles());
    }

    /**
     * Update Role
     */
    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> updateRole(
            @PathVariable UUID id,
            @Valid @RequestBody Role role) {

        return ResponseEntity.ok(
                roleService.updateRole(id, role));
    }

    /**
     * Assign Permissions To Role
     */
    @PutMapping("/{roleId}/permissions")
    public ResponseEntity<RoleResponse> assignPermissions(
            @PathVariable UUID roleId,
            @RequestBody List<UUID> permissionIds) {

        return ResponseEntity.ok(
                roleService.assignPermissions(
                        roleId,
                        permissionIds));
    }

    /**
     * Delete Role
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(
            @PathVariable UUID id) {

        roleService.deleteRole(id);

        return ResponseEntity.ok(
                "Role Deleted Successfully");
    }

}