package com.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.ReviewDAO;
import com.model.Review;



@Service
public class ReviewService {
	@Autowired
	ReviewDAO reviewDAO;
	List<Review> reviewList;
	
	public ReviewService() {
		reviewList=new ArrayList<>();
	}
	
	public void addReview(Review review)
	{
		reviewList.add(review);
		reviewDAO.save(review);
	}
	public List<Review> getAllReviews()
	{
		return reviewDAO.findAll();
	}
	
	public void updateReview(long id,Review review)
	{
		Review existing_review=reviewDAO.findById(id).get();
		
		reviewList.remove(existing_review);
		existing_review.setComment(review.getComment());
		existing_review.setRating(review.getRating());
		existing_review.setReservation(review.getReservation());
		existing_review.setReview_date(review.getReview_date());
		
		reviewList.add(existing_review);
		
		reviewDAO.save(existing_review);
	}
	
	public void deleteReview(Long id)
	{
		Review review=reviewDAO.findById(id).get();
		reviewList.remove(review);
		reviewDAO.delete(review);
	}
	
    public boolean findReview(Review review) {
        if(reviewList.contains(review))
        {
     	   return true;
        }
        return false;
     }
    
    public boolean findById(long id) {
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

}
