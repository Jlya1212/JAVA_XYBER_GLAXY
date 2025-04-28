package java_assignment;

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

    public static void main(String[] args) {
        System.out.println("+==================================+");
        System.out.println("+   Welcome to Xyber Glaxy Store   +");
        System.out.println("+==================================+");

        // Optional: Pre-add a default admin if none exist, useful for testing
        // Example: Check if any admin exists, if not add one
        // if (userManager.getUsers().stream().noneMatch(u -> u instanceof Admin)) {
        //    userManager.addUser(new Admin("admin", "pass", "Default Admin", "admin@store.com", AdminType.SUPER_ADMIN));
        //    System.out.println("-> Added default admin user: username='admin', password='pass'");
        // }

        // Main application loop
        while (true) {
            String role = selectRole(); // Get user role (Admin/Customer)

            if (role.equals("admin")) {
                // Handle the entire admin session (login + menu)
                if (!handleAdminLoginAndMenu()) {
                    // If admin logs out or login fails, loop back to role selection
                    System.out.println("\nReturning to role selection...");
                }
                // Decide if the app should exit here or loop. Current logic loops.

            } else { // Customer role
                // Handle the entire customer session (login/register + menu)
                if (!handleCustomerFlow()) {
                    // If customer logs out or fails login/registration, loop back
                    System.out.println("\nReturning to role selection...");
                }
                // Decide if the app should exit here or loop. Current logic loops.
            }
        }
        // Closing System.in is generally discouraged. If needed, add it here.
        // scanner.close();
        // System.out.println("\nExiting Store Application. Goodbye!");
    }

    // --- Role Selection ---
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
             // Option: Ask if they want to register (if no admins exist?)
             return false; // Login failed, return to role select
        }
    }

    // --- Admin Menu ---
    private static void showAdminMenu(Admin admin) { // Takes Admin object for context/permissions
        while (true) {
            System.out.println("\n======== Admin Dashboard ("+ admin.getName() +") ========");
            System.out.println("1. View Low Stock Alerts");
            System.out.println("2. Manage Products"); // Add, Update, Delete
            System.out.println("3. View All Orders");
            System.out.println("4. Manage Discounts"); // Add, Activate, Deactivate
            System.out.println("5. Manage Users"); // View, Add Admin
            System.out.println("6. Logout");
            System.out.print("Enter choice (1-6): ");

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
                    manageDiscounts(admin);
                    break;
                 case "5":
                    manageUsers(admin);
                    break;
                case "6":
                    System.out.println("Logging out...");
                    return; // Exit admin menu -> handleAdminLoginAndMenu returns true
                default:
                    System.out.println("Invalid Choice! Please enter 1-6.");
            }
        }
    }

    // --- Customer Flow: Entry Point ---
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

    // --- Customer Login ---
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

    // --- Customer Registration ---
    private static void handleCustomerRegistration() {
        userManager.registerCustomer(scanner); // Use UserManager's registration
        // Feedback message now inside registerCustomer
        System.out.println("Please login using the Customer Portal.");
    }

    // --- Customer Menu ---
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

    // --- Product Viewing and Actions ---
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

    // --- Category Selection ---
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

    // --- Actions on Displayed Products ---
    private static void handleProductSelectionActions(Customer customer, Product[] displayedProducts) {
         // displayedProducts currently unused, but could be used to validate ID is from the list shown
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

    // --- Add Product to Cart ---
     private static void handleAddToCart(Customer customer, Product product) {
        try {
            System.out.print("Enter Quantity for '" + product.getName() + "' (Available: " + product.getStockQuantity() + "): ");
            int quantity = Integer.parseInt(scanner.nextLine());

            if (quantity < 1) {
                System.out.println("Quantity must be at least 1!");
                return;
            }

            // Check stock availability *before* adding to cart conceptually
            if (quantity > product.getStockQuantity()) {
                System.out.println("❌ Insufficient stock! Only " + product.getStockQuantity() + " available.");
                return;
            }

            // Add item to cart (Cart class handles updating quantity if item exists)
            customer.getCart().addItem(product, quantity);
            // Note: Stock is only reduced during actual checkout by OrderManager
            System.out.println("\n✅ " + quantity + " x '" + product.getName() + "' added/updated in cart!");

        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input! Please enter numbers only for quantity.");
        }
    }

    // --- View Shopping Cart ---
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
        // Use cart's getTotal method for consistency
        double total = cart.getTotal();

        // Display each item in the cart
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

        // Potential Enhancement: Add option to remove items from cart here
        // System.out.println("[R] Remove item | [C] Continue to Checkout | [M] Back to Menu");
    }

    // --- View Wishlist ---
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

            // Display items concisely
            int itemNum = 1;
            for (Product item : items) {
                 System.out.printf("%d. %s\n", itemNum++, item.toString());
            }
            System.out.println("=========================");

            // Wishlist actions
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

                        // Find product name *before* removing for better message
                        Product productToRemove = null;
                        for(Product p : items){ // Check only within the current wishlist items
                            if(p.getProductID() == productIdRemove){
                                productToRemove = p;
                                break;
                            }
                        }
                        String removedName = (productToRemove != null) ? productToRemove.getName() : "Item ID " + productIdRemove;

                        // Attempt removal using Wishlist method
                        if (wishlist.removeItem(productIdRemove)) {
                            System.out.println("✅ '" + removedName + "' removed successfully!");
                            if (wishlist.isEmpty()) { // Check if now empty
                                System.out.println("Wishlist is now empty!");
                                return; // Exit view if it became empty
                            }
                            // Loop continues to show updated list automatically
                        } else {
                            // This message might be redundant if removeItem fails only when ID not found
                            System.out.println("Product ID " + productIdRemove + " not found in wishlist!");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid input! Please enter numbers only for Product ID.");
                    }
                    break; // Break from switch, loop to show updated list/options

                 case "2": // View Item Details
                     try {
                        System.out.print("Enter Product ID to view details: ");
                        int productIdView = Integer.parseInt(scanner.nextLine());
                        Product productToView = null;
                        // Check if the product exists in the *wishlist*
                         if(wishlist.containsProduct(productIdView)){
                             productToView = pmanager.getProductById(productIdView); // Get details from main manager
                         }

                        if(productToView != null){
                            System.out.println("\n--- Wishlist Item Details ---");
                            productToView.printDetails(); // Show full details
                             System.out.println("----------------------------");
                        } else {
                             System.out.println("Product ID " + productIdView + " not found in your wishlist.");
                        }
                     } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid input! Please enter numbers only for Product ID.");
                    }
                     break; // Break from switch, loop continues

                case "3": // Back to Menu
                    return; // Exit wishlist view method

                default:
                    System.out.println("Invalid choice!");
                    // Loop continues
            }
        }
    }

    // --- Handle Checkout Process ---
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
        // Convert empty input to null for OrderManager logic
        if (discountCode.isEmpty()) {
            discountCode = null;
        }

        // Delegate the core checkout logic to OrderManager
        // OrderManager now handles stock validation, discount application,
        // price calculation, order creation, stock update, and printing the receipt.
        if (orderManager.placeOrder(customer, discountCode)) {
            // Success: placeOrder prints receipt and confirmation
            // No extra message needed here unless desired
        } else {
            // Failure: placeOrder prints specific errors (stock, etc.)
            System.out.println("Checkout could not be completed. Please review messages above.");
        }
    }


    // --- Admin Function Implementations ---

    /** Displays products with stock <= a defined threshold. */
     private static void viewLowStockAlerts(Admin admin) {
          // Optional: Add permission check (e.g., only Product or Super Admin)
          // if (!admin.isProductAdmin() && !admin.isSuperAdmin()) { ... return; }
         System.out.println("\n--- Low Stock Alerts ---");
         int threshold = 10; // Define low stock threshold (could be configurable)
         System.out.println("(Showing items with stock <= " + threshold + ")");

         Product[] lowStockProducts = pmanager.getLowStockProducts(threshold); // Use ProductManager method

         if (lowStockProducts.length == 0) {
             System.out.println("✅ No products are currently low on stock.");
         } else {
             System.out.println("Warning! The following items are low on stock:");
             for (Product p : lowStockProducts) {
                 System.out.printf("  - ID: %d | Name: %-25s | Stock: %d\n",
                                   p.getProductID(), p.getName(), p.getStockQuantity());
             }
         }
          System.out.println("------------------------");
     }

    /** Provides menu for Admin to manage products (Add, Update, Delete, List). */
    private static void manageProducts(Admin admin) {
        // Optional: Add permission check (e.g., only Product or Super Admin)
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
                    Product[] allProducts = pmanager.listProducts();
                    System.out.println("\n--- All Products ---");
                    if(allProducts.length == 0){
                        System.out.println("No products in the system.");
                    } else {
                        for(Product p : allProducts){
                            System.out.println(p.toString() + " | Stock: " + p.getStockQuantity());
                        }
                    }
                    System.out.println("--------------------");
                    break;
                case "2": // Add New Product (Requires more detailed input based on type)
                    System.out.println("Add Product - Feature under construction.");
                    // TODO: Implement detailed add product flow
                    // Ask for type (Computer, Peripheral, Accessory)
                    // Get common details (ID, name, desc, price, stock)
                    // Get specific details based on type
                    // Create object (e.g., new Computer(...))
                    // Call pmanager.addProduct(newProduct);
                    break;
                case "3": // Update Existing Product
                     try {
                        System.out.print("Enter Product ID to update: ");
                        int idToUpdate = Integer.parseInt(scanner.nextLine());
                        if (pmanager.updateProduct(idToUpdate, scanner)) {
                            // Update successful (messages printed within updateProduct/product methods)
                        } else {
                             // updateProduct prints "not found" message
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid Product ID format.");
                    }
                    break;
                case "4": // Delete Product
                     try {
                        System.out.print("Enter Product ID to delete: ");
                        int idToDelete = Integer.parseInt(scanner.nextLine());
                        if (pmanager.deleteProduct(idToDelete)) {
                            // deleteProduct prints success message
                        } else {
                            // deleteProduct prints "not found" message
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid Product ID format.");
                    }
                    break;
                case "5": // Back to Admin Menu
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    /** Displays all orders placed in the system. */
    private static void viewAllOrders(Admin admin) {
         // Optional: Add permission check (e.g., only Order or Super Admin)
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
          // Permission check: Example - Only Super Admins can manage discounts
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
                      // Method prints its own success/failure message
                      System.out.println(discountManager.activeCode(codeToActivate));
                      break;
                  case "3":
                      System.out.print("Enter code to Deactivate: ");
                      String codeToDeactivate = scanner.nextLine().trim();
                      // Method prints its own success/failure message
                      System.out.println(discountManager.deactiveCode(codeToDeactivate));
                      break;
                  case "4":
                      return; // Exit discount management menu
                  default:
                      System.out.println("Invalid choice.");
              }
              // Loop continues until user chooses to go back
          }
     }

    /** Provides menu for Admin to manage users (List, Add Admin). */
     private static void manageUsers(Admin admin) {
          // Permission check: Example - Only Super Admins can manage users
           if (!admin.isSuperAdmin()) {
                System.out.println("❌ You do not have permission to manage users.");
                return;
           }

        while(true) {
             System.out.println("\n--- Manage Users ---");
             userManager.listAllUsers(); // List current users with types

             System.out.println("\nOptions:");
             System.out.println("1. Register New Admin");
             // Add more options? Delete user? Change type? Reset password?
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
             // Loop continues until user chooses back
        }
     }

} 