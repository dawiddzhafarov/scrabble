package com.example.scrabble;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public class HelloApplication extends Application {

    public ArrayList<Field> fieldArrayList = new ArrayList<>();
    public ArrayList<Letter> letterArrayList = new ArrayList<>();
    public ArrayList<Player> playerArrayList = new ArrayList<>();
    public ArrayList<LetterField> letterFieldArrayList = new ArrayList<>();
    public ArrayList<Field> playerGameFields = new ArrayList<>();
    public Player player;
    private Button giveBackWord;
    private boolean ifFirstTurn;
    @Override
    public void start(Stage stage) throws IOException {
        Pane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/scrabble/hello-view.fxml")));
        stage.setScene(new Scene(root));
        stage.setTitle("Hello!");
        stage.show();
        giveBackWord = new Button("Give bACK THE WORD");
        giveBackWord.relocate(600,600);
        root.getChildren().add(giveBackWord);
        Generator generator = new Generator();
        fieldArrayList = generator.mapGenerator(root);
        letterFieldArrayList = generator.LetterFieldsGenerator(root);
        letterArrayList = generator.LetterGenerator();
        playerArrayList.add(new Player("Aga",generator.PlayerLetterRandom(letterArrayList)));
        playerArrayList.add(new Player("Maks",generator.PlayerLetterRandom(letterArrayList)));
        player = playerArrayList.get(0);
        setLettersOfPlayer(player.playersLetters);
        ifFirstTurn = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> gameLoop()));
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.play();
                //setTimeline(timeline);
            }
        }).start();
    }

    private void gameLoop() {
        //System.out.println("siema");
        if (this.giveBackWord.isPressed()){
            nextTurnGenerator();
        }
        setLetterFieldTouch();

        checkIfLetterInput();

    }

    public static void main(String[] args) {
        launch();
    }


    private void nextTurnGenerator(){
        if (checkIfWordCorrectAfter()) {

            for (Field field : playerGameFields) {
                field.isModified = true;
            }

            playerGameFields.clear();
            ArrayList<Letter> lettersToDelete = new ArrayList<>();

            for (LetterField letterField : letterFieldArrayList) {
                if (letterField.button.isDisable()) {
                    lettersToDelete.add(letterField.letter);
                }
            }

            for (Letter s : lettersToDelete) {
                player.playersLetters.remove(s);
                letterFieldArrayList.remove(s);
            }

            ArrayList<Integer> letterFromSackToDelete = new ArrayList<>();
            Random random = new Random();

            for (int i = 0; i < 7 - player.playersLetters.size(); i++) {
                if (letterArrayList.size() >= 1) {
                    int rand = random.nextInt(letterArrayList.size());
                    player.playersLetters.add(letterArrayList.get(rand));
                    letterFromSackToDelete.add(rand);
                }
            }

            for (Integer integer : letterFromSackToDelete) {
                letterArrayList.remove(integer);
            }

            playerArrayList.remove(player);
            playerArrayList.add(player);
            this.player = playerArrayList.get(0);
            setLettersOfPlayer(player.playersLetters);
            activateLetterFields();

        } else {
            System.out.println("word IS BAD");

            for (Field field : playerGameFields) {
                if (field.isModified == false) {
                    field.button.setText("");
                    field.button.setDisable(false);
                }
            }

            for (LetterField letterField : letterFieldArrayList) {
                letterField.button.setDisable(false);
            }

            playerGameFields.clear();
        }
    }

    private void setLettersOfPlayer(ArrayList<Letter> letters){
        int counter = 0;
        for (Letter letter:letters) {
            letterFieldArrayList.get(counter).button.setText(letter.getLetter());
            letterFieldArrayList.get(counter).setLetterPoints(letter.getValue());
            letterFieldArrayList.get(counter).letter = letter;
            counter++;
        }
    }

    private void checkIfLetterInput(){
        for (LetterField letterField:letterFieldArrayList) {
            if (letterField.isTouched()){
                for (Field field:fieldArrayList) {
                    if (field.button.isPressed()){
                        System.out.println("hello");
                        field.button.setText(letterField.button.getText());
                        field.button.setDisable(true);
                        letterField.button.setDisable(true);
                        letterField.setTouched(false);
                        field.setLetterPoints(letterField.getLetterPoints());
                        playerGameFields.add(field);
                    }
                }
            }
        }
    }

    private void setLetterFieldTouch(){
        for (LetterField letterField:letterFieldArrayList) {
            if (letterField.button.isPressed()){
                letterField.setTouched(true);
                for (LetterField letterToFalse:letterFieldArrayList) {
                    if(letterToFalse != letterField){
                        letterToFalse.setTouched(false);
                    }
                }
            }
        }
    }
    private boolean checkIfWordCorrectAfter(){
        ArrayList<Double> xArray = new ArrayList<>();
        ArrayList<Double> yArray = new ArrayList<>();
        for (Field field:playerGameFields) {
            xArray.add(field.button.getLayoutX());
            yArray.add(field.button.getLayoutY());
        }
        Double maxX = Collections.max(xArray);
        Double minX = Collections.min(xArray);
        Double maxY = Collections.max(yArray);
        Double minY = Collections.min(yArray);
        ArrayList<Field> existingWord = new ArrayList<>();

        boolean toReturnX = false;
        boolean toReturnY = false;
        boolean toReturnVertical = false;
        boolean toReturnHorizontal = false;

        Collections.sort(xArray);
        Collections.sort(yArray);

        // if first turn
        if(ifFirstTurn){
            for (Field field:playerGameFields) {
                Double x = field.button.getLayoutX();
                Double y = field.button.getLayoutY();
                if (x==210 && y==210){
                    ifFirstTurn=false;
                    break;
                }
            }
            if (ifFirstTurn){
                return false;
            }
        } else { //if not first turn, normal procedure
            // if word crosses other word - condition
            int noCrossCounter = 0;
            for (Field field : playerGameFields) {
                double x = field.button.getLayoutX();
                double y = field.button.getLayoutY();
                if ((getByXY(fieldArrayList, x, y - 30, true) == null) && (getByXY(fieldArrayList, x, y + 30, true) == null) && (getByXY(fieldArrayList, x - 30, y, true) == null) && (getByXY(fieldArrayList, x + 30, y, true) == null)) {
                    noCrossCounter++;
                }
            }
            if (noCrossCounter == playerGameFields.size()) {
                return false;
            }
        }
        // Checking for collisions
        boolean collisions = false;
        for(Field field: playerGameFields){
            double x = field.button.getLayoutX();
            double y = field.button.getLayoutY();
            if((getByXY(fieldArrayList,x,y-30,true)) != null && (getByXY(fieldArrayList,x-30,y,true) != null || getByXY(fieldArrayList,x+30,y,true) != null)){
                collisions = true;
            } else if ((getByXY(fieldArrayList,x,y+30,true)) != null && (getByXY(fieldArrayList,x-30,y,true) != null || getByXY(fieldArrayList,x+30,y,true) != null)){
                collisions = true;
            } else if ((getByXY(fieldArrayList,x-30,y,true)) != null && (getByXY(fieldArrayList,x,y-30,true) != null || getByXY(fieldArrayList,x,y+30,true) != null)) {
                collisions = true;
            } else if ((getByXY(fieldArrayList,x+30,y,true)) != null && (getByXY(fieldArrayList,x,y-30,true) != null || getByXY(fieldArrayList,x,y+30,true) != null)) {
                collisions = true;
            }
        }
        if (collisions){
            return false;
        }
        // if 1 letter added
        if(playerGameFields.size()==1){
            if(getByXY(fieldArrayList,minX,minY-30,true) != null || (getByXY(fieldArrayList,minX,minY+30,true)) != null){
                toReturnVertical = true;
            } else if(getByXY(fieldArrayList,minX-30,minY,true) != null || (getByXY(fieldArrayList,minX+30,minY,true)) != null){
                toReturnHorizontal = true;
            }
        } else {
            for (int i = 0; i < xArray.size() - 1; i++) {
                if (!xArray.get(i).equals(xArray.get(i + 1))) {
                    toReturnVertical = false;
                    break;
                }
                toReturnVertical = true;
            }
            for (int i = 0; i < yArray.size() - 1; i++) {
                if (!yArray.get(i).equals(yArray.get(i + 1))) {
                    toReturnHorizontal = false;
                    break;
                }
                toReturnHorizontal = true;
            }
            if (!toReturnHorizontal && !toReturnVertical) {
                System.out.println("Ani pion ani poziom");
            }
        }
        // horizontal word
        if (toReturnHorizontal) {
            toReturnX = true;
            for(double i = minX-30; i>=0; i -= 30){
                Field field = getByXY(fieldArrayList,i,minY,true);
                if (field == null){
                    break;
                } else {
                    existingWord.add(0,field);
                }
            }
            existingWord.add(getByXYPlayerGame(playerGameFields,minX,minY));
            for(double i = minX+30; i>=0; i += 30){
                Field field = getByXY(fieldArrayList,i,minY,true);
                if (field == null){
                    Field gameField = getByXYPlayerGame(playerGameFields,i,minY);
                    if (gameField == null) {
                        break;
                    } else {
                        existingWord.add(gameField);
                    }
                } else {
                    existingWord.add(field);
                }
            }
        }
        // vertical word
        if (toReturnVertical) {
            toReturnY = true;
            for(double i = minY-30; i>=0; i -= 30){
                Field field = getByXY(fieldArrayList,maxX,i,true);
                if (field == null){
                    break;
                } else {
                    existingWord.add(0,field);
                }
            }
            existingWord.add(getByXYPlayerGame(playerGameFields,minX,minY));
            for(double i = minY+30; i<=450; i += 30){
                Field field = getByXY(fieldArrayList,minX,i,true);
                if (field == null){
                    Field gameField = getByXYPlayerGame(playerGameFields,minX,i);
                    if (gameField == null) {
                        break;
                    } else {
                        existingWord.add(gameField);
                    }
                } else {
                    existingWord.add(field);
                }
            }
        }

        StringBuilder word = new StringBuilder();
        for(Field field: existingWord) {
            word.append(field.button.getText());
        }
        if ((checkWord(word.toString()) == false) || (!toReturnY && !toReturnX)){
            return false;
        }
        System.out.println(word);
        return true;
    }

    public boolean checkWord(String word){
        boolean isWord = false;
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/com/example/scrabble/dictionary.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals(word)){
                    isWord = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isWord;
    }
    public void activateLetterFields(){
        for (LetterField letterField:letterFieldArrayList) { // pogubiłem sie w letterfield/field/gamefield xD ale działa
            letterField.button.setDisable(false);
        }
    }
    public Field getByXY(ArrayList<Field> list, double x, double y, boolean ismodified){
        for(Field field : list){
            if(field.button.getLayoutY()==y && field.button.getLayoutX()==x && field.isModified == ismodified){
                return field;
            }
        }
        return null;
    }
    public Field getByXYPlayerGame(ArrayList<Field> list, double x, double y){
        for(Field field : list){
            if(field.button.getLayoutY()==y && field.button.getLayoutX()==x){
                return field;
            }
        }
        return null;
    }
}