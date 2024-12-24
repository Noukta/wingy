package com.market.wingy.repository;

import com.market.wingy.model.Shop;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends MongoRepository<Shop, ObjectId> {
    Page<Shop> findByFoodTypesContains(String foodType, Pageable pageable);
    Page<Shop> findByCategoriesContains(String category, Pageable pageable);
    Page<Shop> findByIsActivatedTrue(Pageable pageable);
}