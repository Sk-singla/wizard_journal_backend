package com.samarth.wizardjournal.wizardjournal.controllers;

import com.samarth.wizardjournal.wizardjournal.dto.LoginRequestDto;
import com.samarth.wizardjournal.wizardjournal.dto.AuthResponseDto;
import com.samarth.wizardjournal.wizardjournal.dto.SignupRequestDto;
import com.samarth.wizardjournal.wizardjournal.services.AuthService;
import com.samarth.wizardjournal.wizardjournal.services.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final RateLimiterService rateLimiterService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(HttpServletRequest request, @RequestBody LoginRequestDto loginRequest) {
        rateLimiterService.limitPerIp(request);
        AuthResponseDto response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> signup(HttpServletRequest request, @RequestBody SignupRequestDto signupRequest) {
        rateLimiterService.limitPerIp(request);

        AuthResponseDto response = authService.signup(signupRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDto> refreshToken(HttpServletRequest request, @RequestBody String refreshToken) {
        rateLimiterService.limitPerIp(request);

        AuthResponseDto response = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }

    // ping
    @PostMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }
}
