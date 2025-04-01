package java_assignment;

import java.util.Arrays;

public class Peripheral extends Product {
    private String connectionType;
    private String[] compatibleDevices;

    public Peripheral(int productID, String name, String description, double price, int stockQuantity, String connectionType, String[] compatibleDevices) {
        super(productID, name, description, price, stockQuantity);
        this.connectionType = connectionType;
        this.compatibleDevices = Arrays.copyOf(compatibleDevices, compatibleDevices.length);
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
        System.out.printf("Price: RM%.2f\n", getPrice());
        System.out.println("Stock: " + getStockQuantity());
        System.out.println("Connection: " + connectionType);
        System.out.println("Compatible with: " + String.join(", ", compatibleDevices));
        System.out.println("======================");
    }

    public String[] getCompatibleDevices() {
        return Arrays.copyOf(compatibleDevices, compatibleDevices.length);
    }
}