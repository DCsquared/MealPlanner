/* Program name: FoodList.java
 * Author: David Cannon II, Danial Memon, Charlier Myre
 * Purpose: This class represents a list of food in the database
 * It can generate a list of all food in the database OR
 * a list of food in the fridge OR
 * a list of food not in the fridge but in the database
 * a list of good that needs be purchased (shopping list)
 * a list of food in or not in a recipe
 * adding food to a recipe
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


public class FoodList {
    //FoodList's only variable is an ArrayList of Food
    public ArrayList<Food> content = new ArrayList<>();

    //this constructor makes a FoodList given an ArrayList of Food
    public FoodList(ArrayList<Food> inputList){
        content = inputList;
    }
	
    //this constructor makes a FoodList with nothing in it
    public FoodList(){
    }
    //foodListToString converts a FoodList into an array of Strings with the names of each food in it
    public String[] foodListToString(){
        String[] names = new String[content.size()];
        for(int i = 0; i < content.size(); i++){
            names[i] = content.get(i).getName();
        }
        return names;
    }
    
    //this function queries the database for all food and returns a FoodList with all of them
    public static FoodList allAvailableFood(){
        //connect to the database
	Connection conn = null;
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        conn = ConnectDB.setupConnection();
        //allFood is the FoodList that will be returned
	FoodList allFood = new FoodList();
        try
        {
            String sqlStatement = "select * from Food";
            //prepare the sql statement
            pst = (OraclePreparedStatement)conn.prepareStatement(sqlStatement);
            //store the result of the executed query

            rs = (OracleResultSet) pst.executeQuery();
           
            //loop through the results, for each row, create a variable for each column
            //then use them to create a Food, and add that food to the list to be returned
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
				
            }
            
        }//catch the exception
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }//close the connection to the database
        finally
        {
            ConnectDB.close(rs);
            ConnectDB.close(pst);
            ConnectDB.close(conn);
        }    
        return allFood;
    }
	
    //fridgeFood works the exact same way as allAvailableFood except it only finds food that is in the fridge (inStock = 1)
    public static FoodList fridgeFood(){
        Connection conn = null;
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        conn = ConnectDB.setupConnection();
        FoodList inFood = new FoodList();
        try
        {
            String sqlStatement = "select * from Food  where inStock = 1";
            pst = (OraclePreparedStatement)conn.prepareStatement(sqlStatement); 
            rs = (OracleResultSet) pst.executeQuery();
            
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
                inFood.content.add(foodToAdd);
				
            }
            
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
        return inFood;
    }
    //notInFridgeFood queries for the opposite of fridgeFood: it only returns food that is not the in the fridge (inStock = 0)
    public static FoodList notInFridgeFood(){
        Connection conn = null;
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        conn = ConnectDB.setupConnection();
        FoodList notInFood = new FoodList();
        try
        {
            String sqlStatement = "select * from Food  where inStock = 0";
            pst = (OraclePreparedStatement)conn.prepareStatement(sqlStatement);
            
            
            rs = (OracleResultSet) pst.executeQuery();            
            while(rs.next())
            {
                String name = rs.getString("name");
                String foodGroup = rs.getString("foodGroup");
                int calories = rs.getInt("calories");
                int sugar = rs.getInt("sugar");
                int protein = rs.getInt("protein");
                int sodium = rs.getInt("sodium");
                int fat = rs.getInt("fat");
                int inStock = rs.getInt("inStock"); 
                Food foodToAdd = new Food(name, foodGroup, calories, sugar, protein, sodium, fat, inStock);
                notInFood.content.add(foodToAdd);
            }
            
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
        return notInFood;
    }
    
    //shoppingListFood makes a query for food that is in a recipe used in the current week
    //it is similar to the previous functions aside from the query it makes
    public static FoodList shoppingListFood(){
        Connection conn = null;
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        conn = ConnectDB.setupConnection();
        FoodList shoppingFood = new FoodList();
        try
        {
            String sqlStatement = "select * from Food "
                    + "where inStock = 0 and "
                    + "name = ANY(select foodName from RecipeIngredient "
                    + "where recipeID = ANY(select mealID from WeekPlan "
                    + "where weekID =(select ID from Week where currentWeek = 1)))";
            pst = (OraclePreparedStatement)conn.prepareStatement(sqlStatement);
            rs = (OracleResultSet) pst.executeQuery();    
            
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
                shoppingFood.content.add(foodToAdd);
            }
            
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
        return shoppingFood;
    }
    
    //pass recipe name
    public static FoodList getIngredients(String name){
        Connection conn = null;
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        conn = ConnectDB.setupConnection();
        FoodList thisFood = new FoodList();
        try
        {
            String sqlStatement = "select * from Food where name = ANY(select foodName from "
                    + "RecipeIngredient where recipeID = (select id from Recipes where name = '"
                    +name+"'))";
            pst = (OraclePreparedStatement)conn.prepareStatement(sqlStatement);
            
            
            rs = (OracleResultSet) pst.executeQuery();
            
            
            while(rs.next())
            {
                rs.getInt("calories");
                String foodName = rs.getString("name");
                String foodGroup = rs.getString("foodGroup");
                int calories = rs.getInt("calories");
                int sugar = rs.getInt("sugar");
                int protein = rs.getInt("protein");
                int sodium = rs.getInt("sodium");
                int fat = rs.getInt("fat");
                int inStock = rs.getInt("inStock"); 
                Food foodToAdd = new Food(foodName, foodGroup, calories, sugar, protein, sodium, fat, inStock);
                thisFood.content.add(foodToAdd);
            }
            
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
        return thisFood;
    }
    
    //pass recipe name
    public static FoodList getNotInRecipe(String name){
        Connection conn = null;
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        conn = ConnectDB.setupConnection();
        FoodList otherFood = new FoodList();
        try
        {
            String sqlStatement = "select * from Food where name != All(select foodName from "
                    + "RecipeIngredient where recipeID =(select id from Recipes where name = '"
                    +name+"' )) ";
            pst = (OraclePreparedStatement)conn.prepareStatement(sqlStatement);
            
            
            rs = (OracleResultSet) pst.executeQuery();
            
            while(rs.next())
            {
                rs.getInt("calories");
                String foodName = rs.getString("name");
                String foodGroup = rs.getString("foodGroup");
                int calories = rs.getInt("calories");
                int sugar = rs.getInt("sugar");
                int protein = rs.getInt("protein");
                int sodium = rs.getInt("sodium");
                int fat = rs.getInt("fat");
                int inStock = rs.getInt("inStock"); 
                Food foodToAdd = new Food(foodName, foodGroup, calories, sugar, protein, sodium, fat, inStock);
                otherFood.content.add(foodToAdd);
            }
            
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
        return otherFood;
    }
    
        //pass recipe name
    public void addToRecipe(int i, String f){
        Connection conn = null;
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        conn = ConnectDB.setupConnection();
        FoodList otherFood = new FoodList();
        try
        {
            String sqlStatement = "insert into RecipeIngredient values ("+i+",'"+f+"')";
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

    public void removeFromRecipe(int i, String f){
        Connection conn = null;
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        conn = ConnectDB.setupConnection();
        FoodList otherFood = new FoodList();
        try
        {
            String sqlStatement = "delete from RecipeIngredient where recipeID = "+i+" and foodName = '"+f+"'";
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
    
    //pass new recipe id
    public static void newRecipe(String[] ing, int i){
        for (String fname : ing) {
            insertFood(fname, i);
        }
    }
    
    //pass recipe name
    static void insertFood(String name, int i){
        Connection conn = null;
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        conn = ConnectDB.setupConnection();
        try
        {
            String sqlStatement = "insert into RecipeIngredient values ("+i+",'"+name+"')";
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
