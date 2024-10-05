package com.enigmacamp.merchantbank.service;

import com.enigmacamp.merchantbank.entity.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    Role getOrCreate(Role role);
}
