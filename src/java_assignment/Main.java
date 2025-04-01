package java_assignment;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("+==================================+");
        System.out.println("+   Welcome to Xyber Glaxy Store   +");
        System.out.println("+==================================+");
        
        while (true) {
            String role = selectRole();
            
            if (role.equals("admin")) {
                if (handleAdminFlow()) break;
            } else {
                if (handleCustomerFlow()) break;
            }
        }
        scanner.close();
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

    private static boolean handleAdminFlow() {
        System.out.println("\n======== Admin Login ========");
        User user = UserManager.login(scanner);
        
        if (user instanceof Admin) {
            System.out.println("\nLogin successful! Welcome back " + user.getName());
            showAdminMenu();
            return true;
        }
        System.out.println("\nReturning to main menu...");
        return false;
    }

    private static boolean handleCustomerFlow() {
        while (true) {
            System.out.println("\n======== Customer Portal ========");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter choice (1-3): ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": return handleCustomerLogin();
                case "2": return handleCustomerRegistration();
                case "3": return false;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static boolean handleCustomerLogin() {
        User user = UserManager.login(scanner);
        if (user instanceof Customer) {
            Customer customer = (Customer) user;
            System.out.println("\nWelcome back " + customer.getName() + "!");
            showCustomerMenu(customer);
            return true;
        }
        return false;
    }

    private static boolean handleCustomerRegistration() {
        UserManager.registerUser(scanner);
        System.out.println("\nRegistration successful! Please login:");
        User user = UserManager.login(scanner);
        if (user instanceof Customer) {
            Customer customer = (Customer) user;
            System.out.println("\nWelcome to Xyber Glaxy " + customer.getName() + "!");
            showCustomerMenu(customer);
            return true;
        }
        return false;
    }

    private static void showAdminMenu() {
        while (true) {
            System.out.println("\n======== Admin Dashboard ========");
            System.out.println("1. View Low Stock Alerts");
            System.out.println("2. Manage Products");
            System.out.println("3. View Orders");
            System.out.println("4. Logout");
            System.out.print("Enter choice (1-4): ");
            
            String choice = scanner.nextLine().trim();
            if (choice.equals("4")) return;
            System.out.println("Feature under development!");
        }
    }

    private static void showCustomerMenu(Customer customer) {
        while (true) {
            System.out.println("\n======== Shopping Portal ========");
            System.out.println("1. View Products");
            System.out.println("2. View Cart");
            System.out.println("3. View Wishlist");
            System.out.println("4. Checkout");
            System.out.println("5. Logout");
            System.out.print("Enter choice (1-5): ");
            
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    displayProducts(customer);
                    break;
                case "2":
                    viewCart(customer);
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Feature under development!");
            }
        }
    }

    private static void displayProducts(Customer customer) {
        String category = selectProductCategory();
        Product[] products = ProductManager.getProductsByCategory(category);
        
        System.out.println("\n======== " + category + " Products ========");
        if (products.length == 0) {
            System.out.println("No products found in this category!");
            return;
        }

        for (Product product : products) {
            product.printDetails();
            System.out.println();
        }
        
        handleProductSelection(customer);
    }

    private static void handleProductSelection(Customer customer) {
        while (true) {
            System.out.println("\n[1] Add to Cart");
            System.out.println("[2] Back to Menu");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("2")) break;

            if (choice.equals("1")) {
                try {
                    System.out.print("Enter Product ID: ");
                    int productId = Integer.parseInt(scanner.nextLine());
                    Product product = ProductManager.getProductById(productId);
                    
                    if (product == null) {
                        System.out.println("Product not found!");
                        continue;
                    }

                    System.out.print("Enter Quantity: ");
                    int quantity = Integer.parseInt(scanner.nextLine());
                    
                    if (quantity < 1) {
                        System.out.println("Quantity must be at least 1!");
                        continue;
                    }
                    
                    if (quantity > product.getStockQuantity()) {
                        System.out.println("Insufficient stock! Available: " + product.getStockQuantity());
                        continue;
                    }

                    customer.getCart().addItem(product, quantity);
                    product.reduceStock(quantity);
                    System.out.println("\n" + quantity + " x " + product.getName() + " added to cart!");
                    
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter numbers only.");
                }
            }
        }
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

    private static void viewCart(Customer customer) {
        Product[] items = customer.getCart().getItems();
        int[] quantities = customer.getCart().getQuantities();
        
        System.out.println("\n======== Your Cart ========");
        if (items.length == 0) {
            System.out.println("Your cart is empty!");
            return;
        }

        for (int i = 0; i < items.length; i++) {
            System.out.printf("%d x %s - $%.2f\n", 
                quantities[i], 
                items[i].getName(), 
                items[i].getPrice() * quantities[i]);
        }
        
        System.out.printf("\nTotal: $%.2f\n", customer.getCart().getTotal());
        System.out.println("=====================");
    }
}