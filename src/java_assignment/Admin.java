package java_assignment;

public class Admin extends User {
    private AdminType adminType;

    // Constructor no longer initializes managers
    public Admin(String username, String password, String name, String email, AdminType type) {
        super(username, password, name, email); // Call User constructor
        this.adminType = type;
    }

    // --- Admin Type Access ---
    // These methods are used by the Main class to check permissions
    public AdminType getAdminType() {
        return adminType;
    }

    public boolean isSuperAdmin() {
        return adminType == AdminType.SUPER_ADMIN;
    }

    public boolean isProductAdmin() {
        // Super admins can also manage products
        return adminType == AdminType.SUPER_ADMIN || adminType == AdminType.PRODUCT_ADMIN;
    }

    public boolean isOrderAdmin() {
        // Super admins can also manage orders
        return adminType == AdminType.SUPER_ADMIN || adminType == AdminType.ORDER_ADMIN;
    }

}