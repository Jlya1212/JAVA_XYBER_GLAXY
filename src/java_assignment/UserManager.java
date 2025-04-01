
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
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                System.out.println("Username already exists.");
                return;
            }
        }

        if (!role.equalsIgnoreCase("Customer")) {
            System.out.println("Invalid role specified. Only Customer can be registered.");
            return;
        }

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
}