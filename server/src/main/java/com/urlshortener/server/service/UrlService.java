package com.urlshortener.server.service;

import com.urlshortener.server.model.ShortUrl;
import com.urlshortener.server.repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

@Service
public class UrlService {

    @Autowired
    private ShortUrlRepository shortUrlRepository;

    private static final String URL_REGEX =
            "^(https?|ftp)://[\\w.-]+(?:\\.[\\w\\.-]+)+[/#?]?.*$";

    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    public boolean isValidUrl(String url) {
        return URL_PATTERN.matcher(url).matches();
    }

    public ShortUrl shortenUrl(String originalUrl) {
        Optional<ShortUrl> existing = shortUrlRepository.findByOriginalUrl(originalUrl);
        if (existing.isPresent()) {
            return existing.get();
        }

        String shortCode = generateRandomCode(6);
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(originalUrl);
        shortUrl.setShortCode(shortCode);
        shortUrl.setExpiryTime(expiryTime);

        return shortUrlRepository.save(shortUrl);
    }

    public Optional<ShortUrl> getOriginalUrl(String shortCode) {
        return shortUrlRepository.findByShortCode(shortCode);
    }

    private String generateRandomCode(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            code.append(chars.charAt(random.nextInt(chars.length())));
        }
        return code.toString();
    }
}
