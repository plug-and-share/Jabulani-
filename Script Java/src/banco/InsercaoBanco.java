package banco;

import static controle.Main.arbitros;
import static controle.Main.confederacoes;
import static controle.Main.copas;
import static controle.Main.estadios;
import static controle.Main.fases;
import static controle.Main.jogadores;
import static controle.Main.paises;
import static controle.Main.posicoes;
import static controle.Main.premios;
import static controle.Main.status;
import static controle.Main.tempos;
import static controle.Main.trofeus;
import entidades.*;
import java.sql.*;
import java.util.*;

public class InsercaoBanco
{
    public static void inserirPaises(Connection conexao) throws SQLException
    {
        try(PreparedStatement ps=conexao.prepareStatement("INSERT INTO pais(nome)VALUES(?)"))
        {
            for(Map.Entry<String, String> pais: paises.entrySet())
            {
                ps.setString(1, pais.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public static void inserirFases(Connection conexao) throws SQLException
    {
        try(PreparedStatement ps=conexao.prepareStatement("INSERT INTO fase(nome)VALUES(?)"))
        {
            for(String fase: fases)
            {
                ps.setString(1, fase);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public static void inserirStatus(Connection conexao) throws SQLException
    {
        try(PreparedStatement ps=conexao.prepareStatement("INSERT INTO status(nome)VALUES(?)"))
        {
            for(String s: status)
            {
                ps.setString(1, s);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public static void inserirTempos(Connection conexao) throws SQLException
    {
        try(PreparedStatement ps=conexao.prepareStatement("INSERT INTO tempo(nome)VALUES(?)"))
        {
            for(String tempo: tempos)
            {
                ps.setString(1, tempo);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public static void inserirPremios(Connection conexao) throws SQLException
    {
        try(PreparedStatement ps=conexao.prepareStatement("INSERT INTO premio(nome)VALUES(?)"))
        {
            for(String premio: premios)
            {
                ps.setString(1, premio);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public static void inserirTrofeus(Connection conexao) throws SQLException
    {
        try(PreparedStatement ps=conexao.prepareStatement("INSERT INTO trofeu(nome)VALUES(?)"))
        {
            for(String trofeu: trofeus)
            {
                ps.setString(1, trofeu);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public static void inserirPosicoes(Connection conexao) throws SQLException
    {
        try(PreparedStatement ps=conexao.prepareStatement("INSERT INTO posicao(nome)VALUES(?)"))
        {
            for(Map.Entry<Character, String> posicao: posicoes.entrySet())
            {
                ps.setString(1, posicao.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public static void inserirConfederacoes(Connection conexao) throws SQLException
    {
        try(PreparedStatement ps=conexao.prepareStatement("INSERT INTO confederacao(acronimo,nome)VALUES(?,?)"))
        {
            for(Map.Entry<String, Confederacao> confederacao: confederacoes.entrySet())
            {
                ps.setString(1, confederacao.getValue().acronimo);
                ps.setString(2, confederacao.getValue().nome);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public static void inserirEstadios(Connection conexao) throws SQLException
    {
        try(PreparedStatement ps=conexao.prepareStatement("INSERT INTO estadio(id,nome,cidade,pais,capacidade,ano_construcao)VALUES(?,?,?,(SELECT id FROM pais WHERE nome=?),?,?)"))
        {
            for(Estadio estadio: estadios)
            {
                ps.setShort(1, estadio.id);
                ps.setString(2, estadio.nome);
                ps.setString(3, estadio.cidade);
                ps.setString(4, paises.get(estadio.pais));
                if(estadio.capacidade!=-1)
                    ps.setInt(5, estadio.capacidade);
                else
                    ps.setNull(5, Types.INTEGER);
                if(estadio.anoConstrucao!=Short.MAX_VALUE)
                    ps.setShort(6, estadio.anoConstrucao);
                else
                    ps.setNull(6, Types.SMALLINT);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public static void inserirPessoas(Connection conexao, SortedMap<Integer, Pessoa> pessoas) throws SQLException
    {
        try(PreparedStatement ps=conexao.prepareStatement("INSERT INTO pessoa(id,nome,data_nascimento,naturalidade,jogador,tecnico,arbitro)VALUES(?,?,?,(SELECT id FROM pais WHERE nome=?),?,?,?)"))
        {
            for(Map.Entry<Integer, Pessoa> pessoa: pessoas.entrySet())
            {
                ps.setInt(1, pessoa.getValue().id);
                ps.setString(2, pessoa.getValue().nome);
                if(pessoa.getValue().dataNascimento!=null)
                    ps.setDate(3, java.sql.Date.valueOf(pessoa.getValue().dataNascimento));
                else
                    ps.setNull(3, Types.DATE);
                if(pessoa.getValue().pais!=null)
                    ps.setString(4, paises.get(pessoa.getValue().pais));
                else
                    ps.setNull(4, Types.VARCHAR);
                ps.setBoolean(5, pessoa.getValue().jogador);
                ps.setBoolean(6, pessoa.getValue().tecnico);
                ps.setBoolean(7, pessoa.getValue().arbitro);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public static void inserirCopas(Connection conexao) throws SQLException
    {
        try(PreparedStatement ps=conexao.prepareStatement("INSERT INTO copa(ano,musica,album,instrumento,trofeu)VALUES(?,?,?,?,(SELECT id FROM trofeu WHERE nome=?))"))
        {
            for(Map.Entry<Short, Copa> copa: copas.entrySet())
            {
                ps.setShort(1, copa.getValue().ano);
                ps.setString(2, copa.getValue().musica);
                ps.setString(3, copa.getValue().album);
                ps.setString(4, copa.getValue().instrumento);
                ps.setString(5, copa.getValue().trofeu);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public static void inserirSedes(Connection conexao) throws SQLException
    {
        try(PreparedStatement ps=conexao.prepareStatement("INSERT INTO sede(copa,pais)VALUES(?,(SELECT id FROM pais WHERE nome=?))"))
        {
            for(Map.Entry<Short, Copa> copa: copas.entrySet())
                for(String sede: copa.getValue().sedes)
                {
                    ps.setShort(1, copa.getValue().ano);
                    ps.setString(2, paises.get(sede));
                    ps.addBatch();
                }
            ps.executeBatch();
        }
    }

    public static void inserirMascotes(Connection conexao) throws SQLException
    {
        try(PreparedStatement ps=conexao.prepareStatement("INSERT INTO mascote(nome,copa)VALUES(?,?)"))
        {
            for(Map.Entry<Short, Copa> copa: copas.entrySet())
                for(String mascote: copa.getValue().mascotes)
                {
                    ps.setString(1, mascote);
                    ps.setShort(2, copa.getValue().ano);
                    ps.addBatch();
                }
            ps.executeBatch();
        }
    }

    public static void inserirBolas(Connection conexao) throws SQLException
    {
        try(PreparedStatement ps=conexao.prepareStatement("INSERT INTO bola(nome,copa)VALUES(?,?)"))
        {
            for(Map.Entry<Short, Copa> copa: copas.entrySet())
                for(String bola: copa.getValue().bolas)
                {
                    ps.setString(1, bola);
                    ps.setShort(2, copa.getValue().ano);
                    ps.addBatch();
                }
            ps.executeBatch();
        }
    }

    public static void inserirSelecoes(Connection conexao) throws SQLException
    {
        try(PreparedStatement ps=conexao.prepareStatement("INSERT INTO selecao(id,pais,copa,confederacao)VALUES(?,(SELECT id FROM pais WHERE nome=?),?,(SELECT id FROM confederacao WHERE acronimo=?))"))
        {
            for(Map.Entry<Short, Copa> copa: copas.entrySet())
                for(Map.Entry<String, Selecao> selecao: copa.getValue().selecoes.entrySet())
                {
                    ps.setInt(1, selecao.getValue().id);
                    ps.setString(2, paises.get(selecao.getKey()));
                    ps.setShort(3, copa.getValue().ano);
                    ps.setString(4, confederacoes.get(selecao.getValue().confederacao).acronimo);
                    ps.addBatch();
                }
            ps.executeBatch();
        }
    }

    public static void inserirPartidas(Connection conexao) throws SQLException
    {
        try(PreparedStatement ps=conexao.prepareStatement("INSERT INTO partida(id,copa,fase,grupo,pais1,pais2,hora,estadio,publico,tecnico1,tecnico2,capitao1,capitao2,mvp,arbitro,assistente1,assistente2,arbitro4,gols1,gols2,disputa_penaltis1,disputa_penaltis2,tempo,temperatura,vento,umidade,prorrogacao,disputa_penaltis,gol_ouro)VALUES(?,?,(SELECT id FROM fase WHERE nome=?),?,(SELECT id FROM pais WHERE nome=?),(SELECT id FROM pais WHERE nome=?),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,(SELECT id FROM tempo WHERE nome=?),?,?,?,?,?,?)"))
        {
            for(Map.Entry<Short, Copa> copa: copas.entrySet())
                for(Partida partida: copa.getValue().partidas)
                {
                    ps.setInt(1, partida.id);
                    ps.setShort(2, copa.getValue().ano);
                    ps.setString(3, partida.fase);
                    if(partida.grupo!=0)
                        ps.setString(4, String.valueOf(partida.grupo));
                    else
                        ps.setNull(4, Types.CHAR);
                    ps.setString(5, paises.get(partida.selecao1));
                    ps.setString(6, paises.get(partida.selecao2));
                    ps.setTimestamp(7, Timestamp.valueOf(partida.hora));
                    ps.setShort(8, partida.estadio.id);
                    if(partida.publico!=-1)
                        ps.setInt(9, partida.publico);
                    else
                        ps.setNull(9, Types.INTEGER);
                    ps.setInt(10, partida.tecnico1.id);
                    ps.setInt(11, partida.tecnico2.id);
                    if(partida.capitao1!=0)
                        ps.setInt(12, partida.capitao1);
                    else
                        ps.setNull(12, Types.INTEGER);
                    if(partida.capitao2!=0)
                        ps.setInt(13, partida.capitao2);
                    else
                        ps.setNull(13, Types.INTEGER);
                    if(partida.mvp!=0)
                        ps.setInt(14, partida.mvp);
                    else
                        ps.setNull(14, Types.INTEGER);
                    ps.setInt(15, partida.arbitro.id);
                    ps.setInt(16, partida.assistente1.id);
                    ps.setInt(17, partida.assistente2.id);
                    if(partida.arbitro4!=null)
                        ps.setInt(18, partida.arbitro4.id);
                    else
                        ps.setNull(18, Types.INTEGER);
                    ps.setByte(19, partida.gols1);
                    ps.setByte(20, partida.gols2);
                    if(partida.disputaPenaltis)
                    {
                        ps.setByte(21, partida.disputaPenaltis1);
                        ps.setByte(22, partida.disputaPenaltis2);
                    }
                    else
                    {
                        ps.setNull(21, Types.TINYINT);
                        ps.setNull(22, Types.TINYINT);
                    }
                    if(partida.tempo!=null)
                        ps.setString(23, partida.tempo);
                    else
                        ps.setNull(23, Types.VARCHAR);
                    if(partida.temperatura!=Byte.MIN_VALUE)
                        ps.setByte(24, partida.temperatura);
                    else
                        ps.setNull(24, Types.TINYINT);
                    if(partida.vento!=-1)
                        ps.setShort(25, partida.vento);
                    else
                        ps.setNull(25, Types.SMALLINT);
                    if(partida.umidade!=-1)
                        ps.setByte(26, partida.umidade);
                    else
                        ps.setNull(26, Types.TINYINT);
                    ps.setBoolean(27, partida.prorrogacao);
                    ps.setBoolean(28, partida.disputaPenaltis);
                    ps.setBoolean(29, partida.golOuro);
                    ps.addBatch();
                }
            ps.executeBatch();
        }
    }

    public static void inserirJogadores(Connection conexao) throws SQLException
    {
        try(PreparedStatement ps=conexao.prepareStatement("INSERT INTO jogador_escalado(id,partida,jogador,pais,posicao,numero,titular,status)VALUES(?,?,?,(SELECT id FROM pais WHERE nome=?),(SELECT id FROM posicao WHERE nome=?),?,?,(SELECT id FROM status WHERE nome=?))"))
        {
            for(Map.Entry<Short, Copa> copa: copas.entrySet())
                for(Partida partida: copa.getValue().partidas)
                {
                    prepararJogadores(ps, partida.jogadores1, partida.selecao1, partida.id);
                    prepararJogadores(ps, partida.jogadores2, partida.selecao2, partida.id);
                }
            ps.executeBatch();
        }
    }

    public static void prepararJogadores(PreparedStatement ps, ArrayList<Jogador> jogadores, String pais, int partida) throws SQLException
    {
        for(Jogador jogador: jogadores)
        {
            ps.setLong(1, jogador.id);
            ps.setInt(2, partida);
            ps.setInt(3, jogador.jogador);
            ps.setString(4, paises.get(pais));
            ps.setString(5, posicoes.get(jogador.posicao));
            if(jogador.numero!=0)
                ps.setShort(6, jogador.numero);
            else
                ps.setNull(6, Types.SMALLINT);
            ps.setBoolean(7, jogador.titular);
            if(jogador.status!=null)
                ps.setString(8, jogador.status);
            else
                ps.setNull(8, Types.VARCHAR);
            ps.addBatch();
        }
    }

    public static void inserirCartoes(Connection conexao) throws SQLException
    {
        try(PreparedStatement ps=conexao.prepareStatement("INSERT INTO cartao(jogador,vermelho,minuto,acrescimo)VALUES(?,?,?,?)"))
        {
            for(Map.Entry<Short, Copa> copa: copas.entrySet())
                for(Partida partida: copa.getValue().partidas)
                {
                    prepararCartoes(ps, partida.jogadores1);
                    prepararCartoes(ps, partida.jogadores2);
                }
            ps.executeBatch();
        }
    }

    public static void prepararCartoes(PreparedStatement ps, ArrayList<Jogador> jogadores) throws SQLException
    {
        for(Jogador jogador: jogadores)
            for(Cartao cartao: jogador.cartoes)
            {
                ps.setLong(1, jogador.id);
                ps.setBoolean(2, cartao.vermelho);
                ps.setByte(3, cartao.minuto);
                ps.setByte(4, cartao.acrescimo);
                ps.addBatch();
            }
    }

    public static void inserirGols(Connection conexao) throws SQLException
    {
        try(PreparedStatement ps=conexao.prepareStatement("INSERT INTO gol(autor,contra,penalti,minuto,acrescimo)VALUES(?,?,?,?,?)"))
        {
            for(Map.Entry<Short, Copa> copa: copas.entrySet())
                for(Partida partida: copa.getValue().partidas)
                {
                    prepararGols(ps, partida.listaGols1);
                    prepararGols(ps, partida.listaGols2);
                }
            ps.executeBatch();
        }
    }

    public static void prepararGols(PreparedStatement ps, ArrayList<Gol> gols) throws SQLException
    {
        for(Gol gol: gols)
        {
            ps.setLong(1, gol.autor);
            ps.setBoolean(2, gol.contra);
            ps.setBoolean(3, gol.penalti);
            ps.setByte(4, gol.minuto);
            ps.setByte(5, gol.acrescimo);
            ps.addBatch();
        }
    }

    public static void inserirPenaltis(Connection conexao) throws SQLException
    {
        try(PreparedStatement ps=conexao.prepareStatement("INSERT INTO penalti(cobrador,sequencia,convertido)VALUES(?,?,?)"))
        {
            for(Map.Entry<Short, Copa> copa: copas.entrySet())
                for(Partida partida: copa.getValue().partidas)
                {
                    prepararPenaltis(ps, partida.penaltis1);
                    prepararPenaltis(ps, partida.penaltis2);
                }
            ps.executeBatch();
        }
    }

    public static void prepararPenaltis(PreparedStatement ps, ArrayList<Penalti> penaltis) throws SQLException
    {
        for(Penalti penalti: penaltis)
        {
            ps.setLong(1, penalti.cobrador);
            ps.setByte(2, penalti.sequencia);
            ps.setBoolean(3, penalti.convertido);
            ps.addBatch();
        }
    }

    public static void inserirSubstituicoes(Connection conexao) throws SQLException
    {
        try(PreparedStatement ps=conexao.prepareStatement("INSERT INTO substituicao(substituido,substituto,minuto,acrescimo,intervalo)VALUES(?,?,?,?,?)"))
        {
            for(Map.Entry<Short, Copa> copa: copas.entrySet())
                for(Partida partida: copa.getValue().partidas)
                {
                    prepararSubstituicoes(ps, partida.substituicoes1);
                    prepararSubstituicoes(ps, partida.substituicoes2);
                }
            ps.executeBatch();
        }
    }

    public static void prepararSubstituicoes(PreparedStatement ps, ArrayList<Substituicao> substituicoes) throws SQLException
    {
        for(Substituicao substituicao: substituicoes)
        {
            if(substituicao.substituido!=0)
                ps.setLong(1, substituicao.substituido);
            else
                ps.setNull(1, Types.BIGINT);
            if(substituicao.substituto!=0)
                ps.setLong(2, substituicao.substituto);
            else
                ps.setNull(2, Types.BIGINT);
            ps.setByte(3, substituicao.minuto);
            ps.setByte(4, substituicao.acrescimo);
            ps.setBoolean(5, substituicao.intervalo);
            ps.addBatch();
        }
    }

    public static void inserirJogadoresPremiados(Connection conexao) throws SQLException
    {
        try(PreparedStatement ps=conexao.prepareStatement("INSERT INTO jogador_premiado(jogador,premio,copa)VALUES(?,(SELECT id FROM premio WHERE nome=?),?)"))
        {
            for(Map.Entry<Short, Copa> copa: copas.entrySet())
                for(JogadorPremiado jogador: copa.getValue().jogadoresPremiados)
                {
                    ps.setInt(1, jogador.jogador.id);
                    ps.setString(2, jogador.premio);
                    ps.setShort(3, copa.getValue().ano);
                    ps.addBatch();
                }
            ps.executeBatch();
        }
    }

    public static void inserirSelecoesPremiadas(Connection conexao) throws SQLException
    {
        try(PreparedStatement ps=conexao.prepareStatement("INSERT INTO selecao_premiada(selecao,premio)VALUES(?,(SELECT id FROM premio WHERE nome=?))"))
        {
            for(Map.Entry<Short, Copa> copa: copas.entrySet())
                for(SelecaoPremiada selecao: copa.getValue().selecoesPremiadas)
                {
                    ps.setInt(1, copa.getValue().selecoes.get(selecao.selecao).id);
                    ps.setString(2, selecao.premio);
                    ps.addBatch();
                }
            ps.executeBatch();
        }
    }

    public static void inserir() throws SQLException
    {
        try(Connection conexao=Conexao.obter())
        {
            conexao.setAutoCommit(false);
            inserirPaises(conexao);
            inserirFases(conexao);
            inserirStatus(conexao);
            inserirTempos(conexao);
            inserirPremios(conexao);
            inserirTrofeus(conexao);
            inserirPosicoes(conexao);
            inserirConfederacoes(conexao);
            inserirEstadios(conexao);
            inserirPessoas(conexao, jogadores);
            inserirPessoas(conexao, arbitros);
            inserirCopas(conexao);
            inserirSedes(conexao);
            inserirMascotes(conexao);
            inserirBolas(conexao);
            inserirSelecoes(conexao);
            inserirPartidas(conexao);
            inserirJogadores(conexao);
            inserirCartoes(conexao);
            inserirGols(conexao);
            inserirPenaltis(conexao);
            inserirSubstituicoes(conexao);
            inserirJogadoresPremiados(conexao);
            inserirSelecoesPremiadas(conexao);
            conexao.commit();
        }
    }
}
