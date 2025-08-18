package com.samarth.wizardjournal.wizardjournal.services;

import com.samarth.wizardjournal.wizardjournal.dto.LoginRequestDto;
import com.samarth.wizardjournal.wizardjournal.dto.LoginResponseDto;
import com.samarth.wizardjournal.wizardjournal.dto.SignupRequestDto;
import com.samarth.wizardjournal.wizardjournal.models.User;
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

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        try {
            var a = new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());
            var authentication = authenticationManager.authenticate(
                    a
            );

            User user = (User) authentication.getPrincipal();
            String token = authUtil.generateAccessToken(user);
            return new LoginResponseDto(token, user.getId());
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Invalid email or password");
        }
    }

    public LoginResponseDto signup(SignupRequestDto signupRequestDto) {
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

        String token = authUtil.generateAccessToken(user);
        return new LoginResponseDto(token, user.getId());
    }
}

