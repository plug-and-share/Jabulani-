INSERT INTO partida
( copa, fase, grupo, sequencia, pais1, pais2, hora, estadio, 
publico, bola, acrescimo1, acrescimo2 , acrescimo3, acrescimo4, 
tecnico1, tecnico2, tecnico3, tecnico4, mvp, arbitro, assistente1, 
assistente2, arbitro4, comissario_partida, coordenador_geral, 
gols1, gols2, disputa_penaltis1, disputa_penaltis2, posse1, 
posse2, tempo_posse1, tempo_posse2, chutes1, chutes2, chutes_gol1, 
chutes_gol2, faltas_cometidas1, faltas_cometidas2, faltas_sofridas1, 
faltas_sofrida2, escanteios1, escanteios2, tiros_diretos1, 
tiros_diretos2, tiros_indiretos1, tiros_indiretos2, penaltis_cobrados1, 
penaltis_cobrados2, penaltis_convertidos1, penaltis_convertidos2, 
impedimentos1, impedimentos2, gols_contra1, gols_contra2, advertencias1, 
advertencias2, expulsoes_indiretas1, expulsoes_indiretas2, 
expulsoes_diretas1, expulsoes_diretas2, tempo, temperatura, vento, 
umidade, prorrogacao, disputa_penalti ) 
VALUE( 0, 0, 'A', 0, 0, 0, '00:00', 0, 
0, 0, 0, 0, 0, 0, 
0, 0, 0, 0, 0, 0, 0, 
0, 0, 0, 0, 
0, 0, 0, 0, 0, 
0, 0, 0, 0, 0, 0, 
0, 0, 0, 0, 
0, 0, 0, 0, 
0, 0, 0, 0, 
0, 0, 0, 
0, 0, 0, 0, 0, 
0, 0, 0, 
0, 0, 0, 0, 0, 
0, 'FALSE', 'FALSE' );
-- 
INSERT INTO jogador_escalado
( partida, jogador, pais, posicao, numero, titular, status, tempo_jogo, 
passes_completos, passes_tentados, posicao_passes, passes_recebidos, 
gols_marcados, gols_sofridos, penaltis_convertidos, penaltis_perdidos, 
chutes, chutes_gol, assistencias, impedimentos, defesas, advertencias, 
expulsoes_indiretas, expulsoes_diretas, faltas_cometidas, 
faltas_sofridas, distancia_percorrida, velocidade_maxima, arrancadas ) 
VALUE
( 0, 0, 0, 0, 0, 'TRUE', 0, 0, 
0, 0, 0, 0, 
0, 0, 0, 0, 
0, 0, 0, 0, 0, 0, 
0, 0, 0, 
0, 0, 0, 0 );
--
INSERT INTO jogador
( copas, titulos, partidas, gols, cartoes_amarelos, cartoes_vermelhos, 
altura, pe_direito_dominante ) 
VALUE
( 0, 0, 0, 0, 0, 0, 0, 'TRUE' );
--
INSERT INTO copa
( ano, data_inicio, data_fim, musica, álbum, instrumento, trofeu ) 
VALUE
( 0, '01/01/00', '01/01/00', 0, 'ALBUM', 'V', 0);
--
INSERT INTO trofeu
( nome ) 
VALUE
( 'TROFEU' );
--
INSERT INTO musica
( nome, compositores, interpretes, duracao ) 
VALUE
( 'NOME', 'COMP', 'INTER', 0 );
--
INSERT INTO mascote
( nome, copa ) 
VALUE
( 'MASCOTE', 0 );
--
INSERT INTO sede
( copa, pais ) 
VALUE
( 0, 0 );
--
INSERT INTO selecao
( pais, copa, confederacao, colocacao, jogos, vitorias, empates, 
derrotas, gols_pro, gols_contra, saldo_gols ) 
VALUE
( 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 );
--
INSERT INTO selecao_premiada
( selecao, premio ) 
VALUE
( 0, 0 );
--
INSERT INTO jogador_premiado
( jogador, premio, copa ) 
VALUE
( 0, 0, 0 );
--
INSERT INTO confederacao
( acronimo, nome ) 
VALUE
( 'ACRO', 'NIMO' );
--
INSERT INTO pais
( nome, participacoes, titulos, sedes ) 
VALUE
( 'PAIS', 0, 0, 0 );
--
INSERT INTO estadio
( nome, cidade, estado, pais, capacidade, ano_construcao ) 
VALUE
( 'ESTADIO', 'CIDADE', 'ESTADO', 0, 0, 0 );
--
INSERT INTO passes
( origem, destino, quantidade ) 
VALUE
( 0, 0, 0 );
--
INSERT INTO substituicao
( substituido, substituto, minuto, acrescimo) 
VALUE
( 0, 0, 0, 0 );
--
INSERT INTO cartao
( jogador, vermelho, minuto, acrescimo ) 
VALUE
( 0, 'FALSE', 0, 0 );
--
INSERT INTO posicao
( nome ) 
VALUE
( 'POSIX' );
--
INSERT INTO status
( nome ) 
VALUE
( 'REMEMBER' );
--
INSERT INTO penalti
( cobrador, sequencia, convertido ) 
VALUE
( 0, 0, 'FALSE' );
--
INSERT INTO gol
( autor, contra, penalti, minuto, acrescimo ) 
VALUE
( 0, 'FALSE', 'FALSE', 0, 0 );
--
INSERT INTO arbitro
( copas, partidas ) 
VALUE
( 0, 0 );
--
INSERT INTO tecnico
( copas, partidas, titulos ) 
VALUE
( 0, 0, 0 );
--
INSERT INTO pessoa
( nome, data_nascimento, naturalidade ) 
VALUE
( 'NOME', '01/01/00', 0 );
--
INSERT INTO fase
( nome ) 
VALUE
( 'FASE' );
--
INSERT INTO bola
( nome ) 
VALUE
( 'BOLA' );
--
INSERT INTO tempo
( nome ) 
VALUE
( 'TEMPO' );
--
INSERT INTO premio
( nome )
VALUE
( 'PREMIO' );
