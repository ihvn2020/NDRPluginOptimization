/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.nigeriaemr.dbmanager;

import java.sql.DriverManager;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openmrs.module.nigeriaemr.ndrUtils.Utils;
import org.openmrs.module.nigeriaemr.omodmodels.DBConnection;
import org.openmrs.module.nigeriaemr.xmlgenerator.XMLGenerator;

/**
 * @author lordmaul
 */
public class Database {
	
	//static DBConnection openmrsConn = Utils.getNmrsConnectionDetails();
	
	public static Connection conn = null;
	
	//public static Connection [] connPool = new Connection[10];
	public static DBConnection openmrsConn2 = null;
	
	public static ConnectionPool connectionPool;
	
	public static void initConnection(DBConnection openmrsConn) {
		try {
			
			if (openmrsConn != null) {
				System.out.println("not null");
				//Class.forName("com.mysql.jdbc.Driver");
				Class.forName("org.mariadb.jdbc.Driver");
				System.out.println(openmrsConn.getPassword());
				connectionPool = new ConnectionPool("com.mysql.jdbc.Driver", openmrsConn.getUrl(),
				        openmrsConn.getUsername(), openmrsConn.getPassword(), 5, 10, true);
				//conn = DriverManager.getConnection(openmrsConn.getUrl() + "?useCursorFetch=true", openmrsConn.getUsername(),
				// openmrsConn.getPassword());
				
			} else {
				Class.forName("org.mariadb.jdbc.Driver");
				//Class.forName("com.mysql.jdbc.Driver");
				connectionPool = new ConnectionPool("com.mysql.jdbc.Driver", "jdbc:mysql://192.168.217.136:3306/upth",
				        "lordmaul", "inside12114", 10, 20, true);
				
				//conn = DriverManager.getConnection("jdbc:mysql://192.168.167.138:3317/openmrs?useCursorFetch=true",   "openmrs", "@37~maa5RyqR");
			}
			
			//conn = DriverManager.getConnection("jdbc:mysql://192.168.167.138:3317/openmrs?useCursorFetch=true", "openmrs",
			//"@37~maa5RyqR");
			//conn = DriverManager.getConnection("jdbc:mysql://192.168.43.230:3317/openmrs", "openmrs", "@37~maa5RyqR");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public static Connection getConnection() {
		/*if (Database.conn == null) {
			Database.initConnection();
			return Database.conn;
		} else {
			return Database.conn;
		}*/
		return null;
		
	}
	
	public void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			}
			catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		
	}
	
}
