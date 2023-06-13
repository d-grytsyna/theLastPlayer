package com.example.cardgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("saved-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 700);
        stage.setTitle("The last player");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        SavedController controller = fxmlLoader.getController();
        try {
            controller.checkSaved();
        }catch (IOException exception){}
    }

    public static void main(String[] args) {
        launch();
    }
}