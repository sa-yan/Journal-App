package com.sayan.journalapp.service;

import com.sayan.journalapp.Entity.JournalEntry;
import com.sayan.journalapp.Entity.User;
import com.sayan.journalapp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public void saveEntry(JournalEntry journalEntry, String userName) {
        User user = userService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveUser(user);
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getJournalEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalEntryById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteEntryById(ObjectId id, String userName) {
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x-> x.getId().equals(id));
        userService.saveUser(user);
        journalEntryRepository.deleteById(id);
    }


}
