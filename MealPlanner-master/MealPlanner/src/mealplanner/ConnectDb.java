package mealplanner;

/* Program name: FoodList.java
 * Author: David Cannon II, Danial Memon, Charlier Myre
 * Purpose: Connect to the database
 */

import java.sql.Connection;
import java.sql.DriverManager;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

import javax.swing.JOptionPane;

/**
 *
 * @author EKUStudent
 */
public class ConnectDB {
    public static Connection setupConnection()
    {
        String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
        String jdbcUrl = "jdbc:oracle:thin:@cswinserv.eku.edu:1521:cscdb";
        String username = "Myre5452019";
        String password = "2553";
        
        try
        {
            Class.forName(jdbcDriver);
            
            Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
            return conn;
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
        return null;
    }
    
    static void close(Connection conn)
    {
        if (conn != null)
        {
            try 
            {
                conn.close();
            }
            catch (Throwable whatever)
            {
                
            }
        }
    }
    
    static void close(OraclePreparedStatement st)
    {
        if (st != null)
        {
            try
            {
                st.close();
            }
            catch (Throwable wtever)
            {
                
            }
        }
    }
    static void close(OracleResultSet rs)
    {
        if (rs != null)
        {
            try
            {
                rs.close();
            }
            catch (Throwable wtever)
            {
                
            }
        }
    }
}
