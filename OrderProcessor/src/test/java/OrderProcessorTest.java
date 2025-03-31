import org.example.factory.OrderFactory;
import org.example.model.Order;
import org.example.OrderProcessor;
import org.example.model.ProcessOrderRequest;
import org.example.repository.OrderRepository;
import org.example.service.DiscountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderProcessorTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private DiscountService discountService;

    @Mock
    private OrderFactory orderFactory;

    @InjectMocks
    private OrderProcessor orderProcessor;

    private List<Order> orders = new ArrayList<>();
    private List<String> itemsList;
    private List<Double> prices;

    private Order orderWithoutDiscount;
    private Order orderWithDiscount;
    private String customerName;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        itemsList = Arrays.asList("Apple", "Banana", "Orange");
        prices = Arrays.asList(2.2, 3.3, 4.4);
        customerName = "test";

        orderWithoutDiscount = Order.builder().id(1).customerName("test").items(itemsList).total(9.9).build();
        orderWithDiscount = Order.builder().id(2).customerName("test").items(itemsList).total(8.91).build();
    }

    @Test
    @DisplayName("Exit if ID <= 0")
    void should_exit_if_id_is_inferior_or_equal_to_zero() {
        // Arrange
        int invalidId = -1;

        ProcessOrderRequest processOrderRequest = ProcessOrderRequest.builder()
                .id(invalidId)
                .customerName(customerName)
                .items(itemsList)
                .prices(prices)
                .isDiscounted(false)
                .build();

        // Act
        Throwable exceptionThrown = catchThrowable(() -> orderProcessor.processOrder(processOrderRequest));
        List<Order> orders = orderRepository.getOrders();

        // Verify
        assertThat(exceptionThrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Order ID must be greater than zero");

        assertThat(orders).isEmpty();

    }

    @Test
    @DisplayName("Create new order without discount")
    void should_create_new_order_without_discount() {
        // Arrange
        int validId = 1;
        double expectedTotalWithDiscount = 9.9;
        when(orderRepository.getOrders()).thenReturn(List.of(orderWithoutDiscount));

        ProcessOrderRequest processOrderRequest = ProcessOrderRequest.builder()
                .id(validId)
                .customerName(customerName)
                .items(itemsList)
                .prices(prices)
                .isDiscounted(false)
                .build();

        // Act
        orderProcessor.processOrder(processOrderRequest);
        List<Order> orders = orderRepository.getOrders();

        // Verify
        assertThat(orders).isNotEmpty();
        assertThat(orders.size()).isEqualTo(1);
        assertThat(orders.get(0).getTotal()).isEqualTo(expectedTotalWithDiscount);
        assertThat(orders.get(0).getCustomerName()).isEqualTo(customerName);
    }

    @Test
    @DisplayName("Add discount to total")
    void should_add_discount_to_total() {
        // Arrange
        int validId = 2;
        double total = prices.stream().mapToDouble(Double::doubleValue).sum(); // Calculer le vrai total
        double expectedTotalWithDiscount = 8.91;


        when(discountService.applyDiscount(total, true)).thenReturn(expectedTotalWithDiscount);
        when(orderFactory.createOrder(anyInt(), anyString(), anyList(), anyDouble())).thenReturn(orderWithDiscount);

        ProcessOrderRequest processOrderRequest = ProcessOrderRequest.builder()
                .id(validId)
                .customerName(customerName)
                .items(itemsList)
                .prices(prices)
                .isDiscounted(true)
                .build();

        // Act
        orderProcessor.processOrder(processOrderRequest);

        // Capture l'Order sauvegardé
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderCaptor.capture());

        Order savedOrder = orderCaptor.getValue(); // Récupération de l'Order sauvegardé

        // Verify
        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getTotal()).isEqualTo(expectedTotalWithDiscount);
        assertThat(savedOrder.getCustomerName()).isEqualTo(customerName);
        assertThat(savedOrder.getItems()).containsExactlyElementsOf(itemsList);

        verify(discountService, times(1)).applyDiscount(total, true);
        verify(orderFactory, times(1)).createOrder(anyInt(), anyString(), anyList(), anyDouble());
        verify(orderRepository, times(1)).save(any(Order.class));
    }
}
