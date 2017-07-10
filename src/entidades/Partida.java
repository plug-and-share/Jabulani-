package entidades;

import java.time.*;
import java.util.*;

public class Partida implements Comparable<Partida>
{
    public int id, publico, capitao1, capitao2, mvp;
    public char grupo;
    public boolean prorrogacao, disputaPenaltis, golOuro;
    public byte gols1, gols2, disputaPenaltis1, disputaPenaltis2, temperatura, umidade;
    public short vento;
    public String fase, selecao1, selecao2, tempo;
    public Estadio estadio=new Estadio();
    public LocalDateTime hora;
    public Pessoa arbitro=new Pessoa();
    public Pessoa assistente1=new Pessoa();
    public Pessoa assistente2=new Pessoa();
    public Pessoa arbitro4;
    public Pessoa tecnico1=new Pessoa();
    public Pessoa tecnico2=new Pessoa();
    public ArrayList<Jogador> jogadores1=new ArrayList();
    public ArrayList<Jogador> jogadores2=new ArrayList();
    public ArrayList<Gol> listaGols1=new ArrayList();
    public ArrayList<Gol> listaGols2=new ArrayList();
    public ArrayList<Penalti> penaltis1=new ArrayList();
    public ArrayList<Penalti> penaltis2=new ArrayList();
    public ArrayList<Substituicao> substituicoes1=new ArrayList();
    public ArrayList<Substituicao> substituicoes2=new ArrayList();

    @Override
    public int compareTo(Partida t)
    {
        return Integer.compare(id, t.id);
    }
}
