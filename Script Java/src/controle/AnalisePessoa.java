package controle;

import entidades.*;
import java.io.*;

public class AnalisePessoa
{
    public static void analisar(String link, Pessoa pessoa)
    {
        try
        {
            pessoa.pais=Main.obterPagina(link, true).getElementsByClass("t-nTri").get(0).text().trim();
        }
        catch(IOException ex)
        {
        }
    }
}
