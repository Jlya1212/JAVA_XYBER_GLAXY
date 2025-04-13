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
public class DiscountManager{
    Scanner input = new Scanner(System.in);
    private Discount[] discounts = new Discount[10];
    private int count = 0;
    String status = "Active";
    
    public DiscountManager(){
    }

    public Discount getDiscounts(int address){
        return discounts[address];
    }

    public int getCount(){
        return count;
    }

    public double CodeValidation(String code){
        Discount d = new Discount();
        int num = 0;
        d.setDiscountCode(code);
            for(int i=0;i < getCount();i++){
        if(discounts[i].getDiscountCode().equals(d.getDiscountCode())){
            if(discounts[i].getIsActive() == true){
            System.out.println("Valid code!") ;
            num = i;
            return discounts[num].getPercentage();
            }else{
                System.out.println("The code is overdated!");
            }
        }
     }
            System.out.println("Invalid Code! Please try again!");
            return 1;
    }

    public void addCode(){
        double percentage = 0;
        System.out.println("\n");
        System.out.print("Enter new code name: ");
        String codeName = input.next();
        do{
        System.out.print("Enter percentages: ");
        percentage = input.nextDouble();
        if(percentage > 100 || percentage < 0){
            System.out.print("Invalid Input! Please try again!");
            System.out.print("\n");
        }
        }while(percentage > 100 || percentage < 0);
        Discount d = new Discount(codeName, percentage);
        d.setDiscountCode(codeName);
        d.setPercentage(percentage);
        discounts[count] = d;
        this.count++;
    }

    public void displayAllDiscount(){
        System.out.println("=================");
        System.out.println("| All Discounts |");
        System.out.println("=================");

            for(int i = 0; i < count; i++){
              int num = i + 1;
              boolean currentStatus = discounts[i].getIsActive();

              System.out.print("Code [" + num + "]: " + discounts[i].getDiscountCode());

              if(currentStatus == true){
                  status = "Active";
              }else{
                  status = "Deactive";
              }

              System.out.println(" (" + status + ")");
              System.out.println("Percentage: " + discounts[i].getPercentage());

            }

        System.out.println("=================");
    }

    public String activeCode(String code){
        for(int i = 0; i < count; i++){
            if(discounts[i].getDiscountCode().equals(code)){
                discounts[i].setIsActive(true);
                return "\nSet '" + discounts[i].getDiscountCode() + "' as Active successfully!\n";
            }
        }
        return "\nProgress unsuccessful! Please try again!\n";
    }

    public String deactiveCode(String code){
        for(int i = 0; i < count; i++){
            if(discounts[i].getDiscountCode().equals(code)){
                discounts[i].setIsActive(false);
                return "\nSet '" + discounts[i].getDiscountCode() + "' as Deactive successfully!\n";
            }
        }
        return "\nProgress unsuccessful! Please try again!\n";
    }
    
}
