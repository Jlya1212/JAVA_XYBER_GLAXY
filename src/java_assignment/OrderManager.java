package java_assignment;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.Comparator;

public class OrderManager {
    private List<Order> orders = new ArrayList<>();
    private DiscountManager discountManager;
    
    public OrderManager(DiscountManager discountManager) {
        this.orders = new ArrayList<>();
        if (discountManager == null) {
             throw new IllegalArgumentException("DiscountManager cannot be null.");
        }
        this.discountManager = discountManager;
        
    }

    public boolean placeOrder(Customer customer, String discountCodeInput, PaymentMethod paymentMethod, String cardNumber, String cvv) {
        Cart cart = customer.getCart();

        if (cart.isEmpty()) {
            System.out.println("❌ Error: Cannot place order with an empty cart.");
            return false;
        }

        OrderItem[] orderItems = convertCartToOrderItems(cart);

        // --- Stock Validation ---
        if (!validateStock(orderItems)) {
            // validateStock now prints details
            System.out.println("❌ Error: Order cannot be placed due to insufficient stock.");
            return false;
        }

        // --- Discount Validation ---
        Discount appliedDiscount = null;
        double discountPercentage = 0.0;
        String validatedDiscountCode = null; // The code string to store in the Order if valid

        if (discountCodeInput != null && !discountCodeInput.trim().isEmpty()) {
            String trimmedCode = discountCodeInput.trim();
            appliedDiscount = discountManager.findActiveDiscountByCode(trimmedCode); // Use the new method

            if (appliedDiscount != null) {
                // Discount is valid and active
                discountPercentage = appliedDiscount.getPercentage();
                validatedDiscountCode = appliedDiscount.getDiscountCode(); // Use the canonical code from Discount object
                System.out.println("✅ Discount '" + validatedDiscountCode + "' applied (" + discountPercentage + "%).");
            } else {
                // Discount code provided but was invalid or inactive
                System.out.println("⚠️ Discount code '" + trimmedCode + "' is invalid or inactive. Proceeding without discount.");
                // validatedDiscountCode remains null
            }
        } else {
             // No discount code was entered
             System.out.println("ℹ️ No discount code entered.");
        }


        // --- Calculate Final Total Price ---
        double subTotal = 0;
        for (OrderItem item : orderItems) {
            subTotal += item.getTotalPrice(); // Sum up individual item totals
        }
        // Apply the validated discount percentage (0% if no valid code)
        double finalTotal = subTotal * (1.0 - (discountPercentage / 100.0));
        
        Payment payment = new Payment(finalTotal, paymentMethod, cardNumber, cvv);
        boolean paymentSuccessful = payment.processPayment(); // Attempt payment

        if (!paymentSuccessful) {
            // Payment failed (message printed by payment.processPayment)
            System.out.println("❌ Order cannot be placed due to payment failure.");
            return false; // Stop the order process
        }

        // --- Proceed only if payment was successful ---
        System.out.println("Payment confirmed. Finalizing order...");

        // --- Stock Update ---
        updateStock(orderItems);

        // --- Order Creation ---
        Order order = new Order(customer, orderItems, validatedDiscountCode, finalTotal); // Pass finalTotal now
        orders.add(order);

        // --- Final Steps ---
        cart.clear(); // Clear cart after successful order
        System.out.println("\n✅ Order successfully placed!");
        order.printDetails(); // Print the order details/receipt right after placing

        return true;
    }
    
    public double calculateFinalPrice(Cart cart, String discountCodeInput) {
        if (cart == null || cart.isEmpty()) {
            return 0.0;
        }

        double subTotal = cart.getTotal();
        double discountPercentage = 0.0;
        Discount appliedDiscount = null;

         if (discountCodeInput != null && !discountCodeInput.trim().isEmpty()) {
            appliedDiscount = discountManager.findActiveDiscountByCode(discountCodeInput.trim());
            if (appliedDiscount != null) {
                discountPercentage = appliedDiscount.getPercentage();
            }
        }
        return subTotal * (1.0 - (discountPercentage / 100.0));
    }

