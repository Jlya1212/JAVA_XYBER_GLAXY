/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_assignment;

/**
 *
 * @author justin
 */
// Accessory.java
public class Accessory extends Product {
    private String material;
    private String color;

    public Accessory(int productID, String name, String description,
                    double price, int stockQuantity, String material,
                    String color) {
        super(productID, name, description, price, stockQuantity);
        this.material = material;
        this.color = color;
    }

    // Getters
    public String getMaterial() { 
        return material; 
    }
    public String getColor() { 
        return color; 
    }

    @Override
    public void printDetails() {
        System.out.println("=== Accessory Details ===");
        System.out.println("ID: " + getProductID());
        System.out.println("Name: " + getName());
        System.out.println("Description: " + getDescription());
        System.out.printf("Price: $%.2f\n", getPrice());
        System.out.println("Stock: " + getStockQuantity());
        System.out.println("Material: " + material);
        System.out.println("Color: " + color);
        System.out.println("========================");
    }
}
