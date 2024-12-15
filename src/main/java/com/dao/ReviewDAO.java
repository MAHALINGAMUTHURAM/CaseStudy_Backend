package com.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.model.Review;
@Repository
public interface ReviewDAO extends JpaRepository<Review, Long> {
	
	
    List<Review> findByRating(int rating);
    @Query("SELECT r FROM Review r ORDER BY r.review_date DESC")
    List<Review> findRecentReviews();
}
