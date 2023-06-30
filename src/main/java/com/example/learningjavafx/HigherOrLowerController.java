package com.example.learningjavafx;

import higherOrLower.Card;
import higherOrLower.HigherOrLowerGame;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class HigherOrLowerController {

    @FXML
    private Label lastCardMsg;

    @FXML
    private Button higherBtn;

    @FXML
    private Button lowerBtn;

    @FXML
    private Label totalLbl;

    @FXML
    private Button modeSelectBtn;

    @FXML
    private ImageView unknownCardImg;

    @FXML
    private ImageView lastCardImg;

    private HigherOrLowerGame game;

    private Card lastCard;

    @FXML
    private void initialize() throws FileNotFoundException {
        game = new HigherOrLowerGame();
        lastCard = game.dealFromDeck();
        lastCardImg.setImage(lastCard.getImage());

        try {
            FileInputStream stream = new FileInputStream("src\\main\\java\\higherOrLower\\cards\\back_of_card.png");
            unknownCardImg.setImage(new Image(stream));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        lastCardMsg.setText(game.getCurrentCardMsg(lastCard));
    }

    private Stage stage;
    private Scene scene;

    public void switchToModeSelect(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("mode-select.fxml"));

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void higherBtnClicked(ActionEvent event) {
        Card newCard = game.dealFromDeck();
        unknownCardImg.setImage(newCard.getImage());
        result(game.isCorrect(true, lastCard, newCard), newCard);
    }

    public void lowerBtnClicked(ActionEvent event) {
        Card newCard = game.dealFromDeck();
        unknownCardImg.setImage(newCard.getImage());
        result(game.isCorrect(false, lastCard, newCard), newCard);
    }

    private void result(boolean isCorrect, Card newCard) {
        if(isCorrect) {
            game.incrementScore();
            lastCardMsg.setText("Correct, well done!");
        } else {
            lastCardMsg.setText("Incorrect choice, unlucky!");
        }

        lastCard = newCard;
        totalLbl.setText("Total: " + game.getCurrentScore() + "/" + game.getCardsPlayed());

        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(this::resetScreen);
        delay.play();

    }

    public void resetScreen(ActionEvent event) {
        lastCardMsg.setText(game.getCurrentCardMsg(lastCard));
        lastCardImg.setImage(lastCard.getImage());
        try {
            FileInputStream stream = new FileInputStream("src\\main\\java\\higherOrLower\\cards\\back_of_card.png");
            unknownCardImg.setImage(new Image(stream));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
