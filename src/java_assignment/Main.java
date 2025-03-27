
package java_assignment;

import java.util.Scanner;



public class Main {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String selection, name, password, email;
        
        Display display = new Display();
        display.welcome();
        selection = input.next();
        selection = display.selection(selection);
        
        if(selection.equals("L") && selection.equals("LOGIN")){
            name = display.getName();
            password = display.getPassword();
        }else if(selection.equals("R") && selection.equals("REGISTER")){
            name = display.getName();
            password = display.getPassword();
            email = display.getEmail();
        }
    }
}

class Display {
    
    public void welcome(){
        System.out.println("Hello, welcome to PRXXT Store!");
        System.out.println("==============================");
        System.out.print("Login(L) Or Register(R): ");
    }
    
    public String selection(String selection){
        Scanner input = new Scanner(System.in);
        selection = selection.toUpperCase();
        while(!selection.equals("L") && !selection.equals("LOGIN") && !selection.equals("R") && !selection.equals("REGISTER")){
            System.out.println("Invalid Input! Please Try Again!");
            System.out.print("Login(L) Or Register(R): ");
            selection = input.next();
            selection = selection.toUpperCase();
        }
        
        return selection;
    }
    
    public String getName(){
        Scanner input = new Scanner(System.in);
        String name;
        System.out.print("Enter your name: ");
        name = input.next();
        return name;
    }
    
    public String getPassword(){
        Scanner input = new Scanner(System.in);
        String password;
        System.out.print("Enter your password: ");
        password = input.next();
        return password;
    }
        

    public String getEmail(){
        Scanner input = new Scanner(System.in);
        String email;
        System.out.print("Enter your email: ");
        email = input.next();
        return email;
    }
       
}

