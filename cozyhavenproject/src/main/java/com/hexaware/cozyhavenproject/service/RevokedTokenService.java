package com.hexaware.cozyhavenproject.service;
import com.hexaware.cozyhavenproject.entities.RevokedToken;
import java.util.List;

public interface RevokedTokenService {

	RevokedToken createToken(RevokedToken token);
    RevokedToken getTokenById(Integer id);
    List<RevokedToken> getAllTokens();
    RevokedToken updateToken(RevokedToken token);
    void deleteToken(Integer id);
}
