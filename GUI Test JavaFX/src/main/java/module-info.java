module com.example.guitestjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.guitestjavafx to javafx.fxml;
    exports com.example.guitestjavafx;
}