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

public class InserirPartida {

    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_CONNECTION = "jdbc:oracle:thin:@localhost:1521:MKYONG";
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "password";
    private static char grupo;
    private static int copa, fase, sequencia, pais1, pais2, hora, estadio, publico, bola, acrescimo1, acrescimo2 , acrescimo3, acrescimo4, tecnico1, tecnico2, tecnico3;
    private static int tecnico4, mvp, arbitro, assistente1, assistente2, arbitro4, comissario_partida, coordenador_geral, gols1, gols2, disputa_penaltis1, disputa_penaltis2, posse1, posse2;
    private static int tempo_posse1, tempo_posse2, chutes1, chutes2, chutes_gol1, chutes_gol2, faltas_cometidas1, faltas_cometidas2, faltas_sofridas1, faltas_sofridas2, escanteios1;
    private static int escanteios2, tiros_diretos1, tiros_diretos2, tiros_indiretos1, tiros_indiretos2, penaltis_cobrados1, penaltis_cobrados2, penaltis_convertidos1, penaltis_convertidos2;
    private static int impedimentos1, impedimentos2, gols_contra1, gols_contra2, advertencias1, advertencias2, expulsoes_indiretas1, expulsoes_indiretas2, expulsoes_diretas1;
    private static int expulsoes_diretas2, tempo, temperatura, vento, umidade;
    private static boolean prorrogacao, disputa_penalti;

