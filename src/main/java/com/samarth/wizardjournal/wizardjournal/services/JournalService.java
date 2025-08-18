package com.samarth.wizardjournal.wizardjournal.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.samarth.wizardjournal.wizardjournal.dto.GenerateJournalRequestDto;
import com.samarth.wizardjournal.wizardjournal.dto.JournalDto;
import com.samarth.wizardjournal.wizardjournal.models.Journal;
import com.samarth.wizardjournal.wizardjournal.models.User;
import com.samarth.wizardjournal.wizardjournal.repository.AiRepository;
import com.samarth.wizardjournal.wizardjournal.repository.JournalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JournalService {
    private final AiRepository aiRepository;
    private final JournalRepository journalRepository;

    // TODO: RATE LIMITING

    public JournalDto generateJournal(GenerateJournalRequestDto request) throws JsonProcessingException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var aiJournalResponse = aiRepository.generateJournal(request);
        return JournalDto.fromJournal(journalRepository.save(
                new Journal(
                        aiJournalResponse.getTitle(),
                        aiJournalResponse.getContent(),
                        System.currentTimeMillis(),
                        request.getTheme(),
                        aiJournalResponse.getBackgroundInfo(),
                        user
                )));
    }

    public List<JournalDto> getAllJournals() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return journalRepository.findAllForUserId(user.getId()).stream()
                .map(JournalDto::fromJournal)
                .toList();
    }


}
