package com.sayan.journalapp.controller;

import com.sayan.journalapp.Entity.JournalEntry;
import com.sayan.journalapp.Entity.User;
import com.sayan.journalapp.service.JournalEntryService;
import com.sayan.journalapp.service.UserService;
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

    @Autowired
    private UserService userService;

    @PostMapping("/{userName}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry, @PathVariable String userName) {
        try{
            journalEntryService.saveEntry(journalEntry, userName);
            return new ResponseEntity<>(journalEntry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if(all!=null && !all.isEmpty()) {
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
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

    @DeleteMapping("/id/{userName}/{id}")
    public ResponseEntity<?> deleteEntryById(@PathVariable String id, @PathVariable String userName) {
        if (id.length() < 24) {
            return new ResponseEntity<>("Document Not Found!", HttpStatus.NOT_FOUND);
        }
        ObjectId objectId = new ObjectId(id);
        Optional<JournalEntry> journalEntryById = journalEntryService.getJournalEntryById(objectId);

        if (journalEntryById.isPresent()) {
            journalEntryService.deleteEntryById(objectId, userName);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Document Not Found!", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/id/{userName}/{id}")
    public ResponseEntity<?> updateEntry(@RequestBody JournalEntry newEntry,
                                         @PathVariable String id,
                                         @PathVariable String userName) {
        if (id.length() < 24) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ObjectId objectId = new ObjectId(id);
        JournalEntry oldEntry = journalEntryService.getJournalEntryById(objectId).orElse(null);
        if (oldEntry != null) {
            oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldEntry.getContent());
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
