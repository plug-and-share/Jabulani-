package Infraestrutura;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class InserirSelecao {

	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_CONNECTION = "jdbc:oracle:thin:@localhost:1521:MKYONG";
	private static final String DB_USER = "user";
	private static final String DB_PASSWORD = "password";
	private static int pais, copa, confederacao, colocacao, jogos, vitorias, empates, derrotas, gols_pro, gols_contra, saldo_gols;
        
        public InserirSelecao(String[] argv) {

		try {

			insertRecordIntoTable( pais, copa, confederacao, colocacao, jogos, vitorias, empates, derrotas, gols_pro, gols_contra, saldo_gols );
                         
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

	}

	private static void insertRecordIntoTable( int pais, int copa, int confederacao, int colocacao, int jogos, int vitorias, int empates, int derrotas, int gols_pro, int gols_contra, int saldo_gols ) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO selecao"
				+ "( pais, copa, confederacao, colocacao, jogos, vitorias, empates, derrotas, gols_pro, gols_contra, saldo_gols ) VALUES"
				+ "( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			preparedStatement.setInt(1, pais);
			preparedStatement.setInt(2, copa);
			preparedStatement.setInt(3, confederacao);
			preparedStatement.setInt(4, colocacao);
			preparedStatement.setInt(5, jogos);
			preparedStatement.setInt(6, vitorias);
			preparedStatement.setInt(7, empates);
			preparedStatement.setInt(8, derrotas);
			preparedStatement.setInt(9, gols_pro);
			preparedStatement.setInt(10, gols_contra);
			preparedStatement.setInt(11, saldo_gols);
                        
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

	private static java.sql.Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());

	}

}