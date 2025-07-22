package com.urlshortener.server.dto;

import java.time.LocalDateTime;

public class UrlResponse {
    private String shortCode;
    private LocalDateTime expiryTime;

    public UrlResponse(String shortCode, LocalDateTime expiryTime) {
        this.shortCode = shortCode;
        this.expiryTime = expiryTime;
    }

    public String getShortCode() {
        return shortCode;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }
}
