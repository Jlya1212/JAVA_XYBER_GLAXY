package java_assignment;

public class Computer extends Product {
    private String processorType;
    private int ramSize;
    private int storageSize;

    public Computer(int productID, String name, String description, double price, int stockQuantity, String processorType, int ramSize, int storageSize) {
        super(productID, name, description, price, stockQuantity);
        this.processorType = processorType;
        this.ramSize = ramSize;
        this.storageSize = storageSize;
    }

    @Override
    public String getCategory() { return "Computer"; }

    @Override
    public void printDetails() {
        System.out.println("=== Computer Details ===");
        System.out.println("ID: " + getProductID());
        System.out.println("Name: " + getName());
        System.out.printf("Price: $%.2f\n", getPrice());
        System.out.println("Stock: " + getStockQuantity());
        System.out.println("Processor: " + processorType);
        System.out.println("RAM: " + ramSize + "GB");
        System.out.println("Storage: " + storageSize + "GB");
        System.out.println("========================");
    }
}