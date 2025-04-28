package java_assignment;

import java.util.ArrayList; // Needed for List<Product>, List<User>
import java.util.InputMismatchException; // For number format errors
import java.util.List; // Needed for List<Order>
import java.util.Scanner;

public class Main {
    // Static Scanner for consistent input reading across the application
    private static Scanner scanner = new Scanner(System.in);

    // Manager instances (Initialization order matters for dependencies)
    private static UserManager userManager = new UserManager();
    private static ProductManager pmanager = new ProductManager();
    private static DiscountManager discountManager = new DiscountManager(); // Must be before OrderManager
    private static OrderManager orderManager = new OrderManager(discountManager); // Pass DiscountManager dependency

    // --- main method and initial role selection remain the same ---
    public static void main(String[] args) {
        System.out.println("+==================================+");
        System.out.println("+   Welcome to Xyber Glaxy Store   +");
        System.out.println("+==================================+");

        // Main application loop
        while (true) {
            String role = selectRole(); // Get user role (Admin/Customer)

            if (role.equals("admin")) {
                // Handle the entire admin session (login + menu)
                if (!handleAdminLoginAndMenu()) {
                    System.out.println("\nReturning to role selection...");
                }
            } else { // Customer role
                // Handle the entire customer session (login/register + menu)
                if (!handleCustomerFlow()) {
                    System.out.println("\nReturning to role selection...");
                }
            }
        }
    }

    private static String selectRole() {
        while (true) {
            System.out.println("\nSelect your role:");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.print("Enter choice (1-2): ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": return "admin";
                case "2": return "customer";
                default: System.out.println("Invalid choice! Please enter 1 or 2");
            }
        }
    }

