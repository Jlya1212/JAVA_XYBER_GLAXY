package java_assignment;

public abstract class Product implements ProductInterface {
    private int productID;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;

    public Product(int productID, String name, String description, double price, int stockQuantity) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    // Getters
    public int getProductID() { return productID; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public void reduceStock(int quantity) {
        stockQuantity = Math.max(0, stockQuantity - quantity);
    }

    public void increaseStock(int quantity) {
        stockQuantity += quantity;
    }

    protected void printBaseDetails() {
        System.out.println("ID: " + productID);
        System.out.println("Name: " + name);
        System.out.printf("Price: RM%.2f\n", price);
        System.out.println("Stock: " + stockQuantity);
        System.out.println("Description: " + description);
    }

    @Override
    public String toString() {
        return "[" + getCategory() + "] ID: " + productID + " | Name: " + name + " | RM" + String.format("%.2f", price);
    }

    public abstract String getCategory();
}
