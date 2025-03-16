package com.tirthraj.atlasmongodb.atlasmongodb.dto;

import com.tirthraj.atlasmongodb.atlasmongodb.entity.JournalEntry;
import lombok.Data;

@Data
public class JournalEntryDTO {
    private String writerUsername;
    private String id;
    private String title;
    private String content;

    public JournalEntryDTO(String writerUsername, String id, String title, String content){
        this.writerUsername = writerUsername;
        this.id = id;
        this.title = title;
        this.content = content;
    }
    public JournalEntryDTO(String writerUsername, JournalEntry journalEntry){
        this.writerUsername = writerUsername;
        this.id = journalEntry.getId().toString();
        this.title = journalEntry.getTitle();
        this.content = journalEntry.getContent();
    }
}
