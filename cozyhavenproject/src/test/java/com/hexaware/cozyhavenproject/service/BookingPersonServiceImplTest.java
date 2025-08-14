package com.hexaware.cozyhavenproject.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import com.hexaware.cozyhavenproject.entities.BookingPerson;
import com.hexaware.cozyhavenproject.exceptionhandler.ResourceNotFoundException;
import com.hexaware.cozyhavenproject.repository.BookingPersonRepository;
import com.hexaware.cozyhavenproject.service.impl.BookingPersonServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class BookingPersonServiceImplTest {

	

	 @InjectMocks
	    private BookingPersonServiceImpl service;

	    @Mock
	    private BookingPersonRepository repository;

	    private BookingPerson person;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	        person = new BookingPerson();
	        person.setPersonId(1);
	        person.setName("Revathi");
	    }

	    @Test
	    void testCreatePerson() {
	        when(repository.save(person)).thenReturn(person);
	        BookingPerson result = service.createPerson(person);
	        assertEquals(person, result);
	    }

	    @Test
	    void testGetPersonById_Found() {
	        when(repository.findById(1)).thenReturn(Optional.of(person));
	        BookingPerson result = service.getPersonById(1);
	        assertEquals(person, result);
	    }

	    @Test
	    void testGetPersonById_NotFound() {
	        when(repository.findById(1)).thenReturn(Optional.empty());
	        assertThrows(ResourceNotFoundException.class, () -> service.getPersonById(1));
	    }

	    @Test
	    void testGetAllPersons() {
	        when(repository.findAll()).thenReturn(List.of(person));
	        List<BookingPerson> result = service.getAllPersons();
	        assertEquals(1, result.size());
	    }

	    @Test
	    void testUpdatePerson() {
	        when(repository.existsById(1)).thenReturn(true);
	        when(repository.save(person)).thenReturn(person);
	        BookingPerson result = service.updatePerson(person);
	        assertEquals(person, result);
	    }

	    @Test
	    void testDeletePerson() {
	        when(repository.existsById(1)).thenReturn(true);
	        service.deletePerson(1);
	        verify(repository, times(1)).deleteById(1);
	    }
}
