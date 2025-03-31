package org.example;

import java.util.ArrayList;
import java.util.List;

public class ProcessOrderRequest {

        private final int id;
        private final String customerName;
        private final List<String> items;
        private final List<Double> prices;
        private final boolean isDiscounted;

        private ProcessOrderRequest(Builder processOrderBuilder) {
            this.id = processOrderBuilder.id;
            this.customerName = processOrderBuilder.customerName;
            this.items = processOrderBuilder.items;
            this.prices = processOrderBuilder.prices;
            this.isDiscounted = processOrderBuilder.isDiscounted;
        }

        public int getId() {return id;}
        public String getCustomerName() {return customerName;}
        public List<String> getItems() {return items;}
        public List<Double> getPrices() {return prices;}
        public boolean isDiscounted() {return isDiscounted;}

        public static class Builder {
            private int id;
            private String customerName;
            private List<String> items = new ArrayList<>();
            private List<Double> prices = new ArrayList<>();
            private boolean isDiscounted;

            public Builder setId(int id) {
                this.id = id;
                return this;
            }

            public Builder setCustomerName(String customerName) {
                this.customerName = customerName;
                return this;
            }

            public Builder setItems(List<String> items) {
                this.items = items;
                return this;
            }

            public Builder setPrices(List<Double> prices) {
                this.prices = prices;
                return this;
            }

            public Builder setDiscounted(boolean isDiscounted) {
                this.isDiscounted = isDiscounted;
                return this;
            }

            public ProcessOrderRequest build() {
                return new ProcessOrderRequest(this);
            }
        }
}
