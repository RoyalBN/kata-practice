package org.example;

import java.util.List;

public class OrderBuilder {
    private int id;
    private String customerName;
    private List<String> items;
    private double total;

    public OrderBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public OrderBuilder setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;

    }

    public OrderBuilder setItems(List<String> items) {
        this.items = items;
        return this;

    }

    public OrderBuilder setTotal(double total) {
        this.total = total;
        return this;

    }

    public Order build() {
        return new Order(id, customerName, items, total);
    }
}
