package java_assignment;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("+==================================+");
        System.out.println("+   Welcome to Xyber Glaxy Store   +");
        System.out.println("+==================================+");
        
        while (true) {
            String role = selectRole(scanner);
            
            if (role.equals("admin")) {
                if (handleAdminFlow(scanner)) 
                    break;
            } else {
                if (handleCustomerFlow(scanner)) 
                    break;
            }
        }
        scanner.close();
    }

    private static String selectRole(Scanner scanner) {
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

    private static boolean handleAdminFlow(Scanner scanner) {
        System.out.println("\n======== Admin Login ========");
        User user = UserManager.login(scanner);
        
        if (user instanceof Admin) {
            System.out.println("\nLogin successful! Welcome back " + user.getName());
            showAdminMenu(scanner);
            return true;
        }
        System.out.println("\nReturning to main menu...");
        return false;
    }

    private static boolean handleCustomerFlow(Scanner scanner) {
        System.out.println("\n======== Customer Portal ========");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Back to Main Menu");
        System.out.print("Enter choice (1-3): ");

        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1":
                User user = UserManager.login(scanner);
                if (user instanceof Customer) {
                    System.out.println("\nWelcome back " + user.getName() + "!");
                    showCustomerMenu(scanner);
                    return true;
                }
                return false;

            case "2":
                UserManager.registerUser(scanner);
                System.out.println("\nRegistration successful! Please login:");
                user = UserManager.login(scanner);
                if (user instanceof Customer) {
                    System.out.println("\nWelcome to Xyber Glaxy " + user.getName() + "!");
                    showCustomerMenu(scanner);
                    return true;
                }
                return false;

            case "3":
                return true;

            default:
                System.out.println("Invalid choice!");
                return false;
        }
    }

    private static void showAdminMenu(Scanner scanner) {
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

    private static void showCustomerMenu(Scanner scanner) {
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
                    displayProducts(scanner);
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Feature under development!");
            }
        }
    }

    private static void displayProducts(Scanner scanner) {
        String category = selectProductCategory(scanner);
        List<Product> products = ProductManager.getProductsByCategory(category);
        
        System.out.println("\n======== " + category + " Products ========");
        if (products.isEmpty()) {
            System.out.println("No products found in this category!");
            return;
        }

        for (Product product : products) {
            product.printDetails();
            System.out.println();
        }
        System.out.println("=============================");
    }

    private static String selectProductCategory(Scanner scanner) {
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
}