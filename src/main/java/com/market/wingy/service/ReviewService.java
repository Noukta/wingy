package com.market.wingy.service;

import com.market.wingy.model.Review;
import com.market.wingy.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Optional<Review> findReviewByCustomerAndShop(ObjectId shopId, ObjectId customerId) {
        return reviewRepository.findByShopIdAndCustomerId(shopId, customerId);
    }

    public Page<Review> findReviews(
            ObjectId shopId,
            Integer rating,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable) {
        return reviewRepository.findReviews(shopId, rating, start, end, pageable);
    }

    @Transactional
    public Review save(Review review) {
        if(reviewRepository.existsById(review.getId())){
            review.setUpdatedAt(LocalDateTime.now());
        }
        else review.setCreatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }
}
