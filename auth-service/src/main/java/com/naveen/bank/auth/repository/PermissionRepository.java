package com.naveen.bank.auth.repository;

import com.naveen.bank.auth.entity.Permission;
import com.naveen.bank.auth.enums.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {

    Optional<Permission> findByPermissionName(PermissionType permissionName);

    Boolean existsByPermissionName(PermissionType permissionName);

}