package com.samarth.wizardjournal.wizardjournal.models.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samarth.wizardjournal.wizardjournal.dto.JournalTheme;

public class JournalThemeConverter implements jakarta.persistence.AttributeConverter<JournalTheme, String> {
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public String convertToDatabaseColumn(JournalTheme attribute) {
            try {
                return objectMapper.writeValueAsString(attribute);
            } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Could not serialize JournalTheme", e);
            }
        }

        @Override
        public JournalTheme convertToEntityAttribute(String dbData) {
            try {
                return objectMapper.readValue(dbData, JournalTheme.class);
            } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("Could not deserialize JournalTheme", e);
            }
        }
    }