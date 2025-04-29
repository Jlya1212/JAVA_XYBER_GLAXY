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
        // Your Original Sample Data
        addProduct(new Computer(1001, "Gaming Laptop", "High-performance gaming laptop", 1499.99, 10, "Intel i7-12700H", 32, 1000));
        addProduct(new Peripheral(2001, "Wireless Mouse", "Ergonomic optical mouse", 49.99, 25, "Bluetooth", new String[]{"Windows", "macOS"}));
        addProduct(new Accessory(3001, "Mechanical Keyboard", "RGB backlit keyboard", 129.99, 15, "Aluminum", "Black"));
        // Adding the other sample data back for better testing
        addProduct(new Computer(1002, "Office Desktop", "Reliable workstation PC", 799.00, 20, "Intel i5-11400", 16, 512));
        addProduct(new Peripheral(2002, "Webcam", "1080p HD Webcam with Mic", 65.00, 18, "USB", new String[]{"Windows", "macOS"}));
        addProduct(new Accessory(3002, "USB-C Hub", "7-in-1 Hub", 39.99, 30, "Plastic", "Grey"));
    }

    // 1. Add a product (Check for duplicate ID)
    public void addProduct(Product product) {
         if (product != null) {
            // Check if product with the same ID already exists
            if (getProductById(product.getProductID()) == null) {
                 products.add(product);
            } else {
                 System.out.println("⚠️ Warning: Product with ID " + product.getProductID() + " already exists. Not added.");
            }
        }
    }

    // 2. Delete a product by ID (Add feedback)
    public boolean deleteProduct(int productId) {
        Product p = getProductById(productId);
        if (p != null) {
            products.remove(p);
             System.out.println("✅ Product ID " + productId + " (" + p.getName() + ") deleted."); // Feedback
            return true;
        }
        System.out.println("❌ Product ID " + productId + " not found for deletion."); // Feedback
        return false;
    }

    // 3. Update a product by ID (Add feedback)
    public boolean updateProduct(int productId, Scanner scanner) {
        Product p = getProductById(productId);
        if (p != null) {
            System.out.println("\n--- Updating Product ID: " + productId + " (" + p.getName() + ") ---");
            p.updateFromInput(scanner); // Delegate update logic
            // Success message printed within Product's updateFromInput
            System.out.println("--- Finished updating Product ID: " + productId + " ---");
            return true;
        }
        System.out.println("❌ Product ID " + productId + " not found for update."); // Feedback
        return false;
    }

    // 4. Search for a product by ID
    public Product getProductById(int productId) {
        for (Product p : products) {
             // Add null check for robustness, although products list shouldn't contain nulls with current addProduct logic
             if (p != null && p.getProductID() == productId) {
                return p;
            }
        }
        return null; // Not found
    }

    // 5. Get all products as array
    public Product[] listProducts() {
        return products.toArray(new Product[0]);
    }

    // 6. Get all products from a specific category (ADJUSTED)
    public Product[] getProductsByCategory(String category) {
        
        if (category == null || category.trim().equalsIgnoreCase("All")) {
            return listProducts(); 
        }
        

        List<Product> filtered = new ArrayList<>();
        String trimmedCategory = category.trim(); 

        for (Product p : products) {
            // Check product's category, ignore case (and check for null product)
            if (p != null && p.getCategory().equalsIgnoreCase(trimmedCategory)) {
                filtered.add(p);
            }
        }
        return filtered.toArray(new Product[0]);
    }

   
    public Product[] getLowStockProducts(int threshold) {
     if (threshold < 0) {
         System.out.println("Warning: Low stock threshold cannot be negative. Returning empty list.");
         return new Product[0];
     }

     List<Product> lowStock = new ArrayList<>();
     for (Product p : this.products) {
         if (p != null && p.getStockQuantity() <= threshold) {
             lowStock.add(p);
         }
     }
     return lowStock.toArray(new Product[0]);
 }
}