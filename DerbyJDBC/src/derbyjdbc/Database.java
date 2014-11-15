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

    
    
    
    /**
     * Connect to Database
     */
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
    
    /**
     * Disconnect to the database
     * @throws SQLException 
     */
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
     * @param DOB "yyyy-MM-dd"
     * @param photo 
     * @throws java.text.ParseException 
     * @throws java.sql.SQLException 
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
     * Get a newly add student
     * 
     * @param stringId
     * @return null if no record found or a student record in the following order: id, firstName, lastName,
     *          gender, program, DOB, lastVisit, numOfVisit, photo
     * @throws java.sql.SQLException
     */
    public String[] getLastStudentRecord() throws SQLException{
        
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
        String[] studentRecord = new String[9];  
        String sql = "select * from student";
        
        try {
            ResultSet rs = stmt.executeQuery(sql);
            rs.last();
            
                
                for (int i =0; i < studentRecord.length; i++){
                    
                    studentRecord[i] = rs.getString(i+1);
                }
            
            System.out.println("Succesfully get a newly add student record");
            return studentRecord;   
        } catch (SQLException ex) {
            System.out.println("Failed to get a newly add student record");
            ex.printStackTrace();
        } finally{
            stmt.close();
        }
        return null;        
    }   
    
    
    /**
     * Get a string record of a student by student id
     * 
     * @param stringId
     * @return null if no record found or a student record in the following order: id, firstName, lastName,
     *          gender, program, DOB, lastVisit, numOfVisit, photo
     * @throws java.sql.SQLException
     */
    public String[] getStudentRecord(String stringId) throws SQLException{
        
        int id = Integer.parseInt(stringId);
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
            System.out.println("Succesfully get a student record by student id: " + id);
            return studentRecord;   
        } catch (SQLException ex) {
            System.out.println("Failed to get a new student record by student id: " + id);
            ex.printStackTrace();
        } finally{
            stmt.close();
        }
        return null;        
    }   
    
    
    /**
     * Update student record by id, update date of last visit 
     * and increase number of visit by 1
     * 
     * @param stringId 
     * @throws java.sql.SQLException 
     */
    public void updateStudentRecord(String stringId) throws SQLException{
        int id = Integer.parseInt(stringId);
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
                System.out.println("Succesfully update a student record by student id: " + id);
            }
            
            
        } catch (SQLException ex) {
            System.out.println("Failed to update a student record by student id: " + id);
            ex.printStackTrace();
        } finally{
            stmt.close();
        }
    
    }
    
    /**
     * add new visit event
     * 
     * @param id student id
     * @param reason 
     * @throws java.sql.SQLException 
     */
    public void addVisit(int id, String reason ) throws SQLException {
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date currentDate = new java.util.Date();
        String visitDate = format.format(currentDate);

        String sql =  "insert into visit values("+ id + ",'" + visitDate + "','" + reason + "')" ;
        stmt.executeUpdate(sql);
        System.out.println("Succesfully add a new visit event");
        stmt.close();
    }
    
    /**
     * Get visit record by visit date
     * 
     * @param startDate
     * @param endDate
     * @return null if no record found or all visit records on that date in the order of: id, visitDate, Reason
     * @throws SQLException 
     */
    public String[][] getVisitRecordbyDate(String startDate,String endDate) throws SQLException {
        
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String sql = "select * from visit where visitDate >= '" + startDate +"' and visitDate <= '"+ endDate +"'";
        
        try {   
             ResultSet rs = stmt.executeQuery(sql); 
             rs.last();
             int numOfRows = rs.getRow();
             rs.beforeFirst();
             String[][] visitRecord = new String[numOfRows][3];
             while (rs.next()){
                 for (int i =0; i < visitRecord.length; i++){
                     for (int j =0; j < visitRecord[i].length; j++){
                         visitRecord[i][j] = rs.getString(j+1);
                 }
                rs.next();
             }
             }
             System.out.println("Succesfully get all visit records between " + startDate + " and " + endDate); 
             return visitRecord;
             
         } catch (SQLException ex) {
             System.out.println("Failed to get visit records between " + startDate + " and " + endDate);
             ex.printStackTrace();
         } finally {
            stmt.close();
        }
 
        return null;
    }
   
    
    
    /**
     * Get visit record by visit date
     * 
     * @param startDate
     * @param endDate
     * @return null if no record found or all visit records on that date in the order of: id, visitDate, Reason
     * @throws SQLException 
     */
    public String[][] getVisitRecordbyDateGender(String startDate,String endDate,String gender) throws SQLException {
        
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        String sql = "select visit.reason, count(*) from visit inner join student on visit.id = student.id "
                + "where gender ='"+gender.toUpperCase()+"' and visitDate >= '" + startDate +
                "' and visitDate <= '"+ endDate +"'group by reason";
        
        try {   
             ResultSet rs = stmt.executeQuery(sql); 
             rs.last();
             int numOfRows = rs.getRow();
             rs.beforeFirst();
             String[][] visitRecord = new String[numOfRows][2];
             while (rs.next()){
                 for (int i =0; i < visitRecord.length; i++){
                     for (int j =0; j < visitRecord[i].length; j++){
                         visitRecord[i][j] = rs.getString(j+1);
                 }
                rs.next();
             }
             }
             System.out.println("Succesfully get visit frequency by gender between " + startDate + " and " + endDate); 
             return visitRecord;
             
         } catch (SQLException ex) {
             System.out.println("Failed to get visit frequency by gender between " + startDate + " and " + endDate);
             ex.printStackTrace();
         } finally {
            stmt.close();
        }
 
        return null;
    }
   
    
    
    /**
     * Insert a new announcement record
     * 
     * 
     * @param program
     * @param content
     * @param startDate
     * @param endDate
     * @throws java.text.ParseException
     * @throws java.sql.SQLException
      
     */
    public void addNewAnncmt(String program, String content, String startDate, String endDate) throws ParseException, SQLException{        
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
            String sqlCount = "select * from announcement";
            
            
            
        try {        
            ResultSet rs = stmt.executeQuery(sqlCount);
            rs.last();
            int count = rs.getRow();
            int anncmtId = count +1;
            
            String sql = "insert into announcement " 
                    + "values(" + anncmtId + ",'" + program + "','"+ content +"','" + startDate + "','"+ endDate + "')";         
                stmt.executeUpdate(sql);
                System.out.println("Succesfully add a new announcement record");

        } catch (SQLException ex) {
            System.out.println("Failed to add a new announcement record");
            ex.printStackTrace();
        } finally {
            stmt.close();
        }
    }
    
    /**
     * Get a string record of announcement records by program
     * 
     * @param program
     * @return null if no record found or an announcement record 
     *         in the following order: anncmtId, program, content, startDate, endDate
     * @throws java.sql.SQLException
     */
    public String[][] getAnncmtRecord(String program) throws SQLException{
        
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);  
        String sql = "select * from announcement where program = '" + program + "' and startDate <= CURRENT DATE and endDate >= CURRENT DATE";
        
        try {
            ResultSet rs = stmt.executeQuery(sql);
            rs.last();
            int numOfRows = rs.getRow();
            rs.beforeFirst();
            String[][] anncmtRecord = new String[numOfRows][5];   
            while (rs.next()){
                for (int i =0; i < anncmtRecord.length; i++){
                    for (int j = 0; j< anncmtRecord[i].length; j++){
                    
                    anncmtRecord[i][j] = rs.getString(j+1);
                    }
                rs.next();
                }
            }
            System.out.println("Succesfully get all annoucement records for program: " + program );
            return anncmtRecord;
 
        } catch (SQLException ex) {
            System.out.println("Failed to get announcement records for program: " + program);
            ex.printStackTrace();
        } finally{
            stmt.close();
        }
        return null;    
    }   
}