package com.example.learningjavafx;

import higherOrLower.Card;
import higherOrLower.HigherOrLowerGame;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

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
    private Button modeSelectBtn;

    private HigherOrLowerGame game;

    private Card lastCard;

    @FXML
    private void initialize() {
        HigherOrLowerGame game = new HigherOrLowerGame();
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
        result(game.isCorrect(true, lastCard));
    }

    public void lowerBtnClicked(ActionEvent event) {
        result(game.isCorrect(false, lastCard));
    }

    private void result(boolean isCorrect) {
        Timer T = new Timer();
        TimerTask delay = new TimerTask() {
            int i = 2;
            @Override
            public void run() {
                if(i>0) {
                    i--;
                } else {
                    T.cancel();
                }
            }
        };

        if(isCorrect) {
            game.incrementScore();
            lastCardMsg.setText("Correct, well done!");
        } else {
            lastCardMsg.setText("Incorrect choice, unlucky!");
        }



        T.schedule(delay, 0, 1000);
        lastCard = game.dealFromDeck();
        lastCardMsg.setText(game.getCurrentCardMsg(lastCard));

    }

}
