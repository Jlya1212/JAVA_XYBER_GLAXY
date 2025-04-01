package java_assignment;

import java.util.Arrays;
import java.util.Scanner;

public class UserManager {
    private static User[] users = new User[0];
    private static int userCount = 0;

    static {
        // Add predefined admin account
        users = addUser(users, new Admin("admin", "admin", "Admin User", "admin@xyberglaxy.com"));
    }

    public static void registerUser(Scanner scanner) {
        String username, password, name, email;

        // Username input with validation
        while (true) {
            System.out.print("Enter username: ");
            username = scanner.nextLine().trim();
            if (username.isEmpty()) {
                System.out.println("Username cannot be empty!");
            } else if (isUsernameTaken(username)) {
                System.out.println("Username already exists!");
            } else {
                break;
            }
        }

        // Password input with validation
        while (true) {
            System.out.print("Enter password: ");
            password = scanner.nextLine().trim();
            if (password.isEmpty()) {
                System.out.println("Password cannot be empty!");
            } else {
                break;
            }
        }

        // Name input with validation
        while (true) {
            System.out.print("Enter name: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty!");
            } else {
                break;
            }
        }

        // Email input with validation
        while (true) {
            System.out.print("Enter email: ");
            email = scanner.nextLine().trim();
            if (email.isEmpty()) {
                System.out.println("Email cannot be empty!");
            } else if (!isValidEmail(email)) {
                System.out.println("Invalid email format!");
            } else {
                break;
            }
        }

        // Create and register new customer
        users = addUser(users, new Customer(username, password, name, email));
        System.out.println("\nUser registered successfully!");
    }

    public static User login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        for (User user : users) {
            if (user != null && 
                user.getUsername().equals(username) && 
                user.validatePassword(password)) {
                return user;
            }
        }
        System.out.println("Invalid credentials!");
        return null;
    }

    private static User[] addUser(User[] array, User newUser) {
        User[] newArray = Arrays.copyOf(array, array.length + 1);
        newArray[array.length] = newUser;
        return newArray;
    }

    private static boolean isUsernameTaken(String username) {
        for (User user : users) {
            if (user != null && user.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValidEmail(String email) {
        return email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    }
}