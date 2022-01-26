package com.example.scrabble;

import javafx.scene.layout.Pane;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class Generator {

    public int getTripleWord() {
        return tripleWord;
    }

    public int getDoubleWord() {
        return doubleWord;
    }

    public int getDoubleLetter() {
        return doubleLetter;
    }

    public int getTripleLetter() {
        return tripleLetter;
    }

    public int getMiddle() {
        return middle;
    }

    public int getCasual() {
        return casual;
    }

    public final int tripleWord = new Color(233,30,99).getRGB();
    public final int doubleWord = new Color(0,255,255).getRGB();
    final int doubleLetter = new Color(244,67,54).getRGB();
    final int tripleLetter = new Color(0,128,0).getRGB();
    final int middle = new Color(0,0,128).getRGB();
    final int casual = new Color(192,192,192).getRGB();


    public ArrayList<Field> mapGenerator(Pane layer){
        int x = 0;
        int y = 0;
        ArrayList<Field> buttonArrayList = new ArrayList<Field>();
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("src/main/resources/com/example/scrabble/pixil-frame-0.png"));
        } catch (IOException e) {
        }
        for (int h = 0; h<img.getHeight(); h++) {
            for (int w = 0; w < img.getWidth(); w++) {
                int currentPixel = img.getRGB(h, w);

                if (currentPixel == tripleWord){
                    buttonArrayList.add(new Field3xWordBonus(h*30,w*30,"#E91E63",layer));
                }
                if (currentPixel == doubleWord){
                    buttonArrayList.add(new Field2xWordBonus(h*30,w*30,"#00FFFF",layer));
                }
                if (currentPixel == doubleLetter){
                    buttonArrayList.add(new Field2xLetterBonus(h*30,w*30,"#F44336",layer));
                }
                if (currentPixel == tripleLetter){
                    buttonArrayList.add(new Field3xLetterBonus(h*30,w*30,"#008000",layer));
                }
                if (currentPixel == middle){
                    buttonArrayList.add(new Field(h*30,w*30,"#000080",layer));
                }
                if (currentPixel == casual){
                    buttonArrayList.add(new Field(h*30,w*30,"#c0c0c0",layer));
                }

            }
        }
        return buttonArrayList;
    }

    public ArrayList<LetterField> LetterFieldsGenerator(Pane layer){
        ArrayList<LetterField> buttonArrayList = new ArrayList<LetterField>();
        int x = 0;
        int y = 475;
        for (int i = 0; i < 7; i++) {
            x+=45;
            buttonArrayList.add(new LetterField(x,y,"#FFFF00",layer,"A"));
        }
        return buttonArrayList;

    }

    public ArrayList<Letter> LetterGenerator(){
        ArrayList<Letter> letterArrayList = new ArrayList<Letter>();
        for (int i = 0; i < 12; i++) {
            letterArrayList.add(new Letter("E",1));
        }
        for (int i = 0; i < 9; i++) {
            letterArrayList.add(new Letter("A",1));
            letterArrayList.add(new Letter("I",1));
        }
        for (int i = 0; i < 8; i++) {
            letterArrayList.add(new Letter("O",1));
        }
        for (int i = 0; i < 6; i++) {
            letterArrayList.add(new Letter("N",1));
            letterArrayList.add(new Letter("R",1));
            letterArrayList.add(new Letter("T",1));
        }

        for (int i = 0; i < 4; i++) {
            letterArrayList.add(new Letter("L",1));
            letterArrayList.add(new Letter("S",1));
            letterArrayList.add(new Letter("U",1));
            letterArrayList.add(new Letter("D",2));
        }
        for (int i = 0; i < 3; i++) {
            letterArrayList.add(new Letter("G",2));
        }
        for (int i = 0; i < 2; i++) {
            letterArrayList.add(new Letter("B",3));
            letterArrayList.add(new Letter("C",3));
            letterArrayList.add(new Letter("M",3));
            letterArrayList.add(new Letter("P",3));
            letterArrayList.add(new Letter("F",4));
           letterArrayList.add(new Letter("H",4));
            letterArrayList.add(new Letter("V",4));
            letterArrayList.add(new Letter("W",4));
           letterArrayList.add(new Letter("Y",4));
        }
        for (int i = 0; i < 1; i++) {
            letterArrayList.add(new Letter("K",5));
            letterArrayList.add(new Letter("J",8));
            letterArrayList.add(new Letter("X",8));
            letterArrayList.add(new Letter("Q",10));
           letterArrayList.add(new Letter("Z",10));
        }
        Collections.shuffle(letterArrayList);
        return letterArrayList;
    }
    public ArrayList<Letter> PlayerLetterRandom(ArrayList<Letter> letterArrayList){
        ArrayList<Letter> playerLetterArrayList = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < 7; i++){
            int rand = random.nextInt(letterArrayList.size());
            playerLetterArrayList.add(letterArrayList.get(rand));
            letterArrayList.remove(rand);
        }
        return playerLetterArrayList;
    }


}
