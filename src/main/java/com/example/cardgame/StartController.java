package com.example.cardgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.Integer.parseInt;

public class StartController {
    @FXML
    private Button Button4;
    @FXML
    private Button Button5;
    @FXML
    private Button Button6;
    @FXML
    private Button Button7;
    @FXML
    private Button Button8;
    @FXML
    private Button Button100;
    @FXML
    private Button Button150;
    @FXML
    private Button Button200;
    @FXML
    private Button Button250;
    @FXML
    private Button Button300;
    @FXML
    private Button NextButton;
    @FXML
    private void startView(ActionEvent event) throws IOException {

        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("The last player");
        stage.setScene(new Scene(fxmlLoader));
        stage.show();
        //startGame(new ActionEvent());
    }
    @FXML
    private void getCardsNum(ActionEvent event){
        Button btnCard = (Button)event.getSource();
        GameCondition.Cards = parseInt(btnCard.getText());
        Button[] btnArr = {Button4, Button5, Button6, Button7,Button8};
        for(Button btn:btnArr){
            if(btn!=btnCard){
                btn.setOpacity(0.5);
            }
        }
        btnCard.setOpacity(1);
        checkStart();
    }
    @FXML
    private void getPointsNum(ActionEvent event){
        Button btnPoints = (Button)event.getSource();
        GameCondition.Points = parseInt(btnPoints.getText());
        Button[] btnArr = {Button100, Button150, Button200, Button250,Button300};
        for(Button btn:btnArr){
            if(btn!=btnPoints){
                btn.setOpacity(0.5);
            }
        }
        btnPoints.setOpacity(1);
        checkStart();
    }
    @FXML
    private void checkStart(){
        //Якщо встановлено кількість карт та межу балів - користувачу стає доступна кнопка Next
        if(GameCondition.Points!=0&&GameCondition.Cards!=0) NextButton.setLayoutY(500);
    }

}
