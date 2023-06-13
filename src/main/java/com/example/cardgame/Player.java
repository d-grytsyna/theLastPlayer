package com.example.cardgame;

import java.util.ArrayList;

public class Player {
    private ArrayList<String> playerCards;
    static int points = 0;
    public Player(ArrayList<String> playerCards) {
        this.playerCards = playerCards;
    }
    public ArrayList<String> getPlayerCards(){
        return playerCards;
    }
    public void setPlayerCards(ArrayList<String> cards){
        playerCards = cards;
    }
}
