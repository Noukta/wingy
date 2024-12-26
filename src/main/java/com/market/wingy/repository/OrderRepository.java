package com.market.wingy.repository;

import com.market.wingy.model.Order;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, ObjectId> {
    Page<Order> findByShopId(ObjectId shopId, Pageable pageable);
    Page<Order> findByShopIdAndState(ObjectId shopId, Order.OrderState state, Pageable pageable);
    Page<Order> findByCustomerId(ObjectId customerId, Pageable pageable);
    Page<Order> findByShopIdAndCustomerId(ObjectId shopId, ObjectId customerId, Pageable pageable);
}
