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
    private OrderState state = OrderState.PENDING;
    private Map<OrderState, LocalDateTime> stateTimestamps = new EnumMap<>(OrderState.class);

    public Order() {
        stateTimestamps.put(OrderState.PENDING, LocalDateTime.now());
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