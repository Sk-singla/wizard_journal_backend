package com.samarth.wizardjournal.wizardjournal.services;

import com.samarth.wizardjournal.wizardjournal.dto.LoginRequestDto;
import com.samarth.wizardjournal.wizardjournal.dto.AuthResponseDto;
import com.samarth.wizardjournal.wizardjournal.dto.SignupRequestDto;
import com.samarth.wizardjournal.wizardjournal.models.RefreshToken;
import com.samarth.wizardjournal.wizardjournal.models.User;
import com.samarth.wizardjournal.wizardjournal.repository.RefreshTokenRepository;
import com.samarth.wizardjournal.wizardjournal.repository.UserRepository;
import com.samarth.wizardjournal.wizardjournal.utils.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RefreshTokenRepository tokenRepository;

    public AuthResponseDto login(LoginRequestDto loginRequestDto) {
        var authToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        var authentication = authenticationManager.authenticate(authToken);

        User user = (User) authentication.getPrincipal();
        return issueTokens(user);
    }

    public AuthResponseDto signup(SignupRequestDto signupRequestDto) {
        if(signupRequestDto.getName() == null || signupRequestDto.getEmail() == null || signupRequestDto.getPassword() == null) {
            throw new IllegalArgumentException("Name, email, and password must not be null");
        }
        if(userRepository.existsByEmail(signupRequestDto.getEmail())) {
            throw new IllegalArgumentException("Email is already in use");
        }

        User user = new User();
        user.setName(signupRequestDto.getName());
        user.setEmail(signupRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));

        user = userRepository.save(user);
        return issueTokens(user);
    }

    public AuthResponseDto refreshToken(String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new IllegalArgumentException("Refresh token must not be null or empty");
        }

        Integer userId = authUtil.getUseridFromToken(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        var refTokenObj = tokenRepository.findByUserIdAndTokenAndRevokedFalse(user.getId(), refreshToken);
        if (refTokenObj.isEmpty()) {
            throw new IllegalArgumentException("Invalid or expired refresh token");
        }

        return issueTokens(user);
    }

    private AuthResponseDto issueTokens(User user) {
        revokeOldTokens(user);

        String accessToken = authUtil.generateAccessToken(user);
        String refreshToken = authUtil.generateRefreshToken(user);

        tokenRepository.save(new RefreshToken(refreshToken, user));

        return new AuthResponseDto(accessToken, refreshToken);
    }

    private void revokeOldTokens(User user) {
        var validTokens = tokenRepository.findByUserIdAndRevokedFalse(user.getId());
        for (var t : validTokens) {
            t.revoke();
        }
        tokenRepository.saveAll(validTokens);
    }
}