    public InserirPartida() {

        try {
            insertRecordIntoTable(copa, fase, grupo, sequencia, pais1, pais2, hora, estadio, publico, bola, acrescimo1, acrescimo2 , acrescimo3, acrescimo4, tecnico1, tecnico2, tecnico3, tecnico4, mvp, arbitro, assistente1, assistente2, arbitro4, comissario_partida, coordenador_geral, gols1, gols2, disputa_penaltis1, disputa_penaltis2, posse1, posse2, tempo_posse1, tempo_posse2, chutes1, chutes2, chutes_gol1, chutes_gol2, faltas_cometidas1, faltas_cometidas2, faltas_sofridas1, faltas_sofrida2, escanteios1, escanteios2, tiros_diretos1, tiros_diretos2, tiros_indiretos1, tiros_indiretos2, penaltis_cobrados1, penaltis_cobrados2, penaltis_convertidos1, penaltis_convertidos2, impedimentos1, impedimentos2, gols_contra1, gols_contra2, advertencias1, advertencias2, expulsoes_indiretas1, expulsoes_indiretas2, expulsoes_diretas1, expulsoes_diretas2, tempo, temperatura, vento, umidade, prorrogacao, disputa_penalti);

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

    }

    private static void insertRecordIntoTable( int copa, int fase, char grupo, int sequencia, int pais1, int pais2, int hora, int estadio, int publico, int bola, int acrescimo1, int acrescimo2 , int acrescimo3, int acrescimo4, int tecnico1, int tecnico2, int tecnico3, int tecnico4, int mvp, int arbitro, int assistente1, int assistente2, int arbitro4, int comissario_partida, int coordenador_geral, int gols1, int gols2, int disputa_penaltis1, int disputa_penaltis2, int posse1, int posse2, int tempo_posse1, int tempo_posse2, int chutes1, int chutes2, int chutes_gol1, int chutes_gol2, int faltas_cometidas1, int faltas_cometidas2, int faltas_sofridas1, int faltas_sofrida2, int escanteios1, int escanteios2, int tiros_diretos1, int tiros_diretos2, int tiros_indiretos1, int tiros_indiretos2, int penaltis_cobrados1, int penaltis_cobrados2, int penaltis_convertidos1, int penaltis_convertidos2, int impedimentos1, int impedimentos2, int gols_contra1, int gols_contra2, int advertencias1, int advertencias2, int expulsoes_indiretas1, int expulsoes_indiretas2, int expulsoes_diretas1, int expulsoes_diretas2, int tempo, int temperatura, int vento, int umidade, boolean prorrogacao, boolean disputa_penalti ) throws SQLException {

        Connection dbConnection = null;
        PreparedStatement prepState = null;

        String insertTableSQL = "INSERT INTO partida"
                + "( copa, fase, grupo, sequencia, pais1, pais2, hora, estadio, publico, bola, acrescimo1, acrescimo2 , acrescimo3, acrescimo4, tecnico1, tecnico2, tecnico3,"
                + "tecnico4, mvp, arbitro, assistente1, assistente2, arbitro4, comissario_partida, coordenador_geral, gols1, gols2, disputa_penaltis1, disputa_penaltis2, posse1,"
                + "posse2, tempo_posse1, tempo_posse2, chutes1, chutes2, chutes_gol1, chutes_gol2, faltas_cometidas1, faltas_cometidas2, faltas_sofridas1, faltas_sofridas2, escanteios1,"
                + "escanteios2, tiros_diretos1, tiros_diretos2, tiros_indiretos1, tiros_indiretos2, penaltis_cobrados1, penaltis_cobrados2, penaltis_convertidos1, penaltis_convertidos2,"
                + "impedimentos1, impedimentos2, gols_contra1, gols_contra2, advertencias1, advertencias2, expulsoes_indiretas1, expulsoes_indiretas2, expulsoes_diretas1,"
                + "expulsoes_diretas2, tempo, temperatura, vento, umidade, prorrogacao, disputa_penalti ) VALUES"
                + "( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

        try {
            dbConnection = getDBConnection();
            prepState = dbConnection.prepareStatement(insertTableSQL);

            prepState.setInt(1, copa);
            prepState.setInt(2, fase);
            prepState.setChar(3, grupo);
            prepState.setInt(4, sequencia);
            prepState.setInt(5, pais1 );
            prepState.setInt(6, pais2);
            prepState.setInt(7, hora);
            prepState.setInt(8, estadio);
            prepState.setInt(9, publico);
            prepState.setInt(10, bola);
            prepState.setInt(11, acrescimo1);
            prepState.setInt(12, acrescimo2);
            prepState.setInt(13, acrescimo3);
            prepState.setInt(14, acrescimo4);
            prepState.setInt(15, tecnico1);
            prepState.setInt(16, tecnico2);
            prepState.setInt(17, tecnico3);
            prepState.setInt(18, tecnico4);
            prepState.setInt(19, mvp);
            prepState.setInt(20, disputa_penalti);
            prepState.setInt(21, disputa_penaltis1);
            prepState.setInt(22, disputa_penaltis2);
            prepState.setInt(23, posse1);
            prepState.setInt(24, posse2);
            prepState.setInt(25, tempo_posse1);
            prepState.setInt(26, tempo_posse2);
            prepState.setInt(27, chutes1);
            prepState.setInt(28, chutes2);
            prepState.setInt(29, chutes_gol1);
            prepState.setInt(30, chutes_gol2);
            prepState.setInt(31, faltas_cometidas1);
            prepState.setInt(32, faltas_cometidas2);
            prepState.setInt(33, faltas_sofridas1);
            prepState.setInt(34, faltas_sofridas2);
            prepState.setInt(35, escanteios1);
            prepState.setInt(36, escanteios2);
            prepState.setInt(37, tiros_diretos1);
            prepState.setInt(38, tiros_diretos2);
            prepState.setInt(39, tiros_indiretos1);
            prepState.setInt(40, tiros_indiretos2);
            prepState.setInt(41, penaltis_cobrados1);
            prepState.setInt(42, penaltis_cobrados2);
            prepState.setInt(43, penaltis_convertidos1);
            prepState.setInt(44, penaltis_convertidos2);
            prepState.setInt(45, impedimentos1);
            prepState.setInt(46, impedimentos2);
            prepState.setInt(47, gols_contra1);
            prepState.setInt(48, gols_contra2);
            prepState.setInt(49, advertencias1);
            prepState.setInt(50, advertencias2);
            prepState.setInt(51, expulsoes_indiretas1);
            prepState.setInt(52, expulsoes_indiretas2);
            prepState.setInt(53, expulsoes_diretas1);
            prepState.setInt(54, expulsoes_diretas2);
            prepState.setInt(55, tempo);
            prepState.setInt(56, temperatura);
            prepState.setInt(57, vento);
            prepState.setInt(58, umidade);
            prepState.setBoolean(59, prorrogacao);
            prepState.setBoolean(60, disputa_penalti);


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