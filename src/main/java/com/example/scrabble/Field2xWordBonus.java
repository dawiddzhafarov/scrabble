package com.example.scrabble;

import javafx.scene.layout.Pane;

public class Field2xWordBonus extends Field{

    public Field2xWordBonus(double x, double y, String color, Pane layer) {
        super(x, y, color, layer);
        setWordBonus(2);
    }
}
