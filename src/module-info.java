module pos {
    requires java.xml.crypto;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    exports com.itp.pos;
    exports com.itp.pos.controller;
    opens com.itp.pos.controller to javafx.fxml;
    opens com.itp.pos.view to javafx.fxml;
    opens com.itp.pos.view.tm to javafx.base, javafx.fxml;
    opens com.itp.pos.util to javafx.fxml;
    opens com.itp.pos.db to javafx.fxml;
    opens com.itp.pos.model to javafx.fxml;
    opens com.itp.pos to javafx.fxml;
    requires jBCrypt;
}