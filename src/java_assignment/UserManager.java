package java_assignment;

import java.util.ArrayList;
import java.util.Scanner;

public class UserManager {
    private ArrayList<User> users;

    // Constructor - initialize user list and default admin
    public UserManager() {
        users = new ArrayList<>();
        users.add(new Admin("admin", "admin", "Admin User", "admin@xyberglaxy.com"));
    }

    // 1. Register customer
    public void registerUser(Scanner scanner) {
        String username = getValidInput(scanner, "Enter username: ", this::isUsernameAvailable);
        String password = getValidInput(scanner, "Enter password: ", input -> !input.isEmpty());
        String name = getValidInput(scanner, "Enter name: ", input -> !input.isEmpty());
        String email = getValidInput(scanner, "Enter email: ", this::isValidEmail);

        users.add(new Customer(username, password, name, email));
        System.out.println("✅ User registered successfully.");
    }

    // 2. Login
    public User login(Scanner scanner) {
        System.out.print("Enter username: ");
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

    // 3. List all users
    public void listAllUsers() {
        for (User user : users) {
            user.printDetails();
        }
    }

    // ----------- Private Validation Helpers ------------

    private boolean isUsernameAvailable(String username) {
        if (username.isEmpty()) return false;
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
            System.out.println("Invalid email format.");
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
}
