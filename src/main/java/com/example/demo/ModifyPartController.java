/**
 * Controller class for the modification of Parts
 */
package com.example.demo;
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

public class ModifyPartController implements Initializable {

    @FXML
    private ToggleGroup modifypartsource;
    @FXML
    private RadioButton modifyPartInHouse;
    @FXML
    private RadioButton modifyPartOutsourced;
    @FXML
    private Label modifyPartToggleLabel;
    @FXML public TextField modifyID;
    @FXML public TextField modifyName;
    @FXML public TextField modifyInv;
    @FXML public TextField modifyPrice;
    @FXML public TextField modifyMax;
    @FXML public TextField modifyMin;
    @FXML public TextField modifyMachine;

    /**
     * A blank Part that will be modified
     */
    private static Part modifyme;

    /**
     * copies the selected part into the instance of the part that will be modified on this controller
     * @param part
     */
    public static void addToModify(Part part){
        modifyme = part;
    }

    /**
     * This method switches between outsourced part and in house part
     */
    public void modifySourceRadioButtonChanged(){
        if (this.modifyPartInHouse.isSelected()){
            this.modifyPartToggleLabel.setText("Machine ID");
        }
        if (this.modifyPartOutsourced.isSelected()){
            this.modifyPartToggleLabel.setText("Company Name");
        }
    }

    /**
     * Back to Main scene
     */
    public void toMain(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * fills in text data and selects radio buttons based on info from the selected part
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifypartsource = new ToggleGroup();
        this.modifyPartInHouse.setToggleGroup(modifypartsource);
        this.modifyPartOutsourced.setToggleGroup(modifypartsource);

        modifyID.setText(Integer.toString(modifyme.getId()));
        modifyName.setText(modifyme.getName());
        modifyInv.setText(Integer.toString(modifyme.getStock()));
        modifyPrice.setText(Double.toString(modifyme.getPrice()));
        modifyMax.setText(Integer.toString(modifyme.getMax()));
        modifyMin.setText(Integer.toString(modifyme.getMin()));
        if(modifyme instanceof InHouse){
            modifyMachine.setText(Integer.toString(((InHouse) modifyme).getMachineId()));
            this.modifyPartInHouse.setSelected(true);
        }
        if(modifyme instanceof Outsourced){
            modifyMachine.setText(((Outsourced) modifyme).getCompanyName());
            this.modifyPartOutsourced.setSelected(true);
            this.modifyPartToggleLabel.setText("Company Name");
        }
    }

    /**
     * saves the changes of the selected part into inventory. Includes input validation
     * @param actionEvent
     * @throws IOException
     */
    public void modifyPartSaveButton(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Select \"OK\" to confirm");
        alert.setTitle("Confirm");
        alert.setContentText("Are you sure you want to modify the selected part?");
        if (alert.showAndWait().get()==ButtonType.OK){
            for(Part part: Inventory.getAllParts()) {
                if(part.getId() == modifyme.getId()){
                    Inventory.deletePart(part);

                    int id = modifyme.getId();
                    String n = modifyName.getText();
                    double price = Double.parseDouble(modifyPrice.getText());
                    int s = Integer.parseInt(modifyInv.getText());
                    int min = Integer.parseInt(modifyMin.getText());
                    int max = Integer.parseInt(modifyMax.getText());

                    if (this.modifyPartInHouse.isSelected()){
                        int m = Integer.parseInt(modifyMachine.getText());
                        InHouse p = new InHouse(id,n,price,s,min,max,m);
                        Inventory.addPart(p);
                    }
                    if (this.modifyPartOutsourced.isSelected()){
                        String m = modifyMachine.getText();
                        Outsourced p = new Outsourced(id,n,price,s,min,max,m);
                        Inventory.addPart(p);
                    }

                }
            }
            toMain(actionEvent);
        }
    }

}
