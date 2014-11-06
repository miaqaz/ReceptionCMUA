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
public class Database {
    private Connection conn;
    //private Statement stmt;
    
    
    //connect to the database
    public void connectDB (){
        
        String driver ="org.apache.derby.jdbc.ClientDriver";
        String dbName = "receptionCMUA";
        String dbUrl = "jdbc:derby://localhost:1527/"+dbName+";create=true;user=staff;password=staff";
        
        try{
            Class.forName(driver);
            System.out.println("Driver loaded");
            conn = DriverManager.getConnection(dbUrl);
            System.out.println("Database connected");           
            
        } catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();        
        }
    }
    
    public void disconnectDB() throws SQLException{
        conn.close();
        System.out.println("Database disconnected");
    }
    
    /**
     * Insert a new student record
     * 
     * @param firstName
     * @param lastName
     * @param gender F or M
     * @param program
     * @param DOB "dd-MM-yyyy"
     * @param lastVisit "dd-MM-yyyy"
     * @param photo 
     */
    public void addNewStudent(String firstName, String lastName, String gender, 
        String program, String DOB, String photo) throws ParseException, SQLException{        
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date currentDate = new java.util.Date();
            String lastVisit = format.format(currentDate);
            String sqlCount = "select * from student";
            
            
        try {        
            ResultSet rs = stmt.executeQuery(sqlCount);
            rs.last();
            int count = rs.getRow();
            int id = count +1;
            
            int numOfVisit = 1;
            
            String sql = "insert into student " 
                    + "values(" + id + ",'" + firstName + "','"+ lastName + "','" + gender + "','" + program
                    + "','" + DOB+ "','"+ lastVisit+ "',"+ numOfVisit + ",'" + photo + "')";
         
                stmt.executeUpdate(sql);
                System.out.println("Succesfully add a new student record");

        } catch (SQLException ex) {
            System.out.println("Failed to add a new student record");
            ex.printStackTrace();
        } finally {
            stmt.close();
        }
    }
    
    
    /**
     * Get a string record of a student by student id
     * 
     * @param id
     * @return a student record in the following order: id, firstName, lastName,
     *          gender, program, DOB, lastVisit, numOfVisit, photo
     */
    public String[] getStudentRecord(int id) throws SQLException{
        
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
        String[] studentRecord = new String[9];    
        String sql = "select * from student where id = " + id ;
        
        try {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                for (int i =0; i < studentRecord.length; i++){
                    
                    studentRecord[i] = rs.getString(i+1);
                }
            }
            System.out.println("Succesfully get a student record");
 
        } catch (SQLException ex) {
            System.out.println("Failed to add a new student record");
            ex.printStackTrace();
        } finally{
            stmt.close();
        }

        return studentRecord;    
    
    }   
    
    
    /**
     * Update student record by id, update date of last visit 
     * and increase number of visit by 1
     * 
     * @param id 
     */
    public void updateStudentRecord(int id) throws SQLException{
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date currentDate = new java.util.Date();    
        String lastVisit = format.format(currentDate);
        String sql = "select * from student where id = " + id ;
       
        try {            
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()){
                int numOfVisit = rs.getInt(8) + 1; 
               
                
//                rs.updateString(7, lastVisit);
//                rs.updateInt(8, numOfVisit);
//                rs.updateRow();
//                
                sql = "update student set lastVisit ='" + lastVisit + "', numOfVisit = " + numOfVisit 
                        + " where id= " + id;
                
                
                stmt.executeUpdate(sql);
            }
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally{
            stmt.close();
        }
    
    }
    
    /**
     * add new visit event
     * 
     * @param id
     * @param visitDate
     * @param reason 
     */
    public void addVisit(String id, String visitDate, String reason ){
        
      
    
    
    
    }
    
}
