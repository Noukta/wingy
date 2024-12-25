package com.market.wingy.service;

import com.market.wingy.model.Shop;
import com.market.wingy.model.ShopMember;
import com.market.wingy.repository.ShopMemberRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class ShopMemberService {

    @Autowired
    private ShopMemberRepository shopMemberRepository;

    public Optional<ShopMember> getShopMemberById(ObjectId id) {
        return shopMemberRepository.findById(id);
    }

    public Optional<ShopMember> getShopMemberByEmail(String email) {
        return shopMemberRepository.findByEmail(email);
    }

    public Optional<ShopMember> getShopMemberByPhone(String phone) {
        return shopMemberRepository.findByPhone(phone);
    }

    public Page<ShopMember> findAll(Pageable pageable) {
        return shopMemberRepository.findAll(pageable);
    }

    @Transactional
    public ShopMember createShopMember(ShopMember ShopMember) {
        if (shopMemberRepository.existsByEmail(ShopMember.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (shopMemberRepository.existsByPhone(ShopMember.getPhone())) {
            throw new IllegalArgumentException("Phone number already exists");
        }

        ShopMember.setJoined(LocalDateTime.now());
        ShopMember.setLastActivity(LocalDateTime.now());
        return shopMemberRepository.save(ShopMember);
    }

    @Transactional
    public Optional<ShopMember> updateShopMember(ObjectId id, ShopMember updatedShopMember) {
        return shopMemberRepository.findById(id)
                .map(existingShopMember -> {
                    existingShopMember.setFirstName(updatedShopMember.getFirstName());
                    existingShopMember.setLastName(updatedShopMember.getLastName());
                    existingShopMember.setBirthdate(updatedShopMember.getBirthdate());
                    existingShopMember.setGender(updatedShopMember.getGender());
                    existingShopMember.setShops(updatedShopMember.getShops());
                    existingShopMember.setLastActivity(LocalDateTime.now());
                    return shopMemberRepository.save(existingShopMember);
                });
    }

    @Transactional
    public void deleteShopMember(ObjectId id) {
        shopMemberRepository.deleteById(id);
    }

    @Transactional
    public void updateLastActivity(ObjectId id) {
        shopMemberRepository.findById(id)
                .ifPresent(ShopMember -> {
                    ShopMember.setLastActivity(LocalDateTime.now());
                    shopMemberRepository.save(ShopMember);
                });
    }

    @Transactional
    public Optional<ShopMember> addShop(ObjectId memberId, ObjectId shopId) {
        return shopMemberRepository.findById(memberId)
                .map(shopMember -> {
                    Shop shop = new Shop();
                    shop.setId(shopId);

                    if (shopMember.getShops() == null) {
                        shopMember.setShops(new ArrayList<>());
                    }

                    if (!shopMember.getShops().contains(shop)) {
                        shopMember.getShops().add(shop);
                        return shopMemberRepository.save(shopMember);
                    }

                    return shopMember;
                });
    }

    @Transactional
    public Optional<ShopMember> removeShop(ObjectId memberId, ObjectId shopId) {
        return shopMemberRepository.findById(memberId)
                .map(shopMember -> {
                    Shop shop = new Shop();
                    shop.setId(shopId);

                    if (shopMember.getShops() != null) {
                        shopMember.getShops().remove(shop);
                        return shopMemberRepository.save(shopMember);
                    }

                    return shopMember;
                });
    }
}