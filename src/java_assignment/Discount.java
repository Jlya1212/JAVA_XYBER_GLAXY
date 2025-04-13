/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_assignment;

/**
 *
 * @author user
 */
public class Discount {
    private String discountCode;
    private double percentage;
    private boolean isActive;

    public Discount(){
    }
    
    public Discount(String discountCode, double percentage){
        this.discountCode = discountCode;
        this.percentage = percentage;
        this.isActive = true;
    }

    public String getDiscountCode(){
        return discountCode;
    }

    public double getPercentage(){
        return percentage;
    }

    public boolean getIsActive(){
        return isActive;
    }

    public void setDiscountCode(String discountCode){
        this.discountCode = discountCode;
    }

    public void setPercentage(double percentage){
        this.percentage = percentage;
    }

    public void setIsActive(boolean isActive){
        this.isActive = isActive;
    }

    public double calculateDiscount(double amount, double percentage){
        return amount * (1 - percentage/100);
    }
}


