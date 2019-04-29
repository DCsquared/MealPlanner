/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplanner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author EKUStudent
 */
public class FoodList {
    public ArrayList<Food> content = new ArrayList<>();
   
    public FoodList(ArrayList<Food> inputList){
        content = inputList;
    }
    public FoodList(Food a, Food b, Food c){
        content = new ArrayList<>();
        content.add(a);
        content.add(b);
        content.add(c);
    }
    public FoodList(){
    }
    public String[] foodListToString(){
        String[] names = new String[content.size()];
        for(int i = 0; i < content.size(); i++){
            names[i] = content.get(i).getName();
        }
        return names;
    }
    
    public static FoodList allAvailableFood(){
        Connection conn = null;
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        conn = ConnectDb.setupConnection();
        FoodList allFood = new FoodList();
        try
        {
            String sqlStatement = "select * from Food";
            pst = (OraclePreparedStatement)conn.prepareStatement(sqlStatement);
            
            
            rs = (OracleResultSet) pst.executeQuery();
           // System.out.println(rs);
            
            while(rs.next())
            {
                rs.getInt("calories");
                String name = rs.getString("name");
                String foodGroup = rs.getString("foodGroup");
                int calories = rs.getInt("calories");
                int sugar = rs.getInt("sugar");
                int protein = rs.getInt("protein");
                int sodium = rs.getInt("sodium");
                int fat = rs.getInt("fat");
                int inStock = rs.getInt("inStock"); 
                Food foodToAdd = new Food(name, foodGroup, calories, sugar, protein, sodium, fat, inStock);
                allFood.content.add(foodToAdd);
                //System.out.println(name +  " " + foodGroup +  " " + calories +  " " + sugar +  " " + protein +  " " + sodium +  " " + fat +  " " + inStock);
            }
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        finally
        {
            ConnectDb.close(rs);
            ConnectDb.close(pst);
            ConnectDb.close(conn);
        }    
        return allFood;
    }
   
}
