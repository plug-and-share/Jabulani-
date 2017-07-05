package controle;

import entidades.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class Main
{
    public static ArrayList<Copa> copas=new ArrayList();
    public static List<Estadio> estadios=Collections.synchronizedList(new ArrayList());
    public static SortedSet<Pais> paises=Collections.synchronizedSortedSet(new TreeSet());
    public static SortedSet<Pessoa> arbitros=Collections.synchronizedSortedSet(new TreeSet());
    public static SortedSet<Pessoa> jogadores=Collections.synchronizedSortedSet(new TreeSet());
    public static SortedSet<String> fases=Collections.synchronizedSortedSet(new TreeSet());
    public static SortedSet<String> status=Collections.synchronizedSortedSet(new TreeSet());
    public static SortedSet<String> tempo=Collections.synchronizedSortedSet(new TreeSet());
    public static SortedSet<Character> posicoes=Collections.synchronizedSortedSet(new TreeSet());
    public static final String PAGINA_INICIAL="http://www.fifa.com";
    public static final String PASTA_INICIAL="D:\\Trabalhos escolares\\2017\\Banco de Dados\\FIFA";
    public static boolean cache=true;

    public static void listarCopas(String link) throws IOException, InterruptedException
    {
        int indice;
        Elements elementos=obterPagina(link).getElementsByClass("slider-complink");
        ExecutorService es=Executors.newFixedThreadPool(10);
        for(Element elemento: elementos)
        {
            Copa copa=new Copa();
            String ano=elemento.text();
            ano=ano.substring(ano.lastIndexOf(' ')+1).trim();
            copa.ano=Short.parseShort(ano);
            if(copa.ano>2017)
                break;
            copas.add(copa);
            String linkCopa=elemento.attr("href");
            copa.link=linkCopa.substring(0, linkCopa.lastIndexOf('/'));
            es.execute(new ListaSelecoes(copa.link+"/teams/index.html", copa));
            es.execute(new ListaPartidas(copa.link+"/matches/index.html", copa));
        }
        es.shutdown();
        es.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        es=Executors.newFixedThreadPool(100);
        for(Pessoa jogador: jogadores)
            es.execute(new AnalisePessoa("/fifa-tournaments/players-coaches/people="+jogador.id+"/library/_people_detail.htmx", jogador));
        es.shutdown();
        es.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    }

    public static Element obterPagina(String link) throws IOException
    {
        Path arquivo=Paths.get(PASTA_INICIAL+link);
        if(!cache)
        {
            String pagina=Jsoup.connect(PAGINA_INICIAL+link).get().html();
            Files.createDirectories(arquivo.getParent());
            Files.write(arquivo, pagina.getBytes());
        }
        return Jsoup.parse(Files.newInputStream(arquivo), null, PAGINA_INICIAL+link).body();
    }

    public static void main(String[] args) throws IOException, InterruptedException
    {
        listarCopas("/worldcup/index.html");
        for(Copa copa: copas)
        {
            System.out.println(copa.ano);
            System.out.println("\nSeleções:\n");
            for(Selecao selecao: copa.selecoes)
            {
                System.out.println(selecao.pais);
                System.out.println("");
                for(JogadorCopa jogadorCopa: selecao.jogadores)
                    System.out.printf("%d : %d (%c)\n", jogadorCopa.numero, jogadorCopa.jogador, jogadorCopa.posicao);
                System.out.println("");
            }
            for(Partida partida: copa.partidas)
            {
                System.out.printf("Partida: %d\n\n", partida.id);
                System.out.printf("%s : %s : %s\n", partida.estadio.pais, partida.estadio.cidade, partida.estadio.nome);
                System.out.println(partida.fase);
                System.out.printf("%s X %s\n", partida.selecao1, partida.selecao2);
                System.out.println(partida.hora);
                System.out.printf("Árbitro: %s - %s\n", partida.arbitro.nome, partida.arbitro.pais);
                System.out.printf("Assistente 1: %s - %s\n", partida.assistente1.nome, partida.assistente1.pais);
                System.out.printf("Assistente 2: %s - %s\n", partida.assistente2.nome, partida.assistente2.pais);
                if(partida.arbitro4!=null)
                    System.out.printf("4º árbitro: %s - %s\n", partida.arbitro4.nome, partida.arbitro4.pais);
                System.out.printf("Grupo: %c\n", partida.grupo);
                System.out.printf("Público: %d\n", partida.publico);
                System.out.printf("Gols: %d X %d\n", partida.gols1, partida.gols2);
                System.out.printf("Capitão 1: %d\n", partida.capitao1);
                System.out.printf("Capitão 2: %d\n", partida.capitao2);
                System.out.printf("MVP: %d\n", partida.mvp);
                System.out.printf("Técnico 1: %s : %s\n", partida.tecnico1.nome, partida.tecnico1.pais);
                System.out.printf("Técnico 2: %s : %s\n", partida.tecnico2.nome, partida.tecnico2.pais);
                if(partida.prorrogacao)
                    System.out.println("Prorrogação");
                System.out.println(partida.tempo);
                System.out.println(partida.temperatura);
                System.out.println(partida.vento);
                System.out.println(partida.umidade);
                System.out.println("\nJogadores 1:\n");
                for(JogadorPartida jogadorPartida: partida.jogadores1)
                    System.out.printf("%d : %s : %s %s\n", jogadorPartida.numero, jogadorPartida.jogador, jogadorPartida.titular ? "titular" : "reserva", jogadorPartida.status);
                System.out.println("\nJogadores 2:\n");
                for(JogadorPartida jogadorPartida: partida.jogadores1)
                {
                    System.out.printf("%d : %s : %s %s\n", jogadorPartida.numero, jogadorPartida.jogador, jogadorPartida.titular ? "titular" : "reserva", jogadorPartida.status);
                    for(Cartao cartao: jogadorPartida.cartoes)
                        System.out.printf("    %s - %s : %s\n", cartao.vermelho ? "vermelho" : "amarelo", cartao.minuto, cartao.acrescimo);
                }
                System.out.println("\nGols 1:\n");
                for(Gol gol: partida.listaGols1)
                    System.out.printf("%d - %d : %d%s%s\n", gol.autor, gol.minuto, gol.acrescimo, gol.contra ? " (C)" : "", gol.penalti ? " (P)" : "");
                System.out.println("\nGols 2:\n");
                for(Gol gol: partida.listaGols2)
                    System.out.printf("%d - %d : %d%s%s\n", gol.autor, gol.minuto, gol.acrescimo, gol.contra ? " (C)" : "", gol.penalti ? " (P)" : "");
                if(partida.disputaPenaltis)
                {
                    System.out.printf("Pênaltis: %d X %d\n", partida.disputaPenaltis1, partida.disputaPenaltis2);
                    System.out.println("\nPênaltis 1:\n");
                    for(Penalti penalti: partida.penaltis1)
                        System.out.printf("%d - %d %s\n", penalti.sequencia, penalti.cobrador, penalti.convertido ? "Gol" : "");
                    System.out.println("\nPênaltis 2:\n");
                    for(Penalti penalti: partida.penaltis2)
                        System.out.printf("%d - %d %s\n", penalti.sequencia, penalti.cobrador, penalti.convertido ? "Gol" : "");
                }
                System.out.println("\nSubstituições 1:\n");
                for(Substituicao substituicao: partida.substituicoes1)
                    System.out.printf("%s - %d : %d - %d : %d %s\n", substituicao.id, substituicao.substituido, substituicao.substituto, substituicao.minuto, substituicao.acrescimo, substituicao.intervalo ? "Intervalo" : "");
                System.out.println("");
            }
        }
        for(Estadio estadio: estadios)
            System.out.printf("%s : %s : %s : %d : %d\n", estadio.pais, estadio.cidade, estadio.nome, estadio.anoConstrucao, estadio.capacidade);
        for(Pais pais: paises)
            System.out.printf("%s - %s\n", pais.codigo, pais.nome);
        for(String fase: fases)
            System.out.println(fase);
        for(Pessoa arbitro: arbitros)
            System.out.printf("%s : %s\n", arbitro.pais, arbitro.nome);
        for(Character posicao: posicoes)
            System.out.println(posicao);
        for(Pessoa jogador: jogadores)
            System.out.printf("%d : %s : %s : %s\n", jogador.id, jogador.pais, jogador.nome, jogador.dataNascimento);
        for(String s: status)
            System.out.println(s);
        for(String t: tempo)
            System.out.println(t);
    }
}
