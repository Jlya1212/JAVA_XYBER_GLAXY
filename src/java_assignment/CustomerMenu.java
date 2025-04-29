/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_assignment;

import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 * Handles customer-related menu and interactions
 */
public class CustomerMenu {
    // Dependencies
    private Scanner scanner;
    private UserManager userManager;
    private ProductManager productManager;
    private OrderManager orderManager;
    
    /**
     * Constructor with required dependencies
     */
    public CustomerMenu(Scanner scanner, UserManager userManager, ProductManager productManager, OrderManager orderManager) {
        this.scanner = scanner;
        this.userManager = userManager;
        this.productManager = productManager;
        this.orderManager = orderManager;
    }
    
    /**
     * Main entry point for customer flow (login/register)
     * @return boolean - true if customer interaction completed, false to go back to role selection
     */
    public boolean handleCustomerFlow() {
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

    /**
     * Handles customer login process
     * @return boolean - true if login successful, false otherwise
     */
    private boolean handleCustomerLogin() {
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

    /**
     * Handles customer registration
     */
    private void handleCustomerRegistration() {
        userManager.registerCustomer(scanner); // Use UserManager's registration
        System.out.println("Please login using the Customer Portal.");
    }

    /**
     * Main customer menu for shopping and account management
     * @param customer The logged-in customer
     */
    private void showCustomerMenu(Customer customer) {
        while (true) {
            System.out.println("\n======== Shopping Portal ("+ customer.getName() +") ========");
            System.out.println("1. View Products");
            System.out.println("2. View Cart");
            System.out.println("3. View Wishlist");
            System.out.println("4. View Purchase History"); 
            System.out.println("5. Checkout");               
            System.out.println("6. Logout");              
            // add one more selection 
            System.out.print("Enter choice (1-6): ");       

            String choice = scanner.nextLine().trim();
            switch (choice) {
                // add one more case 
                case "1": displayProductsAndActions(customer); break;
                case "2": viewCart(customer); break;
                case "3": viewWishlist(customer); break;
                case "4": viewPurchaseHistory(customer); break;
                case "5": handleCheckout(customer); break; 
                case "6": System.out.println("Logging out..."); return; 
                default: System.out.println("Invalid Choice! Please enter 1-6."); 
            }
        }
    }

    // add one more function to call out discount manager inside funtion : 
    private void displayProductsAndActions(Customer customer) {
        String category = selectProductCategory(); // Let user choose category
        Product[] products = productManager.getProductsByCategory(category); // Get products

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


    private String selectProductCategory() {
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

    /**
     * Handles product actions (add to cart, wishlist, view details)
     */
    private void handleProductSelectionActions(Customer customer, Product[] displayedProducts) {
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
                    Product product = productManager.getProductById(productId); // Find product globally

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

    /**
     * Handles adding a product to the cart with quantity validation
     */
    private void handleAddToCart(Customer customer, Product product) {
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

    /**
     * Displays the customer's shopping cart
     */
    private void viewCart(Customer customer) {
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

    /**
     * Displays and manages the customer's wishlist
     */
    private void viewWishlist(Customer customer) {
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
                            if(p.getProductID() == productIdRemove){ 
                                productToRemove = p; 
                                break; 
                            }
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
                            productToView = productManager.getProductById(productIdView);
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
    
    /**
     * Displays the customer's order history
     */
    private void viewPurchaseHistory(Customer customer) {
        System.out.println("\n--- Your Purchase History ---");

        // Get orders specifically for this customer from OrderManager
        List<Order> customerOrders = orderManager.getOrdersForCustomer(customer);

        if (customerOrders.isEmpty()) {
            System.out.println("You have not placed any orders yet.");
        } else {
            System.out.println("Total Orders Found: " + customerOrders.size());
            System.out.println("-----------------------------------------------");
            // Display each order using the Order's printDetails method
            for (Order order : customerOrders) {
                order.printDetails(); // Prints the detailed receipt for each past order
                System.out.println("-----------------------------------------------"); // Separator
            }
        }
        System.out.println("--- End of Purchase History ---");
    }

    /**
     * Handles the checkout process
     */
    private void handleCheckout(Customer customer) {
        Cart cart = customer.getCart();
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty! Add items before checking out.");
            return;
        }

        System.out.println("\n--- Proceeding to Checkout ---");
        viewCart(customer); // Show cart contents

        System.out.print("Enter discount code (or press Enter to skip): ");
        String discountCode = scanner.nextLine().trim();
        if (discountCode.isEmpty()) {
            discountCode = null;
        }

        // --- Calculate and Confirm Price BEFORE Payment ---
        double finalAmount = orderManager.calculateFinalPrice(cart, discountCode);
        System.out.println("--------------------------------------------------");
        System.out.printf("Total Amount Due: RM%.2f\n", finalAmount);
        System.out.println("--------------------------------------------------");

        System.out.print("Proceed to payment? (yes/no): ");
        String confirmPayment = scanner.nextLine().trim();

        if (!confirmPayment.equalsIgnoreCase("yes")) {
            System.out.println("Checkout cancelled.");
            return; // Exit checkout process
        }

        // --- Collect Payment Details ---
        PaymentMethod selectedMethod = null;
        while (selectedMethod == null) {
            System.out.println("\nSelect Payment Method:");
            System.out.println("1. Credit Card");
            System.out.println("2. Debit Card");
            System.out.print("Enter choice (1-2): ");
            String methodChoice = scanner.nextLine().trim();
            switch (methodChoice) {
                case "1": selectedMethod = PaymentMethod.CREDIT_CARD; break;
                case "2": selectedMethod = PaymentMethod.DEBIT_CARD; break;
                default: System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        }

        // Use validation helpers for card/CVV input
        String cardNumber = getValidatedStringInput(
            "Enter Card Number (13-16 digits): ",
            s -> s != null && s.matches("\\d{13,16}"), // Basic validation
            "Invalid card number format (must be 13-16 digits)."
        );

        String cvv = getValidatedStringInput(
            "Enter CVV (3-4 digits): ",
            s -> s != null && s.matches("\\d{3,4}"), // Basic validation
            "Invalid CVV format (must be 3-4 digits)."
        );

        if (orderManager.placeOrder(customer, discountCode, selectedMethod, cardNumber, cvv)) {
        } else {
            System.out.println("Checkout could not be completed. Please review messages above or try again.");
        }
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