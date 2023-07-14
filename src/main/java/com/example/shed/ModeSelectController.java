package com.example.shed;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import shed.GameType;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ModeSelectController {

    @FXML
    private Button higherOrLowerBtn;

    @FXML
    private ImageView iconPicImg;

    private Stage stage;
    private Scene scene;

    @FXML
    public void initialize() {
        try{
            InputStream stream = new FileInputStream("src\\images\\IconCards.png");
            iconPicImg.setImage(new Image(stream));
        }catch(IOException e) {
            System.out.println("File not found");
        }
    }

    public void switchToHigherOrLower(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("higher-or-lower.fxml"));

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Shed: Higher or Lower");
        stage.setScene(scene);
        stage.show();
    }

    public void switchToBasicShed(ActionEvent event) throws IOException {
        switchToShed(event, GameType.Basic);
    }

    public void switchToBasicFastShed(ActionEvent event) throws IOException {
        switchToShed(event, GameType.BasicFast);
    }

    public void switchToRegularShed(ActionEvent event) throws IOException {
        switchToShed(event, GameType.Regular);
    }

    public void switchToRegularFastShed(ActionEvent event) throws IOException {
        switchToShed(event, GameType.RegularFast);
    }

    private void switchToShed(ActionEvent event, GameType gameType) throws IOException {
        ShedController shedController = new ShedController();
        shedController.setGameType(gameType);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("shed.fxml"));
        fxmlLoader.setController(shedController);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Shed: " + gameType.toString());

        stage.setScene(scene);
        stage.show();
    }

}