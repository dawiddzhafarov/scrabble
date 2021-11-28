package com.example.scrabble;

import javafx.geometry.Dimension2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Field {

    public boolean isModified;
    private int wordBonus = 0;

    private int letterBonus = 0;

    private Pane layer;

    protected Button button;

    private double x;

    private double y;

    private boolean isPermanent;

    private double letterPoints;


    public Field(double x, double y, String color, Pane layer) {
        this.layer = layer;
        //loadImage(pathStatic);
        Button creatorButton = new Button();
        creatorButton.setPrefSize(28,28);
        creatorButton.setStyle("-fx-background-color:"+color);
        setButton(creatorButton);
        isModified = false;
        setLocation(x, y);
        addToLayer();
    }


    public Pane getLayer() {
        return layer;
    }

    public void setLayer(Pane layer) {
        this.layer = layer;
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }


    public int getWordBonus() {
        return wordBonus;
    }

    public void setWordBonus(int wordBonus) {
        this.wordBonus = wordBonus;
    }

    public int getLetterBonus() {
        return letterBonus;
    }

    public void setLetterBonus(int letterBonus) {
        this.letterBonus = letterBonus;
    }

    public void addToLayer() {
        this.layer.getChildren().add(this.button);
    }
    public void removeFromLayer() {
        this.layer.getChildren().remove(this.button);
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public boolean isPermanent() {
        return isPermanent;
    }

    public void setPermanent(boolean permanent) {
        isPermanent = permanent;
    }

    public double getLetterPoints() {
        return letterPoints;
    }

    public void setLetterPoints(double letterPoints) {
        this.letterPoints = letterPoints;
    }


    public void setLocation(double x, double y) {
        setX(x);
        setY(y);
        this.button.relocate(x, y);

    }

}
