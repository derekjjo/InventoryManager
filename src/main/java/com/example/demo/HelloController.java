package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML private Label pizzaLabel;
    @FXML private CheckBox pizzaChecker;

    public void pizzaButtonPushed(){
        String order = "Toppings are: ";
        if (pizzaChecker.isSelected()){
            order +="\npineapple";

        }

        this.pizzaLabel.setText(order);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pizzaLabel.setText("");

    }
}