package com.example.learningjavafx;

import higherOrLower.Card;
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
    private Pane testHandPane;

    @FXML
    private void initialize() {
        testHandPane.getChildren().addAll(getCards());
    }

    private ArrayList<ImageView> getCards() {
        ArrayList<ImageView> list = new ArrayList<>();
        for(int i=0; i<3; i++) {
            try {
                FileInputStream stream = new FileInputStream("src\\main\\java\\higherOrLower\\cards\\back_of_card.png");
                ImageView view = new ImageView(new Image(stream));
                view.setFitWidth(100);
                view.setFitHeight(150);
                view.setX(i*20);
                view.setY(10);
                view.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        view.setY(-10);
                    }
                });

                view.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        view.setY(10);
                    }
                });

                list.add(view);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }
}
