package com.itp.pos.view.tm;

public class OrderDetailTm {
    private String itemCode;
    private String item;
    private int qty;
    private double unitPrice;
    private double total;

    public OrderDetailTm() {
    }

    public OrderDetailTm(String itemCode, String item, int qty, double unitPrice, double total) {
        this.itemCode = itemCode;
        this.item = item;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.total = total;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderDetailTm{" +
                "itemCode='" + itemCode + '\'' +
                ", item='" + item + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                '}';
    }
}
