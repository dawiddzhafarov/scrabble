package com.example.scrabble.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static com.example.scrabble.Menu.*;
import static com.example.scrabble.Menu.observablePlayerList;

public class loginController {


    @FXML
    private TextField nickTextField;

    @FXML
    private PasswordField passTextField;

    @FXML
    private Button submitButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label infoLabel;

    public void initialize() {

        submitButton.setOnAction(event -> {
            String nick = nickTextField.getText();
            if (!observablePlayerList.contains(nick)) {
                String password = passTextField.getText();
                if (!nick.contains(";") && !password.contains(";")) {
                    String hash = getFromDatabase(nick, 0, 1);
                    if (hash != "") {
                        try {
                            if (validatePassword(password, hash)) {
                                observablePlayerList.add(nick);
                                infoLabel.setText("nice");
                            }
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (InvalidKeySpecException e) {
                            e.printStackTrace();
                        }
                    }else {
                        infoLabel.setText("The user does not exist");
                    }

                } else {
                    infoLabel.setText("Passwords can't contain ';'");
                }
            }else {
                infoLabel.setText("User already logged");
            }
        });

        exitButton.setOnAction(event -> {

            try {
                root.getChildren().remove(login);
                //options = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/scrabble/options.fxml")));
                root.getChildren().add(mainMenu);
            }catch (Exception e){
                e.printStackTrace();
            }

        });

    }


    private String getFromDatabase(String string, Integer referenceNo,Integer returnNo) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("src/main/resources/com/example/scrabble/database.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] parts;
        String stringToReturn = "";
        while (true) {
            assert scanner != null;
            if (!scanner.hasNextLine()) break;
            parts = scanner.nextLine().split(";");
            if (parts[referenceNo].equals(string)){
                stringToReturn = parts[returnNo];
            }
        }
        scanner.close();
        return stringToReturn;
    }

    private static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);
        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] newHash = skf.generateSecret(spec).getEncoded();
        byte[] databaseHash = fromHex(storedPassword.split(":")[2]);
        if (Arrays.equals(newHash, databaseHash)){
            return true;
        }else {
            return false;
        }

    }

    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
    {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i < bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

}
