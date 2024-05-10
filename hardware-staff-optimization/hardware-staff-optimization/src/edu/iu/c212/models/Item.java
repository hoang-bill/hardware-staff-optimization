package edu.iu.c212.models;

public class Item {
    private String name;
    private double price;
    private int quantity;
    private int aisleNum;

    public Item(String name, double price, int quantity, int aisleNum) {
        setName(name);
        setPrice(price);
        setQuantity(quantity);
        setAisle(aisleNum);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim(); // Trim whitespace
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative.");
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("Quantity cannot be negative.");
        this.quantity = quantity;
    }

    public int getAisle() {
        return aisleNum;
    }

    public void setAisle(int aisleNum) {
        this.aisleNum = aisleNum;
    }

    @Override
    public String toString() {
        return String.format("Item{name='%s', price=%.2f, quantity=%d, aisle=%d}", name, price, quantity, aisleNum);
    }
}
