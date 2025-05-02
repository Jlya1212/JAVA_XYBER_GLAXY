/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_assignment;


public class Payment {
    private double amount;
    private PaymentMethod paymentMethod;
    private String cardNumber;
    private String cvv;
    
    
    public Payment(double amount, PaymentMethod paymentMethod, String cardNumber, String cvv){
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }
    
    public double getAmount(){
        return amount;
    }
    public PaymentMethod getPaymentMethod(){
        return paymentMethod;
    }
    public String getCardNumber(){
        return cardNumber;
    }
    public String getCvv(){
        return cvv;
    }
    
    public boolean processPayment() {
        System.out.println("\nProcessing " + paymentMethod + " payment for RM" + String.format("%.2f", amount) + "...");

        
        boolean validCardNumber = cardNumber != null && cardNumber.matches("\\d{13,16}"); // Basic check for 13-16 digits
        boolean validCvv = cvv != null && cvv.matches("\\d{3,4}"); // Basic check for 3 or 4 digits

        if (!validCardNumber) {
            System.out.println("❌ Payment Failed: Invalid card number format (must be 13-16 digits).");
            return false;
        }
        if (!validCvv) {
            System.out.println("❌ Payment Failed: Invalid CVV format (must be 3-4 digits).");
            return false;
        }

        
        System.out.println("✅ Payment Successful!");
        
        return true;
    }
}
