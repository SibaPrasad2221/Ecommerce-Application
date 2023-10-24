package com.example.ecommerce;

import java.sql.ResultSet;

public class Login {
    public Customer customer_login(String username, String pwd){
        String loginQuery = "select * from customer where email= '"+username+"' and password= '"+pwd+"'";
        DbConnection conn = new DbConnection();
        try{
            ResultSet res = conn.getQuerytable(loginQuery);
            if(res.next()){
                return new Customer(res.getInt("id"),
                        res.getString("name"),
                        res.getString("email"),
                        res.getString("mobile"));  //if any record present
            }
            else return null; //this means no record present with this user name and password
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Login login = new Login();
        Customer customer = login.customer_login("sahoo@gmailc.om", "abc123");
        System.out.println("Welcome "+customer.getName());
//        System.out.println(login.customer_login("sahoo@gmailc.om", "abc123"));
    }
}
