package com.example.cardgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FinishController {
    private ArrayList<String> player;
    private ArrayList<String> bot1;
    private ArrayList<String> bot2;
    private ArrayList<String> bot3;
    @FXML private ListView<String> ListView1;
    @FXML private ListView<String> ListView2;
    @FXML private ListView<String> ListView3;
    @FXML private ListView<String> ListView4;
    @FXML private Label Total1;
    @FXML private Label Total2;
    @FXML private Label Total3;
    @FXML private Label Total4;
    @FXML private Label GamePoints1;
    @FXML private Label GamePoints2;
    @FXML private Label GamePoints3;
    @FXML private Label GamePoints4;
    @FXML private Button Continue;
    @FXML private Button Restart;
    @FXML private Button Save;
    @FXML private Label WinnerLabel;
    public void initFinish(ArrayList p1, ArrayList b1, ArrayList b2, ArrayList b3){
        player = p1;
        bot1 = b1;
        bot2 = b2;
        bot3 = b3;
        try {
            setInfo();
        }catch (IOException exception){}
    }
    private void setPlayerPoints(ArrayList<String> playerCards, ListView<String> ListCards, Label pointsGame, Label totalPoints, int playerNum){
        int GamePoints = 0;
        if(!playerCards.isEmpty()) {
            for(String card:playerCards){
                //Підрахунок балів для кожної карти
                String[] cardStr = card.split(" ");
                int points = getPoint(cardStr[0]);
                if(points!=0){
                    String pointsStr = Integer.toString(points);
                    GamePoints += points;
                    card += " " + pointsStr + " points ";
                }
                ListCards.getItems().add(card);
            }

        }
        pointsGame.setText("Points in game: "+Integer.toString(GamePoints));
        switch (playerNum){
            case 1:
                GameCondition.Player1 += GamePoints;
                totalPoints.setText("Points in total: "+GameCondition.Player1);
                break;
            case 2:
                GameCondition.Player2 += GamePoints;
                totalPoints.setText("Points in total: "+GameCondition.Player2);
                break;
            case 3:
                GameCondition.Player3 += GamePoints;
                totalPoints.setText("Points in total: "+GameCondition.Player3);
                break;
            case 4:
                GameCondition.Player4 += GamePoints;
                totalPoints.setText("Points in total: "+GameCondition.Player4);
                break;
        }

    }
    @FXML
    private void setInfo() throws IOException{
        setPlayerPoints(player, ListView1, GamePoints1, Total1, 1);
        setPlayerPoints(bot1, ListView2, GamePoints2, Total2, 2);
        setPlayerPoints(bot2, ListView3, GamePoints3, Total3, 3);
        setPlayerPoints(bot3, ListView4, GamePoints4, Total4, 4);
        //Перевірка чи хтось із гравців перетнув межу балів
        if(GameCondition.Player1>=GameCondition.Points||GameCondition.Player2>=GameCondition.Points||GameCondition.Player3>=GameCondition.Points||GameCondition.Player4>=GameCondition.Points){
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/com/example/cardgame/info.txt"));
            writer.write("");
            writer.close();
            int[] PointsArr = {GameCondition.Player1, GameCondition.Player2, GameCondition.Player3, GameCondition.Player4};
            int winnerPoints = PointsArr[0];
            int winnerPoints2 = -1;
            for(int i=1; i<4; i++){
                if(PointsArr[i]<winnerPoints) winnerPoints = PointsArr[i];
                else if(PointsArr[i]==winnerPoints) winnerPoints2 = PointsArr[i];
            }
            if(winnerPoints2==winnerPoints) WinnerLabel.setText("It's draw! ");
            else{
                if(winnerPoints == GameCondition.Player1) WinnerLabel.setText("Winner is Player 1");
                else if(winnerPoints == GameCondition.Player2) WinnerLabel.setText("Winner is Player 2");
                else if(winnerPoints == GameCondition.Player3) WinnerLabel.setText("Winner is Player 3");
                else WinnerLabel.setText("Winner is Player 4");
            }
            Restart.setLayoutY(600);
        }else {
            Continue.setLayoutY(600);
            Save.setLayoutY(600);
        }
    }
    private int getPoint(String card){
        //Встановлення балів за кожну карту
        int points = 0;
        switch (card){
            case "Jack":
            case "King":
            case "Queen":
            case "10":
                points = 10;
                break;
            case "Ace":
                points = 15;
                break;
            case "8":
                points = 25;
                break;
            case "Joker":
                points = 40;
                break;


        }
        return points;
    }
    @FXML private void ContinueGame(ActionEvent event) throws IOException {
        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("The last player");
        stage.setScene(new Scene(fxmlLoader, 900, 700));
        stage.show();
    }
    @FXML private void RestartGame(ActionEvent event) throws IOException {
        GameCondition.Cards = 0;
        GameCondition.Points = 0;
        GameCondition.Player1 = 0;
        GameCondition.Player2 = 0;
        GameCondition.Player3 = 0;
        GameCondition.Player4 = 0;
        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("start-view.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("The last player");
        stage.setScene(new Scene(fxmlLoader, 900, 700));
        stage.show();
    }
    @FXML private void SaveGame(ActionEvent event) throws IOException{
        //Збереження балів гравців та умови гри у файл
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/com/example/cardgame/info.txt"));
        writer.write("");
        writer.append(Integer.toString(GameCondition.Player1));
        writer.append(" ");
        writer.append(Integer.toString(GameCondition.Player2));
        writer.append(" ");
        writer.append(Integer.toString(GameCondition.Player3));
        writer.append(" ");
        writer.append(Integer.toString(GameCondition.Player4));
        writer.append(" ");
        writer.append(Integer.toString(GameCondition.Points));
        writer.append(" ");
        writer.append(Integer.toString(GameCondition.Cards));
        writer.close();
    }
}
