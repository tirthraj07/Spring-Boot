package com.tirthraj.lombok.controller;

import com.tirthraj.lombok.entity.JournalEntry;
import com.tirthraj.lombok.service.JournalEntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journals")
public class JournalEntryController {

    // Constructor based Dependency Injection
    private final JournalEntryService journalEntryService;

    public JournalEntryController(JournalEntryService journalEntryService){
        this.journalEntryService = journalEntryService;
    }

    // REST APIs

    @GetMapping
    public List<JournalEntry> getAllJournalEntries(){
        return journalEntryService.getAllJournalEntries();
    }

    @PostMapping
    public ResponseEntity<Object> createNewJournalEntry(@RequestBody JournalEntry journalEntry){
        try {
            if (journalEntry.getId() == null || !journalEntryService.journalEntryExistsById(journalEntry.getId())) {
                JournalEntry newJournal = journalEntryService.saveJournalEntry(journalEntry);
                Map<String, Object> response = new HashMap<>() {{
                    put("status", "success");
                    put("message", "JournalEntry created successfully");
                    put("journalEntry", newJournal);
                }};
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
            Map<String, Object> errorResponse = new HashMap<>() {{
                put("status", "error");
                put("message", "JournalEntry with ID" + journalEntry.getId() + "already exists");
            }};
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>() {{
                put("status", "error");
                put("message", e.getMessage());
            }};
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
