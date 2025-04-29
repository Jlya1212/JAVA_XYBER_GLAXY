package java_assignment;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); 
        SystemMenu menu = new SystemMenu(scanner);
        menu.start(); 
    }
}
