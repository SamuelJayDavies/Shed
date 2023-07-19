package com.example.shed;

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

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("mode-select.fxml"));

        Scene scene = new Scene(fxmlLoader.load()); // One hierarchy lower than a stage


        stage.setTitle("Shed: Mode Select");
        stage.setResizable(false);
        try {
            FileInputStream stream = new FileInputStream("src\\images\\IconCards.png");
            stage.getIcons().add(new Image(stream));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        stage.setScene(scene); // Add scene to the stage
        stage.show(); // Shows the stage
    }

    public static void main(String[] args) {
        launch(args);
    }
}