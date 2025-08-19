package com.samarth.wizardjournal.wizardjournal.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.samarth.wizardjournal.wizardjournal.dto.GenerateJournalRequestDto;
import com.samarth.wizardjournal.wizardjournal.dto.JournalDto;
import com.samarth.wizardjournal.wizardjournal.services.JournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
@RequiredArgsConstructor
public class JournalController {
    private final JournalService journalService;

    @PostMapping("/generate")
    public ResponseEntity<JournalDto> generateJournal(@RequestBody GenerateJournalRequestDto request) throws JsonProcessingException {
        return ResponseEntity.ok(journalService.generateJournal(request));
    }

    @GetMapping("/all")
    public ResponseEntity<List<JournalDto>> getAllJournals() {
        return ResponseEntity.ok(journalService.getAllJournals());
    }

    @PostMapping("/insert")
    public ResponseEntity<JournalDto> insertJournal(@RequestBody JournalDto journalDto) {
        return ResponseEntity.ok(journalService.insertJournal(journalDto));
    }

    @PostMapping("/insertMany")
    public ResponseEntity<List<JournalDto>> insertJournals(@RequestBody List<JournalDto> journalDtos) {
        return ResponseEntity.ok(journalService.insertJournals(journalDtos));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteJournal(@PathVariable Integer id) {
        journalService.deleteJournalById(id);
        return ResponseEntity.noContent().build();
    }
    
}
