package entidades;

import java.text.*;
import java.time.*;
import java.util.*;

public class Pessoa implements Comparable<Pessoa>
{
    public int id;
    public String nome, pais;
    public LocalDate dataNascimento;
    public boolean jogador, tecnico, arbitro;
    public TreeSet<String> nomeNormal;

    @Override
    public int compareTo(Pessoa t)
    {
        if(id>0&&t.id>0)
            return Integer.compare(id, t.id);
        int comparacao=pais.compareTo(t.pais);
        if(comparacao!=0)
            return comparacao;
        return nome.compareTo(t.nome);
    }

    @Override
    public boolean equals(Object obj)
    {
        return compareTo((Pessoa)obj)==0;
    }

    public TreeSet<String> getNomeNormal()
    {
        if(nomeNormal!=null)
            return nomeNormal;
        String texto=Normalizer.normalize(nome, Normalizer.Form.NFD).toLowerCase().replaceAll("[^a-z\\- ]", "");
        nomeNormal=new TreeSet(Arrays.asList(texto.split("-| ")));
        return nomeNormal;
    }

    public boolean compararNormal(Pessoa t)
    {
        if(pais==null||!pais.equals(t.pais))
            return false;
        return getNomeNormal().equals(t.getNomeNormal());
    }
}
