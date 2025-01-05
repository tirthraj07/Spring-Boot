package com.tirthraj.lombok.service;

import com.tirthraj.lombok.entity.JournalEntry;
import com.tirthraj.lombok.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    // Constructor-based dependency injection
    private final JournalEntryRepository journalEntryRepository;

    public JournalEntryService(JournalEntryRepository journalEntryRepository){
        this.journalEntryRepository = journalEntryRepository;
    }

    // Services

    public JournalEntry saveJournalEntry(JournalEntry journalEntry){
        journalEntry.setDate(LocalDateTime.now());
        return journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllJournalEntries(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findJournalEntryById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void deleteJournalEntryById(ObjectId id){
        if(journalEntryRepository.existsById(id)){
            journalEntryRepository.deleteById(id);
        }
    }

    public Optional<JournalEntry> updateJournalEntry(ObjectId id, JournalEntry journalEntry){
        if(journalEntryRepository.existsById(id)){
            journalEntry.setId(id);
            return Optional.of(journalEntryRepository.save(journalEntry));
        }
        return Optional.empty();
    }

    public boolean journalEntryExistsById(ObjectId id){
        return journalEntryRepository.existsById(id);
    }

}
