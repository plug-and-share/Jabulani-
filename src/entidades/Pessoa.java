package entidades;

import java.time.*;

public class Pessoa implements Comparable<Pessoa>
{
    public String nome, pais;
    public LocalDate dataNascimento;
    public int id;

    @Override
    public int compareTo(Pessoa t)
    {
        if(id>=0&&t.id>=0)
            return Integer.compare(id, t.id);
        int comparacao=pais.compareTo(t.pais);
        if(comparacao!=0)
            return comparacao;
        comparacao=nome.compareTo(t.nome);
        if(comparacao!=0)
            return comparacao;
        if(dataNascimento==null||t.dataNascimento==null)
            return 0;
        return dataNascimento.compareTo(t.dataNascimento);
    }
}
