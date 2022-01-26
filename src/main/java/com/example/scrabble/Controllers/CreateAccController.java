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

import static com.example.scrabble.Menu.*;
import static com.example.scrabble.Menu.observablePlayerList;

public class CreateAccController {


    @FXML
    private TextField nickTextField;

    @FXML
    private PasswordField passTextField;

    @FXML
    private PasswordField confirmPassTextField;

    @FXML
    private Button submitButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label infoLabel;

    public void initialize() {

        submitButton.setOnAction(event -> {
            String nick = nickTextField.getText();
            String password = passTextField.getText();
            String confirmPass = confirmPassTextField.getText();
            if (!nick.contains(";") && !password.contains(";")){
                if (password.length() >= 8) {
                    if (password.equals(confirmPass)) {
                        createAccount();
                    }else {
                        infoLabel.setText("Passwords are different");
                    }
                }else {
                    infoLabel.setText("Password is too short");
                }
            }else {
                infoLabel.setText("Passwords can't contain ';'");
            }
        });

        exitButton.setOnAction(event -> {

        try {
            root.getChildren().remove(createAcc);
            //options = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/scrabble/options.fxml")));
            root.getChildren().add(mainMenu);
            //observablePlayerList.clear();
        }catch (Exception e){
            e.printStackTrace();
        }

        });

    }

    private void createAccount(){
        BufferedWriter writer, writer2;
        try {
            String password = passTextField.getText();
            int iterations = 1000;
            char[] chars = password.toCharArray();
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            sr.nextBytes(salt);
            PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            String strongPassword = iterations + ":" + toHex(salt) + ":" + toHex(hash);
            writer = new BufferedWriter((new OutputStreamWriter(
                    new FileOutputStream("src/main/resources/com/example/scrabble/database.txt", true), "UTF-8")));
            writer.write(nickTextField.getText()+";"+strongPassword+";"+"0"+";");
            writer.newLine();
            writer.close();
            writer2 = new BufferedWriter((new OutputStreamWriter(
                    new FileOutputStream("src/main/resources/com/example/scrabble/statistics.txt", true), "UTF-8")));
            writer2.write(nickTextField.getText()+";"+ "0" + ";"+"0"+";" +"0");
            //writer2.newLine();
            writer2.close();
            infoLabel.setText("Account Created Successfully");

        } catch (IOException e) {
            infoLabel.setText("Account Creation Failed");
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            infoLabel.setText("Account Creation Failed");
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            infoLabel.setText("Account Creation Failed");
            e.printStackTrace();
        }

    }

    private String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);

        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

}