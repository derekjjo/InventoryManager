package com.example.demo;
/**
 * Product and Part inventory with two separate observable lists that contain Parts and Products
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * initializes ID number counter at 1000
     */
    private static int partIDCounter = 1000;

    /**
     * Increments the id counter when parts are added (does not decrement when parts are deleted)
     */
    public static void incrementCounter(){
        partIDCounter+=2;
    }

    /**
     * adds parts to part inventory
     * @param part
     */
    public static void addPart(Part part){
        allParts.add(part);
        partIDCounter+=2;

    }

    /**
     * adds products to product inventory
     * @param product
     */
    public static void addProduct(Product product){
        allProducts.add(product);
        partIDCounter+=2;

    }

    /**
     * lookup product by name
     * @param productName
     * @return
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> searchedProductList = FXCollections.observableArrayList();

        ObservableList<Product> allProductsSearch = Inventory.getAllProducts();

        for(Product product: allProductsSearch) {
            if(product.getName().contains(productName)){
                searchedProductList.add(product);
            }
        }
        return searchedProductList;
    }

    /**
     * lookup product by ID
     * @param productId
     * @return
     */
    public static Product lookupProduct(int productId){
        ObservableList<Product> allProductSearch = Inventory.getAllProducts();

        for(Product product: allProductSearch) {
            if(product.getId() == productId){
                return product;
            }
        }
        return null;
    }

    /**
     * lookup part by name
     * @param name
     * @return
     */
    public static ObservableList<Part> lookupPart(String name){
        ObservableList<Part> searchedPartList = FXCollections.observableArrayList();

        ObservableList<Part> allPartsSearch = Inventory.getAllParts();

        for(Part part: allPartsSearch) {
            if(part.getName().contains(name)){
                searchedPartList.add(part);
            }
        }

        return searchedPartList;

    }

    /**
     * lookup part by id
     * @param partId
     * @return
     */
    public static Part lookupPart(int partId){
        ObservableList<Part> allPartsSearch = Inventory.getAllParts();

        for(Part part: allPartsSearch) {
            if(part.getId() == partId){
                return part;
            }
        }
        return null;
    }
    public static void updatePart(int index, Part selectedPart){
        for (Part p: getAllParts()){
            if(p.getId() == index){
                getAllParts().set(index,selectedPart);
            }
        }
    }
    public static void updateProduct(int index, Product selectedProduct){
        for (Product p: getAllProducts()){
            if(p.getId() == index){
                getAllProducts().set(index,selectedProduct);
            }
        }
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static boolean deleteProduct(Product product){
        return allProducts.remove(product);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static boolean deletePart(Part part){
        return allParts.remove(part);
    }


    public static int getPartIDCounter() {
        return partIDCounter;
    }
}
