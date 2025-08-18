package com.samarth.wizardjournal.wizardjournal.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResponseDto {
    private String token;
    private int userId;
}
