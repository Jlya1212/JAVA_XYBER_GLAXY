
package java_assignment;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static List<User> users = new ArrayList<>();

    static {
        // Predefined admin account
        users.add(new Admin("admin", "admin", "Admin User", "admin@xyberglaxy.com"));
    }

    public static void registerUser(String username, String password, String name, String email, String role) {
        // Input validation
        if (username == null || username.trim().isEmpty()) {
            System.out.println("Username cannot be empty.");
            return;
        }
        if (password == null || password.trim().isEmpty()) {
            System.out.println("Password cannot be empty.");
            return;
        }
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        if (email == null || email.trim().isEmpty()) {
            System.out.println("Email cannot be empty.");
            return;
        }
        if (!isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return;
        }

        // Check for duplicate username
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists.");
                return;
            }
        }

        // Validate role
        if (!role.equalsIgnoreCase("Customer")) {
            System.out.println("Invalid role specified. Only Customer can be registered.");
            return;
        }

        // Register new customer
        User newUser = new Customer(username, password, name, email);
        users.add(newUser);
        System.out.println("User registered successfully.");
    }

    public static User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.validatePassword(password)) {
                return user;
            }
        }
        System.out.println("Invalid username or password.");
        return null;
    }

    // Simple email validation method
    private static boolean isValidEmail(String email) {
        // Basic check: contains "@" and "." with non-empty parts
        return email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    }
}