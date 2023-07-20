package com.example.shed;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import shed.GameType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ModeSelectController {

    @FXML
    private Button higherOrLowerBtn;

    @FXML
    private ImageView iconPicImg;

    @FXML
    private ImageView profilePicImg;

    @FXML
    private Label playerNameLbl;

    private String p1Name;

    private Image defaultProfilePic;

    private Stage stage;
    private Scene scene;

    @FXML
    public void initialize() {
        try{
            InputStream stream = new FileInputStream("src\\images\\IconCards.png");
            iconPicImg.setImage(new Image(stream));
            InputStream profileStream = new FileInputStream("src\\images\\profilePicture.png");
            defaultProfilePic = new Image(profileStream);
        }catch(IOException e) {
            System.out.println("File not found");
        }

        this.p1Name = "Player 1";
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
        shedController.setP1profilePic(profilePicImg.getImage());
        shedController.setP1Name(p1Name);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("shed.fxml"));
        fxmlLoader.setController(shedController);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Shed: " + gameType.toString());

        stage.setScene(scene);
        stage.show();
    }

    public void switchToHelpScreen(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("help-screen.fxml"));

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Shed: Help Screen");
        stage.setScene(scene);
        stage.show();
    }

    public void setProfilePicture() {
        FileChooser fileChooser = new FileChooser();
        File selectedPicture = fileChooser.showOpenDialog(stage);
        if(selectedPicture != null) {
            try{
                InputStream stream = new FileInputStream(selectedPicture);
                Image newProfilePic = new Image(stream);
                if(newProfilePic.getHeight() == 0) {
                    newProfilePic = defaultProfilePic;
                }
                profilePicImg.setImage(newProfilePic);
            }catch(IOException e) {
                // Change this to use ALERT
                System.out.println("Please select an image");
            }
        }
    }

    public void setName() {
        TextInputDialog textBox = new TextInputDialog();
        textBox.setHeaderText("Please enter your name");
        textBox.setContentText("Name");
        textBox.showAndWait();
        this.p1Name = textBox.getEditor().getText().strip();
        if(this.p1Name.isEmpty()) {
            this.p1Name = "Player 1";
        }
        this.playerNameLbl.setText(this.p1Name);
    }

}