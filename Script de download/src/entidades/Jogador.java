package entidades;

import java.util.*;

public class Jogador implements Comparable<Jogador>
{
    public int id;
    public short numero;
    public boolean titular;
    public char posicao;
    public String status;
    public ArrayList<Cartao> cartoes=new ArrayList();

    @Override
    public int compareTo(Jogador t)
    {
        return Integer.compare(id, t.id);
    }
}
