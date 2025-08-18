package com.samarth.wizardjournal.wizardjournal.models.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samarth.wizardjournal.wizardjournal.dto.BackgroundInfo;
import jakarta.persistence.AttributeConverter;

public class BackgroundInfoConverter implements AttributeConverter<BackgroundInfo, String> {
        private final ObjectMapper objectMapper = new ObjectMapper();


        @Override
        public String convertToDatabaseColumn(BackgroundInfo attribute) {
            try {
                return objectMapper.writeValueAsString(attribute);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Could not serialize BackgroundInfo", e);
            }
        }

        @Override
        public BackgroundInfo convertToEntityAttribute(String dbData) {
            try {
                return objectMapper.readValue(dbData, BackgroundInfo.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Could not deserialize BackgroundInfo", e);
            }
        }
    }
