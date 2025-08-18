package com.samarth.wizardjournal.wizardjournal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupRequestDto {
    private String name;
    private String email;
    private String password;
}
