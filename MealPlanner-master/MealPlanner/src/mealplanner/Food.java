/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplanner;

import java.io.Serializable;
import java.sql.Connection;
import javax.swing.JOptionPane;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author Danial Memon
 */
public class Food{
    private String name;
    private String foodGroup;
    private int calories;
    private int sugar;
    private int protein;
    private int sodium;
    private int fat;
    private int inStock;
    
    public Food(String n, String g, int c, int sug, int p, int sod, int f, int i){
        name = n;
        foodGroup = g;
        calories = c;
        sugar = sug;
        protein = p;
        sodium = sod;
        fat = f;
        inStock = i;
    }
    
    public Food(){
        name = "apple";
        foodGroup = "fruit";
        calories = 1;
        sugar = 2;
        protein = 3;
        sodium = 4;
        fat = 5;
    }    
    
    //accessors
    public String getName(){
        return name;
    }
    public String getFoodGroup(){
        return foodGroup;
    }
    
    public int getCalories(){
        return calories;
    }
    public int getSugar(){
        return sugar;
    }
    public int getProtein(){
        return protein;
    }
    public int getSodium(){
        return sodium;
    }
    public int getFat(){
        return fat;
    }
    public int getInStock(){
        return inStock;
    }
    
    //mutators
    public void setName(String n){
        this.name = n;
    }
    public void setFoodGroup(String g){
        this.foodGroup = g;
    }
    public void setCalories(int c){
        this.calories = c;
    }
    public void setSugar(int s){
        this.sugar = s;
    }
    public void setProtein(int p){
        this.protein = p;
    }
    public void setSodium(int s){
        this.sodium = s;
    }
    public void setFat(int f){
        this.fat = f;
    }
    public void toggleInStock(int i){
        Connection conn = null;
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        conn = ConnectDB.setupConnection();
        try
        {
            String sqlStatement = "update Food set inStock = "+ i +" where name = \'" +this.getName() +"\'";
            pst = (OraclePreparedStatement)conn.prepareStatement(sqlStatement);
            
            
            rs = (OracleResultSet) pst.executeQuery();
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }
        finally
        {
            ConnectDB.close(rs);
            ConnectDB.close(pst);
            ConnectDB.close(conn);
        }    
    
    }
    
    
}
