package com.example.ecommerce;
import java.nio.channels.ConnectionPendingException;
import java.sql.*;

public class DbConnection {
    //this class will help us to connect with MYSQL database
    //we need JDBC connector

    private final String dbUrl = "jdbc:mysql://localhost:3306/ecommerce";
    private final String userName = "root";
    private final String pwd = "sahoo";

    private Statement getStatement(){
        try{
            Connection connection = DriverManager.getConnection(dbUrl, userName, pwd);
            return connection.createStatement();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getQuerytable(String query){
        try{
            Statement statement = getStatement();
            return statement.executeQuery(query); //it will return the table data act like select * from table
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int updateDB(String query){
        try{
            Statement statement = getStatement();
            return statement.executeUpdate(query); //it will return the table data act like select * from table
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        DbConnection conn = new DbConnection();
        ResultSet rs = conn.getQuerytable("select * from customer");
        if(rs!=null){
            System.out.println("Connection successful");
        }else{
            System.out.println("Connection failed");
        }
    }

}
