/**
 *Class for the main page controller corresponding to MainMenu.fxml
 *
 */

package com.example.demo;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML public TableView partTable;
    @FXML public AnchorPane scenePane;

    @FXML public TableColumn id;
    @FXML public TableColumn name;
    @FXML public TableColumn price;
    @FXML public TableColumn stock;
    @FXML public TableColumn min;
    @FXML public TableColumn max;
    @FXML public TableView productTable;
    @FXML public TableColumn productID;
    @FXML public TableColumn productName;
    @FXML public TableColumn productPrice;
    @FXML public TableColumn productStock;
    @FXML public TextField queryTF;
    @FXML public TextField queryProduct;
    public Part modifyPart;

    public Product modifyProduct;

    /**
     * Changes the scene to a blank Add part form where users can add new parts
     * @param event
     * @throws IOException
     */
    public void goToAddPartButtonPushed(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        // This line gets the stage info
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(addPartScene);
        window.show();

    }

    /**
     * Changes the scene to a blank Add Product form where users can add new Products
     * @param event
     * @throws IOException
     */
    public void goToAddProductButtonPushed(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        // This line gets the stage info
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(addPartScene);
        window.show();

    }

    /**
     * Changes the scene to a Modify part form where the highlighted Part is copied and can be modified
     * @param event
     * @throws IOException
     */

    public void goToModifyPartButtonPushed(ActionEvent event) throws IOException {
        modifyPart = (Part) partTable.getSelectionModel().getSelectedItem();

        ModifyPartController.addToModify(modifyPart);

        Parent addPartParent = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        // This line gets the stage info
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(addPartScene);
        window.show();
    }

    /**
     * Changes the scene to a Modify Product form where the highlighted Product is copied and can be modified
     * @param event
     * @throws IOException
     */
    public void goToModifyProductButtonPushed(ActionEvent event) throws IOException {
        modifyProduct = (Product) productTable.getSelectionModel().getSelectedItem();

        ModifyProductController.addToModify(modifyProduct);

        Parent addPartParent = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        // This line gets the stage info
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(addPartScene);
        window.show();


    }

    /**
     * Calls inventory methods that search the parts list for a part that contains a search term entered by the user.
     * The parts table is changed to include only relevant parts
     * @param actionEvent
     */
    public void getResultsHander(ActionEvent actionEvent){
        String q = queryTF.getText();

        ObservableList<Part> partsSearchList = Inventory.lookupPart(q);

        if(partsSearchList.size()== 0) {
            try {
                int partSearchID = Integer.parseInt(q);
                Part p = Inventory.lookupPart(partSearchID);
                if (p != null) {
                    partsSearchList.add(p);
                }
            }
            catch(NumberFormatException e){
                //ignore
            }
        }

        partTable.setItems(partsSearchList);
        queryTF.setText("");
    }

    /**
     * Calls inventory methods that search the Product list for a Product that contains a search term entered by the user.
     * The parts table is changed to include only relevant Products
     * @param actionEvent
     */

    public void getResultsProductHander(ActionEvent actionEvent){
        String q = queryProduct.getText();

        ObservableList<Product> productSearchList = Inventory.lookupProduct(q);

        if(productSearchList.size()== 0) {
            try {
                int partSearchID = Integer.parseInt(q);
                Product p = Inventory.lookupProduct(partSearchID);
                if (p != null) {
                    productSearchList.add(p);
                }
            }
            catch(NumberFormatException e){
                //ignore
            }
        }

        productTable.setItems(productSearchList);
        queryProduct.setText("");
    }

    /**
     * Finds and deletes a selected Part from the parts table
     */
    public void deleteButtonPushed(){

        ObservableList<Part> selectedRow;
        //gives us selected rows
        selectedRow = partTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Select \"OK\" to confirm");
        alert.setTitle("Confirm");
        alert.setContentText("Are you sure you want to DELETE the selected part?");
        if (alert.showAndWait().get()==ButtonType.OK){
            for (Part part: selectedRow){
                Inventory.deletePart(part);
            }
        }
    }

    /**
     * finds and deletes a selected product from the products table
     */
    public void deleteProductButtonPushed(){

        ObservableList<Product> selectedRow;
        //gives us selected rows
        selectedRow = productTable.getSelectionModel().getSelectedItems();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Select \"OK\" to confirm");
        alert.setTitle("Confirm");
        alert.setContentText("Are you sure you want to DELETE the selected product?");
        if (alert.showAndWait().get()==ButtonType.OK){
            for (Product product: selectedRow){
                if (product.getAllAssociatedParts().size()==0){
                    Inventory.deleteProduct(product);
                }
                else{
                    Alert error = new Alert(Alert.AlertType.WARNING);
                    error.setHeaderText("Product has associated parts.");
                    error.setTitle("Error");
                    error.setContentText("You cannot delete a product that has associated parts. Please remove associated parts before attempting to delete a product");
                    error.showAndWait();

                }
            }
        }


    }
    Stage stage;

    /**
     * exits program
     */
    public void logout(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Select \"OK\" to confirm");
        alert.setTitle("Confirm");
        alert.setContentText("Are you sure you want to Exit?");
        if (alert.showAndWait().get()==ButtonType.OK){
            stage = (Stage) scenePane.getScene().getWindow();
            stage.close();
        }
    }

    /**initialize method, initializes table column values
     *
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partTable.setItems(Inventory.getAllParts());
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        productTable.setItems(Inventory.getAllProducts());
        productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
}