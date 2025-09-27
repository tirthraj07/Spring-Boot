package com.tirthraj.service_b.dto;

import java.util.List;

public class ApiResponseDto {
    private boolean success;
    private List<String> message;

    public ApiResponseDto(boolean success, List<String> message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }
}
