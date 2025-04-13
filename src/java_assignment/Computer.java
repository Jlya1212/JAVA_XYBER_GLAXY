package java_assignment;

import java.util.Scanner;

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

    public String getProcessorType() { return processorType; }
    public int getRamSize() { return ramSize; }
    public int getStorageSize() { return storageSize; }

    public void setProcessorType(String processorType) { this.processorType = processorType; }
    public void setRamSize(int ramSize) { this.ramSize = ramSize; }
    public void setStorageSize(int storageSize) { this.storageSize = storageSize; }

    @Override
    public String getCategory() {
        return "Computer";
    }

    @Override
    public void printDetails() {
        System.out.println("=== Computer Details ===");
        printBaseDetails();
        System.out.println("Processor: " + processorType);
        System.out.println("RAM: " + ramSize + "GB");
        System.out.println("Storage: " + storageSize + "GB");
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

        System.out.print("Enter new processor type (" + processorType + "): ");
        processorType = scanner.nextLine();

        System.out.print("Enter new RAM size (" + ramSize + "): ");
        ramSize = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter new storage size (" + storageSize + "): ");
        storageSize = Integer.parseInt(scanner.nextLine());

        System.out.println("✅ Computer product updated.");
    }
}
