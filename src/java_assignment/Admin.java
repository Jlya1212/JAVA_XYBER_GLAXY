package java_assignment;

public class Admin extends User {
    private ProductManager productManager;
    private UserManager userManager;

    public Admin(String username, String password, String name, String email) {
        super(username, password, name, email);
        this.productManager = new ProductManager();
        this.userManager = new UserManager(); // each admin can manage users
    }

    // ---------------- Product Management ----------------
    public void addProduct(Product p) {
        productManager.addProduct(p);
    }

    public boolean deleteProduct(int productId) {
        return productManager.deleteProduct(productId);
    }

    public boolean updateProduct(int productId, java.util.Scanner scanner) {
        return productManager.updateProduct(productId, scanner);
    }

    public Product[] listAllProducts() {
        return productManager.listProducts();
    }

    // ---------------- User Management (non-static now) ----------------
    public void registerNewUser(java.util.Scanner scanner) {
        userManager.registerUser(scanner);
    }

    public void viewAllUsers() {
        userManager.listAllUsers();
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public ProductManager getProductManager() {
        return productManager;
    }
}
