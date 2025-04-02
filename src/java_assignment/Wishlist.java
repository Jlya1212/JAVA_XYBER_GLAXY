package java_assignment;
import java.util.Arrays;

public class Wishlist {
    private Product[] items = new Product[0];
    
    public boolean addItem(Product product) {
        if (containsProduct(product.getProductID())) {
            return false; // Duplicate found
        }
        
        items = Arrays.copyOf(items, items.length + 1);
        items[items.length - 1] = product;
        return true;
    }

    public boolean removeItem(int productId) {
        if (items.length == 0) return false;

        boolean found = false;
        Product[] newItems = new Product[items.length - 1];
        int index = 0;
    
        for (Product item : items) {
            if (item.getProductID() != productId) {
                if (index < newItems.length) {
                    newItems[index++] = item;
                }
            } else {
                found = true;
            }
        }
    
        if (found) {
            items = newItems.length > 0 ? Arrays.copyOf(newItems, newItems.length) : new Product[0];
            return true;
        }
        return false;
    }

    public boolean containsProduct(int productId) {
        for (Product item : items) {
            if (item.getProductID() == productId) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return items.length == 0;
    }

    public Product[] getItems() {
        return Arrays.copyOf(items, items.length);
    }
}