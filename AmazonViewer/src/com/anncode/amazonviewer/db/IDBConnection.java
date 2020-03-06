package com.anncode.amazonviewer.db;

import java.sql.Connection;
import java.sql.DriverManager;
import static  com.anncode.amazonviewer.db.DataBase.*;

public interface IDBConnection {

    default Connection connectionToDB(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection= DriverManager.getConnection(URL+DB+TIMEZONE,USER,PASSWORD);
            if(connection != null){
                System.out.println("se estableció la conección");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return connection;
        }
    }
}
