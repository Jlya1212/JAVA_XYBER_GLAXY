package manager;

import java.util.ArrayList;
import java.util.Scanner;
import java_assignment.User ;
import java_assignment.Customer;
import java_assignment.Admin;
import java_assignment.AdminType;
public class UserManager {
    private ArrayList<User> users;

    // Constructor: Initialize user list
    public UserManager() {
        users = new ArrayList<>();
    }

    // ---------------- Register Customer ----------------
    public void registerCustomer(Scanner scanner) {
        System.out.println("\n=== Register Customer ===");

        String username = getValidInput(scanner, "Enter username: ", this::isUsernameAvailable);
        String password = getValidInput(scanner, "Enter password: ", input -> !input.isEmpty());
        String name = getValidInput(scanner, "Enter name: ", input -> !input.isEmpty());
        String email = getValidInput(scanner, "Enter email: ", this::isValidEmail);

        users.add(new Customer(username, password, name, email));
        System.out.println(" Customer registered successfully.");
    }

    // ---------------- Register Admin ----------------
    public void registerAdmin(Scanner scanner) {
        System.out.println("\n=== Register Admin ===");

        String username = getValidInput(scanner, "Enter username: ", this::isUsernameAvailable);
        String password = getValidInput(scanner, "Enter password: ", input -> !input.isEmpty());
        String name = getValidInput(scanner, "Enter name: ", input -> !input.isEmpty());
        String email = getValidInput(scanner, "Enter email: ", this::isValidEmail);

        System.out.println("Choose Admin Type:");
        System.out.println("1. SUPER_ADMIN");
        System.out.println("2. PRODUCT_ADMIN");
        System.out.println("3. ORDER_ADMIN");
        System.out.print("Enter choice (1-3): ");
        int choice = Integer.parseInt(scanner.nextLine());

        AdminType type = switch (choice) {
            case 1 -> AdminType.SUPER_ADMIN;
            case 2 -> AdminType.PRODUCT_ADMIN;
            case 3 -> AdminType.ORDER_ADMIN;
            default -> {
                System.out.println("Invalid choice. Defaulting to PRODUCT_ADMIN.");
                yield AdminType.PRODUCT_ADMIN;
            }
        };

        users.add(new Admin(username, password, name, email, type));
        System.out.println(" Admin registered successfully as " + type);
    }

    // ---------------- Login ----------------
    public User login(Scanner scanner) {
        System.out.print("\nEnter username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.validatePassword(password)) {
                System.out.println("Login successful.");
                return user;
            }
        }

        System.out.println("Invalid credentials.");
        return null;
    }

    // ---------------- View All Users ----------------
    public void listAllUsers() {
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        for (User user : users) {
            user.printDetails();
        }
    }

    // ---------------- Validation Helpers ----------------
    private boolean isUsernameAvailable(String username) {
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                System.out.println("Username already exists!");
                return false;
            }
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            System.out.println(" Invalid email format.");
            return false;
        }
        return true;
    }

    private String getValidInput(Scanner scanner, String prompt, java.util.function.Predicate<String> validator) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
        } while (!validator.test(input));
        return input;
    }

    // ---------------- Getters ----------------
    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
    }
}
