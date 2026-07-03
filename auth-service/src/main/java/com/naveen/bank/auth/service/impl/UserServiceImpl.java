package com.naveen.bank.auth.service.impl;

import com.naveen.bank.auth.dto.request.RegisterRequest;
import com.naveen.bank.auth.dto.response.UserResponse;
import com.naveen.bank.auth.entity.Role;
import com.naveen.bank.auth.entity.User;
import com.naveen.bank.auth.exception.UserAlreadyExistsException;
import com.naveen.bank.auth.exception.UserNotFoundException;
import com.naveen.bank.auth.mapper.UserMapper;
import com.naveen.bank.auth.producer.UserEventProducer;
import com.naveen.bank.auth.repository.RoleRepository;
import com.naveen.bank.auth.repository.UserRepository;
import com.naveen.bank.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final UserEventProducer userEventProducer;

    @Override
    public UserResponse createUser(RegisterRequest request) {

        log.info("Creating User : {}", request.getUsername());

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists.");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists.");
        }

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // No roles assigned during registration
        user.setRoles(new HashSet<>());

        User savedUser = userRepository.save(user);

        log.info("User Created Successfully : {}", savedUser.getUsername());

        // Kafka Event will be added later
        // userEventProducer.publishUserCreated(savedUser);

        return userMapper.toResponse(savedUser);
    }
    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(UUID id) {

        log.info("Fetching User Id : {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User Not Found : " + id));

        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserByUsername(String username) {

        log.info("Fetching User : {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User Not Found : " + username));

        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {

        log.info("Fetching All Users");

        return userRepository.findAll().stream().map(userMapper::toResponse).toList();
    }

    @Override
    public UserResponse updateUser(UUID id, RegisterRequest request) {

        log.info("Updating User : {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User Not Found : " + id));

        user.setFirstName(request.getFirstName());

        user.setLastName(request.getLastName());

        user.setEmail(request.getEmail());

        user.setMobile(request.getMobile());

        User updatedUser = userRepository.save(user);

        log.info("User Updated Successfully");

        return userMapper.toResponse(updatedUser);
    }

    @Override
    public void changePassword(UUID userId,
                               String oldPassword,
                               String newPassword) {

        log.info("Changing Password for User : {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User Not Found : " + userId));

        if (!passwordEncoder.matches(
                oldPassword,
                user.getPassword())) {

            throw new RuntimeException("Old Password is Incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);

        log.info("Password Changed Successfully");
    }

    @Override
    public UserResponse assignRoles(UUID userId,
                                    List<UUID> roleIds) {

        log.info("Assigning Roles to User : {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User Not Found : " + userId));

        Set<Role> roles = new HashSet<>();

        for (UUID roleId : roleIds) {

            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Role Not Found : " + roleId));

            roles.add(role);
        }

        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        log.info("Roles Assigned Successfully");

        return userMapper.toResponse(savedUser);
    }

    @Override
    public void lockUser(UUID userId) {

        log.info("Locking User : {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User Not Found : " + userId));

        user.setAccountNonLocked(false);

        userRepository.save(user);

        log.info("User Locked Successfully");
    }

    @Override
    public void unlockUser(UUID userId) {

        log.info("Unlocking User : {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User Not Found : " + userId));

        user.setAccountNonLocked(true);

        userRepository.save(user);

        log.info("User Unlocked Successfully");
    }

    @Override
    public void enableUser(UUID userId) {

        log.info("Enabling User : {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User Not Found : " + userId));

        user.setEnabled(true);

        userRepository.save(user);

        log.info("User Enabled Successfully");
    }

    @Override
    public void disableUser(UUID userId) {

        log.info("Disabling User : {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User Not Found : " + userId));

        user.setEnabled(false);

        userRepository.save(user);

        log.info("User Disabled Successfully");
    }

    @Override
    public void deleteUser(UUID userId) {

        log.info("Deleting User : {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User Not Found : " + userId));

        userRepository.delete(user);

        log.info("User Deleted Successfully");
    }

}
