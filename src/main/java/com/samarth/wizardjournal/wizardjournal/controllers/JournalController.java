package com.samarth.wizardjournal.wizardjournal.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.samarth.wizardjournal.wizardjournal.dto.GenerateJournalRequestDto;
import com.samarth.wizardjournal.wizardjournal.dto.JournalDto;
import com.samarth.wizardjournal.wizardjournal.services.JournalService;
import com.samarth.wizardjournal.wizardjournal.services.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/journal")
@RequiredArgsConstructor
public class JournalController {
    private final JournalService journalService;
    private final RateLimiterService rateLimiterService;

    @PostMapping("/generate")
    public ResponseEntity<JournalDto> generateJournal(HttpServletRequest httpRequest, @RequestBody GenerateJournalRequestDto request) throws JsonProcessingException {
        rateLimiterService.limitPerUser(httpRequest);
        return ResponseEntity.ok(journalService.generateJournal(request));
    }

    @GetMapping("/all")
    public ResponseEntity<List<JournalDto>> getAllJournals(HttpServletRequest httpRequest) {
        rateLimiterService.limitPerIp(httpRequest, 30);
        return ResponseEntity.ok(journalService.getAllJournals());
    }

    @PostMapping("/insert")
    public ResponseEntity<JournalDto> insertJournal(HttpServletRequest httpRequest, @RequestBody JournalDto journalDto) {
        rateLimiterService.limitPerIp(httpRequest, 5);
        return ResponseEntity.ok(journalService.insertJournal(journalDto));
    }

    @PostMapping("/insertMany")
    public ResponseEntity<List<JournalDto>> insertJournals(HttpServletRequest httpRequest, @RequestBody List<JournalDto> journalDtos) {
        rateLimiterService.limitPerIp(httpRequest, 5);
        return ResponseEntity.ok(journalService.insertJournals(journalDtos));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteJournal(HttpServletRequest httpRequest, @PathVariable Integer id) {
        rateLimiterService.limitPerIp(httpRequest, 5);

        journalService.deleteJournalById(id);
        return ResponseEntity.noContent().build();
    }
    
}
