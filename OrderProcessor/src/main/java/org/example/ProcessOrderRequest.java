package org.example;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProcessOrderRequest {
    private final int id;
    private final String customerName;
    private final List<String> items;
    private final List<Double> prices;
    private final boolean isDiscounted;
}
