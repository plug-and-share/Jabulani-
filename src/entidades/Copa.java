package entidades;

import java.util.*;

public class Copa
{
    public short ano;
    public String link;
    public ArrayList<Selecao> selecoes=new ArrayList();
    public SortedSet<Partida> partidas=Collections.synchronizedSortedSet(new TreeSet());
}
