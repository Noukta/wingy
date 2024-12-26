package com.market.wingy.service;

import com.market.wingy.model.Extra;
import com.market.wingy.model.Order;
import com.market.wingy.repository.OrderRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Optional<Order> findById(ObjectId id) {
        return orderRepository.findById(id);
    }

    public Page<Order> findByShopId(ObjectId shopId, Order.OrderState state, Pageable pageable) {
        if(state == null) return orderRepository.findByShopId(shopId, pageable);
        return orderRepository.findByShopIdAndState(shopId, state, pageable);
    }

    public Page<Order> findByCustomerId(ObjectId customerId, Pageable pageable) {
        return orderRepository.findByCustomerId(customerId, pageable);
    }

    @Transactional
    public Order save(Order order) {
        calculatePrices(order);
        return orderRepository.save(order);
    }

    @Transactional
    public Order updateOrderState(ObjectId orderId, Order.OrderState newState) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.updateState(newState);
        return orderRepository.save(order);
    }

    private void calculatePrices(Order order) {
        double itemsTotal = order.getItems().stream()
                .mapToDouble(this::calculateItemPrice)
                .sum();

        order.setTotalPrice(itemsTotal + order.getDeliveryPrice());
    }

    private double calculateItemPrice(Order.Item item) {
        double basePrice = item.getProduct().getPrice();
        double extrasPrice = calculateExtrasPrice(item);
        return (basePrice + extrasPrice) * item.getCount();
    }

    private double calculateExtrasPrice(Order.Item item) {
        double extrasPrice = 0;
        for (int optionIndex = 0; optionIndex < item.getSelectedExtras().size(); optionIndex++) {
            for (Integer index : item.getSelectedExtras().get(optionIndex)) {
                Extra extra = item.getProduct().getOptions().get(optionIndex).getExtras().get(index);
                extrasPrice += extra.getPrice();
            }
        }
        return extrasPrice;
    }
}