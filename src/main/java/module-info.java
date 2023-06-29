module com.example.learningjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.learningjavafx to javafx.fxml;
    exports com.example.learningjavafx;
    exports higherOrLower;
    opens higherOrLower to javafx.fxml;
}