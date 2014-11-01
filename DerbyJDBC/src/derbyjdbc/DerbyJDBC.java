/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package derbyjdbc;
import java.sql.*;

/**
 *
 * @author zhangmin10
 */
public class DerbyJDBC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    String driver = "org.apache.derby.jdbc.ClientDriver";//在derby.jar里面
    String dbName="xyz";
    String dbURL = "jdbc:derby://localhost:1527/"+dbName+";create=true";//create=true表示当数据库不存在时就创建它
    try { 
    Class.forName(driver);
    Connection conn = DriverManager.getConnection(dbURL);//启动嵌入式数据库
    Statement st = conn.createStatement();
    st.execute("create table foo (FOOID INT NOT NULL,FOONAME VARCHAR(30) NOT NULL)");//创建foo表
    st.executeUpdate("insert into foo(FOOID,FOONAME) values (1,'chinajash')");//插入一条数据
    ResultSet rs = st.executeQuery("select * from foo");//读取刚插入的数据
    while(rs.next()){
    int id = rs.getInt(1);
    String name = rs.getString(2);
    System.out.println("id="+id+";name="+name);
    }
    } catch(ClassNotFoundException | SQLException e){
    e.printStackTrace();
}
    }
    
}
