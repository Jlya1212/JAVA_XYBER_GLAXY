
package java_assignment;

import java.util.Scanner;




public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String role, selection, username, password, name, email;

        System.out.println("Hello, welcome to Xyber Glaxy Store!");
        System.out.println("====================================");

        // Prompt for role first
        while (true) {
            System.out.print("Select role (Customer/Admin): ");
            role = input.nextLine().trim().toLowerCase();
            if (role.equals("customer") || role.equals("admin")) {
                break;
            }
            System.out.println("Invalid role. Please enter Customer or Admin.");
        }

        if (role.equals("admin")) {
            // Admin can only log in
            System.out.print("Enter username: ");
            username = input.nextLine();
            System.out.print("Enter password: ");
            password = input.nextLine();
            User user = UserManager.login(username, password);
            if (user != null) {
                System.out.println("Logged in as Admin");
            }
        } else {
            // Customer: choose to log in or register
            System.out.print("Login(L) Or Register(R): ");
            selection = input.nextLine().toUpperCase();
            while (!selection.equals("L") && !selection.equals("LOGIN") && !selection.equals("R") && !selection.equals("REGISTER")) {
                System.out.println("Invalid Input! Please Try Again!");
                System.out.print("Login(L) Or Register(R): ");
                selection = input.nextLine().toUpperCase();
            }

            if (selection.equals("R") || selection.equals("REGISTER")) {
                // Customer registration
                System.out.print("Enter username: ");
                username = input.nextLine();
                System.out.print("Enter password: ");
                password = input.nextLine();
                System.out.print("Enter name: ");
                name = input.nextLine();
                System.out.print("Enter email: ");
                email = input.nextLine();
                UserManager.registerUser(username, password, name, email, "Customer");
            } else {
                // Customer login
                System.out.print("Enter username: ");
                username = input.nextLine();
                System.out.print("Enter password: ");
                password = input.nextLine();
                User user = UserManager.login(username, password);
                if (user != null) {
                    System.out.println("Logged in as Customer");
                }
            }
        }
    }
}