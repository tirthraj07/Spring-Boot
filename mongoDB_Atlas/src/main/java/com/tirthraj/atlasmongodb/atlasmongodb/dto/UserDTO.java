package com.tirthraj.atlasmongodb.atlasmongodb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
public class UserDTO {
    private String id;
    private String username;
    private int totalJournalEntries;

    public UserDTO(String id, String username, int totalJournalEntries){
        this.id = id;
        this.username = username;
        this.totalJournalEntries = totalJournalEntries;
    }
}
