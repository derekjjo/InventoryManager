/**
 * @author Derek Johnson
 * Class adds new parts to the parts table
 */

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


//    This method switches text label between outsourced part and in house part
    public void partSourceRadioButtonChanged(){
        if (this.addPartInHouse.isSelected()){
            this.addPartToggleLabel.setText("Machine ID");
        }
        if (this.addPartOutsourced.isSelected()){
            this.addPartToggleLabel.setText("Company Name");
        }
    }

    /**return to main scene
     *
     * @param event
     * @throws IOException
     */
    public void toMain(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    //add part button that adds a new part to the inventory
    //int id, String name, double price, int stock, int min, int max)

    /**
     * RUNTIME ERROR: When created the add part button I had an error when using a Try/catch block to try to validate the Max/Min/Inventory values. To fix this I had to swtich to using a regular logical if statement to validate the data.
     *
     *
     * Method add part button that adds a new part to the inventory
     *
     * @param actionEvent
     * @throws IOException
     */
    public void addPartButton(ActionEvent actionEvent) throws IOException {
        String n = null;
        double price = 0;
        int min = 0;
        int max = 0;
        int s = 0;
        int id = Inventory.getPartIDCounter();



        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Select \"OK\" to confirm");
        alert.setTitle("Confirm");
        alert.setContentText("Are you sure you want to add the part?");

        if (alert.showAndWait().get()==ButtonType.OK) {


            try {
                n = addName.getText().trim();
                price = Double.parseDouble(addPrice.getText().trim());
                min = Integer.parseInt(addMin.getText().trim());
                max = Integer.parseInt(addMax.getText().trim());
                s = Integer.parseInt(addInv.getText().trim());
            } catch (NumberFormatException e) {
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setHeaderText("Part input type values do not match.");
                error.setTitle("Input Value Error");
                error.setContentText("Please make sure to use appropriate input values!");
                error.showAndWait();
                return;
            }

            if (min > max || s > max || s < min) {
                Alert minMax = new Alert(Alert.AlertType.WARNING);
                minMax.setHeaderText("MIN, MAX And/Or inventory Values incorrect");
                minMax.setTitle("Input Value Error");
                minMax.setContentText("Please make sure 'Min' is less than 'Max' and 'Inv' is BETWEEN Min and Max values.");
                minMax.showAndWait();
                return;

            }
            if (this.addPartInHouse.isSelected()) {
                int m = 0;
                try {
                    m = Integer.parseInt(addMachine.getText().trim());
                }
                catch (NumberFormatException e){
                    Alert error = new Alert(Alert.AlertType.WARNING);
                    error.setHeaderText("Machine ID must be and Integer");
                    error.setTitle("Input Value Error");
                    error.setContentText("Please make sure to use appropriate input values!");
                    error.showAndWait();
                    return;

                }
                InHouse p = new InHouse(id, n, price, s, min, max, m);
                Inventory.addPart(p);
            }
            if (this.addPartOutsourced.isSelected()) {
                String m = addMachine.getText().trim();
                Outsourced p = new Outsourced(id, n, price, s, min, max, m);
                Inventory.addPart(p);
            }
            toMain(actionEvent);


        }
    }

    /**
     * initialized radio button toggle group for inhouse/outsourced parts
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsource = new ToggleGroup();
        this.addPartInHouse.setToggleGroup(partsource);
        this.addPartOutsourced.setToggleGroup(partsource);

    }

}
