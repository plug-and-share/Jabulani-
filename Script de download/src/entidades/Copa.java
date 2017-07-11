package entidades;

import java.util.*;

public class Copa
{
    public short ano;
    public String link,musica,album,instrumento,trofeu;
    public ArrayList<String> mascotes=new ArrayList();
    public ArrayList<String> bolas=new ArrayList();
    public TreeMap<String,Selecao> selecoes=new TreeMap();
    public SortedSet<Partida> partidas=Collections.synchronizedSortedSet(new TreeSet());
    public ArrayList<JogadorPremiado> jogadoresPremiados=new ArrayList();
    public ArrayList<SelecaoPremiada> selecoesPremiadas=new ArrayList();
}
