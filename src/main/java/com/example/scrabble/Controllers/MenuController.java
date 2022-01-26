package com.example.scrabble.Controllers;

import com.example.scrabble.HelloApplication;
import com.example.scrabble.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

import static com.example.scrabble.HelloApplication.playerNicknamesArrayList;
import static com.example.scrabble.Menu.*;

public class MenuController {


    @FXML
    private Button newGameButton;

    @FXML
    private Button optionsButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button createAccButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button statisticsButton;

    @FXML
    private Button lastGameButton;

    @FXML
    private Label remiderLabel;


    public void initialize() throws IOException {
        newGameButton.setOnAction(event -> {
            if (!playerNicknamesArrayList.isEmpty()) {
                HelloApplication helloApplication = new HelloApplication();
                clearHistory();
                Stage stage = (Stage) newGameButton.getScene().getWindow();
                try {
                    helloApplication.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                remiderLabel.setVisible(true);
            }
        });

        optionsButton.setOnAction(event -> {
            try {
                root.getChildren().remove(mainMenu);
                remiderLabel.setVisible(false);
                options = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/scrabble/options.fxml")));
                root.getChildren().add(options);
            }catch (Exception e){
                e.printStackTrace();
            }

        });

        createAccButton.setOnAction(event -> {
            try {
                root.getChildren().remove(mainMenu);
                createAcc = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/scrabble/createAcc.fxml")));
                root.getChildren().add(createAcc);

            }catch (Exception e){
                e.printStackTrace();
            }

        });

        loginButton.setOnAction(event -> {
            try {
                root.getChildren().remove(mainMenu);
                login = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/scrabble/login.fxml")));
                root.getChildren().add(login);

            }catch (Exception e){
                e.printStackTrace();
            }

        });

        statisticsButton.setOnAction(event -> {
            try {
                root.getChildren().remove(mainMenu);
                statistics = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/scrabble/statistics.fxml")));
                root.getChildren().add(statistics);

            }catch (Exception e){
                e.printStackTrace();
            }
        });

        exitButton.setOnAction(event -> {
            try {
                Platform.exit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        lastGameButton.setOnAction(event -> {
            try {
                root.getChildren().remove(mainMenu);
                lastGame = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/scrabble/lastGame.fxml")));
                root.getChildren().add(lastGame);

            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }
    public void clearHistory(){
        PrintWriter pw = null;
        try {
            pw = new PrintWriter("src/main/resources/com/example/scrabble/gameHistory.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pw.close();
    }
}
