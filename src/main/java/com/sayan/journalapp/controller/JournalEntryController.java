package com.sayan.journalapp.controller;

import com.sayan.journalapp.Entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private final Map<Long, JournalEntry> journalEntryMap = new HashMap<>();


    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry journalEntry) {
        journalEntryMap.put(journalEntry.getId(), journalEntry);
        return true;
    }

    @GetMapping
    public List<JournalEntry> getJournalEntries() {
        return new ArrayList<>(journalEntryMap.values());
    }

    @GetMapping("/id/{id}")
    public JournalEntry getJournalEntryById(@PathVariable Long id) {
        return journalEntryMap.get(id);
    }

    @DeleteMapping("/id/{id}")
    public JournalEntry deleteEntryById(@PathVariable Long id) {
        return journalEntryMap.remove(id);
    }

    @PutMapping("/id/{id}")
    public JournalEntry updateEntry(@RequestBody JournalEntry journalEntry,
                                    @PathVariable Long id) {
        return journalEntryMap.put(id, journalEntry);
    }
}
