package java_assignment;

import java.util.Arrays;

public class ProductManager {
    private static Product[] products = new Product[0];
    private static int productCount = 0;

    static {
        addProduct(new Computer(1001, "Gaming Laptop", "High-performance gaming laptop", 1499.99, 10, "Intel i7-12700H", 32, 1000));
        
        addProduct(new Peripheral(2001, "Wireless Mouse", "Ergonomic optical mouse", 49.99, 25, "Bluetooth", new String[] {"Windows", "macOS"}));
        
        addProduct(new Accessory(3001, "Mechanical Keyboard", "RGB backlit keyboard", 129.99, 15, "Aluminum", "Black"));
    }

    public static void addProduct(Product product) {
        products = Arrays.copyOf(products, products.length + 1);
        products[productCount++] = product;
    }

    public static Product[] getProductsByCategory(String category) {
        Product[] result = new Product[products.length];
        int count = 0;
        
        for (Product p : products) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                result[count++] = p;
            }
        }
        return Arrays.copyOf(result, count);
    }

    public static Product getProductById(int productId) {
        for (Product p : products) {
            if (p.getProductID() == productId) {
                return p;
            }
        }
        return null;
    }

    public static Product[] listProducts() {
        return Arrays.copyOf(products, products.length);
    }
}