    // --- Admin Flow: Login and Menu ---
    private static boolean handleAdminLoginAndMenu() {
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

    // --- Admin Menu ---
    private static void showAdminMenu(Admin admin) { // Takes Admin object for context/permissions
        while (true) {
            System.out.println("\n======== Admin Dashboard ("+ admin.getName() +") ========");
            System.out.println("1. View Low Stock Alerts");
            System.out.println("2. Manage Products"); // Add, Update, Delete, List
            System.out.println("3. View All Orders");
            System.out.println("4. Manage Discounts"); // Add, Activate, Deactivate, List
            System.out.println("5. Manage Users"); // View, Add Admin
            System.out.println("6. Logout");
            System.out.print("Enter choice (1-6): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                 case "1":
                    viewLowStockAlerts(admin);
                    break;
                 case "2":
                    manageProducts(admin); // Now fully implemented
                    break;
                 case "3":
                    viewAllOrders(admin);
                    break;
                 case "4":
                    manageDiscounts(admin);
                    break;
                 case "5":
                    manageUsers(admin); // Now fully implemented
                    break;
                case "6":
                    System.out.println("Logging out...");
                    return; // Exit admin menu -> handleAdminLoginAndMenu returns true
                default:
                    System.out.println("Invalid Choice! Please enter 1-6.");
            }
        }
    }


     private static boolean handleCustomerFlow() {
        while (true) {
            System.out.println("\n======== Customer Portal ========");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Back to Role Selection");
            System.out.print("Enter choice (1-3): ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    // Attempt login. If successful, showCustomerMenu runs until logout.
                    if (handleCustomerLogin()) return true; // Logout returns true here
                    // If login fails, the loop continues, showing the portal menu again
                    break;
                case "2":
                    handleCustomerRegistration();
                    // After registration, loop continues, prompting login/register again
                    break;
                case "3":
                    return false; // Go back to role selection
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

     private static boolean handleCustomerLogin() {
        User user = userManager.login(scanner); // Use UserManager's login
        if (user instanceof Customer) {
            Customer customer = (Customer) user;
            System.out.println("\nWelcome back " + customer.getName() + "!");
            showCustomerMenu(customer); // Show the main shopping menu
            return true; // User logged out from showCustomerMenu
        } else if (user != null){
             System.out.println("Login successful, but you are not a Customer.");
             return false; // Failed login as customer
        } else {
             // Login failed (message already printed by userManager.login)
            return false; // Failed login as customer
        }
    }

    private static void handleCustomerRegistration() {
        userManager.registerCustomer(scanner); // Use UserManager's registration
        System.out.println("Please login using the Customer Portal.");
    }

    private static void showCustomerMenu(Customer customer) {
        while (true) {
            System.out.println("\n======== Shopping Portal ("+ customer.getName() +") ========");
            System.out.println("1. View Products");
            System.out.println("2. View Cart");
            System.out.println("3. View Wishlist");
            System.out.println("4. Checkout");
            System.out.println("5. Logout");
            System.out.print("Enter choice (1-5): ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": displayProductsAndActions(customer); break;
                case "2": viewCart(customer); break;
                case "3": viewWishlist(customer); break;
                case "4": handleCheckout(customer); break;
                case "5": System.out.println("Logging out..."); return; // Exit customer menu
                default: System.out.println("Invalid Choice!");
            }
        }
    }

    private static void displayProductsAndActions(Customer customer) {
        String category = selectProductCategory(); // Let user choose category
        Product[] products = pmanager.getProductsByCategory(category); // Get products

        System.out.println("\n======== " + category + " Products ========");
        if (products.length == 0) {
            System.out.println("No products found in this category!");
            return; // Nothing to show or do
        }

        // Display products concisely using toString()
        for (Product product : products) {
            System.out.println(product.toString());
        }
        System.out.println("------------------------------");

        // After listing, present actions the user can take
        handleProductSelectionActions(customer, products);
    }

    private static String selectProductCategory() {
        while (true) {
            System.out.println("\nSelect product category:");
            System.out.println("1. All Products");
            System.out.println("2. Computers");
            System.out.println("3. Peripherals");
            System.out.println("4. Accessories");
            System.out.print("Enter choice (1-4): ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": return "All";
                case "2": return "Computer";
                case "3": return "Peripheral";
                case "4": return "Accessory";
                default: System.out.println("Invalid choice! Please enter 1-4");
            }
        }
    }

     private static void handleProductSelectionActions(Customer customer, Product[] displayedProducts) {
        while (true) {
            System.out.println("\nProduct Actions:");
            System.out.println("[1] Add to Cart");
            System.out.println("[2] Add to Wishlist");
            System.out.println("[3] View Product Details");
            System.out.println("[4] Back to Customer Menu");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("4")) break; // Exit action loop, back to customer menu

            if (choice.equals("1") || choice.equals("2") || choice.equals("3")) {
                 try {
                    System.out.print("Enter Product ID: ");
                    int productId = Integer.parseInt(scanner.nextLine());
                    Product product = pmanager.getProductById(productId); // Find product globally

                    if (product == null) {
                        System.out.println("❌ Product ID not found!");
                        continue; // Ask for action again
                    }

                    // Perform selected action
                    switch(choice) {
                        case "1":
                            handleAddToCart(customer, product); // Handles quantity input
                            break;
                        case "2":
                             if (customer.getWishlist().addItem(product)) {
                                System.out.println("✅ '" + product.getName() + "' added to wishlist!");
                             } // Wishlist's addItem method now handles duplicate messages
                             break;
                        case "3":
                            System.out.println("\n--- Product Details ---");
                            product.printDetails(); // Show full details from Product object
                            System.out.println("-----------------------");
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("❌ Invalid input! Please enter a numeric Product ID.");
                } catch (Exception e) { // Catch potential runtime errors
                     System.out.println("An error occurred: " + e.getMessage());
                }
            } else {
                 System.out.println("Invalid choice!");
            }
        }
    }

     private static void handleAddToCart(Customer customer, Product product) {
        try {
            System.out.print("Enter Quantity for '" + product.getName() + "' (Available: " + product.getStockQuantity() + "): ");
            int quantity = Integer.parseInt(scanner.nextLine());

            if (quantity < 1) {
                System.out.println("Quantity must be at least 1!");
                return;
            }

            if (quantity > product.getStockQuantity()) {
                System.out.println("❌ Insufficient stock! Only " + product.getStockQuantity() + " available.");
                return;
            }

            customer.getCart().addItem(product, quantity);
            System.out.println("\n✅ " + quantity + " x '" + product.getName() + "' added/updated in cart!");

        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input! Please enter numbers only for quantity.");
        }
    }

    private static void viewCart(Customer customer) {
        Cart cart = customer.getCart(); // Get the customer's cart
        CartItem[] cartItems = cart.getItems(); // Get items from the cart

        System.out.println("\n======== Your Cart ========");
        if (cart.isEmpty()) { // Use Cart's isEmpty method
            System.out.println("Your cart is empty!");
            System.out.println("=========================");
            return;
        }

        int itemNumber = 1;
        double total = cart.getTotal();

        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            System.out.printf("%d. %d x %-25s (ID: %d) @ RM%-8.2f = RM%.2f\n",
                              itemNumber++,
                              item.getQuantity(),
                              product.getName(),
                              product.getProductID(),
                              product.getPrice(),
                              item.getTotalPrice()); // Use CartItem's price calculation
        }
        System.out.println("--------------------------------------------------");
        System.out.printf("Cart Total: RM%.2f\n", total);
        System.out.println("=========================");
    }

     private static void viewWishlist(Customer customer) {
        Wishlist wishlist = customer.getWishlist();

        while (true) {
            System.out.println("\n======== Wishlist ========");
            Product[] items = wishlist.getItems(); // Get items from wishlist

            if (wishlist.isEmpty()) { // Use Wishlist's isEmpty method
                System.out.println("Your wishlist is empty!");
                System.out.println("==========================");
                return; // Exit if empty
            }

            int itemNum = 1;
            for (Product item : items) {
                 System.out.printf("%d. %s\n", itemNum++, item.toString());
            }
            System.out.println("=========================");

            System.out.println("\nWishlist Actions:");
            System.out.println("[1] Remove Item");
            System.out.println("[2] View Item Details");
            System.out.println("[3] Back to Customer Menu");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": // Remove Item
                     try {
                        System.out.print("Enter Product ID to remove: ");
                        int productIdRemove = Integer.parseInt(scanner.nextLine());
                        Product productToRemove = null;
                        for(Product p : items){
                            if(p.getProductID() == productIdRemove){ productToRemove = p; break; }
                        }
                        String removedName = (productToRemove != null) ? productToRemove.getName() : "Item ID " + productIdRemove;

                        if (wishlist.removeItem(productIdRemove)) {
                            System.out.println("✅ '" + removedName + "' removed successfully!");
                            if (wishlist.isEmpty()) {
                                System.out.println("Wishlist is now empty!");
                                return;
                            }
                        } else {
                            System.out.println("Product ID " + productIdRemove + " not found in wishlist!");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid input! Please enter numbers only for Product ID.");
                    }
                    break;
                 case "2": // View Item Details
                     try {
                        System.out.print("Enter Product ID to view details: ");
                        int productIdView = Integer.parseInt(scanner.nextLine());
                        Product productToView = null;
                         if(wishlist.containsProduct(productIdView)){
                             productToView = pmanager.getProductById(productIdView);
                         }
                        if(productToView != null){
                            System.out.println("\n--- Wishlist Item Details ---");
                            productToView.printDetails();
                             System.out.println("----------------------------");
                        } else {
                             System.out.println("Product ID " + productIdView + " not found in your wishlist.");
                        }
                     } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid input! Please enter numbers only for Product ID.");
                    }
                     break;
                case "3": // Back to Menu
                    return; // Exit wishlist view method
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

     private static void handleCheckout(Customer customer) {
        Cart cart = customer.getCart();
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty! Add items before checking out.");
            return;
        }

        System.out.println("\n--- Proceeding to Checkout ---");
        viewCart(customer); // Show cart contents for final review

        System.out.print("Enter discount code (or press Enter to skip): ");
        String discountCode = scanner.nextLine().trim();
        if (discountCode.isEmpty()) {
            discountCode = null;
        }

        if (orderManager.placeOrder(customer, discountCode)) {
            // Success message/receipt printed by placeOrder
        } else {
            System.out.println("Checkout could not be completed. Please review messages above.");
        }
    }

    // ======================================================
    // ==           Admin Function Implementations         ==
    // ======================================================

    /** Displays products with stock <= a defined threshold. */
     private static void viewLowStockAlerts(Admin admin) {
         // No specific permission needed beyond being an Admin (or could add product/super admin check)
         System.out.println("\n--- Low Stock Alerts ---");
         int threshold = 10; // Define low stock threshold
         System.out.println("(Showing items with stock <= " + threshold + ")");

         Product[] lowStockProducts = pmanager.getLowStockProducts(threshold); // Use ProductManager method

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

    /** Provides menu for Admin to manage products (List, Add, Update, Delete). */
    private static void manageProducts(Admin admin) {
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

    /** Helper for manageProducts: Lists all products. */
    private static void listAllManagedProducts() {
         Product[] allProducts = pmanager.listProducts();
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

     /** Helper for manageProducts: Handles the interactive adding of a new product. */
    private static void addProductInteractive(Admin admin) {
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
            productId = getValidatedIntInput("Enter Product ID: ", id -> pmanager.getProductById(id) == null, "Product ID already exists!");
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
                pmanager.addProduct(newProduct); // Add to the manager
                System.out.println("✅ " + newProduct.getCategory() + " product '" + newProduct.getName() + "' added successfully!");
            }

        } catch (InputMismatchException e) {
            System.out.println("❌ Invalid number format entered. Aborting product addition.");
        } catch (Exception e) { // Catch any other unexpected errors during input
            System.out.println("An error occurred during input: " + e.getMessage() + ". Aborting product addition.");
        }
    }

    /** Helper for manageProducts: Handles updating a product. */
     private static void updateProductInteractive(Admin admin) {
         try {
            System.out.print("Enter Product ID to update: ");
            int idToUpdate = Integer.parseInt(scanner.nextLine());
            // Delegate to ProductManager, which calls the product's updateFromInput
            if (!pmanager.updateProduct(idToUpdate, scanner)) {
                // ProductManager's updateProduct prints "not found" if applicable
            }
            // Success message is printed within the specific product's updateFromInput method
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid Product ID format.");
        }
     }

     /** Helper for manageProducts: Handles deleting a product. */
     private static void deleteProductInteractive(Admin admin) {
          try {
            System.out.print("Enter Product ID to delete: ");
            int idToDelete = Integer.parseInt(scanner.nextLine());
            Product productToDelete = pmanager.getProductById(idToDelete);

            if (productToDelete != null) {
                System.out.print("Are you sure you want to delete '" + productToDelete.getName() + "' (ID: " + idToDelete + ")? (yes/no): ");
                String confirmation = scanner.nextLine().trim();
                if (confirmation.equalsIgnoreCase("yes")) {
                    if (pmanager.deleteProduct(idToDelete)) {
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


    /** Displays all orders placed in the system. */
    private static void viewAllOrders(Admin admin) {
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

    /** Provides menu for Admin to manage discounts (Add, Activate, Deactivate, List). */
     private static void manageDiscounts(Admin admin) {
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

    /** Provides menu for Admin to manage users (List, Add Admin). */
     private static void manageUsers(Admin admin) {
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

    // ======================================================
    // ==           Input Validation Helper Methods        ==
    // ======================================================

    /** Gets integer input, validates using a predicate, and loops until valid. */
    private static int getValidatedIntInput(String prompt, java.util.function.Predicate<Integer> validator, String errorMsg) {
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

    /** Gets double input, validates using a predicate, and loops until valid. */
    private static double getValidatedDoubleInput(String prompt, java.util.function.Predicate<Double> validator, String errorMsg) {
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

    /** Gets string input, validates using a predicate, and loops until valid. */
    private static String getValidatedStringInput(String prompt, java.util.function.Predicate<String> validator, String errorMsg) {
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


} // End of Main Class