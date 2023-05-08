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

    public ObservableList<Part> addProductPartsList = FXCollections.observableArrayList();
    public void toMain(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

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

    public Part getPartByID(int idNumber){
        ObservableList<Part> allPartsSearch = Inventory.getAllParts();

        for(Part part: allPartsSearch) {
            if(part.getId() == idNumber){
                return part;
            }
        }
        return null;
    }
    //calls function to search parts list
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

    public void addProductButton(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Select \"OK\" to confirm");
        alert.setTitle("Confirm");
        alert.setContentText("Are you sure you want to add the product?");
        alert.showAndWait();

        int id = Inventory.getPartIDCounter()+1;
        Inventory.incrementCounter();
        String n = addName.getText();
        double price = Double.parseDouble(addPrice.getText());
        int s = Integer.parseInt(addInv.getText());
        int min = Integer.parseInt(addMin.getText());
        int max = Integer.parseInt(addMax.getText());

        Product p = new Product(id,n,price,s,min,max);
        p.setProductPartsList(addProductPartsList);
        Inventory.addProduct(p);

        toMain(actionEvent);
    }

    public void addPartToProduct(){
        Part modifyPart = (Part) allPartTable.getSelectionModel().getSelectedItem();

        addProductPartsList.add(modifyPart);
    }

    public void removePartFromProduct(){
        Part modifyPart = (Part) productPartTable.getSelectionModel().getSelectedItem();
        addProductPartsList.remove(modifyPart);

    }

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