//date:4/8/25 author 


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
