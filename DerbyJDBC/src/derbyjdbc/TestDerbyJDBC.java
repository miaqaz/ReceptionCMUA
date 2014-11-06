/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package derbyjdbc;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author zhangmin10
 */
public class TestDerbyJDBC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException, SQLException {
    
    Database db = new Database();
    db.connectDB();
    String firstName = "Min";
    String lastName = "Zhang";
    String gender = "F";
    String program = "MISM";
    String DOB = "1990-10-27";
    String photo = "abc.jepg";
 
    db.addNewStudent(firstName, lastName, gender, program, DOB, photo);
    
    String[] s = db.getStudentRecord(1);
    for (int i = 0; i<s.length;i++){
    
        System.out.println(s[i]);
    }
    
    db.updateStudentRecord(1);
    
    s = db.getStudentRecord(1);
    for (int i = 0; i<s.length;i++){
    
        System.out.println(s[i]); 
    }
       db.disconnectDB();
    }
                
    
    
}
