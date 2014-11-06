/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package derbyjdbc;
import java.sql.*;
import java.text.ParseException;

/**
 *
 * @author zhangmin10
 */
public class TestDerbyJDBC {

    /**
     * @param args the command line arguments
     * @throws java.text.ParseException
     * @throws java.sql.SQLException
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
    
    db.addVisit(1, "Ask for help");
    String[] visit = db.getVisitRecord("2014-11-06");
    for (int i = 0; i<visit.length;i++){
    
        System.out.println(visit[i]);
    }
    
    
    
    
    db.addNewAnncmt(program, "Please do not skip classes!");
    String[] anncmt = db.getAnncmtRecord(program);
    
    
    
    for (int i = 0; i<anncmt.length;i++){
    
        System.out.println(anncmt[i]);
    }
 
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
