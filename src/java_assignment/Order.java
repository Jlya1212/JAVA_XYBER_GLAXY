package java_assignment;
import java.util.Arrays;

public class Order implements Printable {
    private static int nextOrderId = 1;
    private int orderId;
    private Customer customer;
    private OrderItem[] items;
    private double totalPrice;
    private String discountCode;

    // Proper constructor
    public Order(Customer customer, OrderItem[] items, String discountCode) {
        this.orderId = nextOrderId++;
        this.customer = customer;
        this.items = Arrays.copyOf(items, items.length);
        this.discountCode = discountCode;
        calculateTotal();
    }

    private void calculateTotal() {
        totalPrice = Arrays.stream(items)
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();
        
        if(discountCode != null) {
            totalPrice *= 0.9; // Example 10% discount
        }
    }

    @Override
    public void printDetails() {
        System.out.println("======== Order #" + orderId + " ========");
        System.out.println("Customer: " + customer.getName());
        for (OrderItem item : items) {
            System.out.printf("%d x %s - $%.2f\n",
                    item.getQuantity(),
                    item.getProduct().getName(),
                    item.getTotalPrice());
        }
        System.out.printf("\nTotal: $%.2f\n", totalPrice);
        System.out.println("====================");
    }
}