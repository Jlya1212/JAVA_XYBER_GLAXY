package java_assignment;
import java.util.Arrays;

public class OrderManager {
    private static Order[] orders = new Order[0];
    
    public static boolean placeOrder(Customer customer, String discountCode) {
        Cart cart = customer.getCart();
        OrderItem[] orderItems = convertCartToOrderItems(cart);
        
        // Validate stock before checkout
        if (!validateStock(orderItems)) {
            System.out.println("Checkout failed: Insufficient stock");
            return false;
        }
        
        // Process order
        updateStock(orderItems);
        Order order = new Order(customer, orderItems, discountCode);
        
        // Add to orders array
        orders = Arrays.copyOf(orders, orders.length + 1);
        orders[orders.length - 1] = order;
        
        // Clear cart
        cart.clear();
        return true;
    }

    private static OrderItem[] convertCartToOrderItems(Cart cart) {
        Product[] products = cart.getItems();
        int[] quantities = cart.getQuantities();
        OrderItem[] items = new OrderItem[products.length];
        
        for (int i = 0; i < products.length; i++) {
            items[i] = new OrderItem(products[i], quantities[i]);
        }
        return items;
    }

    private static boolean validateStock(OrderItem[] items) {
        for (OrderItem item : items) {
            if (item.getProduct().getStockQuantity() < item.getQuantity()) {
                return false;
            }
        }
        return true;
    }

    private static void updateStock(OrderItem[] items) {
        for (OrderItem item : items) {
            item.getProduct().reduceStock(item.getQuantity());
        }
    }
}