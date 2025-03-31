package org.example;

import org.example.factory.OrderFactory;
import org.example.model.Order;
import org.example.model.ProcessOrderRequest;
import org.example.repository.OrderRepository;
import org.example.service.DiscountService;

import java.util.List;

public class OrderProcessor {

    private final OrderRepository orderRepository;
    private final DiscountService discountService;
    private final OrderFactory orderFactory;

    public OrderProcessor(OrderRepository orderRepository, DiscountService discountService, OrderFactory orderFactory) {
        this.orderRepository = orderRepository;
        this.discountService = discountService;
        this.orderFactory = orderFactory;
    }

    public void processOrder(ProcessOrderRequest processOrderRequest) {
        validateId(processOrderRequest.getId());
        double orderTotal = calculateTotal(processOrderRequest.getPrices());
        orderTotal = discountService.applyDiscount(orderTotal, processOrderRequest.isDiscounted());
        Order orderToCreate = orderFactory.createOrder(processOrderRequest.getId(), processOrderRequest.getCustomerName(), processOrderRequest.getItems(), orderTotal);
        orderRepository.save(orderToCreate);
    }

    private void validateId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Order ID must be greater than zero");
        }
    }

    private double calculateTotal(List<Double> prices) {
        return prices.stream().mapToDouble(Double::doubleValue).sum();
    }
}