package com.enigmacamp.merchantbank.service.impl;

import com.enigmacamp.merchantbank.entity.Role;
import com.enigmacamp.merchantbank.repository.RoleRepository;
import com.enigmacamp.merchantbank.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getOrCreate(Role role) {
        Optional<Role> findRole = this.roleRepository.findByName(role.getName());
        return findRole.orElseGet(() -> this.roleRepository.save(role));
    }
}
