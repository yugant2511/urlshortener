package com.example.urlshortener.controller;

import com.example.urlshortener.dto.AnalyticsResponse;
import com.example.urlshortener.dto.UrlRequest;
import com.example.urlshortener.model.Url;
import com.example.urlshortener.service.UrlService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    // Create short URL
    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody UrlRequest request) {

        String shortCode = urlService.shortenUrl(request.getOriginalUrl());

        String shortUrl = "https://urlshortener-production-a498.up.railway.app/api/" + shortCode;

        return ResponseEntity.ok(shortUrl);
    }

    // Redirect to original URL
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToOriginal(@PathVariable String shortCode) {

        Optional<Url> url = urlService.getOriginalUrl(shortCode);

        if (url.isPresent()) {

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(url.get().getOriginalUrl()));

            return ResponseEntity.status(302).headers(headers).build();
        }

        return ResponseEntity.notFound().build();
    }

    // Analytics API
    @GetMapping("/analytics/{shortCode}")
    public ResponseEntity<?> getAnalytics(@PathVariable String shortCode) {

        Optional<Url> url = urlService.getAnalytics(shortCode);

        if (url.isPresent()) {

            AnalyticsResponse response =
                    new AnalyticsResponse(
                            url.get().getOriginalUrl(),
                            url.get().getShortCode(),
                            url.get().getClickCount()
                    );

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }
}