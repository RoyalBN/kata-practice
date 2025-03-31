package org.example;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Order {
    private int id;
    private String customerName;
    private List<String> items;
    private double total;
}