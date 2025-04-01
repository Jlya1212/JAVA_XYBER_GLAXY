/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_assignment;

import java.util.ArrayList;
import java.util.List;

public class Peripheral extends Product {
    private String connectionType;
    private List<String> compatibleDevices;

    public Peripheral(int productID, String name, String description, double price, int stockQuantity, String connectionType, List<String> compatibleDevices) {
        super(productID, name, description, price, stockQuantity);
        this.connectionType = connectionType;
        this.compatibleDevices = new ArrayList<>(compatibleDevices);
    }

    // Getters
    public String getConnectionType() { 
        return connectionType; 
    }
    public List<String> getCompatibleDevices() { 
        return new ArrayList<>(compatibleDevices); 
    }

    @Override
    public void printDetails() {
        System.out.println("=== Peripheral Details ===");
        System.out.println("ID: " + getProductID());
        System.out.println("Name: " + getName());
        System.out.println("Description: " + getDescription());
        System.out.printf("Price: $%.2f\n", getPrice());
        System.out.println("Stock: " + getStockQuantity());
        System.out.println("Connection Type: " + connectionType);
        System.out.println("Compatible with: " + String.join(", ", compatibleDevices));
        System.out.println("=========================");
    }
}
