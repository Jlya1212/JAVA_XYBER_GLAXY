
package java_assignment;

import java.util.Scanner;




public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String selection, username, password, name, email, role;

        System.out.println("Hello, welcome to Xyber Glaxy Store!");
        System.out.println("====================================");
        System.out.print("Login(L) Or Register(R): ");
        selection = input.nextLine().toUpperCase();

        while (!selection.equals("L") && !selection.equals("LOGIN") && !selection.equals("R") && !selection.equals("REGISTER")) {
            System.out.println("Invalid Input! Please Try Again!");
            System.out.print("Login(L) Or Register(R): ");
            selection = input.nextLine().toUpperCase();
        }

        if (selection.equals("R") || selection.equals("REGISTER")) {
            System.out.print("Enter role (Customer): ");
            role = input.nextLine().trim();

            if (role.equalsIgnoreCase("Admin")) {
                System.out.println("Admin cannot register. Please login instead.");
                return;
            } else if (!role.equalsIgnoreCase("Customer")) {
                System.out.println("Invalid role. Only Customer can register.");
                return;
            }

            System.out.print("Enter username: ");
            username = input.nextLine();
            System.out.print("Enter password: ");
            password = input.nextLine();
            System.out.print("Enter name: ");
            name = input.nextLine();
            System.out.print("Enter email: ");
            email = input.nextLine();

            UserManager.registerUser(username, password, name, email, role);
        } else {
            System.out.print("Enter username: ");
            username = input.nextLine();
            System.out.print("Enter password: ");
            password = input.nextLine();
            User user = UserManager.login(username, password);
            if (user != null) {
                if (user instanceof Customer) {
                    System.out.println("Logged in as Customer");
                } else if (user instanceof Admin) {
                    System.out.println("Logged in as Admin");
                }
            }
        }
    }
}