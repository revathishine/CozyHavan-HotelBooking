package com.hexaware.cozyhavenproject.service.impl;
import com.hexaware.cozyhavenproject.entities.RevokedToken;
import com.hexaware.cozyhavenproject.repository.RevokedTokenRepository;
import com.hexaware.cozyhavenproject.service.RevokedTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevokedTokenServiceImpl implements RevokedTokenService {

	@Autowired
    private RevokedTokenRepository revokedTokenRepository;

    public RevokedToken createToken(RevokedToken token) {
        return revokedTokenRepository.save(token);
    }

    public RevokedToken getTokenById(Integer id) {
        return revokedTokenRepository.findById(id).orElse(null);
    }

    public List<RevokedToken> getAllTokens() {
        return revokedTokenRepository.findAll();
    }

    public RevokedToken updateToken(RevokedToken token) {
        return revokedTokenRepository.save(token);
    }

    public void deleteToken(Integer id) {
        revokedTokenRepository.deleteById(id);
    }
}
