package org.example.repository;

import org.example.model.Order;

import java.util.ArrayList;
import java.util.List;

public class InMemoryOrderRepository implements OrderRepository {

    private final List<Order> orders = new ArrayList<>();

    public List<Order> getOrders() {
        return orders;
    }

    public void save(Order order) {
        orders.add(order);
    }
}
