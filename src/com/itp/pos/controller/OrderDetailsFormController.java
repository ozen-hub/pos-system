package com.itp.pos.controller;

import com.itp.pos.db.Database;
import com.itp.pos.model.Item;
import com.itp.pos.model.Order;
import com.itp.pos.model.Product;
import com.itp.pos.view.tm.OrderDetailTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderDetailsFormController {
    public Label lblOrderId;
    public TextField txtCustomerId;
    public TextField txtAddress;
    public TextField txtSalary;
    public TextField txtDate;
    public TextField txtAmount;
    public TableView<OrderDetailTm> tblItems;
    public TableColumn colCode;
    public TableColumn colItemName;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;

    private String orderId;

    public void initialize(){
        colCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("item"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
    }

    public void setOrderId(String orderId){
        this.orderId=orderId;
        lblOrderId.setText(orderId);
        loadData(this.orderId);
    }

    ObservableList<OrderDetailTm> observableList
            = FXCollections.observableArrayList();

    private void loadData(String orderId) {
        for (Order or: Database.orderTable){
            if(or.getOrderId().equals(orderId)){

                //-============
                txtCustomerId.setText(or.getCustomer().getName());
                txtAddress.setText(or.getCustomer().getAddress());
                txtSalary.setText(String.valueOf(or.getCustomer().getSalary()));
                //-============
                txtAmount.setText(String.valueOf(or.getNett()));
                txtDate.setText(
                        new SimpleDateFormat("yyyy-MM-dd")
                                .format(or.getDate())
                );
                //=============

                for (Item i: or.getItems()){

                    Product selectedProduct =
                            findProduct(i.getId());

                    OrderDetailTm tm = new OrderDetailTm(
                            i.getId(),
                            selectedProduct==null?"Not Found":
                            selectedProduct.getDescription(),
                            i.getQty(),
                            i.getUnitPrice(),
                            i.getQty()*i.getUnitPrice()
                    );
                    observableList.add(tm);
                    tblItems.setItems(observableList);
                }

                return;
            }
        }
    }

    private Product findProduct(String code){
        for (Product p: Database.productTable){
            if (p.getProductId().equals(code)){
                return p;
            }
        }
        return null;
    }

}
