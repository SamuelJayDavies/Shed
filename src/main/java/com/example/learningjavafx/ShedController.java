package com.example.learningjavafx;

import higherOrLower.Card;
import higherOrLower.Deck;
import higherOrLower.DeckType;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ShedController {

    @FXML
    private Pane generalHandPane;

    private Deck deck;

    @FXML
    private void initialize() {
        this.deck = new Deck(DeckType.DRAW);
        this.deck.shuffle();
        ArrayList<Card> cards = new ArrayList<>();
        for(int i=0; i<14; i++) {
            cards.add(deck.deal());
        }
        generalHandPane.getChildren().addAll(getCards(cards));
    }

    private ArrayList<ImageView> getCards(ArrayList<Card> cards) {
        ArrayList<ImageView> list = new ArrayList<>();
        int i = 14;
        for(int j=0; j<i; j++) {
            ImageView cardImg = new ImageView(cards.get(j).getImage());
            cardImg.setFitWidth(100);
            cardImg.setFitHeight(150);
            int cardOffset = 18;
            double middleOfWindow = generalHandPane.getPrefWidth()/2;
            int totalCardOffset = (i-1) * cardOffset;
            cardImg.setX(middleOfWindow - (((cardImg.getFitWidth()) + totalCardOffset) /2) + (j*cardOffset));
            cardImg.setY(10);
            cardImg.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        cardImg.setY(-10);
                    }
                });

            cardImg.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        cardImg.setY(10);
                    }
                });

                list.add(cardImg);

                //                view.setX(windowWidth/(3*i) + j*20);
            //                view.setFitWidth(100);
            //                view.setFitHeight(150);
            //                view.setY(10);
        }
        return list;
    }
}
