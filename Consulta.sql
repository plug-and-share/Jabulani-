SELECT * FROM copa;
SELECT * FROM selecao;
SELECT * FROM partida;
SELECT * FROM estadio;
SELECT * FROM jogador_escalado;
SELECT * FROM jogador_premiado;
SELECT * FROM arbitro;
SELECT * FROM tecnico;
SELECT * FROM jogador;
SELECT * FROM mascote;
SELECT * FROM trofeu;
SELECT * FROM musica;
SELECT * FROM sede;
SELECT * FROM premio;
SELECT * FROM selecao_premiada;
SELECT * FROM confederacao;
SELECT * FROM pais;
SELECT * FROM cartao;
SELECT * FROM posicao;
SELECT * FROM status;
SELECT * FROM penalti;
SELECT * FROM gol;
SELECT * FROM subistituicao;
SELECT * FROM bola;
SELECT * FROM tempo;
SELECT * FROM fase;
SELECT * FROM pessoa;
SELECT * FROM tecnico;


SELECT * FROM copa WHERE ano = 2014;

SELECT * FROM selecao WHERE pais = 'Brazil';

SELECT * FROM partida WHERE ( ano = 2014 and pais1 = 'Brazil' and pais2 = 'Germany');

SELECT MAX(gols) FROM jogador WHERE copas = 2014; -- Veja bem, so testando pra saber.

SELECT * FROM pessoa WHERE naturalidade = '223';

SELECT MAX(titulos) FROM tecnico;
