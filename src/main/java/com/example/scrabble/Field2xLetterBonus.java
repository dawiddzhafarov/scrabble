package com.example.scrabble;

import javafx.scene.layout.Pane;

public class Field2xLetterBonus extends Field{

    public Field2xLetterBonus(double x, double y, String color, Pane layer) {
        super(x, y, color, layer);
        setLetterBonus(2);
    }
}