    // --- convertCartToOrderItems, validateStock, updateStock, getAllOrders, getOrderById remain the same ---
    private OrderItem[] convertCartToOrderItems(Cart cart) {
        CartItem[] cartItems = cart.getItems();
        OrderItem[] items = new OrderItem[cartItems.length];
        for (int i = 0; i < cartItems.length; i++) {
            items[i] = new OrderItem(cartItems[i].getProduct(), cartItems[i].getQuantity());
        }
        return items;
    }

    private boolean validateStock(OrderItem[] items) {
        boolean sufficientStock = true;
        for (OrderItem item : items) {
            Product product = item.getProduct();
            if (product.getStockQuantity() < item.getQuantity()) {
                System.out.println("❌ Stock Alert: Not enough stock for '" + product.getName() +
                                   "'. Required: " + item.getQuantity() +
                                   ", Available: " + product.getStockQuantity());
                sufficientStock = false; // Mark as insufficient but continue checking others
            }
        }
        return sufficientStock; // Return overall result
    }

    private void updateStock(OrderItem[] items) {
        for (OrderItem item : items) {
            item.getProduct().reduceStock(item.getQuantity());
        }
    }
    
    public List<Order> getOrdersForCustomer(Customer customer) {
        if (customer == null) {
            return new ArrayList<>(); // Return empty list if customer is null
        }

        List<Order> customerOrders = new ArrayList<>();
        for (Order order : this.orders) {
            // Compare customers based on username (assuming username is unique identifier)
            if (order.getCustomer() != null && order.getCustomer().getUsername().equals(customer.getUsername())) {
                customerOrders.add(order);
            }
        }
        return customerOrders;
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    public Order getOrderById(int orderId) {
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        return null;
    }
    
    public void generateDailyReport(LocalDate targetDate) {
    System.out.println("\n📅 === Daily Report for " + targetDate.format(DateTimeFormatter.ISO_DATE) + " ===");

    List<Order> filteredOrders = orders.stream()
        .filter(order -> order.getDate().toLocalDate().equals(targetDate))
        .collect(Collectors.toList());

    printOrderReportSummary(filteredOrders);
}

    public void generateMonthlyReport(int year, int month) {
        System.out.println("\n📅 === Monthly Report for " + year + "-" + String.format("%02d", month) + " ===");

        List<Order> filteredOrders = orders.stream()
            .filter(order -> {
                LocalDate date = order.getDate().toLocalDate();
                return date.getYear() == year && date.getMonthValue() == month;
            })
            .collect(Collectors.toList());

        printOrderReportSummary(filteredOrders);
    }

    public void generateYearlyReport(int year) {
        System.out.println("\n📅 === Yearly Report for " + year + " ===");

        List<Order> filteredOrders = orders.stream()
            .filter(order -> order.getDate().toLocalDate().getYear() == year)
            .collect(Collectors.toList());

        printOrderReportSummary(filteredOrders);
    }

    private void printOrderReportSummary(List<Order> reportOrders) {
        if (reportOrders.isEmpty()) {
            System.out.println("⚠️ No orders found for this period.");
            return;
        }

        double totalSales = 0.0;
        int totalOrders = reportOrders.size();

        for (Order order : reportOrders) {
            totalSales += order.getTotalPrice();
            System.out.println("- Order #" + order.getOrderId() + " | Customer: " + order.getCustomer().getUsername() + 
                               " | Date: " + order.getDate() + 
                               " | Total: RM" + String.format("%.2f", order.getTotalPrice()));
        }

        System.out.println("\n🧾 Summary:");
        System.out.println("Total Orders: " + totalOrders);
        System.out.println("Total Sales: RM" + String.format("%.2f", totalSales));
    }
}
