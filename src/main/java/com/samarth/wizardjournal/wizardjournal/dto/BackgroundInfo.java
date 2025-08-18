package com.samarth.wizardjournal.wizardjournal.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samarth.wizardjournal.wizardjournal.enums.BackgroundType;
import com.samarth.wizardjournal.wizardjournal.enums.CustomColors;
import jakarta.persistence.AttributeConverter;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BackgroundInfo {
    private BackgroundType type;
    private CustomColors primaryColor;
    private CustomColors secondaryColor;
    private Float gradientAngle;
}
