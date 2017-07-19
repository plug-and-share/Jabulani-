package controle;

import static controle.Main.confederacoes;
import static controle.Main.paises;
import entidades.*;
import java.io.*;
import java.util.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class ListaSelecoes
{
    public String link;
    public Copa copa;

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

    public void listar(String link, Copa copa) throws IOException
    {
        this.copa=copa;
        Element body=Main.obterPagina(link, true);
        Elements elementos=body.getElementsByClass("map-item");
        int i=0;
        for(Element elemento: elementos)
        {
            String pais=elemento.id().trim();
            paises.put(pais, elemento.attr("title"));
            String texto=elemento.attr("href");
            texto=texto.substring(texto.lastIndexOf('=')+1, texto.lastIndexOf('/'));
            texto="/worldcup/archive/edition="+copa.ano+"/library/teams/team="+texto+"/_players/_players_list.html";
            Selecao selecao=new Selecao();
            selecao.id=(copa.ano<<16)|i++;
            copa.selecoes.put(pais, selecao);
            AnaliseSelecao.analisar(texto, selecao);
        }
        elementos=body.getElementsByClass("qlinkbanner-list").get(0).getElementsByClass("item");
        for(Element elemento: elementos)
            confederacoes.put(elemento.text().split(",")[0].trim(), new Confederacao());
        elementos=body.getElementsByClass("col-xs-2");
        i=0;
        for(Map.Entry<String, Confederacao> confederacao: confederacoes.entrySet())
            analisarSelecoes(elementos.get(i++), confederacao.getKey());
    }
}
