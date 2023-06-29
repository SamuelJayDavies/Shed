package com.example.learningjavafx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Button higherOrLowerBtn;

    @FXML
    protected void onHigherOrLowerBtnClick() {
        new Alert(Alert.AlertType.INFORMATION, "Higher or Lower Selected!").show();
    }

}