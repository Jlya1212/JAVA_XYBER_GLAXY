package java_assignment;

public class Accessory extends Product {
    private String material;
    private String color;

    public Accessory(int productID, String name, String description, double price, int stockQuantity, String material, String color) {
        super(productID, name, description, price, stockQuantity);
        this.material = material;
        this.color = color;
    }

    @Override
    public String getCategory() { 
        return "Accessory"; 
    }

    @Override
    public void printDetails() {
        System.out.println("=== Accessory Details ===");
        System.out.println("ID: " + getProductID());
        System.out.println("Name: " + getName());
        System.out.printf("Price: RM%.2f\n", getPrice());
        System.out.println("Stock: " + getStockQuantity());
        System.out.println("Material: " + material);
        System.out.println("Color: " + color);
        System.out.println("========================");
    }
}