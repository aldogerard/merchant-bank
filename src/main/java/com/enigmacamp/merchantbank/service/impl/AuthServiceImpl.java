package com.enigmacamp.merchantbank.service.impl;

import com.enigmacamp.merchantbank.constant.enums.ERole;
import com.enigmacamp.merchantbank.constant.strings.Message;
import com.enigmacamp.merchantbank.dto.request.AuthRequest;
import com.enigmacamp.merchantbank.dto.response.LoginResponse;
import com.enigmacamp.merchantbank.dto.response.RegisterResponse;
import com.enigmacamp.merchantbank.entity.*;
import com.enigmacamp.merchantbank.repository.UserRepository;
import com.enigmacamp.merchantbank.security.JwtUtil;
import com.enigmacamp.merchantbank.service.AuthService;
import com.enigmacamp.merchantbank.service.CustomerService;
import com.enigmacamp.merchantbank.service.RoleService;
import com.enigmacamp.merchantbank.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final CustomerService customerService;
    private final RoleService roleService;
    private final ValidationUtil validationUtil;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse registerCustomer(AuthRequest authRequest) {
        try {
            this.validationUtil.validate(authRequest);

            Role role = roleService.getOrCreate(Role.builder().name(ERole.ROLE_CUSTOMER).build());

            User user = User.builder()
                    .email(authRequest.getEmail().toLowerCase())
                    .password(this.passwordEncoder.encode(authRequest.getPassword()))
                    .role(role)
                    .build();

            this.userRepository.saveAndFlush(user);

            Customer customer = Customer.builder()
                    .user(user)
                    .createdBy(user.getEmail())
                    .balance(10000000.0) // SET SALDO AWAL
                    .build();
            System.out.println(customer);

            customerService.createCustomer(customer);
            return getRegisterResponse(user);
        }catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, Message.IS_EXIST);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse register(AuthRequest authRequest) {
        try {
            this.validationUtil.validate(authRequest);

            Role role = roleService.getOrCreate(Role.builder().name(ERole.ROLE_ADMIN).build());

            User user = User.builder()
                .email(authRequest.getEmail().toLowerCase())
                .password(this.passwordEncoder.encode(authRequest.getPassword()))
                .role(role)
                .build();

            this.userRepository.saveAndFlush(user);

            return getRegisterResponse(user);
        }catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, Message.IS_EXIST);
        }
    }

    @Override
    public LoginResponse login(AuthRequest authRequest) {
        this.validationUtil.validate(authRequest);

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getEmail().toLowerCase(),
                authRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        AppUser appUser = (AppUser) authenticate.getPrincipal();
        String token = jwtUtil.generateToken(appUser);

        return LoginResponse.builder()
                .token(token)
                .role(appUser.getRole().name())
                .build();
    }

    private RegisterResponse getRegisterResponse(User user){
        return RegisterResponse.builder()
                .email(user.getEmail())
                .role(user.getRole().getName().toString())
                .build();
    }
}
