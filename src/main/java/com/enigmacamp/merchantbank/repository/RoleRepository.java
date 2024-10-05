package com.enigmacamp.merchantbank.repository;

import com.enigmacamp.merchantbank.constant.enums.ERole;
import com.enigmacamp.merchantbank.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
