package com.samarth.wizardjournal.wizardjournal.repository;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.samarth.wizardjournal.wizardjournal.dto.GenerateJournalRequestDto;
import com.samarth.wizardjournal.wizardjournal.dto.AiJournalResponse;

public interface AiRepository {
    AiJournalResponse generateJournal(GenerateJournalRequestDto generateJournalRequestDto) throws JsonProcessingException;
}
