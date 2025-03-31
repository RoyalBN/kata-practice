package org.example.service;

public class DefaultDiscountService {

    private static final double DISCOUNT = 0.9;

    public double applyDiscount(double total, boolean isDiscounted) {
        return isDiscounted ? total * DISCOUNT : total;
    }

}
