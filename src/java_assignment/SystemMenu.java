package java_assignment;

import java.util.Scanner;

public class SystemMenu {
    // Static Scanner for consistent input across the application
    private static Scanner scanner;
    
    // Manager instances 
    private UserManager userManager;
    private ProductManager productManager;
    private DiscountManager discountManager;
    private OrderManager orderManager;
    
    // Menu classes
    private AdminMenu adminMenu;
    private CustomerMenu customerMenu;
    
   
    public SystemMenu(Scanner scanner) {
        SystemMenu.scanner = scanner;
        
        // Initialize managers in correct dependency order
        this.userManager = new UserManager();
        this.productManager = new ProductManager();
        this.discountManager = new DiscountManager();
        this.orderManager = new OrderManager(discountManager);
        
        // Create menu objects with necessary dependencies
        this.adminMenu = new AdminMenu(scanner, userManager, productManager, discountManager, orderManager);
        this.customerMenu = new CustomerMenu(scanner, userManager, productManager, orderManager, discountManager);
    }
    
   
    public void start() {
        displayWelcomeMessage();
        
        // Main application loop
        while (true) {
            String role = selectRole();
            
            if (role.equals("admin")) {
                // Handle the entire admin session
                if (!adminMenu.handleAdminLoginAndMenu()) {
                    System.out.println("\nReturning to role selection...");
                }
            } else { // Customer role
                // Handle the entire customer session
                if (!customerMenu.handleCustomerFlow()) {
                    System.out.println("\nReturning to role selection...");
                }
            }
        }
    }
    
    
    private void displayWelcomeMessage() {
        System.out.println("+==================================+");
        System.out.println("+   Welcome to Xyber Glaxy Store   +");
        System.out.println("+==================================+");
    }
    
    
    private String selectRole() {
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
}