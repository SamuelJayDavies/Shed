package com.example.learningjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ModeSelectController {

    @FXML
    private Button higherOrLowerBtn;

    private Stage stage;
    private Scene scene;

    public void switchToHigherOrLower(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("higher-or-lower.fxml"));

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Shed: Higher or Lower");
        stage.setScene(scene);
        stage.show();
    }

}