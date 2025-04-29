/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_assignment;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.time.LocalDate;
/**
 * AdminMenu class handles all admin-related menu operations and functionality
 */
public class AdminMenu {
    private Scanner scanner;
    private UserManager userManager;
    private ProductManager productManager;
    private DiscountManager discountManager;
    private OrderManager orderManager;
    
    /**
     * Constructor to initialize dependencies
     */
    public AdminMenu(Scanner scanner, UserManager userManager, ProductManager productManager, 
                     DiscountManager discountManager, OrderManager orderManager) {
        this.scanner = scanner;
        this.userManager = userManager;
        this.productManager = productManager;
        this.discountManager = discountManager;
        this.orderManager = orderManager;
    }
    
    public boolean handleAdminLoginAndMenu() {
        System.out.println("\n======== Admin Portal ========");
        User user = userManager.login(scanner); // Attempt login

        if (user instanceof Admin) {
            Admin admin = (Admin) user; // Cast to Admin
            System.out.println("\nLogin successful! Welcome back " + admin.getName() + " [" + admin.getAdminType() + "]");
            showAdminMenu(admin); // Pass the logged-in admin object to the menu
            return true; // Indicates successful session (logout returns from showAdminMenu)
        } else if (user != null) {
             System.out.println("Login successful, but you are not an Admin.");
             return false; // Logged in as non-admin, return to role select
        } else {
             // Login failed (message printed by userManager.login)
             System.out.println("Admin login failed.");
             return false; // Login failed, return to role select
        }
    }
    
    private void showAdminMenu(Admin admin) {
        while (true) {
            System.out.println("\n======== Admin Dashboard ("+ admin.getName() +") ========");
            System.out.println("1. View Low Stock Alerts");
            System.out.println("2. Manage Products"); // Add, Update, Delete, List
            System.out.println("3. View All Orders");
            System.out.println("4. View Daily Report");
            System.out.println("5. View Montly Report");
            System.out.println("6. View Year Report ");
            System.out.println("7. Manage Discounts"); // Add, Activate, Deactivate, List
            System.out.println("8. Manage Users"); // View, Add Admin
            System.out.println("9. Logout");

            System.out.print("Enter choice (1-9): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                 case "1":
                    viewLowStockAlerts(admin);
                    break;
                 case "2":
                    manageProducts(admin);
                    break;
                 case "3":
                    viewAllOrders(admin);
                    break;
                 case "4":
                     viewDailySalesReport(admin);
                     break ;
                 case "5":
                     viewMonthlySalesReport(admin);
                     break;
                 case"6":
                     viewYearlySalesReport(admin);
                     break;
                 case "7":
                    manageDiscounts(admin);
                    break;
                 case "8":
                    manageUsers(admin);
                    break;
                case "9":
                    System.out.println("Logging out...");
                    return; // Exit admin menu -> handleAdminLoginAndMenu returns true
                default:
                    System.out.println("Invalid Choice! Please enter 1-6.");
            }
        }
    }
    
    private void viewLowStockAlerts(Admin admin) {
        // No specific permission needed beyond being an Admin (or could add product/super admin check)
        System.out.println("\n--- Low Stock Alerts ---");
        int threshold = 10; // Define low stock threshold
        System.out.println("(Showing items with stock <= " + threshold + ")");

        Product[] lowStockProducts = productManager.getLowStockProducts(threshold); // Use ProductManager method

        if (lowStockProducts.length == 0) {
            System.out.println("✅ No products are currently low on stock.");
        } else {
            System.out.println("⚠️ Warning! The following items are low on stock:");
            for (Product p : lowStockProducts) {
                System.out.printf("  - ID: %d | Name: %-25s | Stock: %d\n",
                                  p.getProductID(), p.getName(), p.getStockQuantity());
            }
        }
        System.out.println("------------------------");
    }
    
