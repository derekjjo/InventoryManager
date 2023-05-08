package com.example.demo;

import javafx.collections.FXCollections;
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

public class HelloController implements Initializable {

    //int id, String name, double price, int stock, int min, int max)
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

    public void goToAddPartButtonPushed(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        // This line gets the stage info
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(addPartScene);
        window.show();

    }
    public void goToAddProductButtonPushed(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        // This line gets the stage info
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(addPartScene);
        window.show();

    }

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

    public void goToModifyProductButtonPushed(ActionEvent event) throws IOException {
        modifyProduct = (Product) productTable.getSelectionModel().getSelectedItem();

        ModifyProductController.addToModify(modifyProduct);

        Parent addPartParent = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        // This line gets the stage info
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(addPartScene);
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
    public ObservableList<Product> searchProductsList(String searchTerm){
        ObservableList<Product> searchedProductList = FXCollections.observableArrayList();

        ObservableList<Product> allProductsSearch = Inventory.getAllProducts();

        for(Product product: allProductsSearch) {
            if(product.getName().contains(searchTerm)){
                searchedProductList.add(product);
            }
        }
        return searchedProductList;
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
    public Product getProductByID(int idNumber){
        ObservableList<Product> allProductSearch = Inventory.getAllProducts();

        for(Product product: allProductSearch) {
            if(product.getId() == idNumber){
                return product;
            }
        }
        return null;
    }

    //calls function to search parts list
    public void getResultsHander(ActionEvent actionEvent){
        String q = queryTF.getText();

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

        partTable.setItems(partsSearchList);
        queryTF.setText("");
    }

    public void getResultsProductHander(ActionEvent actionEvent){
        String q = queryProduct.getText();

        ObservableList<Product> productSearchList = searchProductsList(q);

        if(productSearchList.size()== 0) {
            try {
                int productSearchID = Integer.parseInt(q);
                Product p = getProductByID(productSearchID);
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
                Inventory.removePart(part);
            }
        }
    }
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
                Inventory.removePart(product);
            }
        }


    }
    Stage stage;

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


    private static boolean firstTime = true;

    //add any parts or products to be added to the initial table here
    private void addInitialData(){
        if (!firstTime){
            return;
        }
        firstTime = false;
        Outsourced c = new Outsourced(124,"dog", 12, 12,12,12, "112");
        Inventory.addPart(c);
        Outsourced b = new Outsourced(125,"stank", 12, 12,12,12, "Nike");
        Inventory.addPart(b);
        Outsourced d = new Outsourced(126,"cat", 12, 12,12,12, "Reebok");
        Inventory.addPart(d);
        InHouse f = new InHouse(127,"asdf", 12, 12,12,12, 118);
        Inventory.addPart(f);

        Product n = new Product(124,"dog", 12, 12,12,12);
        Inventory.addProduct(n);
        Product m = new Product(125,"asdff", 12, 12,12,12);
        Inventory.addProduct(m);
        Product k = new Product(126,"tttt", 12, 12,12,12);
        Inventory.addProduct(k);
        Product l = new Product(127,"rat", 12, 12,12,12);
        Inventory.addProduct(l);

        Inventory.initialIDSetter(Inventory.getAllProducts().size()+Inventory.getAllParts().size());
    }
//int id, String name, double price, int stock, int min, int max)
    //initialize method adds initial data and initializes table
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addInitialData();
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