package com.naveen.bank.auth.controller;

import com.naveen.bank.auth.dto.request.RegisterRequest;
import com.naveen.bank.auth.dto.response.UserResponse;
import com.naveen.bank.auth.entity.User;
import com.naveen.bank.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Create User
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(
                userService.createUser(request));
    }

    /**
     * Get User By Id
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                userService.getUserById(id));
    }

    /**
     * Get User By Username
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(
            @PathVariable String username) {

        return ResponseEntity.ok(
                userService.getUserByUsername(username));
    }

    /**
     * Get All Users
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        return ResponseEntity.ok(
                userService.getAllUsers());
    }

    /**
     * Update User
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(
                userService.updateUser(id, request));
    }

    /**
     * Assign Roles
     */
    @PutMapping("/{userId}/roles")
    public ResponseEntity<UserResponse> assignRoles(
            @PathVariable UUID userId,
            @RequestBody List<UUID> roleIds) {

        return ResponseEntity.ok(
                userService.assignRoles(userId, roleIds));
    }

    /**
     * Change Password
     */
    @PutMapping("/{userId}/change-password")
    public ResponseEntity<String> changePassword(
            @PathVariable UUID userId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {

        userService.changePassword(
                userId,
                oldPassword,
                newPassword);

        return ResponseEntity.ok("Password Changed Successfully");
    }

    /**
     * Lock User
     */
    @PutMapping("/{userId}/lock")
    public ResponseEntity<String> lockUser(
            @PathVariable UUID userId) {

        userService.lockUser(userId);

        return ResponseEntity.ok("User Locked Successfully");
    }

    /**
     * Unlock User
     */
    @PutMapping("/{userId}/unlock")
    public ResponseEntity<String> unlockUser(
            @PathVariable UUID userId) {

        userService.unlockUser(userId);

        return ResponseEntity.ok("User Unlocked Successfully");
    }

    /**
     * Enable User
     */
    @PutMapping("/{userId}/enable")
    public ResponseEntity<String> enableUser(
            @PathVariable UUID userId) {

        userService.enableUser(userId);

        return ResponseEntity.ok("User Enabled Successfully");
    }

    /**
     * Disable User
     */
    @PutMapping("/{userId}/disable")
    public ResponseEntity<String> disableUser(
            @PathVariable UUID userId) {

        userService.disableUser(userId);

        return ResponseEntity.ok("User Disabled Successfully");
    }

    /**
     * Delete User
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(
            @PathVariable UUID userId) {

        userService.deleteUser(userId);

        return ResponseEntity.ok("User Deleted Successfully");
    }

}