package com.itp.pos.view.tm;

import javafx.scene.control.ButtonBar;

public class CustomerTm {
    private String id;
    private String name;
    private String address;
    private double salary;
    private ButtonBar tool;

    public CustomerTm() {
    }

    public CustomerTm(String id, String name, String address, double salary, ButtonBar tool) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.salary = salary;
        this.tool = tool;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public ButtonBar getTool() {
        return tool;
    }

    public void setTool(ButtonBar tool) {
        this.tool = tool;
    }

    @Override
    public String toString() {
        return "CustomerTm{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                ", tool=" + tool +
                '}';
    }
}
