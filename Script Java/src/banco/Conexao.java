package banco;

import java.sql.*;

public class Conexao
{
    private static final String URL="jdbc:mysql://localhost:3306/copa_mundo", USUARIO="root", SENHA="root";

    public static Connection obter() throws SQLException
    {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
