
package java_assignment;
import java.util.Scanner;
public class Main {
int i = 0;
String selection;
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Hello World!");
        System.out.print("Login or Register(L/R):");
        String selection = input.next();
        while(selection != "R" || selection != "L"){
        if(selection == "R"){

        }
        }
    }
    
    public String RegisterUsername(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your name");
        String RegisterUsername = input.next();
        return RegisterUsername;
    }
    //this is a comment
}
