package controle;

import static controle.Main.premios;
import entidades.*;
import java.io.*;
import java.util.logging.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class AnalisePremios implements Runnable
{
    public String link;
    public Copa copa;

    public AnalisePremios(String link, Copa copa)
    {
        this.link=link;
        this.copa=copa;
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
            Logger.getLogger(AnalisePremios.class.getName()).log(Level.SEVERE, null, ex);
        }
        Elements elementos=body.getElementsByClass("row");
        for(Element elemento: elementos)
        {
            Elements elementos2=elemento.getElementsByClass("header-wrap");
            for(Element elemento2: elementos2)
            {
                String premio=elemento2.getElementsByTag("span").get(0).text().trim();
                if(premio.equals("Final Tournament Standings"))
                    continue;
                Element elemento3=elemento2.nextElementSibling();
                Elements elementos3=elemento3.getElementsByTag("h2");
                if(elementos3.size()>0)
                {
                    JogadorPremiado jogador=new JogadorPremiado();
                    jogador.jogador.nome=elementos3.get(0).text().trim();
                    jogador.jogador.pais=elemento3.getElementsByClass("t-nTri").get(0).text().trim();
                    jogador.premio=premio;
                    copa.jogadoresPremiados.add(jogador);
                }
                else
                {
                    elementos3=elemento3.getElementsByClass("final-trn-list");
                    if(elementos3.isEmpty())
                        continue;
                    Elements elementos4=elementos3.get(0).getElementsByClass("p");
                    if(elementos4.size()>0)
                        for(Element elemento4: elementos4)
                        {
                            JogadorPremiado jogador=new JogadorPremiado();
                            jogador.jogador.id=Integer.parseInt(elemento4.attr("data-player-id").trim());
                            Elements elementos5=elemento4.getElementsByTag("h3");
                            if(elementos5.isEmpty())
                                jogador.premio=premio;
                            else
                            {
                                String colocacao=elementos5.get(0).text().trim();
                                jogador.premio=premio+(!colocacao.equals("1") ? " - "+colocacao : "");
                            }
                            copa.jogadoresPremiados.add(jogador);
                        }
                    else
                    {
                        elementos3=elementos3.get(0).getElementsByClass("t");
                        for(Element elemento4: elementos3)
                        {
                            SelecaoPremiada selecao=new SelecaoPremiada();
                            elementos4=elemento4.getElementsByClass("t-nTri");
                            if(elementos4.size()>0)
                                selecao.selecao=elementos4.get(0).getElementsByTag("a").get(0).text().trim();
                            else
                                selecao.selecao=elemento4.getElementsByClass("flag").get(0).attr("alt").trim();
                            selecao.premio=premio;
                            copa.selecoesPremiadas.add(selecao);
                        }
                    }
                }
                premios.add(premio);
            }
        }
    }
}
