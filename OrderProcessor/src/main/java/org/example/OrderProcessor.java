package org.example;

import org.example.model.Order;
import org.example.model.ProcessOrderRequest;
import org.example.repository.OrderRepository;
import org.example.service.DiscountService;

import java.util.List;

public class OrderProcessor {

    private OrderRepository orderRepository;
    private DiscountService discountService;

    public OrderProcessor(OrderRepository orderRepository, DiscountService discountService) {
        this.orderRepository = orderRepository;
        this.discountService = discountService;
    }

    public void processOrder(ProcessOrderRequest processOrderRequest) {
        validateId(processOrderRequest.getId());
        double orderTotal = calculateTotal(processOrderRequest.getPrices());
        orderTotal = discountService.applyDiscount(orderTotal, processOrderRequest.isDiscounted());
        createAndAddOrder(processOrderRequest.getId(), processOrderRequest.getCustomerName(), processOrderRequest.getItems(), orderTotal);
    }

    private void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Order ID must be greater than zero");
        }
    }

    private double calculateTotal(List<Double> prices) {
        return prices.stream().mapToDouble(Double::doubleValue).sum();
    }

    private void createAndAddOrder(int id, String customerName, List<String> items, double total) {
        Order newOrder = Order.builder()
                .id(id)
                .customerName(customerName)
                .items(items)
                .total(total)
                .build();

        orderRepository.save(newOrder);
    }
}