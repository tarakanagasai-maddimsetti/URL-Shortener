package com.example.urlshortener.controller;

import com.example.urlshortener.model.ShortUrl;
import com.example.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody Map<String, String> request) {
        try {
            String originalUrl = request.get("originalUrl");
            ShortUrl shortUrl = urlService.shortenUrl(originalUrl);
            return ResponseEntity.ok(shortUrl);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortCode) {
        Optional<ShortUrl> shortUrlOptional = urlService.getOriginalUrl(shortCode);

        if (shortUrlOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("URL does not exist. Please shorten a new one.");
        }

        ShortUrl shortUrl = shortUrlOptional.get();
        if (LocalDateTime.now().isAfter(shortUrl.getExpiresAt())) {
            return ResponseEntity.status(HttpStatus.GONE).body("This URL has expired.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(shortUrl.getOriginalUrl()));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
