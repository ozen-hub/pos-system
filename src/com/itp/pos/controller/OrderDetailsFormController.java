package com.itp.pos.controller;

import com.itp.pos.db.Database;
import com.itp.pos.model.Order;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderDetailsFormController {
    public Label lblOrderId;
    public TextField txtCustomerId;
    public TextField txtAddress;
    public TextField txtSalary;
    public TextField txtDate;
    public TextField txtAmount;
    public TableView tblItems;
    public TableColumn colCode;
    public TableColumn colItemName;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;

    private String orderId;

    public void setOrderId(String orderId){
        this.orderId=orderId;
        lblOrderId.setText(orderId);
        loadData(this.orderId);
    }

    private void loadData(String orderId) {
        for (Order or: Database.orderTable){
            if(or.getOrderId().equals(orderId)){

                //-============
                txtCustomerId.setText(or.getCustomer().getId());
                txtAddress.setText(or.getCustomer().getAddress());
                txtSalary.setText(String.valueOf(or.getCustomer().getSalary()));
                //-============
                txtAmount.setText(String.valueOf(or.getNett()));
                txtDate.setText(
                        new SimpleDateFormat("yyyy-MM-dd")
                                .format(or.getDate())
                );
                //=============

                return;
            }
        }
    }
}
