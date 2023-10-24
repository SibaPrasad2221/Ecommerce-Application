package com.example.ecommerce;

import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Order {

    public static boolean placeOrder(Customer customer, Product product){
        //for group orders
        String groupOrderId = "select max(group_order_id) +1 id from orders";
        DbConnection dbConnection = new DbConnection();
        try{
            ResultSet rs = dbConnection.getQuerytable(groupOrderId);
            if(rs.next()){
                String placeorder = "insert into orders(group_order_id, customer_id, product_id) values ("+rs.getInt("id")+","+customer.getId()+","+product.getId()+")";
                return dbConnection.updateDB(placeorder) !=0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public static int placeMultipleOrder(Customer customer, ObservableList<Product> productlist){
        //for group orders
        String groupOrderId = "select max(group_order_id) +1 id from orders";
        DbConnection dbConnection = new DbConnection();
        try{
            ResultSet rs = dbConnection.getQuerytable(groupOrderId);
            int count=0;
            if(rs.next()){
                for(Product product: productlist){
                    String placeorder = "insert into orders(group_order_id, customer_id, product_id) values ("+rs.getInt("id")+","+customer.getId()+","+product.getId()+")";
                    count += dbConnection.updateDB(placeorder);
                }

                return count;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }
}
