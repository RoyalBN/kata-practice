package org.example;

import java.util.List;
import java.util.ArrayList;

public class OrderProcessor {
    public static final double DISCOUNT = 0.9;
    private List<Order> orders = new ArrayList<>();

    public void processOrder(ProcessOrderRequest processOrderRequest) {
        validateId(processOrderRequest.getId());
        double orderTotal = calculateTotal(processOrderRequest.getPrices());
        orderTotal = applyDiscount(orderTotal, processOrderRequest.isDiscounted());
        createAndAddOrder(processOrderRequest.getId(), processOrderRequest.getCustomerName(), processOrderRequest.getItems(), orderTotal);
    }

    private double applyDiscount(double total, boolean isDiscounted) {
        return isDiscounted ? total * DISCOUNT : total;
    }

    private void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Order ID must be greater than zero");
        }
    }

    private void createAndAddOrder(int id, String customerName, List<String> items, double total) {
        Order newOrder = new OrderBuilder()
                .setId(id)
                .setCustomerName(customerName)
                .setItems(items)
                .setTotal(total)
                .build();

        orders.add(newOrder);
    }

    private double calculateTotal(List<Double> prices) {
        double total = prices.stream().mapToDouble(Double::doubleValue).sum();
        return total;
    }

    public List<Order> getOrders() {
        return orders;
    }
}