package controle;

import entidades.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class ListaPartidas implements Runnable
{
    public String link;
    public Copa copa;

    public ListaPartidas(String link, Copa copa)
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
            elementos=Main.obterPagina(link, true).getElementsByClass("mu-m-link");
        }
        catch(IOException ex)
        {
            Logger.getLogger(ListaPartidas.class.getName()).log(Level.SEVERE, null, ex);
        }
        TreeSet<String> linksPartidas=new TreeSet();
        for(Element elemento: elementos)
            linksPartidas.add(elemento.attr("href"));
        ExecutorService es=Executors.newCachedThreadPool();
        for(String linkPartida: linksPartidas)
            es.execute(new AnalisePartida(linkPartida, copa));
        es.shutdown();
        try
        {
            es.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        }
        catch(InterruptedException ex)
        {
            Logger.getLogger(ListaPartidas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
