package java_assignment;

public abstract class Product implements Printable {
    private int productID;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;

    public Product(int productID, String name, String description, 
                  double price, int stockQuantity) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    // Getters
    public int getProductID() { 
        return productID; 
    }
    public String getName() { 
        return name; 
    }
    public String getDescription() { 
        return description; 
    }
    public double getPrice() { 
        return price; 
    }
    public int getStockQuantity() { 
        return stockQuantity; 
    }

    // Stock management
    public void reduceStock(int quantity) {
    stockQuantity = Math.max(0, stockQuantity - quantity);
    }

    public void increaseStock(int quantity) {
    stockQuantity += quantity;
    }

    public abstract String getCategory();
}