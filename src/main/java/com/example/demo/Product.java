/**
 * Product class extends part class. Includes an observable list of associated parts that are used in the product.
 */

package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product extends Part {

    private static ObservableList<Part> productPartsList = FXCollections.observableArrayList();

    public Product(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
        }
    public static void addAssociatedPart(Part part){
        productPartsList.add(part);
    }
    public static void deleteAssociatedPart(Part part){
        productPartsList.remove(part);
    }

    public static void setProductPartsList(ObservableList<Part> productPartsList) {
        Product.productPartsList = productPartsList;
    }
    public static ObservableList<Part> getAllAssociatedParts() {
        return productPartsList;
    }

}
