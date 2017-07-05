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

public class InserirJogadorEscalado {

	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_CONNECTION = "jdbc:oracle:thin:@localhost:1521:MKYONG";
	private static final String DB_USER = "user";
	private static final String DB_PASSWORD = "password";
        private static int pais, posicao, numero, status, tempo_jogo, passes_completos, passes_tentados, precisao_passes, passes_recebidos, partida, jogador;
        private static int gols_marcados, gols_sofridos, penaltis_convertidos, penaltis_perdidos, chutes, chutes_gol, assistencias, impedimentos, defesas, advertencias, expulsoes_indiretas, expulsoes_diretas, faltas_cometidas, faltas_sofridas,distancia_percorrida, velocidade_maxima, arrancadas; 
        private static boolean titular;
        
        public InserirJogadorEscalado() {

		try {

			insertRecordIntoTable(partida, jogador, pais, posicao, numero, titular, status, tempo_jogo, passes_completos, passes_tentados, precisao_passes, passes_recebidos, gols_marcados, gols_sofridos, penaltis_convertidos, penaltis_perdidos, chutes, chutes_gol, assistencias, impedimentos, defesas, advertencias, expulsoes_indiretas, expulsoes_diretas, faltas_cometidas, faltas_sofridas,distancia_percorrida, velocidade_maxima, arrancadas);

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

	}

	private static void insertRecordIntoTable( int partida, int jogador, int pais, int posicao, int numero, boolean titular, int status, int tempo_jogo, int passes_completos, int  int passes_tentados, int  precisao_passes, int  passes_recebidos, int  gols_marcados, int  gols_sofridos, int  penaltis_convertidos, int  penaltis_perdidos, int  chutes, int  chutes_gol, int  assistencias, int  impedimentos, int  defesas, int  advertencias, int  expulsoes_indiretas, int  expulsoes_diretas, int  faltas_cometidas, int  faltas_sofridas, int distancia_percorrida, int  velocidade_maxima, int  arrancadas) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement prepState = null;

		String insertTableSQL = "INSERT INTO jogador_escalado"
				+ "( partida, jogador, pais, posicao, numero, titular, status, tempo_jogo, passes_completos, passes_tentados, precisao_passes, passes_recebidos, gols_marcados,"
                        + "        gols_sofridos, penaltis_convertidos, penaltis_perdidos, chutes, chutes_gol, assistencias, impedimentos, defesas, advertencias, expulsoes_indiretas, expulsoes_diretas,"
                        + "        faltas_cometidas, faltas_sofridas, distancia_percorrida, velocidade_maxima, arrancadas )  VALUES"
				+ "( ?, ? )";

		try {
			dbConnection = getDBConnection();
			prepState = dbConnection.prepareStatement(insertTableSQL);

			prepState.setInt(1, partida);
			prepState.setInt(2, jogador);
			prepState.setInt(3, pais);
			prepState.setInt(4, posicao);
			prepState.setInt(5, numero);
			prepState.setBoolean(6, titular);
			prepState.setInt(7, status);
			prepState.setInt(8, tempo_jogo);
			prepState.setInt(9, passes_completos);
			prepState.setInt(10, passes_tentados);
			prepState.setInt(11, precisao_passes);
			prepState.setInt(12, passes_recebidos);
			prepState.setInt(13, gols_marcados);
			prepState.setInt(14, gols_sofridos);
			prepState.setInt(15, penaltis_convertidos);
			prepState.setInt(16, penaltis_perdidos);
			prepState.setInt(17, chutes);
			prepState.setInt(18, chutes_gol);
			prepState.setInt(19, assistencias);
			prepState.setInt(20, impedimentos);
			prepState.setInt(21, defesas);
			prepState.setInt(22, advertencias);
			prepState.setInt(23, expulsoes_indiretas);
			prepState.setInt(24, expulsoes_diretas);
			prepState.setInt(25, faltas_cometidas);
			prepState.setInt(26, faltas_sofridas);
			prepState.setInt(27, distancia_percorrida);
			prepState.setInt(28, velocidade_maxima);
			prepState.setInt(29, arrancadas);
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