package com.example.learningjavafx;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import shed.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

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

    @FXML
    private ImageView p1profileImg;

    @FXML
    private ImageView p2profileImg;

    // Java Variables

    private Deck drawPile;

    private Deck discardPile;

    private ArrayList<Player> players;

    private Hand currentHand;

    private double startDragX;

    private double startDragY;

    @FXML
    private void initialize() {

        // Game Related
        players = new ArrayList<>();
        this.players.add(new Player("Sam", false));
        this.players.add(new Player("John", true));
        populateDecks();
        startGame();

        // Scene Related
        try {
            FileInputStream profileStream = new FileInputStream("src\\images\\profilePicture.png");
            Image profiePic = new Image(profileStream);
            p1profileImg.setImage(profiePic);
            p2profileImg.setImage(profiePic);

            FileInputStream drawDeckStream = new FileInputStream("src\\main\\java\\higherOrLower\\cards\\back_of_card.png");
            Image drawDeckPic = new Image(drawDeckStream);
            drawPileImg.setImage(drawDeckPic);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

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
                    Card cardToPlay = currentHand.getCardByString(dragEvent.getDragboard().getString());
                    System.out.println(cardToPlay);
                    if(isCardPlayable(cardToPlay)) {
                        playCard(cardToPlay, currentHand, players.get(0));
                        Hand cpuCurrentHand = getCurrentHand(players.get(1));
                        Card cpuCardToPlay = cpuCardChoice(cpuCurrentHand);
                        if(cpuCardToPlay != null) {
                            playCard(cpuCardToPlay, cpuCurrentHand, players.get(1));
                        } else {
                            // CPU picks up cards
                        }
                        setCurrentState();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Invalid Card");
                        alert.setHeaderText("The card you played was invalid");
                        alert.setContentText("Please try another card");
                        alert.showAndWait();
                    }
                    dragEvent.setDropCompleted(true);
                } else {
                    dragEvent.setDropCompleted(false);
                }
                dragEvent.consume();
            }
        });

        discardPileImg.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                pickUpDiscardPile(players.get(0));
                // CPU plays card
                setCurrentState();
            }
        });
    }

    private void pickUpDiscardPile(Player player) {
        gameLogTxt.setText(gameLogTxt.getText() + player.getName() + " picks up the discard pile");
        player.addToGeneral(discardPile.getCards());
        discardPile.empty();
    }

    private Hand getCurrentHand(Player player) {
        if (player.getGeneralHand().getNumOfCards() != 0) {
            return player.getGeneralHand();
        } else if (player.getConstrainedHand().getNumOfCards() != 0) {
            return player.getConstrainedHand();
        } else {
            return player.getHiddenHand();
        }
    }

    private void populateDecks() {
        drawPile = new Deck(DeckType.DRAW);
        discardPile = new Deck(DeckType.DISCARD);
    }

    private void startGame() {
        drawPile.shuffle();
        dealCards();
        setCurrentState();
        this.currentHand = getCurrentHand(players.get(0));
        this.currentHand.sortHand();
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
        generalHandPane.getChildren().addAll(setGeneralHandImg(p1.getGeneralHand().getCards()));

        altHandPane.getChildren().addAll(
                setAltHandImg(p1.getHiddenHand().getCards(), p1.getConstrainedHand().getCards()));

        if(!(discardPile.isEmpty())) {
            discardPileImg.setImage(discardPile.peekTop().getImage());
        } else {
            discardPileImg.setAccessibleText("Empty");
        }
    }

    private boolean isCardPlayable(Card cardToPlay) {
        if (cardToPlay != null) {
            if (discardPile.isEmpty() || cardToPlay.getValue() == 2 || cardToPlay.getValue() == 10) {
                return true;
            } else {
                if (cardToPlay.getValue() >= discardPile.peekTop().getValue()) {

                    if(discardPile.peekTop().getValue() != 7) {
                        return true;
                    } else {
                        return cardToPlay.getValue() == 7;
                    }

                } else if(discardPile.peekTop().getValue() == 7) {
                    return true;
                }
            }
        }
        return false;
    }

    private void playCard(Card cardToPlay, Hand currentHand, Player player) {

        discardPile.addCard(cardToPlay);
        currentHand.removeCard(cardToPlay);

        gameLogTxt.setText(gameLogTxt.getText() + player.getName() + " has played " + cardToPlay + "\n");

        if (cardToPlay.getValue() == 10) {
            discardPile.empty();
            System.out.println("Discard deck has been cleared\n");
            discardPileImg.setImage(null);
            // Add ability to play another card here
        }
    }

    private Card cpuCardChoice(Hand currentHand) {
        if(currentHand.getHandType().equals(HandType.Hidden)) {
            Random random = new Random();
            return currentHand.getCard(random.nextInt(currentHand.getNumOfCards()));
        }

        Card comparisonCard = discardPile.peekTop();

        if (comparisonCard == null) {
            return currentHand.getLowestRegularCard();
        } else {
            Card cardToPlay = null;
            int cardDiff = -1;
            if (comparisonCard.getValue() != 7) {
                for (Card card : currentHand.getCards()) {
                    // Not happy with the way this method is written.
                    // Should have a special card still assigned as a cardToPlay, but just overridden when a better basic card is
                    // found. Currently, it will ignore special cards and just find them at the end again.
                    if (card.getValue() >= comparisonCard.getValue() && (card.getValue() != 2 || card.getValue() != 10)) {

                        if (cardToPlay == null) {
                            cardToPlay = card;
                            cardDiff = cardToPlay.getValue() - comparisonCard.getValue();

                        } else if (card.getValue() - comparisonCard.getValue() < cardDiff) {
                            cardToPlay = card;
                            cardDiff = cardToPlay.getValue() - comparisonCard.getValue();

                        }
                    }
                }

            } else {
                cardToPlay = currentHand.getLowestRegularCard();
                if (!(cardToPlay.getValue() <= 7)) {
                    cardToPlay = null;
                }
            }

            return (cardToPlay == null) ? currentHand.getLowestSpecialCard() : cardToPlay;
        }

    }

    private ArrayList<ImageView> setGeneralHandImg(ArrayList<Card> cards) {
        generalHandPane.getChildren().clear();
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

    private ArrayList<ImageView> setAltHandImg(ArrayList<Card> hiddenCards, ArrayList<Card> constrainedCards) {
        altHandPane.getChildren().clear();
        ArrayList<ImageView> list = new ArrayList<>();

        int i = hiddenCards.size();
        for (int j = 0; j < i; j++) {
            CardImg cardImg = new CardImg(constrainedCards.get(j));
            cardImg.setImage(cardImg.getCard().getCardBack());
            int cardOffset = 110;
            double middleOfWindow = altHandPane.getPrefWidth() / 2;
            int totalCardOffset = (i - 1) * cardOffset;
            cardImg.setX(middleOfWindow - (((cardImg.getFitWidth()) + totalCardOffset) / 2) + (j * cardOffset) + (10 + j*10));
            cardImg.setY(10);
            list.add(cardImg);

        }

        int j = constrainedCards.size();
        for (int k = 0; k < j; k++) {
            CardImg cardImg = new CardImg(constrainedCards.get(k));
            int cardOffset = 120;
            double middleOfWindow = altHandPane.getPrefWidth() / 2;
            int totalCardOffset = (j - 1) * cardOffset;
            cardImg.setX(middleOfWindow - (((cardImg.getFitWidth()) + totalCardOffset) / 2) + (k * cardOffset));
            cardImg.setY(10);
            list.add(cardImg);

        }
        return list;
    }
}
