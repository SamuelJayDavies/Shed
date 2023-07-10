package com.example.learningjavafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import shed.GameType;
import shed.Shed;

import java.io.IOException;

public class VictoryScreenController {

    @FXML
    private Button yesBtn;

    @FXML
    private Button moreBtn;

    @FXML
    private Button noBtn;

    private Stage stage;

    private Scene scene;

    private GameType gameType;

    public void switchToShed(ActionEvent event) throws IOException {
        ShedController shedController = new ShedController();
        shedController.setGameType(this.gameType);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("shed.fxml"));
        fxmlLoader.setController(shedController);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Shed: Basic");
        stage.setScene(scene);
        stage.show();
    }

    public void switchToModeSelect(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("mode-select.fxml"));

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Shed: Home");
        stage.setScene(scene);
        stage.show();
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
}
