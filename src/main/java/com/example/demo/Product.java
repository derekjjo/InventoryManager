package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product extends Part {

    private static ObservableList<Part> productPartsList = FXCollections.observableArrayList();

    public Product(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
        }
    public static void addPart(Part part){
        productPartsList.add(part);
    }
    public static void removePart(Part part){
        productPartsList.remove(part);
    }

    public static void setProductPartsList(ObservableList<Part> productPartsList) {
        Product.productPartsList = productPartsList;
    }

    public static ObservableList<Part> getProductPartsList() {
        return productPartsList;
    }
}
