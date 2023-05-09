/**
 * Controller for adding new products to products inventory
 */

package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    //int id, String name, double price, int stock, int min, int max)
    @FXML public TableView allPartTable;
    @FXML public TableColumn id;
    @FXML public TableColumn name;
    @FXML public TableColumn price;
    @FXML public TableColumn stock;

    @FXML public TableView productPartTable;
    @FXML public TableColumn prodid;
    @FXML public TableColumn prodname;
    @FXML public TableColumn prodprice;
    @FXML public TableColumn prodstock;
    @FXML public TextField queryPartTF;

    @FXML public TextField addID;
    @FXML public TextField addName;
    @FXML public TextField addInv;
    @FXML public TextField addPrice;
    @FXML public TextField addMax;
    @FXML public TextField addMin;

    /**
     * creates a new product parts list that can be added to a new product
     */
    public ObservableList<Part> addProductPartsList = FXCollections.observableArrayList();

    /**
     * return to main controller
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

    /**
     * searches all parts list by Name for parts that can be added to product
     * @param searchTerm
     * @return
     */
    public ObservableList<Part> searchPartsList(String searchTerm){
        ObservableList<Part> searchedPartList = FXCollections.observableArrayList();

        ObservableList<Part> allPartsSearch = Inventory.getAllParts();

        for(Part part: allPartsSearch) {
            if(part.getName().contains(searchTerm)){
                searchedPartList.add(part);
            }
        }
        return searchedPartList;
    }

    /**
     * searches all parts list by partID for parts that can be added to product
     * @param idNumber
     * @return
     */
    public Part getPartByID(int idNumber){
        ObservableList<Part> allPartsSearch = Inventory.getAllParts();

        for(Part part: allPartsSearch) {
            if(part.getId() == idNumber){
                return part;
            }
        }
        return null;
    }
    /**
     * calls function to search parts list
     * @param actionEvent
     */
    public void getResultsHander(ActionEvent actionEvent){
        String q = queryPartTF.getText();

        ObservableList<Part> partsSearchList = searchPartsList(q);

        if(partsSearchList.size()== 0) {
            try {
                int partSearchID = Integer.parseInt(q);
                Part p = getPartByID(partSearchID);
                if (p != null) {
                    partsSearchList.add(p);
                }
            }
            catch(NumberFormatException e){
                //ignore
            }
        }
        allPartTable.setItems(partsSearchList);
        queryPartTF.setText("");
    }

    /**
     * Adds a new product. Includes input validation.
     * @param actionEvent
     * @throws IOException
     */
    public void addProductButton(ActionEvent actionEvent) throws IOException {
        String n = null;
        double price = 0;
        int min = 0;
        int max = 0;
        int s = 0;
        int id = Inventory.getPartIDCounter()+1;
        Inventory.incrementCounter();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Select \"OK\" to confirm");
        alert.setTitle("Confirm");
        alert.setContentText("Are you sure you want to add the product?");

        if (alert.showAndWait().get()==ButtonType.OK) {
            try {
                n = addName.getText().trim();
                price = Double.parseDouble(addPrice.getText().trim());
                min = Integer.parseInt(addMin.getText().trim());
                max = Integer.parseInt(addMax.getText().trim());
                s = Integer.parseInt(addInv.getText().trim());
            } catch (NumberFormatException e) {
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setHeaderText("Product input type values do not match.");
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
            Product p = new Product(id,n,price,s,min,max);
            p.setProductPartsList(addProductPartsList);
            Inventory.addProduct(p);
            toMain(actionEvent);
        }

    }

    /**
     * adds a selected part to the product's observable parts list
     */
    public void addPartToProduct(){
        Part modifyPart = (Part) allPartTable.getSelectionModel().getSelectedItem();

        addProductPartsList.add(modifyPart);
    }

    /**
     * removes a part from the product's observable parts list
     */
    public void removePartFromProduct(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Select \"OK\" to confirm");
        alert.setTitle("Confirm");
        alert.setContentText("Are you sure you want to remove the part from the product?");

        if (alert.showAndWait().get()==ButtonType.OK) {
            Part modifyPart = (Part) productPartTable.getSelectionModel().getSelectedItem();
            addProductPartsList.remove(modifyPart);
        }


    }

    /**
     * initializes table cell values
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        allPartTable.setItems(Inventory.getAllParts());
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        productPartTable.setItems(addProductPartsList);
        prodid.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodname.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        prodstock.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }
}