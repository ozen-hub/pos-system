package com.itp.pos.model;

public class Product {
    private String productId;
    private String description;
    private double uitPrice;
    private int qtyOnHand;

    public Product() {
    }

    public Product(String productId, String description, double uitPrice, int qtyOnHand) {
        this.productId = productId;
        this.description = description;
        this.uitPrice = uitPrice;
        this.qtyOnHand = qtyOnHand;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUitPrice() {
        return uitPrice;
    }

    public void setUitPrice(double uitPrice) {
        this.uitPrice = uitPrice;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", description='" + description + '\'' +
                ", uitPrice=" + uitPrice +
                ", qtyOnHand=" + qtyOnHand +
                '}';
    }
}
