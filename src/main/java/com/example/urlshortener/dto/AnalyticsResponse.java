package com.example.urlshortener.dto;

public class AnalyticsResponse {

    private String originalUrl;
    private String shortCode;
    private int clickCount;

    public AnalyticsResponse(String originalUrl, String shortCode, int clickCount) {
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.clickCount = clickCount;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public int getClickCount() {
        return clickCount;
    }
}