package com.example.cardgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.*;

public class SavedController {
    @FXML private Button Saved;
    public void checkSaved() throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/cardgame/info.txt"));
        String savedLine = reader.readLine();
        //Перевірка чи файл з збереженою грою пустий
        if(savedLine==null) Saved.setDisable(true);
        else Saved.setDisable(false);
        reader.close();
    }
    @FXML private void NewGame(ActionEvent event) throws IOException {
        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("start-view.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("The last player");
        stage.setScene(new Scene(fxmlLoader, 900, 700));
        stage.show();
    }
    @FXML private void SavedGame(ActionEvent event) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/cardgame/info.txt"));
        String savedLine = reader.readLine();
        // Запуск збереженої гри
        String[] splitLine = savedLine.split(" ");
        GameCondition.Player1 = Integer.parseInt(splitLine[0]);
        GameCondition.Player2 = Integer.parseInt(splitLine[1]);
        GameCondition.Player3 = Integer.parseInt(splitLine[2]);
        GameCondition.Player4 = Integer.parseInt(splitLine[3]);
        GameCondition.Points = Integer.parseInt(splitLine[4]);
        GameCondition.Cards = Integer.parseInt(splitLine[5]);
        reader.close();
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/com/example/cardgame/info.txt"));
        writer.write("");
        writer.close();
        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("The last player");
        stage.setScene(new Scene(fxmlLoader, 900, 700));
        stage.show();
    }
}
