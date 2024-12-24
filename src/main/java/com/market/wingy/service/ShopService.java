package com.market.wingy.service;

import com.market.wingy.model.Shop;
import com.market.wingy.repository.ShopRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    public Page<Shop> findByFoodType(String foodType, Pageable pageable) {
        return shopRepository.findByFoodTypesContains(foodType, pageable);
    }

    public Page<Shop> findByCategory(String category, Pageable pageable) {
        return shopRepository.findByCategoriesContains(category, pageable);
    }

    public Optional<Shop> findById(ObjectId id) {
        return shopRepository.findById(id);
    }

    public Page<Shop> findAll(Pageable pageable) {
        return shopRepository.findAll(pageable);
    }

    public Page<Shop> findAllActivated(Pageable pageable) {
        return shopRepository.findByIsActivatedTrue(pageable);
    }

    public Optional<Shop> update(ObjectId id, Shop updatedShop) {
        return shopRepository.findById(id)
                .map(existingShop -> {
                    existingShop.setName(updatedShop.getName());
                    existingShop.setImage(updatedShop.getImage());
                    existingShop.setAddress(updatedShop.getAddress());
                    existingShop.setLocation(updatedShop.getLocation());
                    existingShop.setFoodTypes(updatedShop.getFoodTypes());
                    existingShop.setCategories(updatedShop.getCategories());
                    existingShop.setAvg_rating(updatedShop.getAvg_rating());
                    existingShop.setCount_rating(updatedShop.getCount_rating());
                    existingShop.setAvailabilityList(updatedShop.getAvailabilityList());

                    return shopRepository.save(existingShop);
                });
    }

    public Shop save(Shop shop) {
        return shopRepository.save(shop);
    }

    public void deleteShop(ObjectId id) {
        shopRepository.deleteById(id);
    }
}