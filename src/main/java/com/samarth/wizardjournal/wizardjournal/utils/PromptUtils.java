package com.samarth.wizardjournal.wizardjournal.utils;

import com.samarth.wizardjournal.wizardjournal.dto.GenerateJournalRequestDto;
import com.samarth.wizardjournal.wizardjournal.dto.JournalTheme;
import org.springframework.stereotype.Component;

@Component
public class PromptUtils {
    public String buildPrompt(GenerateJournalRequestDto request) {
        String userInput = request.getUserInput();
        JournalTheme theme = request.getTheme();

        return String.format(
            "You are a creative writing assistant.\n\n" +
            "I'm providing a personal journal entry text written by the user. Your task is to:\n" +
            "1. Briefly paraphrase the user's input to capture its essence.\n" +
            "2. Reimagine the entry as a short fictional story inspired by the \"%s\" theme.\n\n" +
            "Rewrite Guidelines:\n" +
            "- Use elements from the world of %s. %s\n" +
            "- Map real-world elements to equivalents in this theme.\n" +
            "- Keep the journal's core message and emotion.\n" +
            "- Suggest a matching journal background: type and color(s). It should reflect theme selected by user and user's story.\n\n" +
            "Respond ONLY in JSON in this format:\n" +
            "{\"title\":\"...\",\"content\":\"...\",\"backgroundInfo\":{\"type\":\"...\",\"primaryColor\":...,\"secondaryColor\":...,\"gradientAngle\":...}}\n\n" +
            "User's journal entry text:\n---\n%s\n---",
            theme.getDisplayName(), theme.getDisplayName(), theme.getDescription(), userInput
        );
    }
}
