package com.example.cardgame;

import javafx.fxml.FXML;
import java.util.ArrayList;

public class BotPlayer {
    private Player botPlayer;
    private String ButtonCenter;

    public BotPlayer(Player botPlayer, String ButtonCenter){
        this.botPlayer = botPlayer;
        this.ButtonCenter = ButtonCenter;
    }
    public void setBtnText(String text){
        ButtonCenter = text;
    }
    public ArrayList<String> getBotCards(){
        return botPlayer.getPlayerCards();
    }
    public void setBotCards(ArrayList<String> arr){
        botPlayer.setPlayerCards(arr);
    }
    public Player getBot(){
        return botPlayer;
    }
    @FXML
    public String botPlaying(String condition, ArrayList<String> cardsArr){

        String res ="";
        String[] centerCheck = ButtonCenter.split(" ");
        ArrayList<String> cards = new ArrayList<>();
        //Перевірка чи карта може зробити хід при певній умові гри, запис такої карти у масив
        switch (condition){
            case "normal":
                for(String card:botPlayer.getPlayerCards()){
                    String[] cardWords = card.split(" ");
                    if(cardWords[0].equals(centerCheck[0])||cardWords[1].equals(centerCheck[1])||cardWords[0].equals("8")||cardWords[0].equals("Joker")){
                        cards.add(card);

                    }

                }
                if(cards.size()==1)res = cards.get(0);
                else if(cards.size()!=0)res = chooseCard(cardsArr, cards);
                return res;
            case "card queue":
                for(String card:botPlayer.getPlayerCards()){
                    String[] cardWords = card.split(" ");
                    if((cardWords[0].equals("5")&&cardWords[1].equals(centerCheck[1]))||cardWords[0].equals("Joker")){
                        cards.add(card);
                    }
                }
               if(cards.size()>1){
                  for(String card:cards){
                      String[] cardStr = card.split(" ");
                      if(cardStr[0].equals("5")) res = card;
                  }
               } else if(cards.size()!=0) res = cards.get(0);
               return res;
            case "card queue2":
                for(String card:botPlayer.getPlayerCards()){
                    String[] cardWords = card.split(" ");
                    if((cardWords[0].equals("6")&&cardWords[1].equals(centerCheck[1]))||cardWords[0].equals("Joker")){
                        cards.add(card);
                    }
                }
                if(cards.size()>1){
                    for(String card:cards){
                        String[] cardStr = card.split(" ");
                        if(cardStr[0].equals("6")) res = card;
                    }
                } else if(cards.size()!=0) res = cards.get(0);
                return res;
            case "card queue3":
                for(String card:botPlayer.getPlayerCards()){
                    String[] cardWords = card.split(" ");
                    if((cardWords[0].equals("7")&&cardWords[1].equals(centerCheck[1]))||cardWords[0].equals("Joker")){
                        cards.add(card);
                    }
                }
                if(cards.size()>1){
                    for(String card:cards){
                        String[] cardStr = card.split(" ");
                        if(cardStr[0].equals("7")) res = card;
                    }
                } else if(cards.size()!=0)res = cards.get(0);
                return res;
            case "4 allowed":
                for(String card:botPlayer.getPlayerCards()){
                    String[] cardWords = card.split(" ");
                    if(cardWords[0].equals(centerCheck[0])||cardWords[1].equals(centerCheck[1])||cardWords[0].equals("4")||cardWords[0].equals("8")||cardWords[0].equals("Joker")){
                        cards.add(card);
                    }
                }
                if(cards.size()==1)res = cards.get(0);
                else if(cards.size()!=0)res = chooseCard(cardsArr, cards);
                return res;
            case "joker":
            case "one more card":
                if(botPlayer.getPlayerCards().size()==1) res = botPlayer.getPlayerCards().get(0);
                else res = chooseCard(cardsArr, botPlayer.getPlayerCards());
                return res;

        }
        return res;
    }
    private String chooseCard(ArrayList<String> cardsDeck, ArrayList<String> botCards){
        ArrayList<Integer> priorityArr = new ArrayList<>();
        //Встановлення пріоритетів на різні карти, які є доступні для ходу
        if(((54-(4*GameCondition.Cards))/2)<cardsDeck.size()){ //Якщо в колоді більше половини стартових карт
             for(String card:botCards){
                 String[] cardStr = card.split(" ");
                 switch (cardStr[0]){
                     case "2":
                         priorityArr.add(3);
                         break;
                     case "3":
                         priorityArr.add(2);
                         break;
                     case "4":
                         priorityArr.add(6);
                         break;
                     case "5":
                     case "6":
                     case "7":
                     case "9":
                          priorityArr.add(1);
                          break;
                     case "8":
                     case "Joker":
                           priorityArr.add(0);
                           break;
                     case "10":
                     case "Jack":
                     case "Queen":
                     case "King":
                         priorityArr.add(4);
                         break;
                     case "Ace":
                         priorityArr.add(5);
                         break;
                 }
             }
        }else if(cardsDeck.size()!=0){ //В колоді менше половини карт
            for(String card:botCards){
                String[] cardStr = card.split(" ");
                switch (cardStr[0]){
                    case "2":
                    case "4":
                        priorityArr.add(5);
                        break;
                    case "3":
                        priorityArr.add(2);
                        break;
                    case "5":
                    case "6":
                    case "7":
                    case "9":
                        priorityArr.add(0);
                        break;
                    case "8":
                    case "Joker":
                        priorityArr.add(1);
                        break;
                    case "10":
                    case "Jack":
                    case "Queen":
                    case "King":
                        priorityArr.add(3);
                        break;
                    case "Ace":
                        priorityArr.add(4);
                        break;
                }
            }
        }else { //В колоді не залишилось карт
            for(String card:botCards){
                String[] cardStr = card.split(" ");
                switch (cardStr[0]){
                    case "2":
                    case "4":
                    case "5":
                    case "6":
                    case "7":
                    case "9":
                        priorityArr.add(0);
                        break;
                    case "3":
                        priorityArr.add(1);
                        break;
                    case "8":
                    case "Joker":
                        priorityArr.add(2);
                        break;
                    case "10":
                    case "Jack":
                    case "Queen":
                    case "King":
                        priorityArr.add(3);
                        break;
                    case "Ace":
                        priorityArr.add(4);
                        break;
                }
            }
        }
        int biggestPriority = priorityArr.get(0);
        //Знайти карту з найбільшим пріоритетом
        for(int i=0; i<priorityArr.size(); i++){
            if(priorityArr.get(i)>biggestPriority) biggestPriority = priorityArr.get(i);
        }
        ArrayList<String> priorityCards = new ArrayList<>();
        //Знайти ще такі карти з найбільшим пріоритетом
        for(int i=0; i<priorityArr.size(); i++){
            if(priorityArr.get(i).equals(biggestPriority)){
                priorityCards.add(botCards.get(i));
            }
        }

        if(priorityCards.size()==0) return priorityCards.get(0);
        else{
            ArrayList<Integer> SuitCounter = new ArrayList<>();
            for(String priorCard:priorityCards){
                int SuitCount = 0;
                String[] priorStr = priorCard.split(" ");
                for(String botCard:botCards){
                    String[] botStr = botCard.split(" ");
                    if(botStr[1].equals(priorStr[1])) SuitCount +=1;
                }
                SuitCounter.add(SuitCount);
            }
            // Знайти карту з найбільшою кількістю таких ж мастей
            int maxSuit = SuitCounter.get(0);
            int index = 0;
            for(int j=0; j<SuitCounter.size(); j++){
                if(SuitCounter.get(j)>maxSuit){
                    maxSuit = SuitCounter.get(j);
                    index = j;
                }
            }
            return priorityCards.get(index);
        }
    }
}
