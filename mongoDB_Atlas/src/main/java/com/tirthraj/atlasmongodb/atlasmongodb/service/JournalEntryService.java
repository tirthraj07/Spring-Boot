package com.tirthraj.atlasmongodb.atlasmongodb.service;

import com.tirthraj.atlasmongodb.atlasmongodb.dto.JournalEntryDTO;
import com.tirthraj.atlasmongodb.atlasmongodb.dto.JournalEntryRequest;
import com.tirthraj.atlasmongodb.atlasmongodb.entity.JournalEntry;
import com.tirthraj.atlasmongodb.atlasmongodb.entity.User;
import com.tirthraj.atlasmongodb.atlasmongodb.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    private final JournalEntryRepository journalEntryRepository;
    private final UserService userService;
    public JournalEntryService(JournalEntryRepository journalEntryRepository, UserService userService){
        this.journalEntryRepository = journalEntryRepository;
        this.userService = userService;
    }

    public static JournalEntryDTO convertToDTO(User user, JournalEntry journalEntry){
        return new JournalEntryDTO(
                user.getUsername(),
                journalEntry
        );
    }

    public static JournalEntry convertRequestToJournalEntry(JournalEntryRequest journalEntryRequest){
        JournalEntry journalEntry = new JournalEntry();
        journalEntry.setTitle(journalEntryRequest.getTitle());
        journalEntry.setContent(journalEntryRequest.getContent());
        return journalEntry;
    }

    // Create
    @Transactional
    public Optional<JournalEntry> saveJournalEntry(JournalEntry journalEntry, String username){
        try {
            Optional<User> userEntry = userService.getUserByUsername(username);
            if (userEntry.isPresent()) {
                journalEntry.setDate(LocalDateTime.now());
                JournalEntry savedJournalEntry = journalEntryRepository.save(journalEntry);
                User user = userEntry.get();
                user.getJournalEntries().add(savedJournalEntry);
                userService.updateUserById(user.getId(), user);
                return Optional.of(savedJournalEntry);
            }
            return Optional.empty();
        }
        catch(Exception e){
            return Optional.empty();
        }
    }

    // Read
    public List<JournalEntry> getAllJournalEntries(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalEntryById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    // Delete
    @Transactional
    public Optional<JournalEntry> deleteJournalEntry(ObjectId id, String username) {
        Optional<User> userEntry = userService.getUserByUsername(username);
        if (userEntry.isPresent()) {
            User user = userEntry.get();

            // Find and delete the journal entry
            Optional<JournalEntry> journalEntry = journalEntryRepository.findById(id);
            if (journalEntry.isPresent()) {
                user.getJournalEntries().removeIf(x -> x.getId().equals(id));
                userService.updateUserById(user.getId(), user);

                journalEntryRepository.deleteById(id);  // Perform deletion
                return journalEntry;  // Return the deleted entry
            }
        }
        return Optional.empty();
    }


}
