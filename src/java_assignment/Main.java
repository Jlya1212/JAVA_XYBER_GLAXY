package java_assignment;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static UserManager userManager = new UserManager();
    private static ProductManager pmanager = new ProductManager();
    private static OrderManager orderManager = new OrderManager();
    private static DiscountManager discountManager = new DiscountManager();

    public static void main(String[] args) {
        System.out.println("+==================================+");
        System.out.println("+   Welcome to Xyber Glaxy Store   +");
        System.out.println("+==================================+");

        while (true) {
            String role = selectRole();

            if (role.equals("admin")) {
                if (handleAdminFlow())
                    break;
            } else {
                if (handleCustomerFlow()) 
                    break;
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
        System.out.println("\n======== Admin Portal ========");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.print("Enter choice (1-2): ");
        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            User user = userManager.login(scanner);
            if (user instanceof Admin) {
                System.out.println("\nLogin successful! Welcome back " + user.getName());
                showAdminMenu();
                return true;
            }
        } else if (choice.equals("2")) {
            userManager.registerAdmin(scanner);
        } else {
            System.out.println("Invalid choice!");
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
        User user = userManager.login(scanner);
        if (user instanceof Customer) {
            Customer customer = (Customer) user;
            System.out.println("\nWelcome back " + customer.getName() + "!");
            showCustomerMenu(customer);
            return true;
        }
        return false;
    }

    private static boolean handleCustomerRegistration() {
        userManager.registerCustomer(scanner);
        System.out.println("\nRegistration successful! Please login:");
        return handleCustomerLogin();
    }

    private static void showAdminMenu() {
        while (true) {
            System.out.println("\n======== Admin Dashboard ========");
            System.out.println("1. View Low Stock Alerts");
            System.out.println("2. Manage Products");
            System.out.println("3. View Orders");
            System.out.println("4. Manage Discounts");
            System.out.println("5. Logout");
            System.out.print("Enter choice (1-5): ");

            String choice = scanner.nextLine().trim();
            if (choice.equals("5")) return;
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
                case "1": displayProducts(customer); break;
                case "2": viewCart(customer); break;
                case "3": viewWishlist(customer); break;
                case "4": handleCheckout(customer); break;
                case "5": return;
                default: System.out.println("Invalid Choice!");
            }
        }
    }

    private static void displayProducts(Customer customer) {
        String category = selectProductCategory();
        Product[] products = pmanager.getProductsByCategory(category);

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
            System.out.println("[2] Add to Wishlist");
            System.out.println("[3] Back to Menu");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("3")) break;

            try {
                System.out.print("Enter Product ID: ");
                int productId = Integer.parseInt(scanner.nextLine());
                Product product = pmanager.getProductById(productId);

                if (product == null) {
                    System.out.println("Product not found!");
                    continue;
                }

                switch(choice) {
                    case "1": handleAddToCart(customer, product); break;
                    case "2": customer.getWishlist().addItem(product); System.out.println(product.getName() + " added to wishlist!"); break;
                    default: System.out.println("Invalid choice!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter numbers only.");
            }
        }
    }

    private static void handleAddToCart(Customer customer, Product product) {
        try {
            System.out.print("Enter Quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());

            if (quantity < 1) {
                System.out.println("Quantity must be at least 1!");
                return;
            }

            if (quantity > product.getStockQuantity()) {
                System.out.println("Insufficient stock! Available: " + product.getStockQuantity());
                return;
            }

            customer.getCart().addItem(product, quantity);
            product.reduceStock(quantity);
            System.out.println("\n" + quantity + " x " + product.getName() + " added to cart!");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter numbers only.");
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
    CartItem[] cartItems = customer.getCart().getItems(); 

    System.out.println("\n======== Your Cart ========");
    if (cartItems.length == 0) {
        System.out.println("Your cart is empty!");
        return;
    }

    double total = 0;

    for (CartItem item : cartItems) {
        Product product = item.getProduct();       
        int quantity = item.getQuantity();         
        double subtotal = product.getPrice() * quantity;
        total += subtotal;

        System.out.printf("%d x %s - $%.2f\n", quantity, product.getName(), subtotal);
    }

    System.out.printf("\nTotal: $%.2f\n", total);
    System.out.println("===================");
}


    private static void viewWishlist(Customer customer) {
        Wishlist wishlist = customer.getWishlist();

        while (true) {
            System.out.println("\n======== Wishlist ========");
            Product[] items = wishlist.getItems();
            if (items.length == 0) {
                System.out.println("Your wishlist is empty!");
                System.out.println("==========================");
                return;
            }

            for (Product item : items) {
                item.printDetails();
                System.out.println();
            }
            System.out.println("=========================");

            System.out.println("\n[1] Remove Item");
            System.out.println("[2] Back to Menu");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("2")) return;
            else if (choice.equals("1")) {
                try {
                    System.out.print("Enter Product ID to remove: ");
                    int productId = Integer.parseInt(scanner.nextLine());

                    if (wishlist.removeItem(productId)) {
                        System.out.println("Item removed successfully!");
                        if (wishlist.isEmpty()) {
                            System.out.println("Wishlist is now empty!");
                            return;
                        }
                    } else {
                        System.out.println("Product not found in wishlist!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter numbers only.");
                }
            } else {
                System.out.println("Invalid choice!");
            }
        }
    }

    private static void handleCheckout(Customer customer) {
        Cart cart = customer.getCart();
        if (cart.getItems().length == 0) {
            System.out.println("Your cart is empty!");
            return;
        }

        viewCart(customer);
        System.out.print("Enter discount code (or press enter to skip): ");
        String discountCode = scanner.nextLine().trim();
        if (discountCode.isEmpty()) discountCode = null;

        if (orderManager.placeOrder(customer, discountCode)) {
            System.out.println("Order placed successfully!");
        } else {
            System.out.println("Checkout failed. Please try again.");
        }
    }
}
