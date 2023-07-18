package com.example.shed;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import shed.GameType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class VictoryScreenController {

    @FXML
    private Button yesBtn;

    @FXML
    private Button moreBtn;

    @FXML
    private Button noBtn;

    @FXML
    private Label messageFrontLbl;

    @FXML
    private Label messageBackLbl;

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
        stage.setTitle("Shed: " + gameType.toString());
        try {
            FileInputStream stream = new FileInputStream("src\\images\\IconCards.png");
            stage.getIcons().add(new Image(stream));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
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

    public void setMessageLbl(String message) {
        this.messageFrontLbl.setText(message);
        this.messageBackLbl.setText(message);
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
}
