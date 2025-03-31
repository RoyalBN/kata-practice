package repository;

import org.example.model.Order;
import org.example.repository.InMemoryOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryOrderRepositoryTest {

    private InMemoryOrderRepository inMemoryOrderRepository;

    private List<Order> orders = new ArrayList<>();
    private List<String> itemsList = new ArrayList<>();
    private Order order1;
    private Order order2;

    @BeforeEach
    void setUp() {
        inMemoryOrderRepository = new InMemoryOrderRepository();
        itemsList = Arrays.asList("Banana", "Apple", "Books");
        order1 = Order.builder().id(1).customerName("test").items(itemsList).total(12.0).build();
        order2 = Order.builder().id(2).customerName("test").items(itemsList).total(22.0).build();

        inMemoryOrderRepository.save(order1);
        inMemoryOrderRepository.save(order2);
    }

    @Test
    @DisplayName("Return list of orders")
    void should_return_the_list_of_orders() {
        // Act
        List<Order> ordersList = inMemoryOrderRepository.getOrders();

        // Verify
        assertThat(ordersList).isNotEmpty();
        assertThat(ordersList.size()).isEqualTo(2);
        assertEquals(2, ordersList.size());
    }

    @Test
    @DisplayName("Save new order")
    void should_save_new_order() {
        // Arrange
        Order newOrder = Order.builder()
                .id(3)
                .customerName("add-test")
                .items(itemsList)
                .total(42.0)
                .build();

        // Act
        inMemoryOrderRepository.save(newOrder);
        List<Order> orderListUpdated = inMemoryOrderRepository.getOrders();

        // Verify
        assertThat(orderListUpdated).isNotEmpty();
        assertThat(orderListUpdated.size()).isEqualTo(3);
        assertThat(orderListUpdated.get(2).getCustomerName()).isEqualTo("add-test");
        assertThat(orderListUpdated.get(2).getTotal()).isEqualTo(42.0);
    }
}