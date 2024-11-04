package com.itp.pos.model;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private String orderId;
    private Customer customer;
    private Date date;
    private double nett;
    private ArrayList<Item> items;

    public Order() {
    }

    public Order(String orderId, Customer customer, Date date, double nett, ArrayList<Item> items) {
        this.orderId = orderId;
        this.customer = customer;
        this.date = date;
        this.nett = nett;
        this.items = items;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getNett() {
        return nett;
    }

    public void setNett(double nett) {
        this.nett = nett;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customer=" + customer +
                ", date=" + date +
                ", nett=" + nett +
                ", items=" + items +
                '}';
    }
}
