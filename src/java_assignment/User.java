package java_assignment;

// 1. This class is the abstract base for both Admin and Customer
public abstract class User {
    private String username;
    private String password;
    private String name;
    private String email;

    protected User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    // Getters
    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getEmail() { return email; }

    // Setters
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }

    // Validate password (used in login)
    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    public void printDetails() {
        System.out.println("Name: " + name + ", Username: " + username + ", Email: " + email);
    }
}
