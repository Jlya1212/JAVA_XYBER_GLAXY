package java_assignment;

import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private static List<Product> products = new ArrayList<>();

    static {
        // Initialize with sample products
        addProduct(new Computer(1001, "Gaming Laptop", "High-performance gaming laptop", 1499.99, 10, "Intel i7-12700H", 32, 1000));
        addProduct(new Computer(1002, "Ultrabook", "Thin and light business laptop", 
                1299.99, 5, "AMD Ryzen 7", 16, 512));
        addProduct(new Peripheral(2001, "Wireless Mouse", "Ergonomic optical mouse", 
                49.99, 25, "Bluetooth", List.of("Windows", "macOS")));
        
        addProduct(new Accessory(3001, "Mechanical Keyboard", "RGB backlit keyboard", 
                129.99, 15, "Aluminum", "Black"));
        
        
    }

    public static void addProduct(Product product) {
        products.add(product);
    }

    public static List<Product> listProducts() {
        return new ArrayList<>(products); // Return copy to prevent modification
    }

    public static Product getProductById(int productId) {
        return products.stream()
                .filter(p -> p.getProductID() == productId)
                .findFirst()
                .orElse(null);
    }

    // Other methods remain the same
    // ...
}
