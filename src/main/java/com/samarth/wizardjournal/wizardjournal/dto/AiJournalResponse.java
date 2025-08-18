package com.samarth.wizardjournal.wizardjournal.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;

@JsonDeserialize
@AllArgsConstructor
@Data
public class AiJournalResponse {
    private String title;
    private String content;
    private BackgroundInfo backgroundInfo;
}
