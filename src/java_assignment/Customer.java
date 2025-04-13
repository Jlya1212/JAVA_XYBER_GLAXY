package java_assignment;

// 2. This class represents a normal user with a shopping cart and wishlist
public class Customer extends User {
    private Cart cart = new Cart();
    private Wishlist wishlist = new Wishlist();

    public Customer(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

    public Cart getCart() {
        return cart;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }
}
