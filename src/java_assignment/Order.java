package java_assignment;

import java.util.Arrays;
import java.time.LocalDateTime; // Add at the top of the file


// Ensure Printable interface is available
public class Order implements Printable {
    private static int nextOrderId = 1;
    private int orderId;
    private Customer customer;
    private OrderItem[] items;
    private String appliedDiscountCode; // Store the code string that was successfully applied (or null)
    private double finalTotalPrice;   // Store the final calculated price
    private LocalDateTime datePlaced;

    // Modified Constructor: Accepts final price and validated code
    public Order(Customer customer, OrderItem[] items, String appliedDiscountCode, double finalTotalPrice) {
        if (customer == null || items == null || items.length == 0 || finalTotalPrice < 0) {
             throw new IllegalArgumentException("Invalid arguments for Order constructor.");
        }
        this.orderId = nextOrderId++;
        this.customer = customer;
        // Defensive copy of items
        this.items = Arrays.copyOf(items, items.length);
        this.appliedDiscountCode = appliedDiscountCode; // Store the validated code (or null)
        this.finalTotalPrice = finalTotalPrice;       // Store the pre-calculated final price
        this.datePlaced = LocalDateTime.now(); 
    }

    // calculateTotal() method is no longer needed here.

    // --- Getters ---
    public int getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public OrderItem[] getItems() { return Arrays.copyOf(items, items.length); } // Return a copy
    public String getDiscountCode() { return appliedDiscountCode; } // Return the applied code
    public double getTotalPrice() { return finalTotalPrice; } // Return the final price
    public LocalDateTime getDate() {return datePlaced;}
    
    @Override
    public void printDetails() {
        System.out.println("\n======== Order #" + orderId + " Receipt ========");
        System.out.println("Customer: " + customer.getName() + " (" + customer.getEmail() + ")");
        System.out.println("-------------------- Items --------------------");
        double subTotal = 0; // Calculate subtotal just for printing
        for (OrderItem item : items) {
            Product product = item.getProduct();
            double itemTotal = item.getTotalPrice(); // product.getPrice() * item.getQuantity()
            subTotal += itemTotal;
            System.out.printf("%d x %-30s (ID: %d) @ RM%-8.2f = RM%.2f\n",
                    item.getQuantity(),
                    product.getName(),
                    product.getProductID(),
                    product.getPrice(),
                    itemTotal);
        }
        System.out.println("-----------------------------------------------");
        System.out.printf("%-40s RM%.2f\n", "Subtotal:", subTotal); // Show subtotal aligned

        if (appliedDiscountCode != null) {
            // Calculate discount amount for display
            double discountAmount = subTotal - finalTotalPrice;
            // Assume discount percentage was > 0 if code is present
            System.out.printf("%-40s (%s)\n", "Discount Applied:", appliedDiscountCode);
            System.out.printf("%-40s -RM%.2f\n", "Discount Amount:", discountAmount);
             System.out.println("-----------------------------------------------");
        }
        System.out.printf("%-40s RM%.2f\n", "Final Total:", finalTotalPrice); // Show the stored final total
        System.out.println("===============================================");
        System.out.println("Thank you for your order!");
        System.out.println("===============================================\n");
    }
    
}