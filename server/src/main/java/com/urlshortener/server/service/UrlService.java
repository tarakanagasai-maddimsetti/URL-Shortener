package com.example.urlshortener.service;

import com.example.urlshortener.model.ShortUrl;
import com.example.urlshortener.repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

@Service
public class UrlService {
    @Autowired
    private ShortUrlRepository repository;

    private final Pattern urlPattern = Pattern.compile("^(https?://).+");

    public ShortUrl shortenUrl(String originalUrl) {
        if (!urlPattern.matcher(originalUrl).matches()) {
            throw new IllegalArgumentException("Invalid URL format.");
        }

        Optional<ShortUrl> existing = repository.findByOriginalUrl(originalUrl);
        if (existing.isPresent()) {
            return existing.get();
        }

        String shortCode = generateRandomCode();
        LocalDateTime now = LocalDateTime.now();

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(originalUrl);
        shortUrl.setShortCode(shortCode);
        shortUrl.setCreatedAt(now);
        shortUrl.setExpiresAt(now.plusMinutes(5));

        return repository.save(shortUrl);
    }

    public Optional<ShortUrl> getOriginalUrl(String shortCode) {
        return repository.findByShortCode(shortCode);
    }

    private String generateRandomCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
