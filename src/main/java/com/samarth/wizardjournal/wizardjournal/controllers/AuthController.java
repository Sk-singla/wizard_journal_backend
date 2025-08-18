package com.samarth.wizardjournal.wizardjournal.controllers;

import com.samarth.wizardjournal.wizardjournal.dto.LoginRequestDto;
import com.samarth.wizardjournal.wizardjournal.dto.LoginResponseDto;
import com.samarth.wizardjournal.wizardjournal.dto.SignupRequestDto;
import com.samarth.wizardjournal.wizardjournal.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        LoginResponseDto response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<LoginResponseDto> signup(@RequestBody SignupRequestDto signupRequest) {
        LoginResponseDto response = authService.signup(signupRequest);
        return ResponseEntity.ok(response);
    }
}
