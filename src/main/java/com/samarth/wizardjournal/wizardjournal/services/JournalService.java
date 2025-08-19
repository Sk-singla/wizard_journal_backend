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

    public JournalDto insertJournal(JournalDto journalDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Journal journal = Journal.fromJournalDto(journalDto, user);
        return JournalDto.fromJournal(journalRepository.save(journal));
    }

    // insert multiple journals
    public List<JournalDto> insertJournals(List<JournalDto> journalDtos)
    {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Journal> journals = journalDtos.stream()
                .map(journalDto -> Journal.fromJournalDto(journalDto, user))
                .toList();
        return journalRepository.saveAll(journals).stream()
                .map(JournalDto::fromJournal)
                .toList();
    }

    // delete journal by id
    public void deleteJournalById(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("User is not authenticated");
        }

        User user = (User) authentication.getPrincipal();
        Journal journal = journalRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Journal not found or does not belong to the user"));

        journalRepository.delete(journal);
    }
}
