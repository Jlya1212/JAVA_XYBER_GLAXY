/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package java_assignment;

/**
 *
 * @author student
 */
public class User {
    private String username;
    private String password;
    private String name;
    private String email;


    public User(String username, String password, String name, String email){
    this.username = username;
    this.password = password;
    this.name     = name;
    this.email    = email;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return password;
    }
    
    public String getName(){
        return name;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setUsername(){
        this.username = username;
    }
    
    public void setPassword(){
        this.password = password;
    }
    
    public void setName(){
        this.name = name;
    }
    
    public void setEmail(){
        this.email = email;
    }
}