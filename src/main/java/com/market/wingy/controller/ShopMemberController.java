package com.market.wingy.controller;

import com.market.wingy.model.ShopMember;
import com.market.wingy.service.ShopMemberService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shop-members")
@RequiredArgsConstructor
public class ShopMemberController {
    private final ShopMemberService shopMemberService;

    @GetMapping
    public ResponseEntity<Page<ShopMember>> getAllShopMembers(Pageable pageable) {
        return ResponseEntity.ok(shopMemberService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopMember> getShopMemberById(@PathVariable ObjectId id) {
        return shopMemberService.getShopMemberById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ShopMember> getShopMemberByEmail(@PathVariable String email) {
        return shopMemberService.getShopMemberByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<ShopMember> getShopMemberByPhone(@PathVariable String phone) {
        return shopMemberService.getShopMemberByPhone(phone)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ShopMember> createShopMember(@RequestBody ShopMember shopMember) {
        return ResponseEntity.ok(shopMemberService.createShopMember(shopMember));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShopMember> updateShopMember(
            @PathVariable ObjectId id,
            @RequestBody ShopMember updatedShopMember) {
        return shopMemberService.updateShopMember(id, updatedShopMember)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/last-activity")
    public ResponseEntity<Void> updateLastActivity(@PathVariable ObjectId id) {
        shopMemberService.updateLastActivity(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShopMember(@PathVariable ObjectId id) {
        shopMemberService.deleteShopMember(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{memberId}/shops/{shopId}")
    public ResponseEntity<ShopMember> addShop(
            @PathVariable ObjectId memberId,
            @PathVariable ObjectId shopId) {
        return shopMemberService.addShop(memberId, shopId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{memberId}/shops/{shopId}")
    public ResponseEntity<ShopMember> removeShop(
            @PathVariable ObjectId memberId,
            @PathVariable ObjectId shopId) {
        return shopMemberService.removeShop(memberId, shopId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}