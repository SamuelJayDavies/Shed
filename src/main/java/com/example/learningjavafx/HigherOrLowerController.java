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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class HigherOrLowerController {


    @FXML
    private Label unknownCard;

    @FXML
    private Label lastCardLbl;

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

    private HigherOrLowerGame game;

    private Card lastCard;

    @FXML
    private void initialize() {
        game = new HigherOrLowerGame();
        Card firstCard = game.dealFromDeck();
        lastCard = firstCard;
        lastCardLbl.setText(firstCard.toString());
        lastCardMsg.setText(game.getCurrentCardMsg(firstCard));
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
        unknownCard.setText(newCard.toString());
        result(game.isCorrect(true, lastCard, newCard), newCard);
    }

    public void lowerBtnClicked(ActionEvent event) {
        Card newCard = game.dealFromDeck();
        unknownCard.setText(newCard.toString());
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
        lastCardLbl.setText(lastCard.toString());
        unknownCard.setText("Unknown");
    }

}
