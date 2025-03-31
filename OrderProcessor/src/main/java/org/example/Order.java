package org.example;

import java.util.List;

public class Order {
    private int id;
    private String customerName;
    private List<String> items;
    private double total;

    public Order(int id, String customerName, List<String> items, double total) {
        this.id = id;
        this.customerName = customerName;
        this.items = items;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}