package java_assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductManager {
    // List to store all product objects
    private List<Product> products;

    // Constructor - adds some default products for testing
    public ProductManager() {
        products = new ArrayList<>();

        // Sample Data
        addProduct(new Computer(1001, "Gaming Laptop", "High-performance gaming laptop", 1499.99, 10, "Intel i7-12700H", 32, 1000));
        addProduct(new Peripheral(2001, "Wireless Mouse", "Ergonomic optical mouse", 49.99, 25, "Bluetooth", new String[]{"Windows", "macOS"}));
        addProduct(new Accessory(3001, "Mechanical Keyboard", "RGB backlit keyboard", 129.99, 15, "Aluminum", "Black"));
    }

    // 1. Add a product
    public void addProduct(Product product) {
        products.add(product);
    }

    // 2. Delete a product by ID
    public boolean deleteProduct(int productId) {
        Product p = getProductById(productId);
        if (p != null) {
            products.remove(p);
            return true;
        }
        return false;
    }

    // 3. Update a product by ID (using updateFromInput from Printable)
    public boolean updateProduct(int productId, Scanner scanner) {
        Product p = getProductById(productId);
        if (p != null) {
            p.updateFromInput(scanner);
            return true;
        }
        return false;
    }

    // 4. Search for a product by ID
    public Product getProductById(int productId) {
        for (Product p : products) {
            if (p.getProductID() == productId) {
                return p;
            }
        }
        return null;
    }

    // 5. Get all products as array
    public Product[] listProducts() {
        return products.toArray(new Product[0]);
    }

    // 6. Get all products from a specific category
    public Product[] getProductsByCategory(String category) {
        List<Product> filtered = new ArrayList<>();
        for (Product p : products) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                filtered.add(p);
            }
        }
        return filtered.toArray(new Product[0]);
    }
}
