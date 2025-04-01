package java_assignment;

public class Customer extends User {
    private Cart cart = new Cart();
    private Wishlist wishlist = new Wishlist();

    public Customer(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

    // Add this method
    public Cart getCart() {
        return cart;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }
}