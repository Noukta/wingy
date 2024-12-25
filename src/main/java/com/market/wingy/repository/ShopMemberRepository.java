package com.market.wingy.repository;

import com.market.wingy.model.ShopMember;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopMemberRepository extends MongoRepository<ShopMember, ObjectId> {
    Optional<ShopMember> findByEmail(String email);
    Optional<ShopMember> findByPhone(String phone);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}