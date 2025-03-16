package com.tirthraj.atlasmongodb.atlasmongodb.controllers;

import com.tirthraj.atlasmongodb.atlasmongodb.dto.APIResponse;
import com.tirthraj.atlasmongodb.atlasmongodb.dto.JournalEntryDTO;
import com.tirthraj.atlasmongodb.atlasmongodb.dto.JournalEntryRequest;
import com.tirthraj.atlasmongodb.atlasmongodb.entity.JournalEntry;
import com.tirthraj.atlasmongodb.atlasmongodb.entity.User;
import com.tirthraj.atlasmongodb.atlasmongodb.service.JournalEntryService;
import com.tirthraj.atlasmongodb.atlasmongodb.service.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journals")
public class JournalEntryController {

    private final JournalEntryService journalEntryService;
    private final UserService userService;

    public JournalEntryController(JournalEntryService journalEntryService, UserService userService){
        this.journalEntryService = journalEntryService;
        this.userService = userService;
    }


    @GetMapping
    public List<JournalEntryDTO> getAllJournals() {
        return userService.getAllUsers().stream()
                .flatMap(user -> user.getJournalEntries().stream()
                        .map(journalEntry -> JournalEntryService.convertToDTO(user, journalEntry)))
                .toList();
    }

    @PostMapping
    public ResponseEntity<JournalEntryDTO> createNewJournal(@Valid @RequestBody JournalEntryRequest journalEntryRequest){
        User user = userService.getUserByUsername(journalEntryRequest.getUsername()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
        JournalEntry journalEntry = journalEntryService.saveJournalEntry(JournalEntryService.convertRequestToJournalEntry(journalEntryRequest), journalEntryRequest.getUsername()).orElseThrow(()-> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save Journal"));

        return ResponseEntity.status(HttpStatus.CREATED).body(JournalEntryService.convertToDTO(user, journalEntry));
    }

    @GetMapping("/{username}")
    public List<JournalEntryDTO> getUserJournals(@PathVariable String username){
        User user = userService.getUserByUsername(username).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
        return user.getJournalEntries().stream()
                .map(JournalEntry::getId)  // Extract ObjectId from the JournalEntry reference
                .map(journalEntryService::getJournalEntryById)  // Returns Optional<JournalEntry>
                .flatMap(Optional::stream)  // Filters out empty Optionals (safe way to skip missing entries)
                .map(entry -> JournalEntryService.convertToDTO(user, entry)) // Convert to DTO
                .toList();
    }

    @DeleteMapping("/{username}/journal/{id}")
    public ResponseEntity<APIResponse> deleteJournal(@PathVariable String username, @PathVariable ObjectId id){
        Optional<JournalEntry> journalEntry = journalEntryService.deleteJournalEntry(id, username);
        if(journalEntry.isPresent()) {
            return ResponseEntity.ok(new APIResponse(true, "Journal Entry Deleted Successfully"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, "User or Journal Entry Not Found"));
    }

}