    private void manageProducts(Admin admin) {
        // Permission check: Only Product Admins or Super Admins
        if (!admin.isProductAdmin() && !admin.isSuperAdmin()) {
            System.out.println("❌ You do not have permission to manage products.");
            return;
        }

        while (true) {
            System.out.println("\n--- Manage Products ---");
            System.out.println("1. List All Products");
            System.out.println("2. Add New Product");
            System.out.println("3. Update Existing Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Back to Admin Dashboard");
            System.out.print("Enter choice (1-5): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": // List All Products
                    listAllManagedProducts();
                    break;
                case "2": // Add New Product
                    addProductInteractive(admin); // Call helper method
                    break;
                case "3": // Update Existing Product
                    updateProductInteractive(admin);
                    break;
                case "4": // Delete Product
                    deleteProductInteractive(admin);
                    break;
                case "5": // Back to Admin Menu
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    private void listAllManagedProducts() {
        Product[] allProducts = productManager.listProducts();
        System.out.println("\n--- All Products in System ---");
        if(allProducts.length == 0){
            System.out.println("No products in the system.");
        } else {
            for(Product p : allProducts){
                // Display more info useful for management
                System.out.printf("ID: %-5d | Type: %-10s | Name: %-25s | Price: RM%-8.2f | Stock: %d\n",
                                  p.getProductID(),
                                  p.getCategory(),
                                  p.getName(),
                                  p.getPrice(),
                                  p.getStockQuantity());
            }
        }
        System.out.println("------------------------------");
    }
    
    private void addProductInteractive(Admin admin) {
        System.out.println("\n--- Add New Product ---");
        System.out.println("Select Product Type:");
        System.out.println("1. Computer");
        System.out.println("2. Peripheral");
        System.out.println("3. Accessory");
        System.out.print("Enter type (1-3): ");
        String typeChoice = scanner.nextLine().trim();

        int productId;
        String name, description;
        double price;
        int stock;

        try {
            // --- Get Common Details ---
            productId = getValidatedIntInput("Enter Product ID: ", id -> productManager.getProductById(id) == null, "Product ID already exists!");
            name = getValidatedStringInput("Enter Product Name: ", s -> !s.isEmpty(), "Name cannot be empty.");
            description = getValidatedStringInput("Enter Description: ", s -> !s.isEmpty(), "Description cannot be empty.");
            price = getValidatedDoubleInput("Enter Price (RM): ", p -> p >= 0, "Price cannot be negative.");
            stock = getValidatedIntInput("Enter Stock Quantity: ", q -> q >= 0, "Stock cannot be negative.");

            Product newProduct = null;
            switch (typeChoice) {
                case "1": // Computer
                    String processor = getValidatedStringInput("Enter Processor Type: ", s -> !s.isEmpty(), "Processor cannot be empty.");
                    int ram = getValidatedIntInput("Enter RAM Size (GB): ", r -> r > 0, "RAM must be positive.");
                    int storage = getValidatedIntInput("Enter Storage Size (GB): ", s -> s > 0, "Storage must be positive.");
                    newProduct = new Computer(productId, name, description, price, stock, processor, ram, storage);
                    break;
                case "2": // Peripheral
                    String connectivity = getValidatedStringInput("Enter Connectivity Type (e.g., USB, Bluetooth): ", s -> !s.isEmpty(), "Connectivity cannot be empty.");
                    String osInputStr = getValidatedStringInput("Enter Compatible OS (comma-separated, e.g., Windows,macOS): ", s -> !s.isEmpty(), "OS list cannot be empty.");
                    String[] compatibleOS = osInputStr.split(",");
                    for (int i = 0; i < compatibleOS.length; i++) compatibleOS[i] = compatibleOS[i].trim(); // Trim whitespace
                    newProduct = new Peripheral(productId, name, description, price, stock, connectivity, compatibleOS);
                    break;
                case "3": // Accessory
                    String material = getValidatedStringInput("Enter Material: ", s -> !s.isEmpty(), "Material cannot be empty.");
                    String color = getValidatedStringInput("Enter Color: ", s -> !s.isEmpty(), "Color cannot be empty.");
                    newProduct = new Accessory(productId, name, description, price, stock, material, color);
                    break;
                default:
                    System.out.println("Invalid product type selected.");
                    return; // Exit add process
            }

            if (newProduct != null) {
                productManager.addProduct(newProduct); // Add to the manager
                System.out.println("✅ " + newProduct.getCategory() + " product '" + newProduct.getName() + "' added successfully!");
            }

        } catch (InputMismatchException e) {
            System.out.println("❌ Invalid number format entered. Aborting product addition.");
        } catch (Exception e) {
            System.out.println("An error occurred during input: " + e.getMessage() + ". Aborting product addition.");
        }
    }
    
    private void updateProductInteractive(Admin admin) {
        try {
            System.out.print("Enter Product ID to update: ");
            int idToUpdate = Integer.parseInt(scanner.nextLine());
            // Delegate to ProductManager, which calls the product's updateFromInput
            if (!productManager.updateProduct(idToUpdate, scanner)) {
                // ProductManager's updateProduct prints "not found" if applicable
            }
            // Success message is printed within the specific product's updateFromInput method
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid Product ID format.");
        }
    }
    
    private void deleteProductInteractive(Admin admin) {
        try {
            System.out.print("Enter Product ID to delete: ");
            int idToDelete = Integer.parseInt(scanner.nextLine());
            Product productToDelete = productManager.getProductById(idToDelete);

            if (productToDelete != null) {
                System.out.print("Are you sure you want to delete '" + productToDelete.getName() + "' (ID: " + idToDelete + ")? (yes/no): ");
                String confirmation = scanner.nextLine().trim();
                if (confirmation.equalsIgnoreCase("yes")) {
                    if (productManager.deleteProduct(idToDelete)) {
                        // ProductManager's deleteProduct prints success feedback
                    } else {
                        System.out.println("Deletion failed unexpectedly after confirmation."); // Should not happen if found initially
                    }
                } else {
                    System.out.println("Deletion cancelled.");
                }
            } else {
                System.out.println("Product ID " + idToDelete + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid Product ID format.");
        }
    }
    
    private void viewAllOrders(Admin admin) {
        // Permission check: Only Order Admins or Super Admins
        if (!admin.isOrderAdmin() && !admin.isSuperAdmin()) {
            System.out.println("❌ You do not have permission to view orders.");
            return;
        }
        System.out.println("\n--- All Customer Orders ---");
        List<Order> allOrders = orderManager.getAllOrders(); // Get orders from OrderManager

        if (allOrders.isEmpty()) {
            System.out.println("No orders have been placed yet.");
        } else {
            System.out.println("Total Orders: " + allOrders.size());
            System.out.println("-----------------------------------------------");
            for (Order order : allOrders) {
                order.printDetails(); // Use Order's detailed print method
                System.out.println("-----------------------------------------------"); // Separator between orders
            }
        }
        System.out.println("--- End of Order List ---");
    }
    
    private void viewDailySalesReport(Admin admin ) {       
        if (!admin.isOrderAdmin() && !admin.isSuperAdmin()) {
            System.out.println("❌ You do not have permission to view orders.");
            return;
        }
        try {
            
            System.out.print("📅 Enter date (YYYY-MM-DD): ");
            String input = scanner.nextLine().trim();
            LocalDate date = LocalDate.parse(input);
            orderManager.generateDailyReport(date);
        } catch (Exception e) {
            System.out.println("❌ Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    private void viewMonthlySalesReport(Admin admin ) {
        try {
            System.out.print("📅 Enter year (e.g. 2025): ");
            int year = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("📅 Enter month (1-12): ");
            int month = Integer.parseInt(scanner.nextLine().trim());
            orderManager.generateMonthlyReport(year, month);
        } catch (Exception e) {
            System.out.println("❌ Invalid input. Please enter valid numbers for year and month.");
        }
    }

    private void viewYearlySalesReport(Admin admin ) {
        try {
            System.out.print("📅 Enter year (e.g. 2025): ");
            int year = Integer.parseInt(scanner.nextLine().trim());
            orderManager.generateYearlyReport(year);
        } catch (Exception e) {
            System.out.println("❌ Invalid year. Please enter a valid number.");
        }
    }

    private void manageDiscounts(Admin admin) {
        // Permission check: Only Super Admins can manage discounts
        if (!admin.isSuperAdmin()) {
            System.out.println("❌ You do not have permission to manage discounts.");
            return;
        }

        while(true) {
            System.out.println("\n--- Manage Discount Codes ---");
            discountManager.displayAllDiscount(); // Show current discounts first

            System.out.println("\nOptions:");
            System.out.println("1. Add New Discount Code");
            System.out.println("2. Activate Discount Code");
            System.out.println("3. Deactivate Discount Code");
            System.out.println("4. Back to Admin Dashboard");
            System.out.print("Enter choice (1-4): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    discountManager.addCode(); // Interactive add method in DiscountManager
                    break;
                case "2":
                    System.out.print("Enter code to Activate: ");
                    String codeToActivate = scanner.nextLine().trim();
                    System.out.println(discountManager.activeCode(codeToActivate));
                    break;
                case "3":
                    System.out.print("Enter code to Deactivate: ");
                    String codeToDeactivate = scanner.nextLine().trim();
                    System.out.println(discountManager.deactiveCode(codeToDeactivate));
                    break;
                case "4":
                    return; // Exit discount management menu
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    

    private void manageUsers(Admin admin) {
        // Permission check: Only Super Admins can manage users
        if (!admin.isSuperAdmin()) {
            System.out.println("❌ You do not have permission to manage users.");
            return;
        }

        while(true) {
            System.out.println("\n--- Manage Users ---");
            userManager.listAllUsers(); // List current users with types

            System.out.println("\nOptions:");
            System.out.println("1. Register New Admin");
            // Potential TODO: Add options like Delete User, Change Admin Type
            System.out.println("2. Back to Admin Dashboard");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    userManager.registerAdmin(scanner); // Allows admin to register another admin
                    break;
                case "2":
                    return; // Exit user management menu
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    

    private int getValidatedIntInput(String prompt, Predicate<Integer> validator, String errorMsg) {
        int value = 0;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine().trim());
                if (validator.test(value)) {
                    valid = true;
                } else {
                    System.out.println("⚠️ " + errorMsg);
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a whole number.");
            }
        }
        return value;
    }


    private double getValidatedDoubleInput(String prompt, Predicate<Double> validator, String errorMsg) {
        double value = 0.0;
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            try {
                value = Double.parseDouble(scanner.nextLine().trim());
                if (validator.test(value)) {
                    valid = true;
                } else {
                    System.out.println("⚠️ " + errorMsg);
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a number (e.g., 12.99).");
            }
        }
        return value;
    }


    private String getValidatedStringInput(String prompt, Predicate<String> validator, String errorMsg) {
        String value = "";
        boolean valid = false;
        while (!valid) {
            System.out.print(prompt);
            value = scanner.nextLine().trim();
            if (validator.test(value)) {
                valid = true;
            } else {
                System.out.println("⚠️ " + errorMsg);
            }
        }
        return value;
    }
}