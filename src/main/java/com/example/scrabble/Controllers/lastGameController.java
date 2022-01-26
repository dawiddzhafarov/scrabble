package com.example.scrabble.Controllers;

import com.example.scrabble.Field;
import com.example.scrabble.Generator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import static com.example.scrabble.Menu.*;

public class lastGameController {

    ArrayList<ArrayList<String>> file = new ArrayList<>();
    private int moveCounter = 0;
    public ArrayList<Field> fieldArrayList = new ArrayList<>();
    private int fileLength;

    @FXML
    private Pane mainPane;
    @FXML
    private Button nextMoveButton;
    @FXML
    private Label playerNameLabel;
    @FXML
    private Label pointsLabel;
    @FXML
    private Button goBackButton;
    @FXML
    private Button previousMoveButton;
    @FXML
    private Label noGameInfo;

    public void initialize() throws IOException {
        noGameInfo.setVisible(false);
        readFile();
        System.out.println(fileLength);
        if(this.fileLength==0){
            noGameInfo.setVisible(true);
        } else {
            Generator generator = new Generator();
            fieldArrayList = generator.mapGenerator(mainPane);
            previousMoveButton.setDisable(true);

            nextMoveButton.setOnAction(event -> {
                nextMove();
            });

            previousMoveButton.setOnAction(event -> {
                previousMove();
            });

        }
        goBackButton.setOnAction(event -> {
            try {
                root.getChildren().remove(lastGame);
                root.getChildren().add(mainMenu);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void readFile(){
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("src/main/resources/com/example/scrabble/gameHistory.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<ArrayList<String>> file = new ArrayList<>();

        String[] parts = new String[0];

        while (true) {
            assert scanner != null;
            if (!scanner.hasNextLine()) break;
            parts = scanner.nextLine().split(";");
            ArrayList<String> line = new ArrayList<>();
            for(int i=0;i<parts.length;i++){
                line.add(parts[i]);
            }
            file.add(line);
        }
        this.file = file;
        this.fileLength = file.size();
    }

    public void nextMove(){
        previousMoveButton.setDisable(false);
        ArrayList<String> line = file.get(moveCounter);
        String name = line.get(0);
        playerNameLabel.setText(name);
        String points = line.get(line.size()-1);
        pointsLabel.setText(points);
        ArrayList<String> currentCoord = new ArrayList<String>();

        for(int i = 1; i < line.size()-1; i++){
            String data = line.get(i);
            if(data.length()>1){
                currentCoord.add(data);
            } else {
                currentCoord.add(data);
            }
            if(currentCoord.size()==3){
                for(Field field : fieldArrayList){
                    if((field.getX()==Double.parseDouble(currentCoord.get(0))) && (field.getY()==Double.parseDouble(currentCoord.get(1)))) {
                        field.getButton().setText(currentCoord.get(2));
                    }
                }
                currentCoord.clear();
            }
        }

        moveCounter++;

        if(moveCounter == fileLength){
            nextMoveButton.setDisable(true);
        }
    }

    public void previousMove(){
        nextMoveButton.setDisable(false);
        moveCounter--;

        if(moveCounter==0){
            ArrayList<String> firstLine = file.get(moveCounter);
            playerNameLabel.setText("");
            pointsLabel.setText("");
            previousMoveButton.setDisable(true);
            ArrayList<String> currentCoord = new ArrayList<String>();

            for (int i = 1; i < firstLine.size() - 1; i++) {
                String data = firstLine.get(i);
                if (data.length() > 1) {
                    currentCoord.add(data);
                } else {
                    currentCoord.add(data);
                }
                if (currentCoord.size() == 3) {
                    for (Field field : fieldArrayList) {
                        if ((field.getX() == Double.parseDouble(currentCoord.get(0))) && (field.getY() == Double.parseDouble(currentCoord.get(1)))) {
                            field.getButton().setText("");
                        }
                    }
                    currentCoord.clear();
                }
            }
        } else {
            ArrayList<String> line = file.get(moveCounter - 1);
            String name = line.get(0);
            playerNameLabel.setText(name);
            String points = line.get(line.size() - 1);
            pointsLabel.setText(points);
            ArrayList<String> lineToDelete = file.get(moveCounter); //wczytujemy dane poprzedniego gracza ale usuwamy ruch tego po nim
            ArrayList<String> currentCoord = new ArrayList<String>();

            for (int i = 1; i < lineToDelete.size() - 1; i++) {
                String data = lineToDelete.get(i);
                if (data.length() > 1) {
                    currentCoord.add(data);
                } else {
                    currentCoord.add(data);
                }
                if (currentCoord.size() == 3) {
                    for (Field field : fieldArrayList) {
                        if ((field.getX() == Double.parseDouble(currentCoord.get(0))) && (field.getY() == Double.parseDouble(currentCoord.get(1)))) {
                            field.getButton().setText("");
                        }
                    }
                    currentCoord.clear();
                }
            }
        }
    }
}
