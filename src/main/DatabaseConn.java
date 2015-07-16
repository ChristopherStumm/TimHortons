package main;

import java.sql.*;

public class DatabaseConn {
	
	private static Connection conn = null;
	
	private static final String URL = "http://personalchef.ddns.net:3306";
	private static final String USER = "root";
	private static final String PASSWORD = "root";
	
	public static Connection getDatabaseConn(){
		if(conn != null) return conn;
		else{
			try{
				Class.forName("com.imaginary.sql.msql.MsqlDriver");
				conn = DriverManager.getConnection(URL, USER, PASSWORD);
			} catch(Exception e){
				e.printStackTrace();
			}
			return conn;
		}
	}

}
