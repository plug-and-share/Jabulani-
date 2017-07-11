package entidades;

public class Estadio implements Comparable<Estadio>
{
    public String nome, cidade, pais;
    public int capacidade;
    public short anoConstrucao;

    @Override
    public int compareTo(Estadio t)
    {
        int comparacao=pais.compareTo(t.pais);
        if(comparacao!=0)
            return comparacao;
        comparacao=cidade.compareTo(t.cidade);
        if(comparacao!=0)
            return comparacao;
        return nome.compareTo(t.nome);
    }
}
