/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author ACER
 */
public class DbCon {
    private static Connection connection = null;
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
        if(connection == null){
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); 

            connection=DriverManager.getConnection("jdbc:sqlserver://127.0.0.1:1433;databaseName=Gympro","sa","2308");
            System.out.print("connected");
        }
        return connection;
    }
}
