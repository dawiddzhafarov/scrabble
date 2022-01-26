package com.example.scrabble.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static com.example.scrabble.Menu.*;

public class statisticsController {

    @FXML
    private Button goBackButton;
    @FXML
    private ListView listViewNames;
    @FXML
    private ListView listViewGamesPlayed;
    @FXML
    private ListView listViewGamesWon;
    @FXML
    private ListView listViewPoints;

    private String[][] file;
    private ObservableList<String> obsNames;
    private ObservableList<String> obsGamesPlayed;
    private ObservableList<String> obsGamesWon;
    private ObservableList<String> obsPoints;
    public void initialize() throws IOException {
        readStats();

        listViewNames.setItems(obsNames);
        listViewGamesPlayed.setItems(obsGamesPlayed);
        listViewGamesWon.setItems(obsGamesWon);
        listViewPoints.setItems(obsPoints);

        goBackButton.setOnAction(event -> {
            try {
                root.getChildren().remove(statistics);
                root.getChildren().add(mainMenu);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public  void readStats() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/scrabble/statistics.txt"));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();

        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> gamesPlayed = new ArrayList<>();
        ArrayList<String> gamesWon = new ArrayList<>();
        ArrayList<String> points = new ArrayList<>();

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("src/main/resources/com/example/scrabble/statistics.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[][] file = new String[lines][4];
        String[] parts;

        int counter = 0;
        while (true) {
            assert scanner != null;
            if (!scanner.hasNextLine()) break;
            parts = scanner.nextLine().split(";");
            file[counter][0] = parts[0];
            names.add(parts[0]);
            file[counter][1] = parts[1];
            gamesPlayed.add(parts[1]);
            file[counter][2] = parts[2];
            gamesWon.add(parts[2]);
            file[counter][3] = parts[3];
            points.add(parts[3]);
            counter++;
        }
        this.file = file;
        ObservableList<String> obsNames = FXCollections.observableArrayList(names);
        this.obsNames = obsNames;
        ObservableList<String> obsGamesPlayed = FXCollections.observableArrayList(gamesPlayed);
        this.obsGamesPlayed = obsGamesPlayed;
        ObservableList<String> obsGamesWon = FXCollections.observableArrayList(gamesWon);
        this.obsGamesWon = obsGamesWon;
        ObservableList<String> obsPoints = FXCollections.observableArrayList(points);
        this.obsPoints = obsPoints;
        scanner.close();
    }
}
