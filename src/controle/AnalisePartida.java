package controle;

import static controle.Main.arbitros;
import static controle.Main.estadios;
import static controle.Main.fases;
import static controle.Main.status;
import static controle.Main.tempo;
import entidades.*;
import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.logging.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class AnalisePartida implements Runnable
{
    public String link;
    public Copa copa;
    public Partida partida;

    public AnalisePartida(String link, Copa copa)
    {
        this.link=link;
        this.copa=copa;
    }

    public void analisarGols(Element body, ArrayList<Gol> gols)
    {
        Elements elementos=body.getElementsByClass("mh-scorer");
        for(Element elemento: elementos)
        {
            int autor=Integer.parseInt(elemento.getElementsByClass("p-i-no").get(0).attr("data-player-id"));
            Elements elementosGols=elemento.getElementsByClass("ml-scorer-evmin").get(0).children();
            for(Element elementoGol: elementosGols)
            {
                Gol gol=new Gol();
                String texto=elementoGol.text();
                gol.autor=autor;
                gol.minuto=Byte.parseByte(texto.substring(0, texto.indexOf('\'')));
                int i=texto.indexOf('+');
                if(i==-1)
                    gol.acrescimo=0;
                else
                {
                    int j;
                    for(j=i+1; j<texto.length()&&Character.isDigit(texto.charAt(j)); j++);
                    gol.acrescimo=Byte.parseByte(texto.substring(i+1, j));
                }
                gol.contra=texto.contains("OG");
                gol.penalti=texto.contains("PEN");
                gols.add(gol);
            }
        }
    }

    public void analisarArbitro(Element body, Pessoa arbitro)
    {
        String texto=body.text();
        int i=texto.lastIndexOf('(');
        arbitro.nome=texto.substring(0, i).trim();
        arbitro.pais=texto.substring(i+1, texto.lastIndexOf(')')).trim();
        arbitro.id=-1;
        arbitros.add(arbitro);
    }

    public void analisarCartoes(Elements elementos, ArrayList<Cartao> cartoes, boolean vermelho)
    {
        for(Element elemento: elementos)
        {
            Cartao cartao=new Cartao();
            cartao.vermelho=vermelho;
            String texto=elemento.attr("title");
            cartao.minuto=Byte.parseByte(texto.substring(texto.lastIndexOf('-')+1, texto.lastIndexOf('\'')).trim());
            int i=texto.lastIndexOf('+');
            cartao.acrescimo=i!=-1 ? Byte.parseByte(texto.substring(i+1)) : 0;
            cartoes.add(cartao);
        }
    }

    public void analisarSubstituido(Element body, ArrayList<Substituicao> substituicoes, int jogador)
    {
        Substituicao substituicao=new Substituicao();
        substituicao.id=body.attr("data-guid").trim();
        substituicao.substituido=jogador;
        String texto=body.attr("title");
        substituicao.minuto=Byte.parseByte(texto.substring(texto.lastIndexOf('-')+1, texto.lastIndexOf('\'')).trim());
        int i=texto.lastIndexOf('+');
        substituicao.acrescimo=i!=-1 ? Byte.parseByte(texto.substring(i+1)) : 0;
        substituicao.intervalo=body.classNames().contains("halftime");
        substituicoes.add(substituicao);
    }

    public void analisarSubstituto(Element body, ArrayList<Substituicao> substituicoes, int jogador)
    {
        Substituicao s1=new Substituicao();
        s1.id=body.attr("data-guid").trim();
        s1.substituto=jogador;
        String texto=body.attr("title");
        s1.minuto=Byte.parseByte(texto.substring(texto.lastIndexOf('-')+1, texto.lastIndexOf('\'')).trim());
        int i=texto.lastIndexOf('+');
        s1.acrescimo=i!=-1 ? Byte.parseByte(texto.substring(i+1)) : 0;
        s1.intervalo=body.classNames().contains("halftime");
        for(Substituicao s2: substituicoes)
            if((s2.id.equals("")&&s2.substituto==0&&s1.minuto==s2.minuto&&s1.acrescimo==s2.acrescimo&&s1.intervalo==s2.intervalo)||(!s2.id.equals("")&&s1.id.equals(s2.id)))
            {
                s2.substituto=jogador;
                return;
            }
        substituicoes.add(s1);
    }

    public int analisarJogadores(Elements elementos, ArrayList<JogadorPartida> jogadores, ArrayList<Substituicao> substituicoes, boolean titular)
    {
        int capitao=0;
        for(Element elemento: elementos)
        {
            JogadorPartida jogador=new JogadorPartida();
            Elements elementos2=elemento.getElementsByClass("p-i-no");
            if(elementos2.isEmpty())
                continue;
            jogador.jogador=Integer.parseInt(elementos2.get(0).attr("data-player-id"));
            jogador.numero=Short.parseShort(elementos2.get(0).getElementsByClass("p-i-bibnum").text());
            jogador.titular=titular;
            elementos2=elemento.getElementsByClass("p-k");
            if(elementos2.size()>0&&elementos2.get(0).text().contains("(C)"))
                capitao=jogador.jogador;
            elementos2=elemento.getElementsByTag("img");
            jogador.status=elementos2.size()>0 ? elementos2.get(0).attr("title") : "";
            status.add(jogador.status);
            analisarCartoes(elemento.getElementsByClass("yellow-card"), jogador.cartoes, false);
            analisarCartoes(elemento.getElementsByClass("yellow-card-second"), jogador.cartoes, true);
            analisarCartoes(elemento.getElementsByClass("red-card"), jogador.cartoes, true);
            elementos2=elemento.getElementsByClass("substitution-out");
            if(!elementos2.isEmpty())
                analisarSubstituido(elementos2.get(0), substituicoes, jogador.jogador);
            if(!titular)
            {
                elementos2=elemento.getElementsByClass("substitution-in");
                if(!elementos2.isEmpty())
                    analisarSubstituto(elementos2.get(0), substituicoes, jogador.jogador);
            }
            jogadores.add(jogador);
        }
        return capitao;
    }

    public void analisarTecnico(Element body, Pessoa tecnico)
    {
        tecnico.nome=body.ownText().trim();
        String texto=body.getElementsByTag("span").get(0).text();
        tecnico.pais=texto.substring(texto.indexOf('(')+1, texto.lastIndexOf(')')).trim();
        tecnico.id=-1;
    }

    public void analisarPenaltis(Elements elementos, ArrayList<Penalti> penaltis)
    {
        for(int i=0; i<elementos.size(); i++)
        {
            Penalti penalti=new Penalti();
            Elements elementosPenalti=elementos.get(i).getElementsByClass("p-i-no");
            if(elementosPenalti.isEmpty())
                continue;
            penalti.cobrador=Integer.parseInt(elementosPenalti.get(0).attr("data-player-id"));
            penalti.sequencia=(byte)(i+1);
            penalti.convertido=elementos.get(i).getElementsByClass("p-e").get(0).text().trim().equals("Goal");
            penaltis.add(penalti);
        }
    }

    public void analisarRelatorio(Element body)
    {
        partida.estadio.nome=body.getElementsByClass("mh-i-stadium").get(0).text().trim();
        Element elemento=body.getElementsByClass("mh-i-venue").get(0);
        partida.estadio.cidade=elemento.ownText().trim();
        String texto=elemento.getElementsByTag("span").get(0).text();
        partida.estadio.pais=texto.substring(texto.indexOf('(')+1, texto.lastIndexOf(')')).trim();
        texto=body.getElementsByClass("mh-i-round").get(0).text();
        if(texto.contains(" - "))
        {
            partida.fase=texto.substring(0, texto.lastIndexOf('-')).trim();
            partida.grupo=texto.trim().charAt(texto.length()-1);
        }
        else
        {
            partida.fase=texto.trim();
            partida.grupo='\0';
        }
        fases.add(partida.fase);
        texto=body.getElementsByClass("s-scoreText").get(0).text();
        int i=texto.indexOf('-');
        partida.gols1=Byte.parseByte(texto.substring(0, i));
        partida.gols2=Byte.parseByte(texto.substring(i+1));
        Elements elementos=body.getElementsByClass("t-nTri");
        partida.selecao1=elementos.get(0).text().trim();
        partida.selecao2=elementos.get(1).text().trim();
        elementos=body.getElementsByClass("mh-l-scorers");
        analisarGols(elementos.get(0), partida.listaGols1);
        analisarGols(elementos.get(1), partida.listaGols2);
        texto=body.getElementsByClass("match-name-result").get(0).getElementsByClass("result").get(0).text();
        partida.prorrogacao=texto.contains("a.e.t.");
        partida.disputaPenaltis=texto.contains("PSO");
        if(partida.disputaPenaltis)
        {
            i=texto.lastIndexOf(':');
            int j;
            for(j=i-1; j>=0&&Character.isDigit(texto.charAt(j)); j--);
            partida.disputaPenaltis1=Byte.parseByte(texto.substring(j+1, i));
            for(j=i+1; j<texto.length()&&Character.isDigit(texto.charAt(j)); j++);
            partida.disputaPenaltis2=Byte.parseByte(texto.substring(i+1, j));
        }
        elementos=body.getElementsByClass("match-data").get(0).getElementsByTag("td");
        partida.hora=LocalDateTime.parse(elementos.get(0).text()+' '+elementos.get(2).text(), DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm", Locale.ENGLISH));
        try
        {
            partida.publico=Integer.parseInt(elementos.get(3).text());
        }
        catch(NumberFormatException ex)
        {
            partida.publico=0;
        }
        elementos=body.getElementsByClass("people-name");
        analisarArbitro(elementos.get(0), partida.arbitro);
        analisarArbitro(elementos.get(1), partida.assistente1);
        analisarArbitro(elementos.get(2), partida.assistente2);
        if(elementos.size()>3)
        {
            partida.arbitro4=new Pessoa();
            analisarArbitro(elementos.get(3), partida.arbitro4);
        }
        elemento=body.getElementsByClass("fielded").get(0);
        partida.capitao1=analisarJogadores(elemento.getElementsByClass("home"), partida.jogadores1, partida.substituicoes1, true);
        partida.capitao2=analisarJogadores(elemento.getElementsByClass("away"), partida.jogadores2, partida.substituicoes2, true);
        elemento=body.getElementsByClass("substitutes").get(0);
        analisarJogadores(elemento.getElementsByClass("home"), partida.jogadores1, partida.substituicoes1, false);
        analisarJogadores(elemento.getElementsByClass("away"), partida.jogadores2, partida.substituicoes2, false);
        elementos=body.getElementsByClass("match-people");
        if(elementos.size()!=2)
            System.out.println(link);
        analisarTecnico(elementos.get(0), partida.tecnico1);
        analisarTecnico(elementos.get(1), partida.tecnico2);
        if(partida.disputaPenaltis)
        {
            elemento=body.getElementsByClass("penalty-shoot-out").get(0);
            analisarPenaltis(elemento.getElementsByClass("home"), partida.penaltis1);
            analisarPenaltis(elemento.getElementsByClass("away"), partida.penaltis2);
        }
    }

    public void baixarRelatorio() throws IOException
    {
        Element body=Main.obterPagina(link.replace("index", "report"));
        analisarRelatorio(body);
    }

    public void analisarResumo(Element body)
    {
        String texto=body.getElementsByClass("p-name").get(0).getElementsByTag("a").attr("href");
        partida.mvp=Integer.parseInt(texto.substring(texto.lastIndexOf('=')+1, texto.lastIndexOf('/')));
        try
        {
            partida.tempo=body.getElementsByClass("weather-description").get(0).text().trim();
            tempo.add(partida.tempo);
        }
        catch(IndexOutOfBoundsException e)
        {
            partida.tempo=null;
        }
        texto=body.getElementsByClass("temperature").get(0).text();
        try
        {
            partida.temperatura=Byte.parseByte(texto.substring(0, texto.indexOf('°')).trim());
        }
        catch(StringIndexOutOfBoundsException e)
        {
            partida.temperatura=Byte.MIN_VALUE;
        }
        texto=body.getElementsByClass("windspeed").get(0).text();
        try
        {
            partida.vento=Short.parseShort(texto.substring(0, texto.indexOf(' ')).trim());
        }
        catch(StringIndexOutOfBoundsException e)
        {
            partida.vento=-1;
        }
        texto=body.getElementsByClass("humidity").get(0).text();
        try
        {
            partida.umidade=Byte.parseByte(texto.substring(0, texto.indexOf(' ')).trim());
        }
        catch(StringIndexOutOfBoundsException e)
        {
            partida.umidade=-1;
        }
        Elements elementos=body.getElementsByClass("info");
        try
        {
            partida.estadio.anoConstrucao=Short.parseShort(elementos.get(2).ownText().trim());
        }
        catch(IndexOutOfBoundsException e)
        {
            partida.estadio.anoConstrucao=Short.MAX_VALUE;
        }
        try
        {
            partida.estadio.capacidade=Integer.parseInt(elementos.get(3).ownText().trim());
        }
        catch(IndexOutOfBoundsException e)
        {
            partida.estadio.capacidade=-1;
        }
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
            Logger.getLogger(AnalisePartida.class.getName()).log(Level.SEVERE, null, ex);
        }
        partida=new Partida();
        partida.id=Integer.parseInt(link.substring(link.lastIndexOf('=')+1, link.lastIndexOf('/')));
        if(body.text().contains("Official Match Report"))
        {
            try
            {
                baixarRelatorio();
            }
            catch(IOException ex)
            {
                Logger.getLogger(AnalisePartida.class.getName()).log(Level.SEVERE, null, ex);
            }
            analisarResumo(body);
        }
        else
        {
            analisarRelatorio(body);
            partida.mvp=0;
            partida.tempo=null;
            partida.temperatura=Byte.MIN_VALUE;
            partida.vento=-1;
            partida.umidade=-1;
            partida.estadio.anoConstrucao=Short.MAX_VALUE;
            partida.estadio.capacidade=-1;
        }
        int i=Collections.binarySearch(estadios, partida.estadio);
        if(i>=0)
        {
            Estadio e1=estadios.get(i);
            Estadio e2=partida.estadio;
            if(e1.anoConstrucao==Short.MAX_VALUE&&e2.anoConstrucao!=Short.MAX_VALUE)
                e1.anoConstrucao=e2.anoConstrucao;
            if(e1.capacidade==-1&&e2.capacidade!=-1)
                e1.capacidade=e2.capacidade;
        }
        else
        {
            estadios.add(partida.estadio);
            Collections.sort(estadios);
        }
        copa.partidas.add(partida);
    }
}
