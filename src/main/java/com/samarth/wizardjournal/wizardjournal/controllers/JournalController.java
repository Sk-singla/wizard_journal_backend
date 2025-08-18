package com.samarth.wizardjournal.wizardjournal.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/journal")
public class JournalController {

    // heloo world endpoint
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, World!";
    }
}
