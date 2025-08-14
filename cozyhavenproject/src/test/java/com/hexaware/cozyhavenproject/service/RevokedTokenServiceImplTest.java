package com.hexaware.cozyhavenproject.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import com.hexaware.cozyhavenproject.entities.RevokedToken;
import com.hexaware.cozyhavenproject.exceptionhandler.ResourceNotFoundException;
import com.hexaware.cozyhavenproject.repository.RevokedTokenRepository;
import com.hexaware.cozyhavenproject.service.impl.RevokedTokenServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RevokedTokenServiceImplTest {

	@Test
	void test() {
		fail("Not yet implemented");
	}

	@InjectMocks
    private RevokedTokenServiceImpl service;

    @Mock
    private RevokedTokenRepository repository;

    private RevokedToken token;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        token = new RevokedToken();
        token.setTokenId(1);
        token.setToken("abcd1234");
    }

    @Test
    void testCreateToken() {
        when(repository.save(token)).thenReturn(token);
        assertEquals(token, service.createToken(token));
    }

    @Test
    void testGetTokenById_Found() {
        when(repository.findById(1)).thenReturn(Optional.of(token));
        assertEquals(token, service.getTokenById(1));
    }

    @Test
    void testGetTokenById_NotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getTokenById(1));
    }

    @Test
    void testGetAllTokens() {
        when(repository.findAll()).thenReturn(List.of(token));
        assertEquals(1, service.getAllTokens().size());
    }

    @Test
    void testUpdateToken() {
        when(repository.existsById(1)).thenReturn(true);
        when(repository.save(token)).thenReturn(token);
        assertEquals(token, service.updateToken(token));
    }

    @Test
    void testDeleteToken() {
        when(repository.existsById(1)).thenReturn(true);
        service.deleteToken(1);
        verify(repository, times(1)).deleteById(1);
    }
}
