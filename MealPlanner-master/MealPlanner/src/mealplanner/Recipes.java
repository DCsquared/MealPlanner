/* Program name: Recipes.java
 * Author: David Cannon II, Danial Memon, Charlier Myre
 * Purpose: create a class for recipe item in database
 */
package mealplanner;

import java.sql.Connection;
import javax.swing.JOptionPane;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author DCsquared
 */
public class Recipes {
    private int id;
    private String name;
    private String category;
    private String instructions;
    
    Recipes(int i, String n, String c, String inst){
        id = i;
        name = n;
        category = c;
        instructions = inst;
    }
    
    //the recipe item's ID number
    public int getId(){
        return id;
    }
    
    //the recipe item's Name
    public String getName(){
        return name;
    }
    
    //the recipe item's Category
    public String getCategory(){
        return category;
    }
    
    //the recipe item's Instructions
    public String getInstructions(){
        return instructions;
    }
    
    //mutators
    public void setName(String n){
        this.name = n;
    }
    public void setCategory(String c){
        this.category = c;
    }
    public void setInstructions(String inst){
        this.instructions = inst;
    }
    
    
}

