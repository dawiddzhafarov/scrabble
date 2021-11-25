package com.example.scrabble;

import java.util.ArrayList;

public class Player {

    private String name;
    public ArrayList<Letter> playersLetters = new ArrayList<>();
    private Boolean isInGame;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getInGame() {
        return isInGame;
    }

    public void setInGame(Boolean inGame) {
        isInGame = inGame;
    }

    public Player(String name, ArrayList<Letter> letters){
        setName(name);
        playersLetters = letters;
    }
}
