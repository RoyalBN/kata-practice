import org.example.Order;

import java.util.List;
import java.util.ArrayList;

public class OriginalExercice {
    private List<Order> orders = new ArrayList<>();

    public void processOrder(int id, String customerName, String[] items, double[] prices, boolean isDiscounted) {
        if (id <= 0) {
            System.out.println("Invalid ID");
            return;
        }

        double total = 0.0;
        for (double price : prices) {
            total += price;
        }

        if (isDiscounted) {
            total = total * 0.9;
        }

        Order newOrder = new Order(id, customerName, items, total);
        orders.add(newOrder);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Order findOrderById(int id) {
        for (Order order : orders) {
            if (order.getId() == id) {
                return order;
            }
        }
        return null;
    }

    public double calculateTotalRevenue() {
        double sum = 0.0;
        for (Order order : orders) {
            sum += order.getTotal();
        }
        return sum;
    }
}