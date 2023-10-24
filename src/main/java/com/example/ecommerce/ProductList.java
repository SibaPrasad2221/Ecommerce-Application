package com.example.ecommerce;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ProductList {
    private TableView<Product> productTableView;

    public VBox createTable(ObservableList<Product> data){
        //columns
        TableColumn id =new TableColumn("ID");
//        id.setCellFactory(new PropertyValueFactory<>("id"));
        id.setCellValueFactory(new PropertyValueFactory<>("id")); //all the variables inside setcellfactory should be matched with product class variable

        TableColumn name = new TableColumn("Name");
//        name.setCellFactory(new PropertyValueFactory<>("name"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price = new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));


        productTableView = new TableView<>();
        productTableView.getColumns().addAll(id, name, price);
        productTableView.setItems(data);
        productTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(productTableView);

        return vbox;
    }

    public VBox getDummyTable(){
        //data
        ObservableList<Product> data = FXCollections.observableArrayList();
        data.add(new Product(2, "Iphone", 44565));
        data.add(new Product(5, "Laptop", 50000));

        return createTable(data);
    }

    public VBox getAllProductsfromDB(){
        ObservableList<Product> data = Product.getAllProducts();
        return createTable(data);
    }

    public Product getSelectedProduct(){
        return productTableView.getSelectionModel().getSelectedItem();
    }

    public VBox getProductsInCart(ObservableList<Product> data){
        return createTable(data);
    }
}
