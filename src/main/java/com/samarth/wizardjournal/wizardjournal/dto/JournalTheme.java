package com.samarth.wizardjournal.wizardjournal.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Data
public class JournalTheme {
    private String displayName;
    private String description;


}
