package com.market.wingy.repository;

import com.market.wingy.model.Review;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ReviewRepository extends MongoRepository<Review, ObjectId> {

    Optional<Review> findByShopIdAndCustomerId(ObjectId shopId, ObjectId customerId);

    @Query(value = "{ " +
            "'shop._id': ?0, " +
            "$expr: { " +
            "  $and: [ " +
            "    { $or: [ { $eq: ['?1', null] }, { $eq: ['$rating', ?1] } ] }, " +
            "    { $gte: ['$createdAt', ?2] }, " +
            "    { $lte: ['$createdAt', ?3] } " +
            "  ] " +
            "} }")
    Page<Review> findReviews(
            ObjectId shopId,
            Integer rating,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
    );
}