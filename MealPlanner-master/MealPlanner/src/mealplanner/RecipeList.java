/* Program name: RecipeList.java
 * Author: David Cannon II, Danial Memon, Charlier Myre
 * Purpose: This class represents a list of food in the database
 * It can generate a list of all recipes in the database OR
 * create a recipe
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
 * @author DCsquared
 */
public class RecipeList {
    
    public ArrayList<Recipes> content = new ArrayList<>();
   
    public RecipeList(ArrayList<Recipes> inputList){
        content = inputList;
    }
    
    public RecipeList(){
    }
    
    //recipeListToString converts a RecipeList into an array of Strings with the names of each recipe in it
    public String[] recipeListToString(){
        String[] names = new String[content.size()];
        for(int i = 0; i < content.size(); i++){
            names[i] = content.get(i).getName();
        }
        return names;
    }
    
    //Grabs all of the recipes in the database
    public static RecipeList allAvailableRecipes(){
        Connection conn = null;
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        conn = ConnectDB.setupConnection();
        RecipeList allRecipes = new RecipeList();
        try
        {
            String sqlStatement = "select * from Recipes";
            pst = (OraclePreparedStatement)conn.prepareStatement(sqlStatement);
            
            
            rs = (OracleResultSet) pst.executeQuery();
            while(rs.next())
            {
                int i = rs.getInt("id");
                String name = rs.getString("name");
                String category = rs.getString("category");
                String instructions = rs.getString("instructions");
                Recipes r = new Recipes(i, name, category, instructions);
                allRecipes.content.add(r);
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
        
        return allRecipes;
        
    }
    
    //grabs the max id number, increments it and sets the new recipe's id 
    static int setId(){
        Connection conn = null;
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        conn = ConnectDB.setupConnection();
        int i = 0;
        try
        {
            String sqlStatement = "select max(id) from Recipes";
            pst = (OraclePreparedStatement)conn.prepareStatement(sqlStatement);
            
            
            rs = (OracleResultSet) pst.executeQuery();
            
            while(rs.next())
            {
                i = rs.getInt("max(id)");
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
        return i;
    }
    
    //creates the recipe item in the database
    public static int createRecipe(String n, String c, String inst){
        Connection conn = null;
        OraclePreparedStatement pst = null;
        OracleResultSet rs = null;
        conn = ConnectDB.setupConnection();
        int i = RecipeList.setId() +1;
        try
        {
            String sqlStatement = "insert into Recipes values ("+i+", '"+n+"', '"+c+"', '"+inst+"')";
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
        
        return i;
    }
    
    
}
