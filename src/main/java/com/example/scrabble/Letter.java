package com.example.scrabble;

public class Letter {
    private Integer value;
    private String letter;

    public Letter(String letter, Integer value){
        setLetter(letter);
        setValue(value);
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
