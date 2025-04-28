/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_assignment;


import java.util.Scanner;

/**
 *
 * @author user
 */
public class DiscountManager{
    Scanner input = new Scanner(System.in);
    private Discount[] discounts = new Discount[10];
    private int count = 0;
    //String status = "Active";
    
    public DiscountManager() {
        // Add some sample discounts for testing
        addCode("SAVE10", 10.0);
        addCode("NEWUSER", 20.0);
        // Make one inactive for testing
        if (count > 0) discounts[0].setIsActive(false);
         System.out.println("Discount Manager initialized.");
         if (count > 0 && !discounts[0].getIsActive()) System.out.println(" - Note: '" + discounts[0].getDiscountCode() + "' is currently inactive.");
    }

    // Helper for constructor/testing
    private void addCode(String code, double percentage){
        if (count < discounts.length) {
            Discount d = new Discount(code, percentage);
            discounts[count++] = d;
        }
    }

     public Discount getDiscounts(int address) { // Should be getDiscountByIndex
        if (address >= 0 && address < count) {
            return discounts[address];
        }
        return null; // Or throw exception
    }

    public int getCount() {
        return count;
    }

    
    public Discount findActiveDiscountByCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }
        String trimmedCode = code.trim();
        for (int i = 0; i < count; i++) {
            // Check for null just in case, match code ignoring case, check if active
            if (discounts[i] != null && discounts[i].getDiscountCode().equalsIgnoreCase(trimmedCode) && discounts[i].getIsActive()) {
                return discounts[i]; // Found active match
            }
        }
        return null; // Not found or not active
    }
     public void addCode() { // Renamed from original addCode which took parameters
        if (count >= discounts.length) {
            System.out.println("Cannot add more discounts. Array is full.");
            return;
        }
        double percentage = -1; // Initialize to invalid value
        System.out.println("\n--- Add New Discount Code ---");
        System.out.print("Enter new code name: ");
        String codeName = input.nextLine().trim(); // Use nextLine()

        // Validate code name not empty
        if(codeName.isEmpty()){
            System.out.println("Discount code cannot be empty.");
            return;
        }
        // Optional: Check if code name already exists?

        do {
            System.out.print("Enter percentage (0-100): ");
            try {
                 percentage = Double.parseDouble(input.nextLine().trim());
                 if (percentage < 0 || percentage > 100) {
                     System.out.println("Percentage must be between 0 and 100.");
                     percentage = -1; // Keep it invalid to loop again
                 }
            } catch (NumberFormatException e) {
                 System.out.println("Invalid input. Please enter a number.");
                 percentage = -1; // Keep it invalid
            }

        } while (percentage < 0 || percentage > 100); // Loop while invalid

        Discount d = new Discount(codeName, percentage);
        discounts[count] = d;
        this.count++;
        System.out.println("✅ Discount code '" + codeName + "' added successfully.");
    }

     public void displayAllDiscount(){
        System.out.println("\n=================");
        System.out.println("| All Discounts |");
        System.out.println("=================");

        if (count == 0) {
            System.out.println("No discounts available.");
            System.out.println("=================");
            return;
        }

        String localStatus; // Make status local
        for(int i = 0; i < count; i++){
            int num = i + 1;
            boolean currentStatus = discounts[i].getIsActive();

            System.out.print("Code [" + num + "]: " + discounts[i].getDiscountCode());

            if(currentStatus){ // Simplified check
                localStatus = "Active";
            }else{
                localStatus = "Inactive"; // Changed 'Deactive' to 'Inactive' for consistency
            }

            System.out.println(" (" + localStatus + ")");
            System.out.printf("Percentage: %.2f%%\n", discounts[i].getPercentage()); // Format percentage
            System.out.println("-----------------"); // Separator
        }
        // System.out.println("================="); // Removed redundant bottom border
    }

    // --- activeCode / deactiveCode --- (Consider boolean return type)
    public String activeCode(String code) {
        for (int i = 0; i < count; i++) {
            if (discounts[i] != null && discounts[i].getDiscountCode().equalsIgnoreCase(code)) {
                discounts[i].setIsActive(true);
                return "✅ Set '" + discounts[i].getDiscountCode() + "' to Active successfully!";
            }
        }
        return "❌ Code '" + code + "' not found. Activation failed.";
    }

    public String deactiveCode(String code) {
        for (int i = 0; i < count; i++) {
            if (discounts[i] != null && discounts[i].getDiscountCode().equalsIgnoreCase(code)) {
                discounts[i].setIsActive(false);
                return "✅ Set '" + discounts[i].getDiscountCode() + "' to Inactive successfully!";
            }
        }
        return "❌ Code '" + code + "' not found. Deactivation failed.";
    }
}
