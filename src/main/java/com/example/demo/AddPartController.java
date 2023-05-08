package com.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {

    @FXML
    private ToggleGroup partsource;
    @FXML
    private RadioButton addPartInHouse;
    @FXML
    private RadioButton addPartOutsourced;
    @FXML
    private Label addPartToggleLabel;
    @FXML public TextField addName;
    @FXML public TextField addInv;
    @FXML public TextField addPrice;
    @FXML public TextField addMax;
    @FXML public TextField addMin;
    @FXML public TextField addMachine;


//    This method switches between outsourced part and in house part
    public void partSourceRadioButtonChanged(){
        if (this.addPartInHouse.isSelected()){
            this.addPartToggleLabel.setText("Machine ID");
        }
        if (this.addPartOutsourced.isSelected()){
            this.addPartToggleLabel.setText("Company Name");
        }
    }

    public void toMain(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    //add part button that adds a new part to the inventory
    //int id, String name, double price, int stock, int min, int max)
    public void addPartButton(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Select \"OK\" to confirm");
        alert.setTitle("Confirm");
        alert.setContentText("Are you sure you want to add the part?");
        if (alert.showAndWait().get()==ButtonType.OK){
            int id = Inventory.getPartIDCounter();
            String n = addName.getText();
            double price = Double.parseDouble(addPrice.getText());
            int s = Integer.parseInt(addInv.getText());
            int min = Integer.parseInt(addMin.getText());
            int max = Integer.parseInt(addMax.getText());


            if (this.addPartInHouse.isSelected()){
                int m = Integer.parseInt(addMachine.getText());
                InHouse p = new InHouse(id,n,price,s,min,max,m);
                Inventory.addPart(p);
            }
            if (this.addPartOutsourced.isSelected()){
                String m = addMachine.getText();
                Outsourced p = new Outsourced(id,n,price,s,min,max,m);
                Inventory.addPart(p);
            }
            toMain(actionEvent);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsource = new ToggleGroup();
        this.addPartInHouse.setToggleGroup(partsource);
        this.addPartOutsourced.setToggleGroup(partsource);

    }

}
