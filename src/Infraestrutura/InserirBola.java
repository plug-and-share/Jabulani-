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

public class InserirBola {

    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_CONNECTION = "jdbc:oracle:thin:@localhost:1521:MKYONG";
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "password";
    private static String nome;

    public InserirBola( String nome) {

        try {
            insertRecordIntoTable(nome);

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

    }

    private static void insertRecordIntoTable( String nome ) throws SQLException {

        Connection dbConnection = null;
        PreparedStatement prepState = null;

        String insertTableSQL = "INSERT INTO bola"
                + "( nome ) VALUES"
                + "( ? )";

        try {
            dbConnection = getDBConnection();
            prepState = dbConnection.prepareStatement(insertTableSQL);

            prepState.setString(1, nome);

            prepState.executeUpdate();

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (prepState != null) {
                prepState.close();
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