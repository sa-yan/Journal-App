package com.sayan.journalapp.controller;

import com.sayan.journalapp.Entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry journalEntry) {
        return true;
    }

    @GetMapping
    public List<JournalEntry> getJournalEntries() {
        return null;
    }

    @GetMapping("/id/{id}")
    public JournalEntry getJournalEntryById(@PathVariable Long id) {
        return null;

    }

    @DeleteMapping("/id/{id}")
    public JournalEntry deleteEntryById(@PathVariable Long id) {
        return null;

    }

    @PutMapping("/id/{id}")
    public JournalEntry updateEntry(@RequestBody JournalEntry journalEntry,
                                    @PathVariable Long id) {
        return null;

    }
}
