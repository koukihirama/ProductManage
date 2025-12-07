package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    // ★ ここは自分の環境に合わせて書き換えてね
	private static final String URL =
	        "jdbc:mysql://127.0.0.1:3306/product_management?serverTimezone=Asia/Tokyo";
	    private static final String USER = "java";
	    private static final String PASS = "pass";

	    static {
	        try {
	            System.out.println("★ Loading JDBC driver...");
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            System.out.println("★ JDBC Driver loaded OK!");
	        } catch (ClassNotFoundException e) {
	            System.out.println("★ JDBC Driver failed to load!");
	            e.printStackTrace();   // ← これが大事！
	            throw new ExceptionInInitializerError(e);
	        }
	    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}