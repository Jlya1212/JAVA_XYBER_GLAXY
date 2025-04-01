package java_assignment;

import java.util.List;
import java.util.ArrayList;

public class Peripheral extends Product {
    private String connectionType;
    private List<String> compatibleDevices;

    public Peripheral(int productID, String name, String description, double price, int stockQuantity, String connectionType, List<String> compatibleDevices) {
        super(productID, name, description, price, stockQuantity);
        this.connectionType = connectionType;
        this.compatibleDevices = new ArrayList<>(compatibleDevices);
    }

    @Override
    public String getCategory() { 
        return "Peripheral"; 
    }

    @Override
    public void printDetails() {
        System.out.println("=== Peripheral Details ===");
        System.out.println("ID: " + getProductID());
        System.out.println("Name: " + getName());
        System.out.printf("Price: $%.2f\n", getPrice());
        System.out.println("Stock: " + getStockQuantity());
        System.out.println("Connection: " + connectionType);
        System.out.println("Compatible with: " + String.join(", ", compatibleDevices));
        System.out.println("==========================");
    }
}