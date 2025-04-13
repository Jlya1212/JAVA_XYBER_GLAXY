package java_assignment;

public class Admin extends User {
    private AdminType adminType;
    private ProductManager productManager;
    private OrderManager orderManager;

    public Admin(String username, String password, String name, String email, AdminType type) {
        super(username, password, name, email);
        this.adminType = type;
        this.productManager = new ProductManager();
        this.orderManager = new OrderManager();
    }

    // ---------------- Admin Type Access ----------------
    public AdminType getAdminType() {
        return adminType;
    }

    public boolean isSuperAdmin() {
        return adminType == AdminType.SUPER_ADMIN;
    }

    public boolean isProductAdmin() {
        return isSuperAdmin() || adminType == AdminType.PRODUCT_ADMIN;
    }

    public boolean isOrderAdmin() {
        return isSuperAdmin() || adminType == AdminType.ORDER_ADMIN;
    }

    // ---------------- Product Management ----------------
    public void addProduct(Product product) {
        if (!isProductAdmin()) {
            System.out.println("You do not have permission to add products.");
            return;
        }
        productManager.addProduct(product);
        System.out.println(" Product added.");
    }

    public boolean deleteProduct(int productId) {
        if (!isProductAdmin()) {
            System.out.println("You do not have permission to delete products.");
            return false;
        }
        return productManager.deleteProduct(productId);
    }

    public boolean updateProduct(int productId, java.util.Scanner scanner) {
        if (!isProductAdmin()) {
            System.out.println("You do not have permission to update products.");
            return false;
        }
        return productManager.updateProduct(productId, scanner);
    }

    public Product[] listAllProducts() {
        if (!isProductAdmin()) {
            System.out.println("You do not have permission to view product list.");
            return new Product[0];
        }
        return productManager.listProducts();
    }

    // ---------------- Order Management ----------------
    public boolean placeOrderForCustomer(Customer customer, String discountCode) {
        if (!isOrderAdmin()) {
            System.out.println("You do not have permission to place orders.");
            return false;
        }
        return orderManager.placeOrder(customer, discountCode);
    }

    public void viewAllOrders() {
        if (!isOrderAdmin()) {
            System.out.println("You do not have permission to view orders.");
            return;
        }
        for (Order order : orderManager.getAllOrders()) {
            order.printDetails();
        }
    }

    // ---------------- Getters ----------------
    public ProductManager getProductManager() {
        return productManager;
    }

    public OrderManager getOrderManager() {
        return orderManager;
    }
}
