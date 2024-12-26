package com.market.wingy.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {
    @Id
    private ObjectId id;
    @DocumentReference
    private Shop shop;
    @DocumentReference
    private Customer customer;
    private List<Item> items;
    private double deliveryPrice;
    private double totalPrice;
    private OrderState state;
    private Map<OrderState, LocalDateTime> stateTimestamps = new EnumMap<>(OrderState.class);

    public void calculatePrices() {
        double itemsTotal = items.stream()
                .mapToDouble(this::calculateItemPrice)
                .sum();
        totalPrice = itemsTotal + deliveryPrice;
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

    public void initiateState(){
        this.state = OrderState.PENDING;
        this.stateTimestamps.put(OrderState.PENDING, LocalDateTime.now());
    }
    public void updateState(OrderState newState) {
        if (isValidStateTransition(newState)) {
            this.state = newState;
            this.stateTimestamps.put(newState, LocalDateTime.now());
        } else {
            throw new IllegalStateException("Invalid state transition from " + state + " to " + newState);
        }
    }

    private boolean isValidStateTransition(OrderState newState) {
        return switch (state) {
            case PENDING -> newState == OrderState.PREPARING || newState == OrderState.DECLINED || newState == OrderState.CANCELED ;
            case PREPARING -> newState == OrderState.PREPARED;
            case PREPARED -> newState == OrderState.PICKED_UP;
            case PICKED_UP -> newState == OrderState.DELIVERED;
            default -> false;
        };
    }

    public enum OrderState {
        PENDING,
        PREPARING,
        PREPARED,
        PICKED_UP,
        DELIVERED,
        DECLINED,
        CANCELED
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        @DocumentReference
        private Product product;
        private List<List<Integer>> selectedExtras = new ArrayList<>();
        private int count;
    }
}