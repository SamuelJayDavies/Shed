package com.example.shed;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import shed.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    private Label drawDeckLbl;

    @FXML
    private Pane cpuGeneralHandPane;

    @FXML
    private Pane cpuAltHandPane;

    @FXML
    private ImageView p1profileImg;

    @FXML
    private ImageView p2profileImg;

    @FXML
    private Button continueBtn;

    @FXML
    Label gameTypeFrontLbl;

    @FXML
    Label gameTypeBackLbl;

    // Java Variables

    private Deck drawPile;

    private Deck discardPile;

    private ArrayList<Player> players;

    private Hand currentHand;

    public GameType gameType;

    private int roundNum;

    private ArrayList<Card> selectedCards;

    private double startDragX;

    private double startDragY;

    private ArrayList<Image> stackImgs;

    private ArrayList<Image> stackSnapShotImgs;

    public ShedController() {

    }

    @FXML
    private void initialize() {

        // Game Related
        players = new ArrayList<>();
        this.players.add(new Player("Sam", false));
        this.players.add(new Player("John", true));
        this.selectedCards = new ArrayList<>();
        this.stackImgs = new ArrayList<>();
        this.stackSnapShotImgs = new ArrayList<>();
        roundNum = 1;

        String gameTypeStr = gameType.toString();

        if(gameTypeStr.contains("Fast")) {
            if(gameTypeStr.contains("Basic")) {
                gameType = GameType.BasicFast;
            } else {
                gameType = GameType.RegularFast;
            }
            drawDeckLbl.setText("Cards: " + 0 + " / " + Deck.DECK_SIZE);
        } else if(gameTypeStr.contains("Basic")){
            gameType = GameType.Basic;
        } else {
            gameType = GameType.Regular;
        }

        setGameTypeLbl(this.gameType);

        gameLogTxt.setText(gameLogTxt.getText() + "\n" + "------------------------- Round " + roundNum + " -------------------------\n\n");
        roundNum++;

        populateDecks();
        startGame();

        try {
            FileInputStream stack2Stream = new FileInputStream("src\\main\\java\\higherOrLower\\cards\\stack2.png");
            stackImgs.add(new Image(stack2Stream));
            FileInputStream stack3Stream = new FileInputStream("src\\main\\java\\higherOrLower\\cards\\stack3.png");
            stackImgs.add(new Image(stack3Stream));
            FileInputStream stack4Stream = new FileInputStream("src\\main\\java\\higherOrLower\\cards\\stack4.png");
            stackImgs.add(new Image(stack4Stream));
            FileInputStream stackSnap2Stream = new FileInputStream("src\\main\\java\\higherOrLower\\cards\\stack2.png");
            stackSnapShotImgs.add(new Image(stackSnap2Stream, 100, 150, false, true));
            FileInputStream stackSnap3Stream = new FileInputStream("src\\main\\java\\higherOrLower\\cards\\stack3.png");
            stackSnapShotImgs.add(new Image(stackSnap3Stream, 100, 150, false, true));
            FileInputStream stackSnap4Stream = new FileInputStream("src\\main\\java\\higherOrLower\\cards\\stack4.png");
            stackSnapShotImgs.add(new Image(stackSnap4Stream, 100, 150, false, true));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

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
                    playTurn(dragEvent.getDragboard().getString());
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
                cpuTurn();
                if(hasWon(players.get(1))) {
                    switchToVictoryScreen();
                }
                if((gameType.equals(GameType.Regular) || gameType.equals(GameType.Basic)) && (!drawPile.isEmpty())) {
                    preGameDraw();
                }
                setCurrentState();
            }
        });
    }

    private void playTurn(String cardString) {
        Player p1 = players.get(0);
        currentHand = getCurrentHand(p1);
        Card cardToPlay = currentHand.getCardByString(cardString);
        System.out.println(cardToPlay);
        if(isCardPlayable(cardToPlay)) {
            playCard(cardToPlay, currentHand, p1);
            if(gameType.equals(GameType.Regular) || gameType.equals(GameType.RegularFast)) {
                if(selectedCards.size() >= 2) {
                    selectedCards.remove(0);
                    for(int i=0; i<selectedCards.size(); i++) {
                        playCard(selectedCards.get(i), currentHand, p1);
                    }
                    selectedCards.clear();
                }
            }

            if(hasWon(p1)) {
                switchToVictoryScreen();
            } else if(cardToPlay.getValue() == 10 && (gameType.equals(GameType.Regular) || gameType.equals(GameType.RegularFast))) {
                gameLogTxt.setText(gameLogTxt.getText() + p1.getName() + " plays another card\n");
            }  else if (isLastCardsEqual()) {
                clearDeck();
                gameLogTxt.setText(gameLogTxt.getText() + "4 cards have been played, " + p1.getName() + " plays another card\n");
            } else {
                cpuTurn();
                if(hasWon(players.get(1))) {
                    switchToVictoryScreen();
                }
            }
        } else {
            if(currentHand.getHandType().equals(HandType.Hidden)) {
                playCard(cardToPlay, currentHand, players.get(0));
                pickUpDiscardPile(players.get(0));
                selectedCards.clear();
                currentHand = getCurrentHand(p1);
                cpuTurn();
                if(hasWon(players.get(1))) {
                    switchToVictoryScreen();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Card");
                alert.setHeaderText("The card you played was invalid");
                alert.setContentText("Please try another card");
                alert.showAndWait();
            }
        }
        if((gameType.equals(GameType.Regular) || gameType.equals(GameType.Basic)) && (!drawPile.isEmpty())) {
            preGameDraw();
        }
        setCurrentState();
    }

    private void preGameDraw() {
        for (Player player : players) {
            if (player.getGeneralHand().getNumOfCards() < 3) {
                ArrayList<Card> cards = new ArrayList<>();
                for(int i=3; i>player.getGeneralHand().getNumOfCards(); i--) {
                    if(!(drawPile.isEmpty())) {
                        cards.add(drawPile.deal());
                    }
                }
                receiveCards(player, cards);
            }
        }
    }

    private boolean isLastCardsEqual() {
        ArrayList<Card> cards = discardPile.getCards();
        int cardsSize = cards.size();
        if(cardsSize >= 4) {
            if(cards.get(cardsSize-1).getValue() == cards.get(cardsSize-2).getValue()
                    && (cards.get(cardsSize-2).getValue() == cards.get(cardsSize-3).getValue())
                    && (cards.get(cardsSize-3).getValue() == cards.get(cardsSize-4).getValue())) {

                return true;

            }
        }
        return false;
    }

    public void switchToVictoryScreen() {
        boolean gameOver = false;
        boolean playerWon = false;
        for(Player player: players) {
            if(hasWon(player)) {
                gameOver = true;
                if(player.equals(players.get(0))) {
                    playerWon = true;
                }
            }
        }

        if(gameOver) {
            VictoryScreenController victoryController = new VictoryScreenController();
            victoryController.setGameType(this.gameType);
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("victory-screen.fxml"));
            fxmlLoader.setController(victoryController);

            Stage stage = new Stage();
            try {
                stage.setScene(new Scene(fxmlLoader.load()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                FileInputStream stream = new FileInputStream("src\\images\\IconCards.png");
                stage.getIcons().add(new Image(stream));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            stage.setTitle("Shed: Endgame");
            stage.show();
            victoryController.setMessageLbl(playerWon ? "Congrats on the win. Do you want to play again?" : "Unlucky on the loss. Do you want to play again?");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Game is not over");
            alert.setHeaderText("The game has not finished");
            alert.setContentText("Click this button when the game has finished");
            alert.showAndWait();
        }
    }

    private boolean hasWon(Player player) {
        return player.getHiddenHand().getNumOfCards() +
                player.getConstrainedHand().getNumOfCards() +
                player.getGeneralHand().getNumOfCards() == 0;
    }

    private void pickUpDiscardPile(Player player) {
        gameLogTxt.setText(gameLogTxt.getText() + player.getName() + " picks up the discard pile\n");
        player.addToGeneral(discardPile.getCards());
        discardPile.empty();
    }

    private boolean cpuPlaysCard(Player player) {
        Hand cpuCurrentHand = getCurrentHand(player);
        Card cpuCardToPlay = cpuCardChoice(cpuCurrentHand);
        if(cpuCardToPlay != null) {
            if(cpuCurrentHand.getHandType().equals(HandType.Hidden)) {
                if(isCardPlayable(cpuCardToPlay)) {
                    playCard(cpuCardToPlay, cpuCurrentHand, player);
                } else {
                    pickUpDiscardPile(player);
                }
            } else {
                playCard(cpuCardToPlay, cpuCurrentHand, player);
            }
        } else {
            pickUpDiscardPile(player);
        }

        if(this.gameType.equals(GameType.Regular) || this.gameType.equals(GameType.RegularFast)) {
            if(cpuCardToPlay != null && cpuCurrentHand.getHandType() != HandType.Hidden && canMultipleBePlayed(cpuCurrentHand, cpuCardToPlay)) {
                for (int i = cpuCurrentHand.getNumOfCards() - 1; i >= 0; i--) {
                    Card currentCard = cpuCurrentHand.getCard(i);
                    if (currentCard.getValue() == cpuCardToPlay.getValue()) {
                        playCard(currentCard, cpuCurrentHand, player);
                    }
                }
            }

            if (cpuCardToPlay != null && cpuCardToPlay.getValue() == 10
                    && (gameType.equals(GameType.Regular) || gameType.equals(GameType.RegularFast))) {
                return false;
            } else if (isLastCardsEqual()) {
                return false;

            }
        }
        gameLogTxt.setText(gameLogTxt.getText() + "\n" + "------------------------- Round " + roundNum + " -------------------------\n\n");
        roundNum++;
        return true;
    }

    private boolean canMultipleBePlayed(Hand currentHand, Card previousCard) {
        boolean multiple = false;
        for(Card card: currentHand.getCards()) {
            if(card.getValue() == previousCard.getValue()) {
                multiple = true;
            }
        }
        return multiple;
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

    private void receiveCards(Player player, ArrayList<Card> cards) {
        player.addToGeneral(cards);
    }

    private void setCurrentState() {
        Player p1 = players.get(0);
        p1.getGeneralHand().sortHand();                                                         // Replace with isCPU from Player
        generalHandPane.getChildren().addAll(setGeneralHandImg(p1.getGeneralHand().getCards(), true));

        altHandPane.getChildren().addAll(
                setAltHandImg(p1.getHiddenHand().getCards(), p1.getConstrainedHand().getCards(), true));

        Player p2 = players.get(1);
        p2.getGeneralHand().sortHand();

        cpuGeneralHandPane.getChildren().addAll(setGeneralHandImg(p2.getGeneralHand().getCards(), false));
        cpuAltHandPane.getChildren().addAll(
                setAltHandImg(p2.getHiddenHand().getCards(), p2.getConstrainedHand().getCards(), false));

        if(gameType.equals(GameType.Regular) || gameType.equals(GameType.Basic)) {
            drawDeckLbl.setText("Cards: " + drawPile.getDeckSize() + " / " + Deck.DECK_SIZE);
        }

        if(!(discardPile.isEmpty())) {
            discardPileImg.setImage(discardPile.peekTop().getImage());
        } else {
            discardPileImg.setImage(null);
        }

        gameLogTxt.selectPositionCaret(gameLogTxt.getLength());
        gameLogTxt.deselect();
    }

    private void setDiscardPileImg(ActionEvent event) {
        if(!(discardPile.isEmpty())) {
            discardPileImg.setImage(discardPile.peekTop().getImage());
        } else {
            discardPileImg.setImage(null);
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
            clearDeck();
        }
    }

    private void clearDeck() {
        discardPile.empty();
        gameLogTxt.setText(gameLogTxt.getText() + "Discard deck has been cleared\n");
        discardPileImg.setImage(null);
    }

    private void cpuTurn() {
        Player p2 = players.get(1);
        boolean cpuTurnOver = cpuPlaysCard(p2);
        while(!cpuTurnOver && !hasWon(p2)) {
            gameLogTxt.setText(gameLogTxt.getText() + p2.getName() + " plays another card\n");
            cpuTurnOver = cpuPlaysCard(p2);
        }
    }

    private Card cpuCardChoice(Hand currentHand) {
        Card cardToPlay = null;
        Card comparisonCard = discardPile.peekTop();

        if(currentHand.getHandType().equals(HandType.Hidden)) {
            Random random = new Random();
            cardToPlay =  currentHand.getCard(random.nextInt(currentHand.getNumOfCards()));
        }

        if (comparisonCard == null) {
            return currentHand.getLowestRegularCard();
        } else {
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

    private ArrayList<ImageView> setGeneralHandImg(ArrayList<Card> cards, boolean isPlayer) {
        Pane workingPane;
        if(isPlayer) {
            workingPane = generalHandPane;
        } else {
            workingPane = cpuGeneralHandPane;
        }
        workingPane.getChildren().clear();
        ArrayList<ImageView> list = new ArrayList<>();
        int i = cards.size();
        for (int j = 0; j < i; j++) {
            CardImg cardImg = new CardImg(cards.get(j));

            if(!(isPlayer)) {
                cardImg.setImage(cardImg.getCard().getCardBack());
            }

            if(selectedCards.size() >= 2) {
                if(cardImg.getCard() == selectedCards.get(0)) {
                    cardImg.setImage(stackImgs.get(selectedCards.size()-2));
                    cardImg.getCard().setSnapShot(stackSnapShotImgs.get(selectedCards.size()-2));
                }
            }

            if(gameType.equals(GameType.RegularFast) || gameType.equals(GameType.Regular)) {
                cardImg.setOnDragOver(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent dragEvent) {
                        if(dragEvent.getGestureSource() != cardImg && dragEvent.getDragboard().hasString()) {
                            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        }
                    }
                });
                cardImg.setOnDragDropped(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent dragEvent) {
                        System.out.println("Test");
                        if(dragEvent.getGestureSource() != cardImg && dragEvent.getDragboard().hasString()) {
                            if(selectedCards.get(0).getValue() == cardImg.getCard().getValue()) {
                                selectedCards.add(cardImg.getCard());
                                currentHand.removeCard(cardImg.getCard());
                                setCurrentState();
                            }
                            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        }
                    }
                });

                cardImg.setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(selectedCards.size() <= 1) {
                            selectedCards.clear();
                            selectedCards.add(cardImg.getCard());
                        } else {
                            if(cardImg.getCard().getValue() != selectedCards.get(0).getValue()) {
                                selectedCards.remove(0);
                                players.get(0).addToGeneral(selectedCards);
                                selectedCards.clear();
                            }
                        }

                        System.out.println("Card Selected");

                        Dragboard db = cardImg.startDragAndDrop(TransferMode.ANY);

                        ClipboardContent content = new ClipboardContent();
                        content.putString(cardImg.getCard().toString());
                        content.putImage(cardImg.getCard().getSnapShot());
                        db.setContent(content);
                    }
                });
            }

            int cardOffset = 20;
            double middleOfWindow = workingPane.getPrefWidth() / 2;
            int totalCardOffset = (i - 1) * cardOffset;
            cardImg.setX(middleOfWindow - (((cardImg.getFitWidth()) + totalCardOffset) / 2) + (j * cardOffset));
            cardImg.setY(10);
            list.add(cardImg);

        }
        return list;
    }

    private ArrayList<ImageView> setAltHandImg(ArrayList<Card> hiddenCards, ArrayList<Card> constrainedCards, boolean isPlayer) {
        Pane workingPane;
        if(isPlayer) {
            workingPane = altHandPane;
        } else {
            workingPane = cpuAltHandPane;
        }
        workingPane.getChildren().clear();
        ArrayList<ImageView> list = new ArrayList<>();

        int i = hiddenCards.size();
        for (int j = 0; j < i; j++) {
            CardImg cardImg = new CardImg(hiddenCards.get(j));
            cardImg.setImage(cardImg.getCard().getCardBack());
            int cardOffset = 110;
            double middleOfWindow = workingPane.getPrefWidth() / 2;
            int totalCardOffset = (i - 1) * cardOffset;
            cardImg.setX(middleOfWindow - (((cardImg.getFitWidth()) + totalCardOffset) / 2) + (j * cardOffset) + (10 + j*10));
            cardImg.setY(10);
            list.add(cardImg);

            cardImg.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println("Card Selected");

                    Dragboard db = cardImg.startDragAndDrop(TransferMode.ANY);

                    ClipboardContent content = new ClipboardContent();
                    content.putString(cardImg.getCard().toString());
                    content.putImage(cardImg.getCard().getCardBackSnapShot());
                    db.setContent(content);
                }
            });
        }

        int j = constrainedCards.size();
        for (int k = 0; k < j; k++) {
            CardImg cardImg = new CardImg(constrainedCards.get(k));
            int cardOffset = 120;
            double middleOfWindow = workingPane.getPrefWidth() / 2;
            int totalCardOffset = (j - 1) * cardOffset;
            cardImg.setX(middleOfWindow - (((cardImg.getFitWidth()) + totalCardOffset) / 2) + (k * cardOffset));
            cardImg.setY(10);
            list.add(cardImg);

        }
        return list;
    }

    public void setGameTypeLbl(GameType gameType) {
        this.gameTypeBackLbl.setText(gameType.toString());
        this.gameTypeFrontLbl.setText(gameType.toString());
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

}
