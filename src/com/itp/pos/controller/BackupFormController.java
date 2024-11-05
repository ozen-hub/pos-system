package com.itp.pos.controller;

import com.itp.pos.db.Database;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;


public class BackupFormController {
    public AnchorPane context;
    public TextArea txtClipboard;

    public void initialize() {
        setClipboardData();
    }

    private void setClipboardData() {
        txtClipboard.setEditable(false);
        // create the header ==========
        txtClipboard.appendText("Date: " + new Date());
        txtClipboard.appendText("\n===========================");

        txtClipboard.appendText(
                "\n\n*====Customer Data====*\n"
        );
        for (int i = 0; i <
                Database.customerTable.size(); i++) {
            txtClipboard.appendText(
                    (i + 1) + ". " + Database.customerTable.get(i)
                            .toString() +
                            "\n"
            );
        }
        txtClipboard.appendText(
                "\n\n*====Product Data====*\n"
        );
        for (int i = 0; i <
                Database.productTable.size(); i++) {
            txtClipboard.appendText(
                    (i + 1) + ". " + Database.productTable.get(i)
                            .toString() +
                            "\n"
            );
        }

        if (!Database.orderTable.isEmpty()) {
            txtClipboard.appendText(
                    "\n\n*====Order Data====*\n"
            );
            for (int i = 0; i <
                    Database.orderTable.size(); i++) {
                txtClipboard.appendText(
                        (i + 1) + ". " + Database.orderTable.get(i)
                                .toString() +
                                "\n"
                );
            }
        }
        txtClipboard.appendText(
                "\n\n*====User Data====*\n"
        );
        for (int i = 0; i <
                Database.userTable.size(); i++) {
            txtClipboard.appendText(
                    (i + 1) + ". " + Database.userTable.get(i)
                            .toString() +
                            "\n"
            );
        }
        txtClipboard.appendText(
                "\n\n*=======End=======*\n"
        );

    }


    public void backToHomeOnAction(ActionEvent actionEvent) {
        try {
            setUi("DashboardForm");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startBackupOnAction(ActionEvent actionEvent) {

        FileChooser fileChooser
                = new FileChooser();
        fileChooser.setTitle("Generate Backup file");
        /*fileChooser.setInitialFileName("backupFile_"+
                new Date()+"_.txt");*/
        fileChooser.setInitialFileName("backup.txt");
        fileChooser.getExtensionFilters()
         .add(new FileChooser.
          ExtensionFilter("Text File",
                 "*.txt"));
        Stage stage = (Stage) context.getScene().getWindow();
        File file =
                fileChooser.
                        showOpenDialog(stage);
        if(file!=null){
            try(
                    BufferedWriter bufferedWriter
                            = new BufferedWriter(
                                    new FileWriter(file)
                    )
                    ){
                bufferedWriter.write(txtClipboard.getText());
                new Alert(Alert.AlertType.INFORMATION,"Success!...").show();
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR,"Something went wrong!... = >"+e.getMessage()).show();
                throw new RuntimeException(e);
            }
        }

    }

    private void setUi(String location) throws IOException {
        Stage stage = (Stage)
                context.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/itp/pos/view/"+location+".fxml"));

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), stage.getScene().getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> {

            stage.setScene(new Scene(root));


            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), root);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });


        fadeOut.play();

    }

}
