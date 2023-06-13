package com.example.cardgame;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import static java.lang.Integer.parseInt;

public class GameController {
    private ArrayList<String> arrCards;
    private Player[] players = new Player[4];
    private BotPlayer[] bots = new BotPlayer[3];
    private String permission = "normal";
    private double AnimationT;
    private String gameStatus = "normal";
    private String card3Add = "no";
    private Button StepButton;
    private int botGameCounter = 1;
    private String queue = "";
    private int SkipCounter = 0;
    private Button Button3Step;
    private boolean End = false;
    @FXML
    private Label AdviseLabel;
    @FXML
    private Label AdviseLabelPlayer;
    @FXML
    private Button Start;
    @FXML
    private Button DeckButton;
    @FXML
    private Button Button1;
    @FXML
    private Button Button2;
    @FXML
    private Button Button3;
    @FXML
    private Button Button4;
    @FXML
    private Button Button5;
    @FXML
    private Button ButtonCenter;
    @FXML
    private Button BotButton1;
    @FXML
    private Button BotButton2;
    @FXML
    private Button BotButton3;
    @FXML
    private Button DeckButton1;
    @FXML
    private Button NextButton;
    @FXML
    private Button BackButton;
    @FXML
    private Button SkipButton;
    @FXML
    private Button BotButton12;
    @FXML
    private Button BotButton13;
    @FXML
    private Button BotButton14;
    @FXML
    private Button BotButton22;
    @FXML
    private Button BotButton23;
    @FXML
    private Button BotButton24;
    @FXML
    private Button BotButton32;
    @FXML
    private Button BotButton33;
    @FXML
    private Button BotButton34;
    @FXML
    private Button BotButton15;
    @FXML
    private Button BotButton25;
    @FXML
    private Button BotButton35;
    @FXML
    private Button ButtonHearts;
    @FXML
    private Button ButtonSpades;
    @FXML
    private Button ButtonClubs;
    @FXML
    private Button ButtonDiamonds;
    @FXML
    private Pane StartPane;
    @FXML
    private Label FinishLabel;
    @FXML
    private Label gameStatusLabel;
    @FXML
    private void startGame(ActionEvent event){
        // Початок нового раунда гри
        Start.setLayoutY(700);
        StartPane.setLayoutY(700);
        FinishLabel.setText("Finish points " + GameCondition.Points);
        arrCards = generateCards(); // Генерація колоди карт
        for(int i=0; i<4; i++){
            Player player = new Player(playerCards(GameCondition.Cards));//Роздача карт гравцям
            players[i] = player;
        }
        String fileName = "src/main/resources/com/example/cardgame/cards/Card.png";
        try{
            FileInputStream inp = new FileInputStream(fileName);
            Image im = new Image(inp);
            BackgroundImage bi = new BackgroundImage(im,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            Background bg = new Background(bi);
            DeckButton.setBackground(bg);
            DeckButton1.setBackground(bg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileNameCenter = "src/main/resources/com/example/cardgame/gameStyle/CenterCard.png";
        try{
            FileInputStream inp = new FileInputStream(fileNameCenter);
            Image im = new Image(inp);
            BackgroundImage bi = new BackgroundImage(im,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            Background bg = new Background(bi);
            ButtonCenter.setBackground(bg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] Suit = {"Spades", "Hearts", "Clubs", "Diamonds"};
        Button[] SuitBtn = {ButtonSpades, ButtonHearts, ButtonClubs, ButtonDiamonds};
        for(int i=0; i<4; i++){
            String suitName = "src/main/resources/com/example/cardgame/gameStyle/";
            suitName += Suit[i] + ".png";
            try{
                FileInputStream inp = new FileInputStream(suitName);
                Image im = new Image(inp);
                BackgroundImage bi = new BackgroundImage(im,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                Background bg = new Background(bi);
                SuitBtn[i].setBackground(bg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        bots[0] = new BotPlayer(players[1],  ButtonCenter.getText());
        bots[1] = new BotPlayer(players[2],  ButtonCenter.getText());
        bots[2] = new BotPlayer(players[3],  ButtonCenter.getText());
        Button[] ButtonArr = {Button1, Button2, Button3, Button4, Button5};
        DeckButton1.setDisable(true);
        DeckButton1.setOpacity(1);
        setCards(players[0], ButtonArr, 0);//Виведення карт користувача
    }
    private ArrayList<String> generateCards(){
        String[] suits = {
                "Clubs", "Diamonds", "Hearts", "Spades"
        };
        String[] rank = {
                "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "Jack", "Queen", "King", "Ace"
        };
        int n = 54;
        ArrayList<String> deck = new ArrayList<>();
        for (int i = 0; i < rank.length; i++) {
            for (int j = 0; j < suits.length; j++) {
                deck.add(rank[i] + " " + suits[j]);//Генерація звичайної колоди
            }
        }
        deck.add("Joker Red");
        deck.add("Joker Black");
        for (int i = 0; i < n; i++) {
            int r = i + (int) (Math.random() * (n-i)); // Випадкова карта в колоді
            String tmp = deck.get(r);//Перемішування колоди
            deck.set(r, deck.get(i));
            deck.set(i, tmp);
        }
        return deck;
    }
    private ArrayList<String> playerCards(int amount){
        ArrayList<String> playerArr = new ArrayList<>();
        int i = 0;
        int j = 0;
        while(i<amount){
            playerArr.add(arrCards.get(j));//Роздача карт гравцям
            arrCards.remove(j);
            i++;
        }
        return playerArr;
    }
    private void setCards(Player player, Button[] ButtonArr, double delay){
        int xLayout = 200;
        for(int i=0; i<ButtonArr.length; i++){
            ButtonArr[i].setLayoutX(xLayout);
            ButtonArr[i].setLayoutY(700);
            xLayout += 100;
        }
        // Виведення карт користувача
        for(int j=0; j<player.getPlayerCards().size(); j++){
            ButtonArr[j].setDisable(false);
            ButtonArr[j].setOpacity(1);
            ButtonArr[j].setText(player.getPlayerCards().get(j));
            ButtonArr[j].setTextFill(Paint.valueOf("transparent"));
            String[] cardName = player.getPlayerCards().get(j).split(" ");
            String fileName = "src/main/resources/com/example/cardgame/cards/";
            fileName += cardName[0] + cardName[1] + ".png";
            try{
                FileInputStream inp = new FileInputStream(fileName);
                Image im = new Image(inp);
                BackgroundImage bi = new BackgroundImage(im,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                Background bg = new Background(bi);
                ButtonArr[j].setBackground(bg);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Timeline btnAnimation = new Timeline(
                    new KeyFrame(Duration.seconds(1.5), new KeyValue(ButtonArr[j].layoutYProperty(), 500))
            );
            btnAnimation.setDelay(Duration.seconds(delay));
            btnAnimation.play();
            if(player.getPlayerCards().size()>5&&j==4){
                NextButton.setDisable(false);//Якщо у користувача більше 5 карт
                Timeline nextBtnAnimation = new Timeline(
                        new KeyFrame(Duration.seconds(1.5), new KeyValue(NextButton.layoutYProperty(), 500))
                );
                nextBtnAnimation.setDelay(Duration.seconds(delay));
                nextBtnAnimation.play();
                break;
            }
        }
        //Якщо у користувача менше 5 карт
        if(ButtonArr.length>player.getPlayerCards().size()){
            for(int i=player.getPlayerCards().size(); i<ButtonArr.length; i++){
                ButtonArr[i].setText("");
                ButtonArr[i].setLayoutY(700);
            }
        }
    }
    private void resetCards(Button[] ButtonArr, Button ChosenBtn){
        //Карти користувача виводяться з поля
        for(int i=0; i<ButtonArr.length; i++){
            ButtonArr[i].setDisable(true);
            ButtonArr[i].setOpacity(1);
            if((ButtonArr[i]!=ChosenBtn&&ButtonArr[i]!=Button3Step)||(ButtonArr[i]==Button3Step&&(!ButtonArr[i].getText().equals(ButtonCenter.getText()))&&ButtonArr[i]!=ChosenBtn)){
            Timeline btnAnimation = new Timeline(

                    new KeyFrame(Duration.seconds(0.7), new KeyValue(ButtonArr[i].layoutYProperty(), 700))
            );
            btnAnimation.setDelay(Duration.seconds(0.7));
            btnAnimation.play();
            }
        }
        Timeline btnAnimationNext= new Timeline(
                new KeyFrame(Duration.seconds(0.7), new KeyValue(NextButton.layoutYProperty(), 700)),
                new KeyFrame(Duration.seconds(0.7), new KeyValue(BackButton.layoutYProperty(), 700)),
                new KeyFrame(Duration.seconds(0.7), new KeyValue(SkipButton.layoutYProperty(), 700)),
                new KeyFrame(Duration.seconds(0.7), new KeyValue(AdviseLabelPlayer.textProperty(), "")));
        NextButton.setDisable(true);
        BackButton.setDisable(true);
        btnAnimationNext.setDelay(Duration.seconds(0.7));
        btnAnimationNext.play();
        Button3Step = new Button();
    }
    @FXML
    private void nextCards(ActionEvent event){
        // Перехід до наступних карт користувача
        Button[] btnArr = {Button1, Button2, Button3, Button4, Button5};
        int xLayout = 200;
        for(int i=0; i<5; i++){
            btnArr[i].setLayoutX(xLayout);
            btnArr[i].setLayoutY(700);
            xLayout += 100;
        }
        int index = players[0].getPlayerCards().indexOf(Button5.getText());
        index += 1;
        for(int i=index, j=0; i<players[0].getPlayerCards().size(); i++, j++){
            btnArr[j].setText(players[0].getPlayerCards().get(i));
            btnArr[j].setTextFill(Paint.valueOf("transparent"));
            String[] cardName = players[0].getPlayerCards().get(i).split(" ");
            String fileName = "src/main/resources/com/example/cardgame/cards/";
            fileName += cardName[0] + cardName[1] + ".png";
            try{
                FileInputStream inp = new FileInputStream(fileName);
                Image im = new Image(inp);
                BackgroundImage bi = new BackgroundImage(im,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                Background bg = new Background(bi);
                btnArr[j].setBackground(bg);
            } catch (IOException e) {
                e.printStackTrace();
            }
            btnArr[j].setLayoutY(500);
            if(j==4) break;
        }
        if(index+5>=players[0].getPlayerCards().size()){//Якщо у користувача більше за 5 карт
            NextButton.setLayoutY(700);
            NextButton.setDisable(true);
        }
        if(!Button1.getText().equals(players[0].getPlayerCards().get(0))){
        BackButton.setLayoutY(500);
        BackButton.setDisable(false);
        }
    }
    @FXML
    private void backCards(ActionEvent event){
        //Повернення до попередніх карт
        Button[] btnArr = {Button1, Button2, Button3, Button4, Button5};
        int xLayout = 200;
        for(int i=0; i<5; i++){
            btnArr[i].setLayoutX(xLayout);
            btnArr[i].setLayoutY(700);
            xLayout += 100;
        }
        if(players[0].getPlayerCards().size()==5){
            for(int j=0; j<5; j++){
            btnArr[j].setText(players[0].getPlayerCards().get(j));
            btnArr[j].setTextFill(Paint.valueOf("transparent"));
            String[] cardName = players[0].getPlayerCards().get(j).split(" ");
            String fileName = "src/main/resources/com/example/cardgame/cards/";
            fileName += cardName[0] + cardName[1] + ".png";
            try{
                FileInputStream inp = new FileInputStream(fileName);
                Image im = new Image(inp);
                BackgroundImage bi = new BackgroundImage(im,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                Background bg = new Background(bi);
                btnArr[j].setBackground(bg);
            } catch (IOException e) {
                e.printStackTrace();
            }
            btnArr[j].setLayoutY(500);
            }
            BackButton.setLayoutY(700);
        }else{
        int index = players[0].getPlayerCards().indexOf(Button1.getText());
            if(index==-1) index = 4;
            if(index>=5)index -= 1;
        for(int i=index, j=4; i<players[0].getPlayerCards().size(); i--, j--){
            btnArr[j].setText(players[0].getPlayerCards().get(i));
            btnArr[j].setTextFill(Paint.valueOf("transparent"));
            String[] cardName = players[0].getPlayerCards().get(i).split(" ");
            String fileName = "src/main/resources/com/example/cardgame/cards/";
            fileName += cardName[0] + cardName[1] + ".png";
            try{
                FileInputStream inp = new FileInputStream(fileName);
                Image im = new Image(inp);
                BackgroundImage bi = new BackgroundImage(im,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                Background bg = new Background(bi);
                btnArr[j].setBackground(bg);
            } catch (IOException e) {
                e.printStackTrace();
            }
            btnArr[j].setLayoutY(500);
            if(j==0) break;
        }
        if(index-4==0){
            BackButton.setLayoutY(700);
            BackButton.setDisable(true);
        }
        NextButton.setLayoutY(500);
        NextButton.setDisable(false);
        }
    }
    private boolean checking(String check){
        String[] strClicked = check.split(" ");
        String[] strChecked = ButtonCenter.getText().split(" ");
        // Перевірка чи можливо зробити хід картою при заданій умові гри
        switch (permission){
            case "normal":
                if(strChecked[0].equals(strClicked[0])||strChecked[1].equals(strClicked[1])||strClicked[0].equals("8")||strClicked[0].equals("Joker")) return true;
                break;
            case "4 allowed":
                if(strChecked[0].equals(strClicked[0])||strChecked[1].equals(strClicked[1])||strClicked[0].equals("4")||strClicked[0].equals("8")||strClicked[0].equals("Joker")) return true;
                break;
            case "card queue":
                if((strChecked[1].equals(strClicked[1])&&strClicked[0].equals("5")) ||strClicked[0].equals("Joker"))return true;
                break;
            case "card queue2":
                if((strChecked[1].equals(strClicked[1])&&strClicked[0].equals("6"))||strClicked[0].equals("Joker")) return true;
                break;
            case "card queue3":
                if((strChecked[1].equals(strClicked[1])&&strClicked[0].equals("7")) ||strClicked[0].equals("Joker"))return true;
                break;
            case "joker":
                return true;
        }
        if(card3Add.equals("allowed")) {
            card3Add = "no";
            return true;
        }
        return false;
    }
    @FXML
    private void chosenCard(ActionEvent event){
        // Спрацьовує коли користувач обрав карту
        Button ButtonOut = (Button)event.getSource();
        System.out.println(card3Add);
        System.out.println(permission);
        String[] strClicked = ButtonOut.getText().split(" ");
        String[] strChecked = ButtonCenter.getText().split(" ");
        System.out.println(ButtonCenter.getText());
        if(ButtonCenter.getText().equals("")||checking(ButtonOut.getText())){ //Перевірка чи хід можливий
            StepButton = ButtonOut;
            SkipCounter = 0; // Гравець не пропустив хід
            String text = ((Button) event.getSource()).getText();
            ButtonCenter.setTextFill(Paint.valueOf("transparent"));
            String fileName = "src/main/resources/com/example/cardgame/cards/";
            fileName += strClicked[0] + strClicked[1] + ".png";
            try {
                FileInputStream inp = new FileInputStream(fileName);
                Image im = new Image(inp);
                BackgroundImage bi = new BackgroundImage(im,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                Background bg = new Background(bi);
                Timeline btnAnimation = new Timeline(
                        new KeyFrame(Duration.seconds(0.7), new KeyValue(ButtonOut.layoutYProperty(), 270)),
                        new KeyFrame(Duration.seconds(0.7), new KeyValue(ButtonOut.layoutXProperty(), 345)),
                        new KeyFrame(Duration.seconds(0.7), new KeyValue(ButtonCenter.textProperty(), text)),
                        new KeyFrame(Duration.seconds(0.7), new KeyValue(ButtonCenter.backgroundProperty(), bg)));
                btnAnimation.play();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread threadReverse = new Thread() {
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted.");
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run(){
                            ButtonOut.setLayoutY(700);
                            if(strClicked[0].equals("3")){
                                NextButton.setDisable(false);
                                BackButton.setDisable(false);
                                Button1.setDisable(false);
                                Button2.setDisable(false);
                                Button3.setDisable(false);
                                Button4.setDisable(false);
                                Button5.setDisable(false);
                            }
                        }
                    });
                }
            };
           threadReverse.start();
            players[0].getPlayerCards().remove(ButtonOut.getText());
            if(strClicked[0].equals("Joker")){ //Встановлення значення центральної карти, якщо хід зроблено Джокером
                if(permission.equals("card queue")){
                    text = "5" + " "+ strChecked[1];
                }else if(permission.equals("card queue2")){
                   text = "6" + " "+ strChecked[1];
                }else if(permission.equals("card queue3")){
                    text = "7" + " "+ strChecked[1];
                }
            }
            ButtonCenter.setText(text);
            System.out.println(ButtonCenter.getText());
            AnimationT = 1;
            if(players[0].getPlayerCards().isEmpty()){
                closeGame(ButtonCenter);
                return;
            }
            //Встановлення умови гри
            if(permission.equals("card queue")){
                permission = "card queue2";
            }else if(permission.equals("card queue2")){
                permission = "card queue3";
            }else if(permission.equals("card queue3")){
                permission = "4 allowed";
            }else{
                setCardCondition(ButtonCenter.getText());
                // if(permission.equals("reverse")||permission.equals("take card")) permission = "normal";
            }

            if ((!strClicked[0].equals("3") || !card3Add.equals("no"))&&!strClicked[0].equals("8")) {
                //Перевірка напрямку гри
                if (permission.equals("reverse")) {
                    if (gameStatus.equals("normal")) gameStatus = "reverse";
                    else gameStatus = "normal";
                    permission = "normal";
                }
                //Запуск гри супротивників
                if (gameStatus.equals("normal")) botGame(bots[0]);
                else botGame(bots[2]);
                Button[] ButtonArr = {Button1, Button2, Button3, Button4, Button5};
                resetCards(ButtonArr, StepButton);
                gameChecker();
            } else if(strClicked[0].equals("8")){
                ButtonHearts.setDisable(false);
                ButtonSpades.setDisable(false);
                ButtonClubs.setDisable(false);
                ButtonDiamonds.setDisable(false);
                BackButton.setDisable(true);
                NextButton.setDisable(true);
                Button1.setDisable(true);
                Button2.setDisable(true);
                Button3.setDisable(true);
                Button4.setDisable(true);
                Button5.setDisable(true);
                Button1.setOpacity(1);
                Button2.setOpacity(1);
                Button3.setOpacity(1);
                Button4.setOpacity(1);
                Button5.setOpacity(1);
                Timeline btnAnimation = new Timeline(
                        new KeyFrame(Duration.seconds(1.5), new KeyValue(ButtonSpades.layoutYProperty(), 420)),
                        new KeyFrame(Duration.seconds(1.5), new KeyValue(ButtonHearts.layoutYProperty(), 420)),
                        new KeyFrame(Duration.seconds(1.5), new KeyValue(ButtonClubs.layoutYProperty(), 420)),
                        new KeyFrame(Duration.seconds(1.5), new KeyValue(ButtonDiamonds.layoutYProperty(), 420))
                );
                btnAnimation.play();
            }else {
                NextButton.setDisable(true);
                BackButton.setDisable(true);
                Button1.setDisable(true);
                Button2.setDisable(true);
                Button3.setDisable(true);
                Button4.setDisable(true);
                Button5.setDisable(true);
                card3Add = "allowed";
                //Можна зробити хід ще однією картою
                Button3Step = ButtonOut;
                SkipButton.setLayoutY(450);
            }
        }
    }
    @FXML
    private void changeSuit(ActionEvent event){
        Button ButtonSuit = (Button)event.getSource();
        String[] btnCard = ButtonCenter.getText().split(" ");
        // Заміна масті для центральної карти
        if(ButtonSuit==ButtonSpades) ButtonCenter.setText(btnCard[0]+" "+"Spades");
        else if(ButtonSuit==ButtonHearts) ButtonCenter.setText(btnCard[0]+" "+"Hearts");
        else if(ButtonSuit==ButtonClubs) ButtonCenter.setText(btnCard[0]+" "+"Clubs");
        if(ButtonSuit==ButtonDiamonds) ButtonCenter.setText(btnCard[0]+" "+"Diamonds");
        ButtonSpades.setDisable(true);
        ButtonHearts.setDisable(true);
        ButtonDiamonds.setDisable(true);
        ButtonClubs.setDisable(true);
        ButtonHearts.setOpacity(1);
        ButtonClubs.setOpacity(1);
        ButtonSpades.setOpacity(1);
        ButtonDiamonds.setOpacity(1);
        Timeline btnAnimation = new Timeline(
                new KeyFrame(Duration.seconds(1), new KeyValue(ButtonSpades.layoutYProperty(), 700)),
                new KeyFrame(Duration.seconds(1), new KeyValue(ButtonHearts.layoutYProperty(), 700)),
                new KeyFrame(Duration.seconds(1), new KeyValue(ButtonClubs.layoutYProperty(), 700)),
                new KeyFrame(Duration.seconds(1), new KeyValue(ButtonDiamonds.layoutYProperty(), 700))
        );
        btnAnimation.play();
        if (gameStatus.equals("normal")) botGame(bots[0]);
        else botGame(bots[2]);
        Button[] ButtonArr = {Button1, Button2, Button3, Button4, Button5};
        resetCards(ButtonArr, StepButton);
        gameChecker();

    }
    private void closeGame(Button btn){
        Thread threadFinish = new Thread() {
            public void run() {
                try {
                    Thread.sleep((long)(AnimationT*1000)+1000);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted.");
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run(){
                        try{
                            if(!End)gameFinished((Stage) btn.getScene().getWindow());
                        }catch (IOException exception){
                            System.out.println("exception");
                        }
                    }
                });
            }
        };
        threadFinish.start();
    }
    @FXML
    private void skipStep(ActionEvent event) {
        AnimationT = 1;
        if(arrCards.isEmpty()){
            StepButton = new Button();
            if(permission.equals("normal")||permission.equals("4 allowed"))SkipCounter +=1; //Гравець пропускає хід
            else SkipCounter = 0;
        }
        // Встановлення умови гри
        if(permission.equals("card queue")){
            permission = "card queue2";
        }else if(permission.equals("card queue2")){
            permission = "card queue3";
        }else if(permission.equals("card queue3")){
            permission = "4 allowed";
        }else permission = "normal";
        if (gameStatus.equals("normal")) botGame(bots[0]);
        else botGame(bots[2]);
        Button[] ButtonArr = {Button1, Button2, Button3, Button4, Button5};
        StepButton = new Button();
        resetCards(ButtonArr, StepButton);
        gameChecker(); // Перевірка умови гри

    }
    private void gameChecker(){
        Thread thread = new Thread() {
            public void run() {
                //Thread.currentThread().sleep(2000);
                try {
                    Thread.sleep((long)(AnimationT*1000)+1000);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted.");
                }

                Platform.runLater(new Runnable(){
                    @Override public void run() {
                        botGameCounter = 1;
                        if(permission.equals("skip")){
                            AdviseLabelPlayer.setText("You skip");
                            permission = "normal";
                            AnimationT = 0;
                            if (gameStatus.equals("normal")) botGame(bots[0]);
                            else botGame(bots[2]);
                            gameChecker();
                        }else if(permission.equals("take card")){
                            AdviseLabelPlayer.setText("You get 1 card");
                            permission = "normal";
                            if(arrCards.size()!=0){
                            getCardFromDeck(new ActionEvent());}
                            else{
                                AdviseLabelPlayer.setText("No cards, just skip");
                                AnimationT = 0;
                                if (gameStatus.equals("normal")) botGame(bots[0]);
                                else botGame(bots[2]);
                                gameChecker();
                            }
                        }else{
                            cardsBack();
                        }
                    }
                });
            }
        };
        thread.start();
    }
    private void cardsBack(){
        gameStatusLabel.setText(gameStatus);
        if(SkipCounter<4) {
            if(card3Add.equals("allowed")) card3Add = "no";
            AdviseLabelPlayer.setText("Choose card");
            Button[] ButtonArr = {Button1, Button2, Button3, Button4, Button5};
            setCards(players[0], ButtonArr, 0);
            boolean checker = false;
            for (int i = 0; i < players[0].getPlayerCards().size(); i++) {
                checker = checking(players[0].getPlayerCards().get(i));
                if (checker) break;

            }
            if (!checker) {
                if (!arrCards.isEmpty()){
                    AdviseLabelPlayer.setText("Get card from deck");
                    DeckButton1.setDisable(false);
                    DeckButton1.setOpacity(1);
                }else{
                    AdviseLabelPlayer.setText("Skip, deck is empty");
                    SkipButton.setLayoutY(450);
                                }
            }
        }else{
            AnimationT = 1;
            if(!End)closeGame(ButtonCenter);
        }
    }
    @FXML
    private void getCardFromDeck(ActionEvent event){
        DeckButton1.setDisable(true);
        DeckButton1.setOpacity(1);
        if(permission.equals("card queue")||permission.equals("card queue2")||permission.equals("card queue3")) queue = permission;
        if(queue.equals("")){
            //Отримання карти з колоди
            players[0].getPlayerCards().add(arrCards.get(0));
            arrCards.remove(0);
        }else{
            int amount;
            String[] btnNum = ButtonCenter.getText().split(" ");
            amount = parseInt(btnNum[0]);
            if(amount>arrCards.size()) amount = arrCards.size();
            //Взяти карти по умові гри
            for(int i=0; i<amount; i++){
                players[0].getPlayerCards().add(arrCards.get(0));
                arrCards.remove(0);
            }
            queue = "";
        }
        double x = DeckButton1.getLayoutY();
        Timeline btnAnimation = new Timeline(
                new KeyFrame(Duration.seconds(1.4), new KeyValue(DeckButton1.layoutYProperty(), 700)),
                new KeyFrame(Duration.seconds(1.4), new KeyValue(AdviseLabelPlayer.textProperty(), ""))
        );
        Timeline btnAnimBack = new Timeline(
                new KeyFrame(Duration.seconds(1.4), new KeyValue(DeckButton1.layoutYProperty(), x))
        );
        btnAnimation.play();
        Button[] ButtonArr = {Button1, Button2, Button3, Button4, Button5};
        resetCards(ButtonArr, DeckButton1);
        btnAnimBack.setDelay(Duration.seconds(1.4));
        btnAnimBack.play();
        AnimationT = 2;
        //Встановлення умови гри
        if(permission.equals("card queue")){
            permission = "card queue2";
        }else if(permission.equals("card queue2")){
            permission = "card queue3";
        }else if(permission.equals("card queue3")){
            permission = "4 allowed";
        }
        if(gameStatus.equals("normal")) botGame(bots[0]);
        else botGame(bots[2]);
        gameChecker();
    }
    private void changeGame(){
        //Задає напрямок гри
        if(permission.equals("reverse")){
            botGameCounter +=1;
            if(gameStatus.equals("normal"))gameStatus = "reverse";
            else gameStatus = "normal";
            permission = "normal";
        }
    }
    private Button getButton(BotPlayer bot){
        Button[] btnArr = {BotButton1, BotButton2, BotButton3};
        switch (botGameCounter){
            case 2:
                btnArr[0] = BotButton12;
                btnArr[1] = BotButton22;
                btnArr[2] = BotButton32;
                break;
            case 3:
                btnArr[0] = BotButton13;
                btnArr[1] = BotButton23;
                btnArr[2] = BotButton33;
                break;
            case 4:
                btnArr[0] = BotButton14;
                btnArr[1] = BotButton24;
                btnArr[2] = BotButton34;
                break;
            case 5:
                btnArr[0] = BotButton15;
                btnArr[1] = BotButton25;
                btnArr[2] = BotButton35;
                break;
        }
        if(bot==bots[0]) return btnArr[0];
        else if(bot==bots[1]) return btnArr[1];
        else return btnArr[2];
    }
    @FXML
    private void botGame(BotPlayer bot){
        AdviseLabel.setText("");
                 if(bot==bots[0]){ //Перший гравець ходить
                     playCondition(bots[0], getButton(bots[0]));
                     changeGame();
                     if(bots[0].getBotCards().isEmpty()){
                         closeGame(ButtonCenter);
                         return;
                     }
                     if(gameStatus.equals("normal"))botGame(bots[1]); //Перевірка напрямку гри

                 }else if(bot==bots[1]){  //Другий гравець ходить
                     playCondition(bots[1], getButton(bots[1]));
                     changeGame();
                     if(bots[1].getBotCards().isEmpty()){
                         closeGame(ButtonCenter);
                         return;
                     }
                     if(gameStatus.equals("normal"))botGame(bots[2]); //Перевірка напрямку гри
                     else botGame(bots[0]);
                 }else{ //Третій гравець ходить
                     playCondition(bots[2], getButton(bots[2]));
                     changeGame();
                     if(bots[2].getBotCards().isEmpty()){
                         closeGame(ButtonCenter);
                         return;
                     }
                     if(gameStatus.equals("reverse"))botGame(bots[1]); //Перевірка напрямку гри
                 }
    }
    @FXML
    private void botAnimation(Button botButton, String btnText, double delay, String labeltext){
        double x = ButtonCenter.getLayoutX() - botButton.getLayoutX();
        double y = ButtonCenter.getLayoutY() - botButton.getLayoutY();
        Timeline btnAnimation2 = new Timeline(
                new KeyFrame(Duration.seconds(1.5), new KeyValue(botButton.translateYProperty(), y)),
                new KeyFrame(Duration.seconds(1.5), new KeyValue(botButton.translateXProperty(), x)),
                new KeyFrame(Duration.seconds(1.5), new KeyValue(ButtonCenter.textProperty(), btnText)),
                new KeyFrame(Duration.seconds(1.5), new KeyValue(AdviseLabel.textProperty(), labeltext)));
        btnAnimation2.setDelay(Duration.seconds(delay));
        btnAnimation2.play();
        long res = (long)(delay*1000)+1500;
        Thread thread = new Thread() {
            public void run() {
                try {
                    Thread.sleep(res);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted.");
                }
                Platform.runLater(new Runnable(){
                    @Override public void run() {
                        String[] cardName = botButton.getText().split(" ");
                        String fileName = "src/main/resources/com/example/cardgame/cards/";
                        fileName += cardName[0] + cardName[1] + ".png";

                        try{
                            FileInputStream inp = new FileInputStream(fileName);
                            Image im = new Image(inp);
                            BackgroundImage bi = new BackgroundImage(im,
                                    BackgroundRepeat.NO_REPEAT,
                                    BackgroundRepeat.NO_REPEAT,
                                    BackgroundPosition.DEFAULT,
                                    BackgroundSize.DEFAULT);
                            Background bg = new Background(bi);
                            ButtonCenter.setBackground(bg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        thread.start();
        Timeline btnAnimBack = new Timeline(
                new KeyFrame(Duration.seconds(1.5), new KeyValue(botButton.translateYProperty(), 0)),
                new KeyFrame(Duration.seconds(1.5), new KeyValue(botButton.translateXProperty(), 0)));
        btnAnimBack.setDelay(Duration.seconds(delay+1.6));
        btnAnimBack.play();
    }
    public void playCondition(BotPlayer bot, Button botButton){
        switch (permission){
            case "take card":
                  if(arrCards.size()!=0) {
                      ArrayList<String> botCards = bot.getBotCards();
                      int amount = 1;
                      if (queue.equals("")) {
                          botCards.add(arrCards.get(0));
                          System.out.println("ARRAY CARD");
                          System.out.println(arrCards.get(0));
                          bot.setBotCards(botCards);
                          arrCards.remove(0);
                      } else {
                          String[] btnNum = ButtonCenter.getText().split(" ");
                          amount = parseInt(btnNum[0]);
                          if(amount>arrCards.size()) amount = arrCards.size();
                          for (int i = 0; i < amount; i++) {
                              botCards.add(arrCards.get(0));
                              System.out.println("ARRAY CARD");
                              System.out.println(arrCards.get(0));
                              bot.setBotCards(botCards);
                              arrCards.remove(0);
                          }
                          queue = "";
                      }
                      String text = "Get " + Integer.toString(amount);
                      if(amount==1) text += " card";
                      else text += " cards";
                      double x = botButton.getLayoutX() - DeckButton.getLayoutX();
                      double y = botButton.getLayoutY() - DeckButton.getLayoutY();
                      Timeline btnAnimation = new Timeline(
                              new KeyFrame(Duration.seconds(1.5), new KeyValue(DeckButton.translateXProperty(), x)),
                              new KeyFrame(Duration.seconds(1.5), new KeyValue(DeckButton.translateYProperty(), y)),
                              new KeyFrame(Duration.seconds(1.5), new KeyValue(AdviseLabel.textProperty(), text))
                      );
                      btnAnimation.setDelay(Duration.seconds(AnimationT));
                      Timeline btnAnimBack = new Timeline(
                              new KeyFrame(Duration.seconds(1.5), new KeyValue(DeckButton.translateYProperty(), 0)),
                              new KeyFrame(Duration.seconds(1.5), new KeyValue(DeckButton.translateXProperty(), 0))
                      );
                      btnAnimBack.setDelay(Duration.seconds(AnimationT + 1.5));
                      btnAnimBack.play();
                      AnimationT += 2;
                      btnAnimation.play();
                      String[] btnArr = ButtonCenter.getText().split(" ");
                      if (btnArr[0].equals("2")) {
                          permission = "normal";
                      } else {
                          setCardCondition(ButtonCenter.getText());
                          if (permission.equals("skip")) permission = "normal";
                      }
                  }else{
                      Timeline btnAnimationSkip = new Timeline(
                              new KeyFrame(Duration.seconds(1.5), new KeyValue(AdviseLabel.textProperty(), "Skip: no cards in deck"))
                      );
                      btnAnimationSkip.setDelay(Duration.seconds(AnimationT));
                      AnimationT +=2;
                      btnAnimationSkip.play();
                      if(permission.equals("card queue")){
                          permission = "card queue2";
                      }else if(permission.equals("card queue2")){
                          permission = "card queue3";
                      }else if(permission.equals("card queue3")){
                          permission = "4 allowed";
                      }else{
                          setCardCondition(ButtonCenter.getText());
                          if(!permission.equals("4 allowed"))permission = "normal";
                      }
                  }
                  break;
            case "joker":
            case "card queue":
            case "card queue2":
            case "card queue3":
            case "4 allowed":
            case "normal":
                String labeltext = "";
                bot.setBtnText(ButtonCenter.getText());
                String card2 = "";
                card2 = bot.botPlaying(permission, arrCards);
                if(!card2.isEmpty()){
                    SkipCounter = 0;
                    labeltext = card2;
                    String[] strCard = card2.split(" ");
                    String[] strCenter = ButtonCenter.getText().split(" ");
                    bot.getBot().getPlayerCards().remove(card2);
                    if(strCard[0].equals("3")&&bot.getBot().getPlayerCards().size()!=0){
                        permission = "one more card";
                        labeltext = "2 cards : " + card2 + " + ";
                        card2 = bot.botPlaying(permission, arrCards);
                        bot.getBot().getPlayerCards().remove(card2);
                        labeltext += card2;
                    }
                    botButton.setText(card2);
                    botButton.setTextFill(Paint.valueOf("transparent"));
                    String[] cardName = botButton.getText().split(" ");
                    String fileName = "src/main/resources/com/example/cardgame/cards/";
                    fileName += cardName[0] + cardName[1] + ".png";
                    try{
                        FileInputStream inp = new FileInputStream(fileName);
                        Image im = new Image(inp);
                        BackgroundImage bi = new BackgroundImage(im,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.DEFAULT,
                                BackgroundSize.DEFAULT);
                        Background bg = new Background(bi);
                        botButton.setBackground(bg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ButtonCenter.setTextFill(Paint.valueOf("transparent"));
                    if(strCard[0].equals("8")){
                        String[] suitArr= {"Hearts", "Spades", "Clubs", "Diamonds"};
                        int randNum = ThreadLocalRandom.current().nextInt(0,4);
                        String ms = suitArr[randNum];
                        card2 = "8" + " " + ms;
                        labeltext = "Suit is  " + ms;
                    }
                    if(strCard[0].equals("Joker")){
                        if(permission.equals("card queue")){
                            card2 = "5" + " "+ strCenter[1];
                        }else if(permission.equals("card queue2")){
                            card2 = "6" + " "+ strCenter[1];
                        }else if(permission.equals("card queue3")){
                            card2 = "7" + " "+ strCenter[1];
                        }
                    }
                    ButtonCenter.setText(card2);
                    botAnimation(botButton, card2, AnimationT, labeltext);
                    if(permission.equals("card queue")){
                        permission = "card queue2";
                    }else if(permission.equals("card queue2")){
                        permission = "card queue3";
                    }else if(permission.equals("card queue3")){
                        permission = "4 allowed";
                    }else{
                        setCardCondition(ButtonCenter.getText());
                    }
                    AnimationT +=2;
                }else{
                    if(arrCards.isEmpty()&&(permission.equals("normal")||permission.equals("4 allowed"))) SkipCounter+=1;
                    else if(arrCards.isEmpty()) SkipCounter = 0;
                    String permBef = permission;
                    permission = "take card";
                    if(permBef.equals("card queue")||permBef.equals("card queue2")||permBef.equals("card queue3")) queue = permBef;
                    playCondition(bot, botButton);
                    if(permBef.equals("card queue")){
                        permission = "card queue2";
                    }else if(permBef.equals("card queue2")){
                        permission = "card queue3";
                    }else if(permBef.equals("card queue3")){
                        permission = "4 allowed";
                    }else{
                        setCardCondition(ButtonCenter.getText());
                        if(!permission.equals("4 allowed"))permission = "normal";
                    }

                }
                break;
            case "skip":
                permission = "normal";
                break;

        }
    }
    private void setCardCondition(String card){
        //Встановлення умови гри по карті
        String[] cardArr = card.split(" ");
        switch (cardArr[0]){
            case "2":
                permission = "take card";
                break;
            case "4":
                permission = "card queue";
                break;
            case "5":
            case "6":
            case "7":
                permission = "4 allowed";
                break;
            case "Jack":
                permission = "skip";
                break;
            case "Ace":
                permission = "reverse";
                break;
            case "Joker":
                permission = "joker";
                break;
            default:
                permission = "normal";
        }
    }
    private void gameFinished(Stage stage) throws IOException{
         FXMLLoader fxmlLoader = new FXMLLoader();
         fxmlLoader.setLocation(getClass().getResource("finish-view.fxml"));
         Parent fxmlLoaderP = fxmlLoader.load();
         FinishController controller = fxmlLoader.getController();
        //Передати карти гравців для підрахунку балів
         controller.initFinish(players[0].getPlayerCards(), bots[0].getBotCards(), bots[1].getBotCards(), bots[2].getBotCards());
         End = true;
        stage.setTitle("The last player");
        stage.setScene(new Scene(fxmlLoaderP, 900, 700));
        stage.show();
    }
}