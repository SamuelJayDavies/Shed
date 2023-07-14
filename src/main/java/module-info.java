module com.example.learningjavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.shed to javafx.fxml;
    exports com.example.shed;
    exports higherOrLower;
    opens higherOrLower to javafx.fxml;
}