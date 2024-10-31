package com.itp.pos.model;

public class Item {
    private String id;
    private int qty;
    private double unitPrice;

    public Item() {
    }

    public Item(String id, int qty, double unitPrice) {
        this.id = id;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
