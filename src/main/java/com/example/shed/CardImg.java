package com.example.shed;

import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import shed.Card;

public class CardImg extends ImageView {

    private Card card;

    public CardImg(Card cardObj) {
       this.setImage(cardObj.getImage());
       this.card = cardObj;
       this.setFitWidth(100);
       this.setFitHeight(150);

       this.setOnMouseEntered(mouseEvent -> cardRaise());

       this.setOnMouseExited(mouseEvent -> cardLower());

       this.setOnMouseClicked(mouseEvent -> System.out.println(getCard()));

       this.setOnDragDetected(mouseEvent -> cardOnDragged());

       this.setOnMouseDragged(mouseEvent -> mouseEvent.setDragDetect(true));

    }

    private void cardRaise() {
        this.setY(-10);
    }

    private void cardLower() {
        this.setY(10);
    }

    private void cardOnDragged() {
        System.out.println("Card Selected");

        Dragboard db = this.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putString(this.card.toString());
        content.putImage(this.getCard().getSnapShot());
        db.setContent(content);
    }

    public Card getCard() {
        return this.card;
    }

}
