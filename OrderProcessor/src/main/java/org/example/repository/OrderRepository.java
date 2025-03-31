package org.example.repository;

import org.example.model.Order;

import java.util.List;

public interface OrderRepository {
    void save(Order order);
    List<Order> getOrders();
}
