package com.naveen.bank.auth.service;

import com.naveen.bank.auth.dto.request.RegisterRequest;
import com.naveen.bank.auth.dto.response.UserResponse;
import com.naveen.bank.auth.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponse createUser(RegisterRequest request);

    UserResponse getUserById(UUID userId);

    UserResponse getUserByUsername(String username);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(UUID userId,
                            RegisterRequest request);

    void changePassword(UUID userId,
                        String oldPassword,
                        String newPassword);

    UserResponse assignRoles(UUID userId,
                             List<UUID> roleIds);

    void lockUser(UUID userId);

    void unlockUser(UUID userId);

    void enableUser(UUID userId);

    void disableUser(UUID userId);

    void deleteUser(UUID userId);
}