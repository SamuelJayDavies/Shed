package com.example.learningjavafx;

import javafx.event.EventHandler;
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
    }

    private void cardRaise() {
        this.setY(-10);
    }

    public void cardLower() {
        this.setY(10);
    }

    public void setOnMouseClicked(MouseEvent event) {
        System.out.println(getCard());
    }
    public Card getCard() {
        return this.card;
    }

}
