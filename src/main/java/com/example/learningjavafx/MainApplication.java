package com.example.learningjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainApplication extends Application {
    @Override // Needs to be overridden as Application has this as an abstract method
    public void start(Stage stage) throws IOException {

        // Here fxmlLoader is a root node, each scene needs a root note to be passed in to act as a layout manager
        // of sorts. Won't delve into this too much here.
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("mode-select.fxml"));

        Scene scene = new Scene(fxmlLoader.load()); // One hierarchy lower than a stage

        scene.getStylesheets().add("src\\main\\resources\\homeStyle.css");

        stage.setTitle("Shed: Mode Select");
//        stage.setWidth(600);
//        stage.setHeight(600);
        stage.setResizable(true);
        final String dir = System.getProperty("user.dir");
        System.out.println("current dir = " + dir);
        try {
            FileInputStream stream = new FileInputStream("src\\images\\IconCards.png");
            stage.getIcons().add(new Image(stream));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

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