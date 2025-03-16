package com.tirthraj.atlasmongodb.atlasmongodb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
public class HealthCheckDTO {
    private String status;
    private String message;
}
