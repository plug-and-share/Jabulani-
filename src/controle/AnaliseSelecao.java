package controle;

import static controle.Main.jogadores;
import static controle.Main.posicoes;
import entidades.*;
import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.logging.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class AnaliseSelecao implements Runnable
{
    public String link;
    public Selecao selecao;

    public AnaliseSelecao(String link, Selecao selecao)
    {
        this.link=link;
        this.selecao=selecao;
    }

    @Override
    public void run()
    {
        Element body=null;
        try
        {
            body=Main.obterPagina(link);
        }
        catch(IOException ex)
        {
            Logger.getLogger(AnaliseSelecao.class.getName()).log(Level.SEVERE, null, ex);
        }
        Elements elementos=body.getElementsByClass("p-i-no");
        if(elementos.isEmpty())
            elementos=body.getElementsByClass("p-i-prt-1");
        for(Element elemento: elementos)
        {
            Pessoa pessoa=new Pessoa();
            JogadorCopa jogadorCopa=new JogadorCopa();
            String texto=elemento.attr("data-player-id").trim();
            pessoa.id=Integer.parseInt(texto);
            pessoa.nome=elemento.attr("data-player-name").trim();
            try
            {
                pessoa.dataNascimento=LocalDate.parse(elemento.getElementsByClass("data").get(0).attr("data-birthdate"));
            }
            catch(DateTimeParseException e)
            {
            }
            jogadores.add(pessoa);
            jogadorCopa.jogador=pessoa.id;
            try
            {
                jogadorCopa.posicao=elemento.attr("data-player-role").trim().charAt(0);
                posicoes.add(jogadorCopa.posicao);
            }
            catch(StringIndexOutOfBoundsException e)
            {
                jogadorCopa.posicao=0;
            }
            try
            {
                jogadorCopa.numero=Short.parseShort(elemento.getElementsByClass("p-i-bibnum").get(0).text());
            }
            catch(NumberFormatException e)
            {
                jogadorCopa.numero=0;
            }
            selecao.jogadores.add(jogadorCopa);
        }
    }
}
