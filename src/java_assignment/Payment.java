/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_assignment;

import java.util.Scanner;

/**
 *
 * @author user
 */
public class Payment {
    CreditCardValidation creditCardValidate = new CreditCardValidation();
    EWalletValidation eWalletValidate = new EWalletValidation();
    String cardNum, cvv, expiryDate, phoneNum,securityCode;
    boolean result = false;
    
    public void selectPaymentMethod(){
        Scanner input = new Scanner(System.in);
        int selection;
        
        do{
        paymentMethodMenu();
        selection = input.nextInt();
        if(selection > 0 || selection <= 2){
            result = true;
        }else{
            System.out.println("Invalid Input!");
            paymentMethodMenu();
            selection = input.nextInt();
        }
        }while(result = false);
        
        switch(selection){
            case 1:
                CreditCard();
                break;
            case 2:
                EWallet();
                break;
            default:
                System.out.println("Invalid choice.");
    }
    }
    
    public void paymentMethodMenu(){
        System.out.println("=======================");
        System.out.println("    Payment Method");
        System.out.println("-----------------------");
        System.out.println(" 1. Credit Card");
        System.out.println(" 2. E-Wallet");
        System.out.println("-----------------------");
        System.out.print("Select Payment Method(1-2): ");
    }
    
    public void CreditCard(){
        Scanner input = new Scanner(System.in);
        
        //Enter card number
        do{
        System.out.print("Enter Credit Card Number(16-digits): ");
        cardNum = input.next();
        result = creditCardValidate.validCardNumber(cardNum);
        if(result == false){
            System.out.println("Invalid credit card format! Please try again!");
            System.out.println("\n--------------------------------\n");
        }
        }while(result == false);
        
        result = false;
        
        //Enter expiry date
        do{
        System.out.print("Enter Credit Card Expiry Date(MM/YY): ");
        expiryDate = input.next();
        result = creditCardValidate.validExpiryDate(expiryDate);
        if(result == false){
            System.out.println("Invalid credit card Expiry Date! Please try again!");
            System.out.println("\n--------------------------------\n");
        }
        }while(result == false);
        
        result = false;
        
        //Enter CVV 
        do{
        System.out.print("Enter Credit Card CVV(3-digits): ");
        cvv = input.next();
        result = creditCardValidate.validCVV(cvv);
        if(result == false){
            System.out.println("Invalid credit card CVV! Please try again!");
            System.out.println("\n--------------------------------\n");
        }
        }while(result == false);
    }
    
    public void EWallet(){
        Scanner input = new Scanner(System.in);
        
        //Enter phone number
        do{
        System.out.print("Enter phone number(without -): ");
        phoneNum = input.next();
        result = eWalletValidate.ValidPhone(phoneNum);
        if(result == false){
            System.out.println("Invalid Phone Number! Please try again!");
            System.out.println("\n--------------------------------\n");
        }
        }while(result == false);
        
        result = false;
        
        //Enter security code 
        do{
        System.out.print("Enter Security Code(6-digits): ");
        securityCode = input.next();
        result = eWalletValidate.ValidSecurityCode(securityCode);
        if(result == false){
            System.out.println("Invalid code! Please try again!");
            System.out.println("\n--------------------------------\n");
        }
        }while(result == false);
    }
}
