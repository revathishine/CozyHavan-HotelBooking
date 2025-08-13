package com.hexaware.cozyhavenproject.service;
import com.hexaware.cozyhavenproject.entities.Review;
import java.util.List;

public interface ReviewService {

	Review createReview(Review review);
    Review getReviewById(Integer id);
    List<Review> getAllReviews();
    Review updateReview(Review review);
    void deleteReview(Integer id);
    List<Review> getReviewsByHotelId(Integer hotelId);
}
