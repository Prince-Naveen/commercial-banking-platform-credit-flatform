package com.naveen.bank.auth.repository;

import com.naveen.bank.auth.entity.Role;
import com.naveen.bank.auth.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByRoleName(RoleType roleName);

    Boolean existsByRoleName(RoleType roleName);

}