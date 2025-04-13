package java_assignment;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<CartItem> items = new ArrayList<>();


    public void addItem(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getProductID() == product.getProductID()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }


    public boolean removeItem(int productId) {
        return items.removeIf(item -> item.getProduct().getProductID() == productId);
    }


    public CartItem[] getItems() {
        return items.toArray(new CartItem[0]);
    }


    public void clear() {
        items.clear();
    }


    public double getTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }


    public boolean isEmpty() {
        return items.isEmpty();
    }
}
