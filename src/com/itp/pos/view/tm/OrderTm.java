package com.itp.pos.view.tm;

import javafx.scene.control.Button;

import java.util.Date;

public class OrderTm {
    private String orderId;
    private Date date;
    private String customerName;
    private int itemCount;
    private double nettAmount;
    private Button btn;

    public OrderTm() {
    }

    public OrderTm(String orderId, Date date, String customerName, int itemCount, double nettAmount, Button btn) {
        this.orderId = orderId;
        this.date = date;
        this.customerName = customerName;
        this.itemCount = itemCount;
        this.nettAmount = nettAmount;
        this.btn = btn;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public double getNettAmount() {
        return nettAmount;
    }

    public void setNettAmount(double nettAmount) {
        this.nettAmount = nettAmount;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "OrderTm{" +
                "orderId='" + orderId + '\'' +
                ", date=" + date +
                ", customerName='" + customerName + '\'' +
                ", itemCount=" + itemCount +
                ", nettAmount=" + nettAmount +
                ", btn=" + btn +
                '}';
    }
}
