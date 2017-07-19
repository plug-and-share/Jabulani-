package controle;

import entidades.*;
import java.io.*;
import java.util.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class ListaPartidas
{
    public static void listar(String link, Copa copa) throws IOException
    {
        Elements elementos=Main.obterPagina(link, true).getElementsByClass("mu-m-link");
        TreeSet<String> linksPartidas=new TreeSet();
        for(Element elemento: elementos)
            linksPartidas.add(elemento.attr("href"));
        for(String linkPartida: linksPartidas)
            new AnalisePartida().analisar(linkPartida, copa);
        for(Partida partida: copa.partidas)
            copa.sedes.add(partida.estadio.pais);
    }
}
