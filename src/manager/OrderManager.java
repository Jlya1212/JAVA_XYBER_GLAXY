/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package manager;

/**
 *
 * @author hocky
 */
import java.util.ArrayList;
import java.util.List;
import java_assignment.Cart;
import java_assignment.Order;
import java_assignment.CartItem;
import java_assignment.OrderItem;
import java_assignment.Customer;

public class OrderManager {
    private List<Order> orders = new ArrayList<>();

    public boolean placeOrder(Customer customer, String discountCode) {
        Cart cart = customer.getCart();

        if (cart.isEmpty()) return false;

        OrderItem[] orderItems = convertCartToOrderItems(cart);

        if (!validateStock(orderItems)) return false;

        updateStock(orderItems);
        Order order = new Order(customer, orderItems, discountCode);
        orders.add(order);

        cart.clear();
        return true;
    }

    private OrderItem[] convertCartToOrderItems(Cart cart) {
        CartItem[] cartItems = cart.getItems();
        OrderItem[] items = new OrderItem[cartItems.length];

        for (int i = 0; i < cartItems.length; i++) {
            CartItem cartItem = cartItems[i];
            items[i] = new OrderItem(cartItem.getProduct(), cartItem.getQuantity());
        }
        return items;
    }

    private boolean validateStock(OrderItem[] items) {
        for (OrderItem item : items) {
            if (item.getProduct().getStockQuantity() < item.getQuantity()) {
                return false;
            }
        }
        return true;
    }

    private void updateStock(OrderItem[] items) {
        for (OrderItem item : items) {
            item.getProduct().reduceStock(item.getQuantity());
        }
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders); // Return copy to protect internal list
    }
}
