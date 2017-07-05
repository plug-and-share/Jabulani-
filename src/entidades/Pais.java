package entidades;

public class Pais implements Comparable<Pais>
{
    public String nome, codigo;

    @Override
    public int compareTo(Pais t)
    {
        if(codigo==null||t.codigo==null)
        {
            if(nome!=null&&t.nome!=null)
                return nome.compareTo(t.nome);
            return -1;
        }
        return codigo.compareTo(t.codigo);
    }
}
