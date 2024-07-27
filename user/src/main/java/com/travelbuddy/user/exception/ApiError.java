package com.travelbuddy.user.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ApiError {

    private LocalDateTime timestamp;
    private List<String> message;
    private String details;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public List<String> getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public ApiError(LocalDateTime timestamp, List<String> string, String details) {
        super();
        this.timestamp = timestamp;
        this.message = string;
        this.details = details;
    }


}