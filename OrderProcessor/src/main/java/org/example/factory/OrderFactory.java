package org.example.factory;

import org.example.model.Order;

import java.util.List;

public class OrderFactory {

    public Order createOrder(int id, String customerName, List<String> items, double total) {
        return Order.builder()
                .id(id)
                .customerName(customerName)
                .items(items)
                .total(total)
                .build();
    }
}
