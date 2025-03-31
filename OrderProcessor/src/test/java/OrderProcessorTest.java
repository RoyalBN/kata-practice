import org.example.Order;
import org.example.OrderProcessor;
import org.example.ProcessOrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderProcessorTest {

    private Order order;
    private OrderProcessor orderProcessor;
    private List<Order> orders = new ArrayList<>();
    private List<String> items = Arrays.asList("Apple", "Banana", "Orange");
    private List<Double> prices = Arrays.asList(2.2, 3.3, 4.4);
    private String customerName = "test";
    double total = 9.9;

    @BeforeEach
    void setUp() {
        this.orderProcessor = new OrderProcessor();
        this.orders = null;
    }

    @Test
    @DisplayName("Exit if ID <= 0")
    void should_exit_if_id_is_inferior_or_equal_to_zero() {
        // Arrange
        int invalidId = -1;

        ProcessOrderRequest processOrderRequest = ProcessOrderRequest.builder()
                .id(invalidId)
                .customerName(customerName)
                .items(items)
                .prices(prices)
                .isDiscounted(false)
                .build();

        // Act
        Throwable exceptionThrown = catchThrowable(() -> orderProcessor.processOrder(processOrderRequest));
        this.orders = orderProcessor.getOrders();

        // Verify
        assertThat(exceptionThrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Order ID must be greater than zero");

        assertThat(this.orders).isEmpty();

    }

    @Test
    @DisplayName("Create new order")
    void should_create_new_order() {
        // Arrange
        int validId = 1;

        ProcessOrderRequest processOrderRequest = ProcessOrderRequest.builder()
                .id(validId)
                .customerName(customerName)
                .items(items)
                .prices(prices)
                .isDiscounted(false)
                .build();

        // Act
        orderProcessor.processOrder(processOrderRequest);
        this.orders = orderProcessor.getOrders();

        // Verify
        assertThat(orders).isNotEmpty();
        assertThat(orders.size()).isEqualTo(1);
        assertThat(orders.get(0).getCustomerName()).isEqualTo(customerName);
    }

    @Test
    @DisplayName("Add discount to total")
    void should_add_discount_to_total() {
        // Arrange
        int validId = 1;

        ProcessOrderRequest processOrderRequest = ProcessOrderRequest.builder()
                .id(validId)
                .customerName(customerName)
                .items(items)
                .prices(prices)
                .isDiscounted(true)
                .build();

        // Act
        orderProcessor.processOrder(processOrderRequest);
        this.orders = orderProcessor.getOrders();

        // Verify
        assertThat(orders).isNotEmpty();
        assertThat(orders.size()).isEqualTo(1);
        assertThat(orders.get(0).getTotal()).isEqualTo(8.91);
    }
}
