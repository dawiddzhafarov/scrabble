package com.example.scrabble;

import javafx.scene.layout.Pane;

public class Field3xLetterBonus extends Field{

    public Field3xLetterBonus(double x, double y, String color, Pane layer) {
        super(x, y, color, layer);
        setLetterBonus(3);
    }
}
