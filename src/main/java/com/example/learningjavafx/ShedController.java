package com.example.learningjavafx;

import javafx.scene.control.TextArea;
import shed.*;
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

    @FXML
    private TextArea gameLogTxt;

    private Deck drawPile;

    private Deck discardPile;

    @FXML
    private void initialize() {
        populateDecks();
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            cards.add(drawPile.deal());
        }
        generalHandPane.getChildren().addAll(getCards(cards));
    }

    private void populateDecks() {
        drawPile = new Deck(DeckType.DRAW);
        discardPile = new Deck(DeckType.DISCARD);
    }

    private ArrayList<ImageView> getCards(ArrayList<Card> cards) {
        ArrayList<ImageView> list = new ArrayList<>();
        int i = 3;
        for (int j = 0; j < i; j++) {
            CardImg cardImg = new CardImg(cards.get(j));
            int cardOffset = 20;
            double middleOfWindow = generalHandPane.getPrefWidth() / 2;
            int totalCardOffset = (i - 1) * cardOffset;
            cardImg.setX(middleOfWindow - (((cardImg.getFitWidth()) + totalCardOffset) / 2) + (j * cardOffset));
            cardImg.setY(10);
            list.add(cardImg);

        }
        return list;
    }
}
