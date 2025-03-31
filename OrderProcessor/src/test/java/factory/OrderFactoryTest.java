package factory;

import org.example.factory.OrderFactory;
import org.example.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class OrderFactoryTest {

    private OrderFactory orderFactory;
    private List<String> itemsList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        orderFactory = new OrderFactory();
        itemsList = Arrays.asList("Banana", "Apple", "Books");
    }

    @Test
    @DisplayName("Create order")
    void should_create_order() {
        // Arrange
        int id = 1;
        String customerName = "create-order";
        double total = 12.0;

        // Act
        Order createdOrder = orderFactory.createOrder(id, customerName, itemsList, total);

        // Verify
        assertThat(createdOrder).isNotNull();
        assertThat(createdOrder.getId()).isEqualTo(1);
        assertThat(createdOrder.getCustomerName()).isEqualTo(customerName);
        assertThat(createdOrder.getTotal()).isEqualTo(total);
    }
}