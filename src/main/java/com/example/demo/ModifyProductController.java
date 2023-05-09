/**
 * Controller class for the modification of Products
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

    /**
     * blank product to be modified
     */
    private static Product modifyMeProduct;

    /**
     * copies product selected in the Main product table into the blank product instantiated on this controller
     * @param product
     */
    public static void addToModify(Product product){
        modifyMeProduct = product;
    }

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
     * searches all parts inventory for parts that can be added to the product being modified
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
     * searches parts by ID
     * @param idNumber
     * @return
     */
    public Part getPartByID(int idNumber) {
        ObservableList<Part> allPartsSearch = Inventory.getAllParts();

        for (Part part : allPartsSearch) {
            if (part.getId() == idNumber) {
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
     * Saves the modifications to the product into the product inventory
     * @param actionEvent
     * @throws IOException
     */
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
                    product.setProductPartsList(modifyMeProduct.getAllAssociatedParts());

                }
            }
            toMain(actionEvent);
        }



    }

    /**
     * adds selected part to the product's associated parts list
     */
    public void addPartToProduct(){
        Part modifyPart = (Part) allPartTable.getSelectionModel().getSelectedItem();

        modifyMeProduct.addAssociatedPart(modifyPart);
    }

    /**
     * removes selected part from the product's associated parts list
     */
    public void removePartFromProduct() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Select \"OK\" to confirm");
        alert.setTitle("Confirm");
        alert.setContentText("Are you sure you want to remove the part from the product?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            Part modifyPart = (Part) productPartTable.getSelectionModel().getSelectedItem();
            modifyMeProduct.deleteAssociatedPart(modifyPart);
        }

    }

    /**
     * initializes parts tables and fills in text fields with data from the Product that is to be modified

     */

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

        productPartTable.setItems(modifyMeProduct.getAllAssociatedParts());
        prodid.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodname.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        prodstock.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }
}