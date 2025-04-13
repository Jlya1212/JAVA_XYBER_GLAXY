package java_assignment;

import java.util.Arrays;
import java.util.Scanner;

public class Peripheral extends Product {

    private String connectivityType;
    private String[] compatibleOS;

    public Peripheral(int productID, String name, String description, double price, int stockQuantity, String connectivityType, String[] compatibleOS) {
        super(productID, name, description, price, stockQuantity);
        this.connectivityType = connectivityType;
        this.compatibleOS = compatibleOS;
    }

    public String getConnectivityType() { return connectivityType; }
    public String[] getCompatibleOS() { return compatibleOS; }

    public void setConnectivityType(String connectivityType) { this.connectivityType = connectivityType; }
    public void setCompatibleOS(String[] compatibleOS) { this.compatibleOS = compatibleOS; }

    @Override
    public String getCategory() {
        return "Peripheral";
    }

    @Override
    public void printDetails() {
        System.out.println("=== Peripheral Details ===");
        printBaseDetails();
        System.out.println("Connectivity: " + connectivityType);
        System.out.println("Compatible OS: " + String.join(", ", compatibleOS));
        System.out.println("==========================");
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

        System.out.print("Enter new connectivity type (" + connectivityType + "): ");
        connectivityType = scanner.nextLine();

        System.out.print("Enter new compatible OS (comma-separated) (" + String.join(", ", compatibleOS) + "): ");
        String[] osInput = scanner.nextLine().split(",");
        for (int i = 0; i < osInput.length; i++) {
            osInput[i] = osInput[i].trim(); // clean whitespace
        }
        compatibleOS = osInput;

        System.out.println("✅ Peripheral product updated.");
    }
}
