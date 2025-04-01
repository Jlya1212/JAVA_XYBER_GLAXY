package java_assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductManager {
    private static List<Product> products = new ArrayList<>();

    static {
        // Preload sample products
        products.add(new Computer(1001, "Gaming Laptop", "High-end gaming laptop", 1499.99, 10, "Intel i7-12700H", 32, 1000));
        
        products.add(new Peripheral(2001, "Wireless Mouse", "Ergonomic optical mouse", 49.99, 25, "Bluetooth", List.of("Windows", "macOS")));
        
        products.add(new Accessory(3001, "Mechanical Keyboard", "RGB backlit keyboard", 129.99, 15, "Aluminum", "Black"));
    }

    public static List<Product> getProductsByCategory(String category) {
        if (category.equalsIgnoreCase("All")) {
            return new ArrayList<>(products);
        }
        return products.stream()
            .filter(p -> p.getCategory().equalsIgnoreCase(category))
            .collect(Collectors.toList());
    }

    public static Product getProductById(int productId) {
        return products.stream()
            .filter(p -> p.getProductID() == productId)
            .findFirst()
            .orElse(null);
    }
}