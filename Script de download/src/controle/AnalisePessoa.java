package controle;

import entidades.*;
import java.io.*;
import java.nio.file.*;
import java.util.logging.*;
import org.jsoup.*;

public class AnalisePessoa implements Runnable
{
    public String link;
    public Pessoa pessoa;

    public AnalisePessoa(String link, Pessoa pessoa)
    {
        this.link=link;
        this.pessoa=pessoa;
    }

    @Override
    public void run()
    {
        try
        {
            pessoa.pais=Main.obterPagina(link, true).getElementsByClass("t-nTri").get(0).text().trim();
        }
        catch(HttpStatusException|NoSuchFileException ex)
        {
            System.out.println("Erro 404");
            System.out.println(link);
        }
        catch(IOException ex)
        {
            Logger.getLogger(AnalisePessoa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
