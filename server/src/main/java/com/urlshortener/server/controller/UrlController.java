package com.urlshortener.server.controller;

import com.urlshortener.server.model.ShortUrl;
import com.urlshortener.server.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("originalUrl");

        if (!urlService.isValidUrl(originalUrl)) {
            return ResponseEntity.badRequest().body("Invalid URL format");
        }

        ShortUrl shortUrl = urlService.shortenUrl(originalUrl);

        Map<String, Object> response = new HashMap<>();
        response.put("shortCode", shortUrl.getShortCode());
        response.put("expiryTime", shortUrl.getExpiryTime().toString());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{shortCode}")
    public Object redirectToOriginal(@PathVariable String shortCode) {
        Optional<ShortUrl> optionalShortUrl = urlService.getOriginalUrl(shortCode);

        if (optionalShortUrl.isPresent()) {
            ShortUrl shortUrl = optionalShortUrl.get();
            if (LocalDateTime.now().isBefore(shortUrl.getExpiryTime())) {
                return new RedirectView(shortUrl.getOriginalUrl());
            } else {
                return ResponseEntity.status(410).body("Short URL has expired");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
