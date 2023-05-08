package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static int partIDCounter;
    public static void initialIDSetter(int idCounter){
        partIDCounter = idCounter + 1000;
    }

    public static void addPart(Part part){
        allParts.add(part);
        partIDCounter+=2;

    }
    public static void addProduct(Product product){
        allProducts.add(product);
        partIDCounter+=2;

    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static void removePart(Product product){
        allProducts.remove(product);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static void removePart(Part part){
        allParts.remove(part);
    }


    public static int getPartIDCounter() {
        return partIDCounter;
    }

    public static void incrementCounter(){
        partIDCounter+=2;
    }
}
