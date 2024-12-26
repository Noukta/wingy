package com.market.wingy.controller;

import com.market.wingy.model.Order;
import com.market.wingy.service.OrderService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable ObjectId id) {
        return orderService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/shop/{shopId}")
    public ResponseEntity<Page<Order>> getShopOrders(
            @PathVariable ObjectId shopId,
            @RequestParam(required = false) Order.OrderState state,
            Pageable pageable) {
        return ResponseEntity.ok(orderService.findByShopId(shopId, state, pageable));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Page<Order>> getCustomerOrders(
            @PathVariable ObjectId customerId,
            Pageable pageable) {
        return ResponseEntity.ok(orderService.findByCustomerId(customerId, pageable));
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.save(order));
    }

    @PutMapping("/{id}/state")
    public ResponseEntity<Order> updateOrderState(
            @PathVariable ObjectId id,
            @RequestParam Order.OrderState state) {
        return ResponseEntity.ok(orderService.updateOrderState(id, state));
    }
}