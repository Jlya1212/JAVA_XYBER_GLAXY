package java_assignment;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items = new ArrayList<>();

    // Add item to cart with quantity
    public void addItem(Product product, int quantity) {
        // Check for existing product in cart
        for (CartItem item : items) {
            if (item.getProduct().getProductID() == product.getProductID()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    // Get all cart items
    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    // Calculate total price
    public double getTotal() {
        return items.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }
}