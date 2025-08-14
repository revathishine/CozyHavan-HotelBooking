package com.hexaware.cozyhavenproject.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import com.hexaware.cozyhavenproject.entities.User;
import com.hexaware.cozyhavenproject.exceptionhandler.ResourceNotFoundException;
import com.hexaware.cozyhavenproject.repository.UserRepository;
import com.hexaware.cozyhavenproject.service.impl.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class UserServiceImplTest {

	

	 @InjectMocks
	    private UserServiceImpl service;

	    @Mock
	    private UserRepository repository;

	    @Mock
	    private PasswordEncoder passwordEncoder;

	    private User user;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	        user = new User();
	        user.setUserId(1);
	        user.setName("revathi");
	        user.setPassword("pass123");
	    }

	    @Test
	    void testCreateUser() {
	        when(passwordEncoder.encode("pass123")).thenReturn("encodedPass");
	        when(repository.save(any(User.class))).thenReturn(user);

	        User result = service.createUser(user);
	        assertEquals(user, result);
	        verify(passwordEncoder, times(1)).encode("pass123");
	    }

	    @Test
	    void testGetUserById_Found() {
	        when(repository.findById(1)).thenReturn(Optional.of(user));
	        assertEquals(user, service.getUserById(1));
	    }

	    @Test
	    void testGetUserById_NotFound() {
	        when(repository.findById(1)).thenReturn(Optional.empty());
	        assertThrows(ResourceNotFoundException.class, () -> service.getUserById(1));
	    }

	    @Test
	    void testGetAllUsers() {
	        when(repository.findAll()).thenReturn(List.of(user));
	        assertEquals(1, service.getAllUsers().size());
	    }

	    @Test
	    void testUpdateUser() {
	        when(repository.existsById(1)).thenReturn(true);
	        when(repository.save(user)).thenReturn(user);
	        assertEquals(user, service.updateUser(user));
	    }

	    @Test
	    void testDeleteUser() {
	        when(repository.existsById(1)).thenReturn(true);
	        service.deleteUser(1);
	        verify(repository, times(1)).deleteById(1);
	    }
}
