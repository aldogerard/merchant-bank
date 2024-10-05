package com.enigmacamp.merchantbank.service;

import com.enigmacamp.merchantbank.entity.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {
    AppUser loadUserByUserId(String id);
    void deleteUserById(String id);
}
