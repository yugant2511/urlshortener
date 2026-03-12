package com.example.urlshortener.service;

import com.example.urlshortener.model.Url;
import com.example.urlshortener.repository.UrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;

import java.util.Optional;
import java.util.Random;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenUrl(String originalUrl) {

        String shortCode = generateShortCode();

        Url url = new Url(originalUrl, shortCode);

        urlRepository.save(url);

        return shortCode;
    }


    @Cacheable(value = "urls", key = "#shortCode")
    public Optional<Url> getOriginalUrl(String shortCode) {

        Optional<Url> urlOptional = urlRepository.findByShortCode(shortCode);

        urlOptional.ifPresent(url -> {
            url.setClickCount(url.getClickCount() + 1);
            urlRepository.save(url);
        });

        return urlOptional;
    }

    private String generateShortCode() {

        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        StringBuilder shortCode = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            shortCode.append(characters.charAt(random.nextInt(characters.length())));
        }

        return shortCode.toString();
    }
    public Optional<Url> getAnalytics(String shortCode) {
        return urlRepository.findByShortCode(shortCode);
    }
}