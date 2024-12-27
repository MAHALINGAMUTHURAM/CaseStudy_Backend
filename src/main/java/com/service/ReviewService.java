package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.ReviewDAO;
import com.model.Review;



@Service
public class ReviewService {
	@Autowired
	ReviewDAO reviewDAO;

	
	public void saveReview(Review review)
	{
		reviewDAO.save(review);
	}
	public List<Review> getAllReviews()
	{
		return reviewDAO.findAll();
	}
	
	public void updateReview(long id,Review review)
	{
		Review existing_review=reviewDAO.findById(id).get();
		
		existing_review.setComment(review.getComment());
		existing_review.setRating(review.getRating());
		existing_review.setReservation(review.getReservation());
		existing_review.setReview_date(review.getReview_date());
		
		reviewDAO.save(existing_review);
	}
	
	public void deleteReview(long id)
	{
		Review review=reviewDAO.findById(id).get();
		reviewDAO.delete(review);
	}
    
    public boolean existsById(long id) {
        return reviewDAO.findById(id).isPresent();
    }
    
    public Review getReviewById(long id)
    {
    	return reviewDAO.findById(id).get();
    }
    
    public List<Review> getRecentReviews() {
        return reviewDAO.findRecentReviews();
    }
    
    public List<Review> getReviewsByRating(int rating) {
        return reviewDAO.findByRating(rating);
    }
    
    public List<Review> findByReservation(long id)
    {
    	return reviewDAO.findByReservation_ReservationId(id);
    }
    
    public List<Review> getReviewsByHotel(long hotelId) {
        // Call the repository method using the native query
        return reviewDAO.findByHotel_HotelId(hotelId);
    }

}
