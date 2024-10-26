package com.sayan.journalapp.controller;

import com.sayan.journalapp.Entity.JournalEntry;
import com.sayan.journalapp.service.JournalEntryService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
@Slf4j
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(journalEntry);
        return new ResponseEntity<>(journalEntry,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getJournalEntries() {
        return new ResponseEntity<>(journalEntryService.getJournalEntries(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable String id) {
        if (id.length() < 24) {
            return new ResponseEntity<>("Document Not Found!", HttpStatus.NOT_FOUND);
        }
            ObjectId objectId = new ObjectId(id);
            Optional<JournalEntry> journalEntryById = journalEntryService.getJournalEntryById(objectId);

            if (journalEntryById.isPresent()) {
                return new ResponseEntity<>(journalEntryById.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Document Not Found!", HttpStatus.NOT_FOUND);
            }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteEntryById(@PathVariable String id) {
        if (id.length() < 24) {
            return new ResponseEntity<>("Document Not Found!", HttpStatus.NOT_FOUND);
        }
        ObjectId objectId = new ObjectId(id);
        Optional<JournalEntry> journalEntryById = journalEntryService.getJournalEntryById(objectId);

        if (journalEntryById.isPresent()) {
            journalEntryService.deleteEntryById(objectId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Document Not Found!", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateEntry(@RequestBody JournalEntry newEntry,
                                    @PathVariable String id) {
        if(id.length()<24){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ObjectId objectId = new ObjectId(id);
        JournalEntry oldEntry = journalEntryService.getJournalEntryById(objectId).orElse(null);
        if(oldEntry != null) {
            oldEntry.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent()!=null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldEntry.getContent());
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }
}
