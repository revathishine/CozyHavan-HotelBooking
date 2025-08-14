package com.hexaware.cozyhavenproject.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import com.hexaware.cozyhavenproject.entities.Review;
import com.hexaware.cozyhavenproject.exceptionhandler.ResourceNotFoundException;
import com.hexaware.cozyhavenproject.repository.ReviewRepository;
import com.hexaware.cozyhavenproject.service.impl.ReviewServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceImplTest {

	@Test
	void test() {
		fail("Not yet implemented");
	}

	@InjectMocks
    private ReviewServiceImpl service;

    @Mock
    private ReviewRepository repository;

    private Review review;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        review = new Review();
        review.setReviewId(1);
        review.setComment("Excellent");
    }

    @Test
    void testCreateReview() {
        when(repository.save(review)).thenReturn(review);
        assertEquals(review, service.createReview(review));
    }

    @Test
    void testGetReviewById_Found() {
        when(repository.findById(1)).thenReturn(Optional.of(review));
        assertEquals(review, service.getReviewById(1));
    }

    @Test
    void testGetReviewById_NotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getReviewById(1));
    }

    @Test
    void testGetAllReviews() {
        when(repository.findAll()).thenReturn(List.of(review));
        assertEquals(1, service.getAllReviews().size());
    }

    @Test
    void testUpdateReview() {
        when(repository.existsById(1)).thenReturn(true);
        when(repository.save(review)).thenReturn(review);
        assertEquals(review, service.updateReview(review));
    }

    @Test
    void testDeleteReview() {
        when(repository.existsById(1)).thenReturn(true);
        service.deleteReview(1);
        verify(repository, times(1)).deleteById(1);
    }
}
