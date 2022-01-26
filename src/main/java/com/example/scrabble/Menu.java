package com.example.scrabble;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class Menu extends Application {

    static public Pane root;
    static public Pane mainMenu;
    static public Pane options;
    static public Pane createAcc;
    static public Pane login;
    static public Pane results;
    static public Pane statistics;
    static public Pane lastGame;
    static public ObservableList<String> observablePlayerList;

    @Override

    public void start(Stage primaryStage) throws Exception {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/scrabble/mainFrame.fxml")));
        mainMenu = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/scrabble/menu.fxml")));
        root.getChildren().add(mainMenu);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setResizable(false);
        observablePlayerList = FXCollections.observableArrayList();
        observablePlayerList.add("Bot Easy");
        observablePlayerList.add("Bot Medium");
        observablePlayerList.add("Bot Hard");
        observablePlayerList.add("");
    }
}
