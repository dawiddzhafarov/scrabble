package com.example.scrabble.Controllers;

import com.example.scrabble.Menu;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import com.example.scrabble.HelloApplication;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;

public class resultsController {
    @FXML
    private Label number4;
    @FXML
    private Label firstPlayer;

    @FXML
    private Label firstPoints;

    @FXML
    private Label fourthPlayer;

    @FXML
    private Label fourthPoints;

    @FXML
    private Label secondPlayer;

    @FXML
    private Label secondPoints;

    @FXML
    private Label thirdPlayer;

    @FXML
    private Label thirdPoints;

    @FXML
    private Button goBackButton;

    String winner;
    int winner_Points;
    String second;
    int second_Points;
    String third;
    int third_Points;
    String fourth;
    int fourth_Points;

    public void initialize() throws IOException {

        HashMap<String,Integer> results = new HashMap<>();
        results.put(HelloApplication.player1.getText(),Integer.parseInt(HelloApplication.player1Points.getText()));
        results.put(HelloApplication.player2.getText(),Integer.parseInt(HelloApplication.player2Points.getText()));

        if (!HelloApplication.player3.getText().isEmpty()) {
            results.put(HelloApplication.player3.getText(),Integer.parseInt(HelloApplication.player3Points.getText()));
        }
        if (!HelloApplication.player4.getText().isEmpty()) {
            results.put(HelloApplication.player4.getText(),Integer.parseInt(HelloApplication.player4Points.getText()));
        }
        String winner = new String();
        int winnerPlayerPoints=0;
        String second = new String();
        int secondPlayerPoints=0;
        String third = new String();
        int thirdPlayerPoints=0;
        String fourth = new String();
        int fourthPlayerPoints=0;
        ArrayList<Integer> scores = new ArrayList<>(results.values());
        Collections.sort(scores);
        for (String key : results.keySet()){
            if(results.get(key) == scores.get(scores.size()-1)){
                winner = key;
                winnerPlayerPoints = scores.get(scores.size()-1);
            }
        }
        for (String key : results.keySet()){
            if(results.get(key) == scores.get(scores.size()-2)){
                second = key;
                secondPlayerPoints = scores.get(scores.size()-2);
            }
        }
        if(scores.size() == 3) {
            for (String key : results.keySet()) {
                if (results.get(key) == scores.get(scores.size() - 3)) {
                    third = key;
                    thirdPlayerPoints = scores.get(scores.size()-3);
                }
            }
        }
        if(scores.size()==4) {
            for (String key : results.keySet()) {
                if (results.get(key) == scores.get(scores.size() - 3)) {
                    third = key;
                    thirdPlayerPoints = scores.get(scores.size()-3);
                }
            }
            for (String key : results.keySet()) {
                if (results.get(key) == scores.get(scores.size() - 4)) {
                    fourth = key;
                    fourthPlayerPoints = scores.get(scores.size()-4);
                }
            }
        }
        this.winner = winner;
        this.winner_Points = winnerPlayerPoints;
        this.second = second;
        this.second_Points = secondPlayerPoints;
        if(!third.isEmpty()){
            this.third = third;
            this.third_Points = thirdPlayerPoints;
        }
        if(!fourth.isEmpty()){
            this.fourth = fourth;
            this.fourth_Points = fourthPlayerPoints;
        }

        firstPlayer.setText(winner);
        firstPoints.setText(String.valueOf(winnerPlayerPoints) + " pkt");
        secondPlayer.setText(second);
        secondPoints.setText(String.valueOf(secondPlayerPoints) + " pkt");
        if(scores.size()==2){
            thirdPoints.setText("");
            fourthPoints.setText("");
            number4.setText("");
        } else if (scores.size()==3){
            thirdPlayer.setText(third);
            thirdPoints.setText(String.valueOf(thirdPlayerPoints) + " pkt");
            fourthPoints.setText("");
            number4.setText("");
        } else {
            fourthPlayer.setText(fourth);
            fourthPoints.setText(String.valueOf(fourthPlayerPoints) + " pkt");
        }
        updateStatistics();

        goBackButton.setOnAction(event -> {
            Stage stage = (Stage) goBackButton.getScene().getWindow();
            Menu menu = new Menu();
            try {
                menu.start(stage);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }

    private void updateStatistics() throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/scrabble/statistics.txt"));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();


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
            System.out.println(parts[1]);
            file[counter][0] = parts[0];
            file[counter][1] = parts[1];
            file[counter][2] = parts[2];
            file[counter][3] = parts[3];
            counter++;
        }
        scanner.close();
        for(int i = 0; i < lines; i++){
            if((file[i][0].equals(winner))){
                int games = Integer.parseInt(file[i][1]);
                games++;
                file[i][1] = Integer.toString(games);
                int wins = Integer.parseInt(file[i][2]);
                wins++;
                file[i][2] = Integer.toString(wins);
                int points = Integer.parseInt(file[i][3]);
                points += winner_Points;
                file[i][3] = Integer.toString(points);
            } else if (file[i][0].equals(second)){
                int games = Integer.parseInt(file[i][1]);
                games++;
                file[i][1] = Integer.toString(games);
                int points = Integer.parseInt(file[i][3]);
                points += second_Points;
                file[i][3] = Integer.toString(points);
            } else if ((file[i][0].equals(third))){
                int games = Integer.parseInt(file[i][1]);
                games++;
                file[i][1] = Integer.toString(games);
                int points = Integer.parseInt(file[i][3]);
                points += third_Points;
                file[i][3] = Integer.toString(points);
            }
            else if (file[i][0].equals(fourth)){
                int games = Integer.parseInt(file[i][1]);
                games++;
                file[i][1] = Integer.toString(games);
                int points = Integer.parseInt(file[i][3]);
                points += fourth_Points;
                file[i][3] = Integer.toString(points);
            }
        }
        BufferedWriter writer;
        try {
            writer = new BufferedWriter((new OutputStreamWriter(
                    new FileOutputStream("src/main/resources/com/example/scrabble/statistics.txt", false), "UTF-8")));
            for(int i=0; i< lines; i++){
                writer.write(file[i][0] + ";" + file[i][1] + ";" + file[i][2] + ";" + file[i][3]);
                writer.newLine();
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
