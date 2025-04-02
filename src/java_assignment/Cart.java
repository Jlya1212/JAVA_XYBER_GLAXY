package java_assignment;

import java.util.Arrays;

public class Cart {
    private Product[] items = new Product[0];
    private int[] quantities = new int[0];

    public void addItem(Product product, int quantity) {
        // Check for existing product
        for (int i = 0; i < items.length; i++) {
            if (items[i].getProductID() == product.getProductID()) {
                quantities[i] += quantity;
                return;
            }
        }

        // Add new item
        items = Arrays.copyOf(items, items.length + 1);
        quantities = Arrays.copyOf(quantities, quantities.length + 1);
        items[items.length - 1] = product;
        quantities[quantities.length - 1] = quantity;
    }

    public Product[] getItems() {
        return Arrays.copyOf(items, items.length);
    }

    public int[] getQuantities() {
        return Arrays.copyOf(quantities, quantities.length);
    }

    public double getTotal() {
        double total = 0;
        for (int i = 0; i < items.length; i++) {
            total += items[i].getPrice() * quantities[i];
        }
        return total;
    }
    
    public void clear(){
        items = new Product[0];
        quantities = new int[0];
    }
    
}