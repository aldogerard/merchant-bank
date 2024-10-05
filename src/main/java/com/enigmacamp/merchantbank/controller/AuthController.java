package com.enigmacamp.merchantbank.controller;

import com.enigmacamp.merchantbank.base.BaseResponse;
import com.enigmacamp.merchantbank.constant.strings.PathApi;
import com.enigmacamp.merchantbank.dto.request.AuthRequest;
import com.enigmacamp.merchantbank.dto.response.LoginResponse;
import com.enigmacamp.merchantbank.dto.response.RegisterResponse;
import com.enigmacamp.merchantbank.mapper.BaseMapper;
import com.enigmacamp.merchantbank.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PathApi.AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody AuthRequest authRequest) {
        RegisterResponse registerResponse = authService.register(authRequest);
        BaseResponse<?> commonResponse = BaseMapper.mapToBaseResponse("Successfully register", HttpStatus.CREATED.value(), registerResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PostMapping("/signup/customer")
    public ResponseEntity<?> registerCustomer(@RequestBody AuthRequest authRequest) {
        RegisterResponse registerResponse = authService.registerCustomer(authRequest);
        BaseResponse<?> commonResponse = BaseMapper.mapToBaseResponse("Successfully register customer", HttpStatus.CREATED.value(), registerResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        LoginResponse loginResponse = authService.login(authRequest);
        BaseResponse<?> commonResponse = BaseMapper.mapToBaseResponse("Successfully login", HttpStatus.OK.value(), loginResponse);
        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }
}
