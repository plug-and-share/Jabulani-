package controle;

import static controle.Main.jogadores;
import static controle.Main.posicoes;
import entidades.*;
import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class AnaliseSelecao
{
    public static void analisar(String link, Selecao selecao) throws IOException
    {
        Element body=Main.obterPagina(link, true);
        Elements elementos=body.getElementsByClass("p-i-no");
        if(elementos.isEmpty())
            elementos=body.getElementsByClass("p-i-prt-1");
        for(Element elemento: elementos)
        {
            Jogador jogador=new Jogador();
            String texto=elemento.attr("data-player-id").trim();
            int id=Integer.parseInt(texto);
            Pessoa pessoa=jogadores.get(id);
            if(pessoa==null)
            {
                pessoa=new Pessoa();
                pessoa.id=id;
                pessoa.nome=elemento.attr("data-player-name").trim();
                pessoa.jogador=pessoa.tecnico=pessoa.arbitro=false;
                try
                {
                    pessoa.dataNascimento=LocalDate.parse(elemento.getElementsByClass("data").get(0).attr("data-birthdate"));
                }
                catch(DateTimeParseException e)
                {
                }
                Elements elementos2=elemento.getElementsByClass("p-i-clubname");
                if(elementos2.size()>0)
                {
                    texto=elementos2.get(0).text();
                    pessoa.pais=texto.substring(texto.indexOf('(')+1, texto.lastIndexOf(')')).trim();
                }
                jogadores.put(id, pessoa);
            }
            jogador.jogador=id;
            try
            {
                jogador.posicao=elemento.attr("data-player-role").trim().charAt(0);
                posicoes.put(jogador.posicao, elemento.getElementsByClass("p-i-fieldpos").get(0).text().trim());
            }
            catch(StringIndexOutOfBoundsException e)
            {
                jogador.posicao=0;
            }
            if(jogador.posicao=='0')
                pessoa.tecnico=true;
            else
                pessoa.jogador=true;
            try
            {
                jogador.numero=Short.parseShort(elemento.getElementsByClass("p-i-bibnum").get(0).text());
            }
            catch(NumberFormatException e)
            {
                jogador.numero=0;
            }
            selecao.jogadores.add(jogador);
        }
        Collections.sort(selecao.jogadores);
    }
}
