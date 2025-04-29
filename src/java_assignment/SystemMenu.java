/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_assignment;

import java.util.Scanner;

/**
 * SystemMenu class handles the main system flow and entry points to the application
 */
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
    
    /**
     * Constructor initializes all the required managers and menus
     * @param scanner The scanner for user input
     */
    public SystemMenu(Scanner scanner) {
        SystemMenu.scanner = scanner;
        
        // Initialize managers in correct dependency order
        this.userManager = new UserManager();
        this.productManager = new ProductManager();
        this.discountManager = new DiscountManager();
        this.orderManager = new OrderManager(discountManager);
        
        // Create menu objects with necessary dependencies
        this.adminMenu = new AdminMenu(scanner, userManager, productManager, discountManager, orderManager);
        this.customerMenu = new CustomerMenu(scanner, userManager, productManager, orderManager);
    }
    
    /**
     * Start the application with a welcome message and main menu
     */
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
    
    /**
     * Display welcome message when system starts
     */
    private void displayWelcomeMessage() {
        System.out.println("+==================================+");
        System.out.println("+   Welcome to Xyber Glaxy Store   +");
        System.out.println("+==================================+");
    }
    
    /**
     * Menu for selecting user role
     * @return The selected role as a string ("admin" or "customer")
     */
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