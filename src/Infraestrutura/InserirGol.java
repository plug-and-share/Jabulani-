/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Infraestrutura;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Renan.Tashiro
 */
public class InserirGol {        
    
        private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_CONNECTION = "jdbc:oracle:thin:@localhost:1521:MKYONG";
	private static final String DB_USER = "user";
	private static final String DB_PASSWORD = "password";
        
	public InserirGol(int autor, boolean contra, boolean penalti, int minuto, int acrescimo) {
            
                try {
                        insertRecordIntoTable(autor, contra, penalti, minuto, acrescimo);
                } catch (SQLException e) {
                        System.out.println(e.getMessage());
                }
                
	}

	private static void insertRecordIntoTable(int autor, boolean contra, boolean penalti, int minuto, int acrescimo) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO pais"
				+ "( autor, contra, penalti, minuto, acrescimo ) VALUES"
				+ "( ?, ?, ?, ?, ? )";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			preparedStatement.setInt(1, autor);
                        preparedStatement.setBoolean(2, contra);
                        preparedStatement.setBoolean(3, penalti);
                        preparedStatement.setInt(4, minuto);
                        preparedStatement.setInt(5, acrescimo);
                        
			// execute insert SQL stetement
			preparedStatement.executeUpdate();

			System.out.println("Record is inserted into DBUSER table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	private static Connection getDBConnection() {

		Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(
                            DB_CONNECTION, DB_USER,DB_PASSWORD);
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}  
    
}
