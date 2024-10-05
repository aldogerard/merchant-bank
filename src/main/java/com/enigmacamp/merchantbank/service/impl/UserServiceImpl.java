package com.enigmacamp.merchantbank.service.impl;

import com.enigmacamp.merchantbank.constant.enums.ERole;
import com.enigmacamp.merchantbank.entity.AppUser;
import com.enigmacamp.merchantbank.entity.Role;
import com.enigmacamp.merchantbank.entity.User;
import com.enigmacamp.merchantbank.repository.UserRepository;
import com.enigmacamp.merchantbank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // VERIFIKASI JWT
    @Override
    public AppUser loadUserByUserId(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));
        return AppUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole().getName())
                .build();
    }

    @Override
    public void deleteUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));
        userRepository.deleteById(user.getId());
    }

    // VERIFIKASI AUTHENTICATE LOGIN
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));

        return AppUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole().getName())
                .build();
    }


}

