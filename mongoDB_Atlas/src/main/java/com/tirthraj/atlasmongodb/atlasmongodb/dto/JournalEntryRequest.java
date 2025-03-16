package com.tirthraj.atlasmongodb.atlasmongodb.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class JournalEntryRequest {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "content is required")
    private String content;

}
