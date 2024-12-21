package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.model.Review;
import com.service.ReviewService;
import com.exception.CustomException;
import com.exception.Response;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/post")
    public ResponseEntity<?> addReview(@RequestBody Review review) throws CustomException{

            if (!reviewService.findByReservation(review.getReservation().getReservationId()).isEmpty()) {
                throw new CustomException("ADDFAILS", "Review already exists");
            }

            reviewService.saveReview(review);
            return ResponseEntity.status(201).body(new Response("POSTSUCCESS","Review added successfully"));

    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllReviews() throws CustomException{

            List<Review> reviews = reviewService.getAllReviews();
            if (reviews.isEmpty()) {
                throw new CustomException("GETALLFAILS", "Review list is empty");
            }
            return ResponseEntity.ok(reviews);

    }

    @GetMapping("/{review_id}")
    public ResponseEntity<?> getReviewById(@PathVariable Long review_id) throws CustomException{

            if (!reviewService.existsById(review_id)) {
                throw new CustomException("GETFAILS", "Review doesn't exist");
            }
            return ResponseEntity.ok(reviewService.getReviewById(review_id));

    }

    @GetMapping("/reviews/rating/{rating}")
    public ResponseEntity<?> getReviewsByRating(@PathVariable int rating) throws CustomException{

            List<Review> reviews = reviewService.getReviewsByRating(rating);
            if (reviews.isEmpty()) {
                throw new CustomException("GETALLFAILS", "Review list is empty");
            }
            return ResponseEntity.ok(reviews);

    }

    @GetMapping("/reviews/recent")
    public ResponseEntity<?> getRecentReviews() throws CustomException{

            List<Review> reviews = reviewService.getRecentReviews();
            if (reviews.isEmpty()) {
                throw new CustomException("GETFAILS", "Review doesn't exist");
            }
            return ResponseEntity.ok(reviews);
    }

    @PutMapping("/update/{review_id}")
    public ResponseEntity<?> updateReview(@PathVariable Long review_id, @RequestBody Review review) throws CustomException{

            if (!reviewService.existsById(review_id)) {
                throw new CustomException("UPDTFAILS", "Review doesn't exist");
            }
            reviewService.updateReview(review_id, review);
            return ResponseEntity.ok(new Response("UPDATESUCCESS","Review updated successfully"));


    }

    @DeleteMapping("/delete/{review_id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long review_id) throws CustomException{

            if (!reviewService.existsById(review_id)) {
                throw new CustomException("DLTFAILS", "Review doesn't exist");
            }
            reviewService.deleteReview(review_id);
            return ResponseEntity.ok(new Response("DELETESUCCESS","Review deleted successfully"));


    }
}
