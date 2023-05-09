/**
 * FUTURE ENHANCEMENT
 * A future enhancement to this product could be a way to measure product and part inventory over time to predict ordering patterns. This could be done by measuring product/part inventory over time and notifying users when there needs to be more product ordered based on previous trends.
 */

package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        stage.setTitle("Inventory Management System");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();

    }
}