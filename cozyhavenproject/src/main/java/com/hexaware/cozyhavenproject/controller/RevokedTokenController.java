//date 4/8/25 author 


package com.hexaware.cozyhavenproject.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexaware.cozyhavenproject.dto.RevokedTokenDto;
import com.hexaware.cozyhavenproject.entities.RevokedToken;
import com.hexaware.cozyhavenproject.entities.User;
import com.hexaware.cozyhavenproject.service.RevokedTokenService;
import com.hexaware.cozyhavenproject.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/revoked-tokens")
@RequiredArgsConstructor
@Slf4j
public class RevokedTokenController {

    private final RevokedTokenService revokedTokenService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<RevokedToken> createToken(@Valid @RequestBody RevokedTokenDto tokenDto) {
        log.info("Create revoked token for user {}", tokenDto.getUserId());
        User user = userService.getUserById(tokenDto.getUserId());
        RevokedToken token = new RevokedToken();
        token.setUser(user);
        token.setToken(tokenDto.getToken());
        token.setRevokedAt(tokenDto.getRevokedAt());
        RevokedToken created = revokedTokenService.createToken(token);
        return ResponseEntity.created(URI.create("/api/revoked-tokens/" + created.getTokenId())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RevokedToken> getToken(@PathVariable Integer id) {
        return ResponseEntity.ok(revokedTokenService.getTokenById(id));
    }

    @GetMapping
    public ResponseEntity<List<RevokedToken>> getAllTokens() {
        return ResponseEntity.ok(revokedTokenService.getAllTokens());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RevokedToken> updateToken(@PathVariable Integer id, @Valid @RequestBody RevokedTokenDto tokenDto) {
        RevokedToken token = revokedTokenService.getTokenById(id);
        User user = userService.getUserById(tokenDto.getUserId());
        token.setUser(user);
        token.setToken(tokenDto.getToken());
        token.setRevokedAt(tokenDto.getRevokedAt());
        RevokedToken updated = revokedTokenService.updateToken(token);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToken(@PathVariable Integer id) {
        revokedTokenService.deleteToken(id);
        return ResponseEntity.noContent().build();
    }
}

