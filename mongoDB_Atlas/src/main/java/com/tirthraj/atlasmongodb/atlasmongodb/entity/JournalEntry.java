package com.tirthraj.atlasmongodb.atlasmongodb.entity;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "journal_entries")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class JournalEntry {
    @Id
    @JsonSerialize(using = JsonSerializer.class)
    private ObjectId id;

    @NonNull
    private String title;

    @NonNull
    private String content;

    private LocalDateTime date;
}
