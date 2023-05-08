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

public class ModifyProductController implements Initializable {

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

    @FXML public TextField modifyID;
    @FXML public TextField modifyName;
    @FXML public TextField modifyInv;
    @FXML public TextField modifyPrice;
    @FXML public TextField modifyMax;
    @FXML public TextField modifyMin;
    private static Product modifyMeProduct;

    public static void addToModify(Product product){
        modifyMeProduct = product;
    }

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
    public void modifyProductSaveButton(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Select \"OK\" to confirm");
        alert.setTitle("Confirm");
        alert.setContentText("Are you sure you want to modify the selected product?");
        if (alert.showAndWait().get()==ButtonType.OK){
            for(Product product: Inventory.getAllProducts()) {
                if(product.getId() == modifyMeProduct.getId()){

                    product.setName(modifyName.getText());
                    product.setStock(Integer.parseInt(modifyInv.getText()));
                    product.setPrice(Double.parseDouble(modifyPrice.getText()));
                    product.setMax(Integer.parseInt(modifyMax.getText()));
                    product.setMin(Integer.parseInt(modifyMin.getText()));
                    product.setProductPartsList(modifyMeProduct.getProductPartsList());

                }
            }
            toMain(actionEvent);
        }



    }

    public void addPartToProduct(){
        Part modifyPart = (Part) allPartTable.getSelectionModel().getSelectedItem();

        modifyMeProduct.addPart(modifyPart);
    }

    public void removePartFromProduct(){
        Part modifyPart = (Part) productPartTable.getSelectionModel().getSelectedItem();
        modifyMeProduct.removePart(modifyPart);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        allPartTable.setItems(Inventory.getAllParts());
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        modifyID.setText(Integer.toString(modifyMeProduct.getId()));
        modifyName.setText(modifyMeProduct.getName());
        modifyInv.setText(Integer.toString(modifyMeProduct.getStock()));
        modifyPrice.setText(Double.toString(modifyMeProduct.getPrice()));
        modifyMax.setText(Integer.toString(modifyMeProduct.getMax()));
        modifyMin.setText(Integer.toString(modifyMeProduct.getMin()));

        productPartTable.setItems(modifyMeProduct.getProductPartsList());
        prodid.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodname.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        prodstock.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }
}