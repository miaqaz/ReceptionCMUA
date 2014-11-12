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
    
    //db.addVisit(1, "Ask for help");
    //db.addVisit(2, "Ask for test");
    String[][] visit = db.getVisitRecord("2014-11-12");
    for (int i = 0; i<visit.length;i++){
        for (int j=0; j<visit[i].length;j++){
    
        System.out.println(visit[i][j]);
        }
        
        System.out.println();
    }
    
    
    
    
    db.addNewAnncmt(program, "Please do not skip classes!","2014-10-01","2014-11-18");
    db.addNewAnncmt(program, "Please submit telecom assignment!","2014-10-15","2014-11-20");
    String[][] anncmt = db.getAnncmtRecord(program);
    
    for (int i = 0; i<anncmt.length;i++){
        for (int j=0; j<anncmt[i].length;j++){
    
        System.out.println(anncmt[i][j]);
        }
        
        System.out.println();
    }
    db.addNewStudent(firstName, lastName, gender, program, DOB, photo);
    
    String[] s = db.getStudentRecord("1");
    for (int i = 0; i<s.length;i++){
    
        System.out.println(s[i]);
    }
    
    db.updateStudentRecord("1");
    
    s = db.getStudentRecord("1");
    for (int i = 0; i<s.length;i++){
    
        System.out.println(s[i]); 
    }
       db.disconnectDB();
    }
                
    
    
}
