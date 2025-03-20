package com.tirthraj.auth0.auth0.dto;

import lombok.Data;

@Data
public class APIResponse {
    private boolean success;
    private String message;
    public APIResponse(boolean success, String message){
        this.success = success;
        this.message = message;
    }
}
