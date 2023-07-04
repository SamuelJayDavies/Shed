package com.example.learningjavafx;

import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
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

    // FXML Variables
    @FXML
    private AnchorPane mainStage;

    @FXML
    private Pane generalHandPane;

    @FXML
    private Pane altHandPane;

    @FXML
    private TextArea gameLogTxt;

    @FXML
    private ImageView discardPileImg;

    @FXML
    private ImageView drawPileImg;

    // Java Variables

    private Deck drawPile;

    private Deck discardPile;

    private ArrayList<Player> players;

    private double startDragX;

    private double startDragY;

    @FXML
    private void initialize() {
        // Scene Related
        discardPileImg.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if(dragEvent.getGestureSource() != discardPileImg && dragEvent.getDragboard().hasString()) {
                    dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
            }
        });

        discardPileImg.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                Dragboard db = dragEvent.getDragboard();
                if(db.hasString()) {
                    System.out.println(dragEvent.getDragboard().getString());
                    dragEvent.setDropCompleted(true);
                } else {
                    dragEvent.setDropCompleted(false);
                }
                dragEvent.consume();
            }
        });

        // Game Related
        players = new ArrayList<>();
        this.players.add(new Player("Sam", false));
        this.players.add(new Player("John", true));
        populateDecks();
        startGame();
    }

    private void populateDecks() {
        drawPile = new Deck(DeckType.DRAW);
        discardPile = new Deck(DeckType.DISCARD);
    }

    private void startGame() {
        drawPile.shuffle();
        dealCards();
        setCurrentState();
    }

    private void dealCards() {
        for (int i = 0; i < 3; i++) {
            for (Player player : players) {
                receiveCard(player, drawPile.deal(), HandType.Hidden);
            }
        }
        for (int i = 0; i < 3; i++) {
            for (Player player : players) {
                receiveCard(player, drawPile.deal(), HandType.Constrained);
            }
        }
        for (int i = 0; i < 3; i++) {
            for (Player player : players) {
                receiveCard(player, drawPile.deal(), HandType.Regular);
            }
        }
    }

    private void receiveCard(Player player, Card card, HandType type) {
        player.addToHand(card, type);
    }

    private void setCurrentState() {
        Player p1 = players.get(0);
        p1.getGeneralHand().sortHand();
        generalHandPane.getChildren().addAll(setGeneralHand(p1.getGeneralHand().getCards()));

        altHandPane.getChildren().addAll(
                setAltHand(p1.getHiddenHand().getCards(), p1.getConstrainedHand().getCards()));
    }

    private ArrayList<ImageView> setGeneralHand(ArrayList<Card> cards) {
        ArrayList<ImageView> list = new ArrayList<>();
        int i = cards.size();
        for (int j = 0; j < i; j++) {
            CardImg cardImg = new CardImg(cards.get(j));

//            cardImg.setOnMousePressed(new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent mouseEvent) {
//                    startDragX = mouseEvent.getSceneX();
//                    startDragY = mouseEvent.getSceneY();
//                    mainStage.getChildren().add(cardImg);
//                    generalHandPane.getChildren().remove(cardImg);
//                    cardImg.setX(startDragX);
//                    cardImg.setY(startDragY);
//                }
//            });
//
//            cardImg.setOnMouseReleased(new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent mouseEvent) {
//                    generalHandPane.getChildren().add(cardImg);
//                    mainStage.getChildren().remove(cardImg);
//                    cardImg.setX(startDragX);
//                    cardImg.setY(startDragY);
//                }
//            });
//
//            cardImg.setOnMouseDragged(new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent mouseEvent) {
//                    cardImg.setTranslateX(mouseEvent.getSceneX() - startDragX);
//                    cardImg.setTranslateY(mouseEvent.getSceneY() - startDragY);
//
//                }
//            });


            int cardOffset = 20;
            double middleOfWindow = generalHandPane.getPrefWidth() / 2;
            int totalCardOffset = (i - 1) * cardOffset;
            cardImg.setX(middleOfWindow - (((cardImg.getFitWidth()) + totalCardOffset) / 2) + (j * cardOffset));
            cardImg.setY(10);
            list.add(cardImg);

        }
        return list;
    }

    private ArrayList<ImageView> setAltHand(ArrayList<Card> hiddenCards, ArrayList<Card> constrainedCards) {
        ArrayList<ImageView> list = new ArrayList<>();

        int i = hiddenCards.size();
        for (int j = 0; j < i; j++) {
            CardImg cardImg = new CardImg(constrainedCards.get(j));
            cardImg.setImage(cardImg.getCard().getCardBack());
            int cardOffset = 110;
            double middleOfWindow = generalHandPane.getPrefWidth() / 2;
            int totalCardOffset = (i - 1) * cardOffset;
            cardImg.setX(middleOfWindow - (((cardImg.getFitWidth()) + totalCardOffset) / 2) + (j * cardOffset) + (10 + j*10));
            cardImg.setY(10);
            list.add(cardImg);

        }

        int j = constrainedCards.size();
        for (int k = 0; k < j; k++) {
            CardImg cardImg = new CardImg(constrainedCards.get(k));
            int cardOffset = 120;
            double middleOfWindow = generalHandPane.getPrefWidth() / 2;
            int totalCardOffset = (j - 1) * cardOffset;
            cardImg.setX(middleOfWindow - (((cardImg.getFitWidth()) + totalCardOffset) / 2) + (k * cardOffset));
            cardImg.setY(10);
            list.add(cardImg);

        }
        return list;
    }
}
