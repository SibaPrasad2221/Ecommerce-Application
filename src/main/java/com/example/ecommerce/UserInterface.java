package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class UserInterface {

    GridPane loginPage;
    HBox headerBar;

    HBox footerBar;
    Button signInButton;
    Label welcomeLable;
    VBox body;

    Customer loggedInCustomer;

    ProductList productList = new ProductList();
    VBox productPage;

    Button placeOrderButton = new Button("Place Order");


    ObservableList<Product> itemsInCart = FXCollections.observableArrayList();

    public BorderPane createContent(){
        BorderPane root = new BorderPane();
        root.setPrefSize(800, 600);
        //root.getChildren().add(loginPage); //method to add nodes as children to pane
        root.setTop(headerBar);
//        root.setCenter(loginPage);
        body = new VBox();
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        root.setCenter(body);
        productPage = productList.getAllProductsfromDB();
        body.getChildren().add(productPage);

        root.setBottom(footerBar);
        return root;
    }

    public UserInterface(){
        createLoginPage();
        createHeaderBar();
        createFooterBar();
    }


    private void createLoginPage(){
        Text userNameText = new Text("User Name");
        Text pwdText = new Text("Password");

        TextField userName = new TextField();
        userName.setPromptText("Type you username here");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Type your password here");

        Label messageLabel = new Label("Hi");

        Button loginButton = new Button("Login");


        loginPage = new GridPane();
        loginPage.setStyle(" -fx-background-color: grey; ");
        loginPage.setAlignment(Pos.CENTER);
        loginPage.setHgap(10);
        loginPage.setVgap(10);
        loginPage.add(userNameText, 0, 0);
        loginPage.add(userName, 1, 0);
        loginPage.add(pwdText, 0, 1);
        loginPage.add(passwordField, 1,1);
        loginPage.add(messageLabel, 0, 2);
        loginPage.add(loginButton, 1,2);


        loginButton.setOnAction(new EventHandler<ActionEvent>() { //to perform some action when button got pressed
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = userName.getText();
                String password = passwordField.getText();
                Login login = new Login();
                loggedInCustomer = login.customer_login(name, password);

                if(loggedInCustomer != null){ //login is successful
                    messageLabel.setText("Welcome "+loggedInCustomer.getName());
                    welcomeLable.setText("Welcome, "+ loggedInCustomer.getName()+"! :) Have a nice day");
                    welcomeLable.setStyle("-fx-background-color: red;");
                    headerBar.getChildren().add(welcomeLable);
                    body.getChildren().clear();
                    body.getChildren().add(productPage);
                }else{
                    messageLabel.setText("UserName/Password Wrong! :(  Please Provide correct credentials ");
                }
            }
        });

    }
    private void createHeaderBar(){

        Button homeButton = new Button();
        Image image = new Image("C:\\Users\\sahoo\\OneDrive\\Desktop\\E-Commerce\\src\\img_2.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(80);
        homeButton.setGraphic(imageView);

        TextField searchbar = new TextField();
        searchbar.setPromptText("Search Bar");
        searchbar.setPrefWidth(280);

        Button searchButton = new Button("Search");

        signInButton = new Button("Sign In");
        welcomeLable = new Label();

        Button cartButton =  new Button("Cart");

        Button orderButton = new Button("Orders");




        headerBar =new HBox();
        headerBar.setStyle(" -fx-background-color: #2f4f4f; ");
        headerBar.setPadding(new Insets(10));
        headerBar.setSpacing(10);
        headerBar.setAlignment(Pos.CENTER);
        headerBar.getChildren().addAll(homeButton, searchbar, searchButton, signInButton, cartButton, orderButton);

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear(); //remove everything form body after login
                body.getChildren().add(loginPage); //put login page
                headerBar.getChildren().remove(signInButton); //now after login no need of signinbutton so remove it
            }
        });

        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                VBox prodPage = productList.getProductsInCart(itemsInCart);
                prodPage.setAlignment(Pos.CENTER);
                prodPage.setSpacing(10);
                prodPage.getChildren().add(placeOrderButton);
                body.getChildren().add(prodPage);
                footerBar.setVisible(false);
            }
        });

        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //need list of products and a customer to placeorder
                Product product= productList.getSelectedProduct();
                if(itemsInCart==null){ //if customer hasnot selected anything for order
                    showDialog("Please add some products in the cart to place oder");
                    return ;
                }
                if(loggedInCustomer==null){
                    showDialog("Please login first to place order");
                    return ;
                }

                int count = Order.placeMultipleOrder(loggedInCustomer, itemsInCart);
                if(count!=0) {
                    showDialog("Order for "+count+" products Placed Successfully!! ");
                }
                else{
                    showDialog("Order Failed !! :(");
                }
            }
        });

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(productPage);
                footerBar.setVisible(true);
                if(loggedInCustomer==null){
//                    System.out.println(headerBar.getChildren().add(signInButton));
                    if(headerBar.getChildren().indexOf(signInButton)==-1){ //if we combe back to home again it throws error to handle we used this conditon
                        headerBar.getChildren().add(signInButton);
                    }
                }
            }
        });
    }

    private void createFooterBar(){
        Button buyNowButton = new Button("BuyNow");
        Button addtoCart = new Button("Add to Cart");

        footerBar =new HBox();
        footerBar.setStyle(" -fx-background-color: #2f4f4f; ");
        footerBar.setPadding(new Insets(10));
        footerBar.setSpacing(10);
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton, addtoCart);

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product= productList.getSelectedProduct();
                if(product==null){ //if customer hasnot selected anything for order
                    showDialog("Please select a product first to place order");
                    return ;
                }
                if(loggedInCustomer==null){
                    showDialog("Please login first to place order");
                    return ;
                }

                boolean status = Order.placeOrder(loggedInCustomer, product);
                if(status==true) {
                    showDialog("Order Placed Successfully");
                }
                else{
                    showDialog("Order Failed !! :(");
                }
            }
        });

        addtoCart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //we need all the selected products in cart
                Product product= productList.getSelectedProduct();
                if(product==null){ //if customer hasnot selected anything for order
                    showDialog("Please select a product first to add it to cart!");
                    return ;
                }
                itemsInCart.add(product);
                showDialog("Item has been added to the cart successfully");
            }
        });
    }

    private void showDialog(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Message");
        alert.showAndWait();
    }
}
