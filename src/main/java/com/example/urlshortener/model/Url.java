package com.example.urlshortener.model;

import java.io.Serializable;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "urls")
public class Url implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2048)
    private String originalUrl;

    @Column(nullable = false, unique = true)
    private String shortCode;

    private LocalDateTime createdAt;

    private int clickCount;

    public Url() {}

    public Url(String originalUrl, String shortCode) {
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.createdAt = LocalDateTime.now();
        this.clickCount = 0;
    }

    public Long getId() { return id; }

    public String getOriginalUrl() { return originalUrl; }

    public String getShortCode() { return shortCode; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public int getClickCount() { return clickCount; }

    public void setClickCount(int clickCount) { this.clickCount = clickCount; }
}