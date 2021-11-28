package com.example.scrabble;

import javafx.scene.layout.Pane;

public class LetterField extends Field{
    private boolean isTouched;

    public Letter letter;

    public boolean isTouched() {
        return isTouched;
    }

    public void setTouched(boolean touched) {
        isTouched = touched;
    }

    public LetterField(double x, double y, String color, Pane layer, String letter) {
        super(x, y, color, layer);
        this.button.setText(letter);
        this.button.setPrefSize(40,40);
    }
}
