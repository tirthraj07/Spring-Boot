package com.tirthraj.springSecurity.spring_security.DTO;

import lombok.Data;

@Data
public class APIResponse {
    private boolean success;
    private String message;

    public APIResponse(boolean success, String message){
        this.message = message;
        this.success = success;
    }

    public APIResponse(boolean success){
        this.message = "";
        this.success = success;
    }

}
