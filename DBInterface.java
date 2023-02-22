import java.sql.*;
import java.io.File;
import java.util.*;
/**
 * Write a description of class DBInterface here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DBInterface
{
    // instance variables - replace the example below with your own
     private Connection dbConn = null;

    /**
     * Constructor for objects of class DBInterface
     */

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public DBInterface(String fileName) {
        try {
            // Connect to database
            String dbUrl = "jdbc:ucanaccess://" + 
                    new File(System.getProperty("user.dir")) + "\\" +
                    fileName;
            dbConn = DriverManager.getConnection(dbUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if (dbConn == null) {
            System.out.println(fileName + " not connected.");                
        } else {
            System.out.println(fileName + " connected.");                
        }      
    }
    public boolean gotAWord(String str){
        try {
            Statement s = dbConn.createStatement();
            ResultSet rs = s.executeQuery(
                    "SELECT * FROM Guess_Word WHERE Word = \'" + str +"\'");
            return rs.next();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public String generateWord(){
        try {
            Statement s = dbConn.createStatement();
            ResultSet rs = s.executeQuery(
                    "SELECT word FROM Game_Word ORDER BY RAND() LIMIT 1");
            System.out.println(rs.toString());
            return rs.toString();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
