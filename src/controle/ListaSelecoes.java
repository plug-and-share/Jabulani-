package controle;

import static controle.Main.paises;
import entidades.*;
import java.io.*;
import java.util.concurrent.*;
import java.util.logging.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class ListaSelecoes implements Runnable
{
    public String link;
    public Copa copa;

    public ListaSelecoes(String link, Copa copa)
    {
        this.link=link;
        this.copa=copa;
    }

    @Override
    public void run()
    {
        Elements elementos=null;
        try
        {
            elementos=Main.obterPagina(link).getElementsByClass("map-item");
        }
        catch(IOException ex)
        {
            Logger.getLogger(ListaSelecoes.class.getName()).log(Level.SEVERE, null, ex);
        }
        ExecutorService es=Executors.newCachedThreadPool();
        for(Element elemento: elementos)
        {
            Pais pais=new Pais();
            pais.nome=elemento.attr("title").trim();
            pais.codigo=elemento.id().trim();
            paises.add(pais);
            String texto=elemento.attr("href");
            texto=texto.substring(texto.lastIndexOf('=')+1, texto.lastIndexOf('/'));
            texto="/worldcup/archive/edition="+copa.ano+"/library/teams/team="+texto+"/_players/_players_list.html";
            Selecao selecao=new Selecao();
            selecao.pais=pais.codigo;
            copa.selecoes.add(selecao);
            es.execute(new AnaliseSelecao(texto, selecao));
        }
        es.shutdown();
        try
        {
            es.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        }
        catch(InterruptedException ex)
        {
            Logger.getLogger(ListaSelecoes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
