package controle;

import entidades.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class Main
{
    public static TreeMap<Short, Copa> copas=new TreeMap();
    public static SortedMap<String, Confederacao> confederacoes=Collections.synchronizedSortedMap(new TreeMap());
    public static List<Estadio> estadios=Collections.synchronizedList(new ArrayList());
    public static SortedSet<Pais> paises=Collections.synchronizedSortedSet(new TreeSet());
    public static SortedSet<Pessoa> arbitros=Collections.synchronizedSortedSet(new TreeSet());
    public static SortedSet<Pessoa> jogadores=Collections.synchronizedSortedSet(new TreeSet());
    public static SortedSet<String> fases=Collections.synchronizedSortedSet(new TreeSet());
    public static SortedSet<String> status=Collections.synchronizedSortedSet(new TreeSet());
    public static SortedSet<String> tempo=Collections.synchronizedSortedSet(new TreeSet());
    public static SortedSet<String> premios=Collections.synchronizedSortedSet(new TreeSet());
    public static ArrayList<String> trofeus=new ArrayList();
    public static SortedSet<Character> posicoes=Collections.synchronizedSortedSet(new TreeSet());
    public static final String PASTA="D:/Trabalhos escolares/2017/Banco de Dados/Copa do Mundo/Dados/";
    public static boolean cache=true;

    public static void listarCopas(String link) throws IOException, InterruptedException
    {
        int indice;
        Elements elementos=obterPagina(link, true).getElementsByClass("slider-complink");
        ExecutorService es=Executors.newFixedThreadPool(10);
        for(Element elemento: elementos)
        {
            Copa copa=new Copa();
            String ano=elemento.text();
            copa.ano=Short.parseShort(ano.substring(ano.lastIndexOf(' ')+1).trim());
            if(copa.ano>2017)
                continue;
            copas.put(copa.ano, copa);
            String linkCopa=elemento.attr("href");
            copa.link=linkCopa.substring(0, linkCopa.lastIndexOf('/'));
            es.execute(new ListaSelecoes(copa.link+"/teams/index.html", copa));
            es.execute(new AnalisePremios(copa.link+"/awards/index.html", copa));
        }
        es.shutdown();
        es.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        es=Executors.newFixedThreadPool(1);
        for(Map.Entry<Short, Copa> copa: copas.entrySet())
            if(copa.getValue().ano<=2017)
                es.execute(new ListaPartidas(copa.getValue().link+"/matches/index.html", copa.getValue()));
        es.shutdown();
        es.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        es=Executors.newFixedThreadPool(500);
        for(Pessoa jogador: jogadores)
            es.execute(new AnalisePessoa("/fifa-tournaments/players-coaches/people="+jogador.id+"/library/_people_detail.htmx", jogador));
        es.shutdown();
        es.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
    }

    public static void analisarConfederacoes(String link) throws IOException
    {
        Elements elementos=obterPagina(link, false).getElementsByClass("wikitable").get(0).getElementsByTag("tr");
        for(int i=2; i<elementos.size(); i++)
        {
            Elements elementos2=elementos.get(i).getElementsByTag("th");
            if(elementos2.isEmpty())
                continue;
            Confederacao confederacao=confederacoes.get(elementos2.get(0).text().split(",")[0].trim());
            elementos2=elementos.get(i).getElementsByTag("td");
            confederacao.nome=elementos2.get(0).getElementsByTag("a").get(0).text().trim();
            confederacao.acronimo=elementos2.get(1).text().trim();
        }
    }

    public static void analisarSimbolos(String link) throws IOException
    {
        Element body=Main.obterPagina(link, false);
        Elements elementos=body.getElementsByClass("wikitable").get(0).getElementsByTag("tr");
        for(int i=1; i<elementos.size(); i++)
        {
            Elements elementos2=elementos.get(i).getElementsByTag("td");
            String texto=elementos2.get(0).getElementsByTag("a").get(0).text().trim();
            Copa copa=copas.get(Short.parseShort(texto.substring(texto.length()-4)));
            if(copa==null)
                continue;
            elementos2=elementos2.get(1).getElementsByTag("b");
            for(Element elemento: elementos2)
                copa.mascotes.add(elemento.text().trim());
        }
        elementos=body.getElementsByClass("nowraplinks").get(1).getElementsByTag("tr");
        Elements elementos2=elementos.get(1).getElementsByTag("li");
        for(Element elemento: elementos2)
        {
            Copa copa=copas.get(Short.parseShort(elemento.getElementsByTag("a").get(0).text().trim()));
            String texto=elemento.text();
            copa.musica=texto.substring(texto.indexOf('"')+1, texto.lastIndexOf('"')).trim();
        }
        elementos2=elementos.get(5).getElementsByTag("li");
        for(Element elemento: elementos2)
        {
            Copa copa=copas.get(Short.parseShort(elemento.getElementsByTag("a").get(0).text().trim()));
            copa.album=elemento.getElementsByTag("i").get(0).text().trim();
        }
        elementos2=elementos.get(6).getElementsByTag("li");
        for(Element elemento: elementos2)
        {
            Copa copa=copas.get(Short.parseShort(elemento.getElementsByTag("a").get(0).text().trim()));
            copa.instrumento=elemento.getElementsByTag("a").get(2).text().trim();
        }
    }

    public static void analisarTrofeus(String link) throws IOException
    {
        Element elemento=obterPagina(link, false).getElementsByTag("h2").get(3);
        while(true)
        {
            elemento=elemento.nextElementSibling();
            if(!elemento.tagName().equals("p"))
                break;
            String trofeu=elemento.getElementsByTag("b").get(0).text().trim();
            elemento=elemento.nextElementSibling();
            Elements elementos=elemento.getElementsByTag("li");
            for(Element elemento2: elementos)
            {
                Matcher m=Pattern.compile("\\d+").matcher(elemento2.ownText());
                while(m.find())
                    copas.get(Short.parseShort(m.group())).trofeu=trofeu;
            }
            trofeus.add(trofeu);
        }
    }

    public static void analisarBolas(String link) throws IOException
    {
        int linhas=0;
        boolean proximo=true;
        Copa copa=null;
        Elements elementos=obterPagina(link, false).getElementsByClass("wikitable").get(0).getElementsByTag("tr");
        for(int i=1; i<elementos.size(); i++)
        {
            String texto;
            Elements elementos2=elementos.get(i).getElementsByTag("td");
            Element elemento2=elementos2.get(0);
            if(linhas==0)
            {
                texto=elemento2.attr("rowspan");
                linhas=texto.isEmpty() ? 0 : Integer.parseInt(texto)-1;
                texto=elemento2.getElementsByTag("a").get(0).text().trim();
                if(!texto.matches("\\d+"))
                {
                    proximo=false;
                    continue;
                }
                copa=copas.get(Short.parseShort(texto));
                texto=elementos2.get(1).text();
            }
            else
            {
                linhas--;
                if(proximo)
                    texto=elemento2.text();
                else
                    continue;
            }
            String[] textos=texto.split("(\\([^)]*\\))|(\\[[^\\]]*\\])");
            for(String bola: textos)
                copa.bolas.add(bola.trim());
            proximo=true;
        }
    }

    public static Element obterPagina(String link, boolean fifa) throws IOException
    {
        String siteFifa="http://www.fifa.com", siteWikipedia="http://en.wikipedia.org";
        Path arquivo=Paths.get(PASTA+(fifa ? "FIFA" : "Wikipedia")+link);
        if(!cache)
        {
            String pagina=Jsoup.connect((fifa ? siteFifa : siteWikipedia)+link).get().html();
            Files.createDirectories(arquivo.getParent());
            Files.write(arquivo, pagina.getBytes());
        }
        return Jsoup.parse(Files.newInputStream(arquivo), null, (fifa ? siteFifa : siteWikipedia)+link).body();
    }

    public static void main(String[] args) throws IOException, InterruptedException
    {
        listarCopas("/worldcup/index.html");
        analisarConfederacoes("/wiki/Geography_of_association_football");
        analisarSimbolos("/wiki/FIFA_World_Cup_mascot");
        analisarTrofeus("/wiki/FIFA_World_Cup_Trophy");
        analisarBolas("/wiki/Ball_(association_football)");
        for(Map.Entry<Short, Copa> copa: copas.entrySet())
        {
            System.out.println(copa.getValue().ano);
            System.out.println("");
            System.out.println(copa.getValue().musica);
            System.out.println(copa.getValue().album);
            System.out.println(copa.getValue().instrumento);
            System.out.println(copa.getValue().trofeu);
            System.out.println("\nMascotes:\n");
            for(String mascote: copa.getValue().mascotes)
                System.out.println(mascote);
            System.out.println("\nBolas:\n");
            for(String bola: copa.getValue().bolas)
                System.out.println(bola);
            System.out.println("\nSeleções:\n");
            for(Map.Entry<String, Selecao> selecao: copa.getValue().selecoes.entrySet())
            {
                System.out.printf("%s (%s)\n", selecao.getKey(), selecao.getValue().confederacao);
                System.out.println("");
                for(Jogador jogador: selecao.getValue().jogadores)
                    System.out.printf("%d : %d (%c)\n", jogador.numero, jogador.id, jogador.posicao);
                System.out.println("");
            }
            for(Partida partida: copa.getValue().partidas)
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
                if(partida.golOuro)
                    System.out.println("Gol de ouro");
                System.out.println(partida.tempo);
                System.out.println(partida.temperatura);
                System.out.println(partida.vento);
                System.out.println(partida.umidade);
                System.out.println("\nJogadores 1:\n");
                for(Jogador jogador: partida.jogadores1)
                {
                    System.out.printf("%d : %s (%c) %s %s\n", jogador.numero, jogador.id, jogador.posicao, jogador.titular ? "titular" : "reserva", jogador.status);
                    for(Cartao cartao: jogador.cartoes)
                        System.out.printf("    %s - %s : %s\n", cartao.vermelho ? "vermelho" : "amarelo", cartao.minuto, cartao.acrescimo);
                }
                System.out.println("\nJogadores 2:\n");
                for(Jogador jogador: partida.jogadores2)
                {
                    System.out.printf("%d : %s (%c) %s %s\n", jogador.numero, jogador.id, jogador.posicao, jogador.titular ? "titular" : "reserva", jogador.status);
                    for(Cartao cartao: jogador.cartoes)
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
                System.out.println("\nSubstituições 2:\n");
                for(Substituicao substituicao: partida.substituicoes2)
                    System.out.printf("%s - %d : %d - %d : %d %s\n", substituicao.id, substituicao.substituido, substituicao.substituto, substituicao.minuto, substituicao.acrescimo, substituicao.intervalo ? "Intervalo" : "");
                System.out.println("");
            }
            System.out.println("\nJogadores premiados:\n");
            for(JogadorPremiado jogador: copa.getValue().jogadoresPremiados)
                System.out.printf("%d - %s - %s : %s\n", jogador.jogador.id, jogador.jogador.nome, jogador.jogador.pais, jogador.premio);
            System.out.println("\nSeleções premiadas:\n");
            for(SelecaoPremiada selecao: copa.getValue().selecoesPremiadas)
                System.out.printf("%s : %s\n", selecao.selecao, selecao.premio);
            System.out.println("");
        }
        for(Map.Entry<String, Confederacao> confederacao: confederacoes.entrySet())
            System.out.printf("%s : %s : %s\n", confederacao.getKey(), confederacao.getValue().acronimo, confederacao.getValue().nome);
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
        for(String trofeu: trofeus)
            System.out.println(trofeu);
    }
}
