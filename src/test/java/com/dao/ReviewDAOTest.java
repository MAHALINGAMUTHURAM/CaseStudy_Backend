package com.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.model.Review;
import com.service.ReviewService; // Assuming you have a service that uses the DAO

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class ReviewDAOTest {

    @Mock
    private ReviewDAO reviewDAO;

    @InjectMocks
    private ReviewService reviewService; // Assuming you have a service that uses the DAO

    private Review review;
    private final int rating = 5; // Dynamic value for rating
    private final long reservationId = 1L; // Dynamic value for reservation ID
    private final long hotelId = 2L; // Dynamic value for hotel ID

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Initialize Review object
        review = new Review();
        review.setReview_id(1L); // Assuming Review has an ID field
        review.setRating(rating); // Set rating dynamically
        // Set other properties as needed
    }

    @Test
    public void testFindByRating() {
        // Given
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);
        
        when(reviewDAO.findByRating(rating)).thenReturn(reviews);

        // When
        List<Review> foundReviews = reviewDAO.findByRating(rating);

        // Then
        assertThat(foundReviews).isNotEmpty();
        assertThat(foundReviews.get(0).getRating()).isEqualTo(rating);

        // Verify that the method was called once
        verify(reviewDAO, times(1)).findByRating(rating);
    }

    @Test
    public void testFindByRating_NoResults() {
        // Given
        when(reviewDAO.findByRating(rating)).thenReturn(new ArrayList<>());

        // When
        List<Review> foundReviews = reviewDAO.findByRating(rating);

        // Then
        assertThat(foundReviews).isEmpty();

        // Verify that the method was called once
        verify(reviewDAO, times(1)).findByRating(rating);
    }

    @Test
    public void testFindRecentReviews() {
        // Given
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);
        
        when(reviewDAO.findRecentReviews()).thenReturn(reviews);

        // When
        List<Review> foundReviews = reviewDAO.findRecentReviews();

        // Then
        assertThat(foundReviews).isNotEmpty();
        
        // Verify that the method was called once
        verify(reviewDAO, times(1)).findRecentReviews();
    }

    @Test
    public void testFindByReservation_ReservationId() {
        // Given
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);
        
        when(reviewDAO.findByReservation_ReservationId(reservationId)).thenReturn(reviews);

        // When
        List<Review> foundReviews = reviewDAO.findByReservation_ReservationId(reservationId);

        // Then
        assertThat(foundReviews).isNotEmpty();
        assertThat(foundReviews.get(0).getReview_id()).isEqualTo(review.getReview_id());

        // Verify that the method was called once
        verify(reviewDAO, times(1)).findByReservation_ReservationId(reservationId);
    }

    @Test
    public void testFindByReservation_ReservationId_NoResults() {
        // Given
        when(reviewDAO.findByReservation_ReservationId(reservationId)).thenReturn(new ArrayList<>());

        // When
        List<Review> foundReviews = reviewDAO.findByReservation_ReservationId(reservationId);

        // Then
        assertThat(foundReviews).isEmpty();

        // Verify that the method was called once
        verify(reviewDAO, times(1)).findByReservation_ReservationId(reservationId);
    }

    @Test
    public void testFindByHotel_HotelId() {
       // Given 
       List<Review> reviews = new ArrayList<>();
       reviews.add(review);
       
       when(reviewDAO.findByHotel_HotelId(hotelId)).thenReturn(reviews);

       // When 
       List<Review> foundReviews = reviewDAO.findByHotel_HotelId(hotelId);

       // Then 
       assertThat(foundReviews).isNotEmpty();
       assertThat(foundReviews.get(0).getReview_id()).isEqualTo(review.getReview_id());

       // Verify that the method was called once 
       verify(reviewDAO, times(1)).findByHotel_HotelId(hotelId);
   }

   @Test 
   public void testFindByHotel_HotelId_NoResults() { 
       // Given 
       when(reviewDAO.findByHotel_HotelId(hotelId)).thenReturn(new ArrayList<>());

       // When 
       List<Review> foundReviews = reviewDAO.findByHotel_HotelId(hotelId);

       // Then 
       assertThat(foundReviews).isEmpty();

       // Verify that the method was called once 
       verify(reviewDAO, times(1)).findByHotel_HotelId(hotelId); 
   }
}
