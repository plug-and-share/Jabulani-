package controle;

import static controle.Main.confederacoes;
import static controle.Main.paises;
import entidades.*;
import java.io.*;
import java.util.*;
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

    public void analisarSelecoes(Element body, String confederacao)
    {
        Elements elementos=body.getElementsByTag("img");
        for(Element elemento: elementos)
        {
            String texto=elemento.attr("src");
            texto=texto.substring(texto.lastIndexOf('/')+1, texto.lastIndexOf('.')).toUpperCase();
            copa.selecoes.get(texto).confederacao=confederacao;
        }
    }

    @Override
    public void run()
    {
        Element body=null;
        try
        {
            body=Main.obterPagina(link, true);
        }
        catch(IOException ex)
        {
            Logger.getLogger(ListaSelecoes.class.getName()).log(Level.SEVERE, null, ex);
        }
        Elements elementos=body.getElementsByClass("map-item");
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
            copa.selecoes.put(pais.codigo, selecao);
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
        elementos=body.getElementsByClass("qlinkbanner-list").get(0).getElementsByClass("item");
        for(Element elemento: elementos)
            confederacoes.put(elemento.text().split(",")[0].trim(), new Confederacao());
        elementos=body.getElementsByClass("col-xs-2");
        int i=0;
        for(Map.Entry<String, Confederacao> confederacao: confederacoes.entrySet())
            analisarSelecoes(elementos.get(i++), confederacao.getKey());
    }
}
