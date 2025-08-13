//package com.hexaware.cozyhavenproject.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.hexaware.cozyhavenproject.dto.ReviewDto;
//import com.hexaware.cozyhavenproject.entities.Review;
//import com.hexaware.cozyhavenproject.service.ReviewService;
//
//import jakarta.validation.Valid;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//
//@RestController
//@RequestMapping("/api/reviews")
//public class ReviewController {
//
//	
//	 @Autowired
//	    private ReviewService reviewService;
//
//	    @PostMapping
//	    public Review createReview(@Valid @RequestBody ReviewDto reviewDto) {
//	    	 log.info("POST /api/reviews - create review for hotel {}");
//	        Review review = new Review();
//	        review.setRating(reviewDto.getRating());
//	        review.setComment(reviewDto.getComment());
//	        review.setReviewDate(reviewDto.getReviewDate());
//	        return reviewService.createReview(review);
//	    }
//
//	    @GetMapping("/{id}")
//	    public Review getReview(@PathVariable Integer id) {
//	    	 log.info("GET /api/reviews/{} - fetch review", id);
//	        return reviewService.getReviewById(id);
//	    }
//
//	    @GetMapping
//	    public List<Review> getAllReviews() {
//	    	 log.info("GET /api/reviews - fetch all reviews");
//	        return reviewService.getAllReviews();
//	    }
//
//	    @PutMapping("/{id}")
//	    public Review updateReview(@PathVariable Integer id, @Valid @RequestBody ReviewDto reviewDto) {
//	    	log.info("PUT /api/reviews/{} - update review", id);
//	        Review review = reviewService.getReviewById(id);
//	        if (review != null) {
//	            review.setRating(reviewDto.getRating());
//	            review.setComment(reviewDto.getComment());
//	            review.setReviewDate(reviewDto.getReviewDate());
//	        }
//	        return reviewService.updateReview(review);
//	    }
//
//	    @DeleteMapping("/{id}")
//	    public void deleteReview(@PathVariable Integer id) {
//	    	 log.info("DELETE /api/reviews/{} - delete review", id);
//	        reviewService.deleteReview(id);
//	    }
//}

package com.hexaware.cozyhavenproject.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexaware.cozyhavenproject.dto.ReviewDto;
import com.hexaware.cozyhavenproject.entities.Review;
import com.hexaware.cozyhavenproject.entities.User;
import com.hexaware.cozyhavenproject.entities.Hotel;
import com.hexaware.cozyhavenproject.service.ReviewService;
import com.hexaware.cozyhavenproject.service.UserService;
import com.hexaware.cozyhavenproject.service.HotelService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<Review> createReview(@Valid @RequestBody ReviewDto reviewDto) {
        log.info("Create review by user {} for hotel {}", reviewDto.getUserId(), reviewDto.getHotelId());
        User user = userService.getUserById(reviewDto.getUserId());
        Hotel hotel = hotelService.getHotelById(reviewDto.getHotelId());

        Review review = new Review();
        review.setUser(user);
        review.setHotel(hotel);
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        review.setReviewDate(reviewDto.getReviewDate());

        Review created = reviewService.createReview(review);
        return ResponseEntity.created(URI.create("/api/reviews/" + created.getReviewId())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReview(@PathVariable Integer id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Integer id, @Valid @RequestBody ReviewDto reviewDto) {
        log.info("Update review {}", id);
        Review review = reviewService.getReviewById(id);
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        review.setReviewDate(reviewDto.getReviewDate());
        Review updated = reviewService.updateReview(review);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
    
    
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Review>> getReviewsByHotel(@PathVariable Integer hotelId) {
        return ResponseEntity.ok(reviewService.getReviewsByHotelId(hotelId));
    }
}
