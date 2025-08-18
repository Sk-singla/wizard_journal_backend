package com.samarth.wizardjournal.wizardjournal.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.Schema;
import com.google.genai.types.Type;
import com.samarth.wizardjournal.wizardjournal.dto.*;
import com.samarth.wizardjournal.wizardjournal.enums.BackgroundType;
import com.samarth.wizardjournal.wizardjournal.enums.CustomColors;
import com.samarth.wizardjournal.wizardjournal.utils.GlobalUtils;
import com.samarth.wizardjournal.wizardjournal.utils.PromptUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class GeminiAiRepository implements AiRepository {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${gemini.api.key}")
    private String apiKey;

    private static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemma-3n-e2b-it:generateContent";
    private final ObjectMapper objectMapper;
    private final GlobalUtils globalUtils;
    private final PromptUtils promptUtils;
    private final Client client = new Client();

    @Override
    public AiJournalResponse generateJournal(GenerateJournalRequestDto generateJournalRequestDto) throws JsonProcessingException, InternalError {
        var resp = client.models.generateContent(
                "gemini-2.0-flash-lite",
                promptUtils.buildPrompt(generateJournalRequestDto),
                GenerateContentConfig.builder()
                        .responseMimeType("application/json")
                        .responseSchema(
                                Schema.builder()
                                        .type(Type.Known.OBJECT)
                                        .properties(
                                                Map.of(
                                                        "title", Schema.builder().type(Type.Known.STRING).description("title of journal story").build(),
                                                        "content", Schema.builder().type(Type.Known.STRING).description("rewritten journal story").build(),
                                                        "backgroundInfo", Schema.builder()
                                                                .type(Type.Known.OBJECT)
                                                                .description("background info")
                                                                .properties(Map.of(
                                                                        "type", Schema.builder()
                                                                                .type(Type.Known.STRING)
                                                                                .description("background type")
                                                                                .format("enum")
                                                                                .enum_(globalUtils.getEnumNames(BackgroundType.class))
                                                                                .build(),
                                                                        "primaryColor", Schema.builder()
                                                                                .type(Type.Known.STRING)
                                                                                .description("primary color")
                                                                                .format("enum")
                                                                                .enum_(globalUtils.getEnumNames(CustomColors.class))
                                                                                .build(),
                                                                        "secondaryColor", Schema.builder()
                                                                                .type(Type.Known.STRING)
                                                                                .description("secondary color if gradient type, null if not applicable")
                                                                                .format("enum")
                                                                                .enum_(globalUtils.getEnumNames(CustomColors.class))
                                                                                .build(),
                                                                        "gradientAngle", Schema.builder()
                                                                                .type(Type.Known.NUMBER)
                                                                                .description("angle, nullable")
                                                                                .build()
                                                                ))
                                                                .required("type", "primaryColor")
                                                                .build()
                                                )
                                        )
                                        .required("title", "content", "backgroundInfo")
                        )
                        .build()
        );

        System.out.println(resp.text());

        return objectMapper.readValue(resp.text(), AiJournalResponse.class);
    }
}
