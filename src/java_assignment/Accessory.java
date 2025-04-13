package java_assignment;

import java.util.Scanner;

public class Accessory extends Product {

    private String material;
    private String color;

    public Accessory(int productID, String name, String description, double price, int stockQuantity, String material, String color) {
        super(productID, name, description, price, stockQuantity);
        this.material = material;
        this.color = color;
    }

    public String getMaterial() { return material; }
    public String getColor() { return color; }

    public void setMaterial(String material) { this.material = material; }
    public void setColor(String color) { this.color = color; }

    @Override
    public String getCategory() {
        return "Accessory";
    }

    @Override
    public void printDetails() {
        System.out.println("=== Accessory Details ===");
        printBaseDetails();
        System.out.println("Material: " + material);
        System.out.println("Color: " + color);
        System.out.println("========================");
    }

    @Override
    public void updateFromInput(Scanner scanner) {
        System.out.print("Enter new name (" + getName() + "): ");
        setName(scanner.nextLine());

        System.out.print("Enter new description (" + getDescription() + "): ");
        setDescription(scanner.nextLine());

        System.out.print("Enter new price (" + getPrice() + "): ");
        setPrice(Double.parseDouble(scanner.nextLine()));

        System.out.print("Enter new stock quantity (" + getStockQuantity() + "): ");
        setStockQuantity(Integer.parseInt(scanner.nextLine()));

        System.out.print("Enter new material (" + material + "): ");
        material = scanner.nextLine();

        System.out.print("Enter new color (" + color + "): ");
        color = scanner.nextLine();

        System.out.println(" Accessory product updated.");
    }
}
