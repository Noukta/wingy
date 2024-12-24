package com.market.wingy.controller;

import com.market.wingy.model.Shop;
import com.market.wingy.service.ShopService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shops")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @GetMapping("/{id}")
    public ResponseEntity<Shop> getShopById(@PathVariable ObjectId id) {
        return shopService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/foodType/{foodType}")
    public ResponseEntity<Page<Shop>> getShopsByFoodType(@PathVariable String foodType, Pageable pageable) {
        return ResponseEntity.ok(shopService.findByFoodType(foodType, pageable));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Page<Shop>> getShopsByCategory(@PathVariable String category, Pageable pageable) {
        return ResponseEntity.ok(shopService.findByCategory(category, pageable));
    }

    @GetMapping
    public ResponseEntity<Page<Shop>> getAllShops(Pageable pageable) {
        return ResponseEntity.ok(shopService.findAll(pageable));
    }

    @GetMapping("/activated")
    public ResponseEntity<Page<Shop>> getAllActivatedShops(Pageable pageable) {
        return ResponseEntity.ok(shopService.findAllActivated(pageable));
    }

    @PostMapping
    public ResponseEntity<Shop> createShop(@RequestBody Shop shop) {
        return ResponseEntity.ok(shopService.save(shop));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateShop(@PathVariable ObjectId id, @RequestBody Shop updatedShop) {
        return shopService.update(id, updatedShop)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShop(@PathVariable ObjectId id) {
        shopService.deleteShop(id);
        return ResponseEntity.noContent().build();
    }
}