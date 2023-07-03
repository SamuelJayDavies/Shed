package com.example.learningjavafx;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import shed.Card;
import javafx.scene.input.MouseEvent;

public class CardImg extends ImageView {

    private Card card;

    public CardImg(Card cardObj) {
       this.setImage(cardObj.getImage());
       this.card = cardObj;
       this.setFitWidth(100);
       this.setFitHeight(150);

       this.setOnMouseEntered(mouseEvent -> cardRaise());

       this.setOnMouseExited(mouseEvent -> cardLower());


       this.setOnMouseClicked(mouseEvent -> System.out.println(getCardObj()));
    }

    private void cardRaise() {
        this.setY(-10);
    }

    public void cardLower() {
        this.setY(10);
    }

    public void setOnMouseClicked(MouseEvent event) {
        System.out.println(getCardObj());
    }


    public Card getCardObj() {
        return this.card;
    }

}
