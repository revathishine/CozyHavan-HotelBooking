package com.hexaware.cozyhavenproject.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class RevokedToken {

	 @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer tokenId;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private User user;

   @Lob
   private String token;

   private LocalDateTime revokedAt;

   // Getters and Setters
   public Integer getTokenId() { return tokenId; }
   public void setTokenId(Integer tokenId) { this.tokenId = tokenId; }

   public User getUser() { return user; }
   public void setUser(User user) { this.user = user; }

   public String getToken() { return token; }
   public void setToken(String token) { this.token = token; }

   public LocalDateTime getRevokedAt() { return revokedAt; }
   public void setRevokedAt(LocalDateTime revokedAt) { this.revokedAt = revokedAt; }

}
