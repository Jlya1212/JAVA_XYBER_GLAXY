
package java_assignment;

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

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }
}
