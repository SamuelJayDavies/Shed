package com.example.learningjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class HelloApplication extends Application {
    @Override // Needs to be overridden as Application has this as an abstract method
    public void start(Stage stage) throws IOException {

        // Here fxmlLoader is a root node, each scene needs a root note to be passed in to act as a layout manager
        // of sorts. Won't delve into this too much here.
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load()); // One hierarchy lower than a stage

        scene.getStylesheets().add(getClass().getResource("homeStyle.css").toExternalForm());

        stage.setTitle("Shed: Login");
//        stage.setWidth(600);
//        stage.setHeight(600);
        stage.setResizable(true);

        /*
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("YOU CAN'T LEAVE!");
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("q"));
        */

        stage.setScene(scene); // Add scene to the stage
        stage.show(); // Shows the stage
    }

    public static void main(String[] args) {
        launch(args);
    }
}