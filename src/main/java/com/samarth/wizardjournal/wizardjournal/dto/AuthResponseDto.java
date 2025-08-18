package com.samarth.wizardjournal.wizardjournal.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthResponseDto {
    private String token;
    private String refreshToken;
}
