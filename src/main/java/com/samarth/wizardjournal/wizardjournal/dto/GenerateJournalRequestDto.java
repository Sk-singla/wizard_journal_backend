package com.samarth.wizardjournal.wizardjournal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class GenerateJournalRequestDto {
    private String userInput;
    private JournalTheme theme;
}
