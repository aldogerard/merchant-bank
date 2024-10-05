package com.enigmacamp.merchantbank.service;

import com.enigmacamp.merchantbank.dto.request.AuthRequest;
import com.enigmacamp.merchantbank.dto.response.LoginResponse;
import com.enigmacamp.merchantbank.dto.response.RegisterResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    RegisterResponse register(AuthRequest authRequest);
    RegisterResponse registerCustomer(AuthRequest authRequest);
    LoginResponse login(AuthRequest authRequest);
}
