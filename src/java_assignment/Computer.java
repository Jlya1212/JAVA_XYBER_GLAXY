
package java_assignment;

// Computer.java
public class Computer extends Product {
    private String processorType;
    private int ramSize;
    private int storageSize;

    public Computer(int productID, String name, String description, 
                   double price, int stockQuantity, String processorType, 
                   int ramSize, int storageSize) {
        super(productID, name, description, price, stockQuantity);
        this.processorType = processorType;
        this.ramSize = ramSize;
        this.storageSize = storageSize;
    }

    // Getters
    public String getProcessorType() { return processorType; }
    public int getRamSize() { return ramSize; }
    public int getStorageSize() { return storageSize; }

    @Override
    public void printDetails() {
        System.out.println("=== Computer Details ===");
        System.out.println("ID: " + getProductID());
        System.out.println("Name: " + getName());
        System.out.println("Description: " + getDescription());
        System.out.printf("Price: $%.2f\n", getPrice());
        System.out.println("Stock: " + getStockQuantity());
        System.out.println("Processor: " + processorType);
        System.out.println("RAM: " + ramSize + "GB");
        System.out.println("Storage: " + storageSize + "GB");
        System.out.println("========================");
    }
}
