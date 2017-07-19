SELECT * FROM bola;

SELECT
	cartao.id, partida.copa,
	pais1.nome AS pais1,
    pais2.nome AS pais2,
    pessoa.nome AS jogador,
    selecao.nome AS pais,
    vermelho, minuto, acrescimo
	FROM cartao
	LEFT JOIN jogador_escalado ON cartao.jogador=jogador_escalado.id
    LEFT JOIN partida ON jogador_escalado.partida=partida.id
    LEFT JOIN pais pais1 ON partida.pais1=pais1.id
	LEFT JOIN pais pais2 ON partida.pais2=pais2.id
	LEFT JOIN pessoa ON jogador_escalado.jogador=pessoa.id
    LEFT JOIN pais selecao ON jogador_escalado.pais=selecao.id;
    
SELECT * FROM confederacao;

SELECT
	ano, musica, album, instrumento,
    trofeu.nome AS trofeu
	FROM copa
    LEFT JOIN trofeu ON trofeu=id;
    
SELECT
	estadio.id, estadio.nome, cidade,
    pais.nome AS pais,
    capacidade, ano_construcao
	FROM estadio
    LEFT JOIN pais ON pais=pais.id;
    
SELECT * FROM fase;

SELECT
	gol.id, partida.copa,
    pais1.nome AS pais1,
    pais2.nome AS pais2,
    pessoa.nome AS autor,
    selecao.nome AS pais,
    contra, penalti, minuto, acrescimo
	FROM gol
	LEFT JOIN jogador_escalado ON autor=jogador_escalado.id
    LEFT JOIN partida ON jogador_escalado.partida=partida.id
    LEFT JOIN pais pais1 ON partida.pais1=pais1.id
	LEFT JOIN pais pais2 ON partida.pais2=pais2.id
    LEFT JOIN pessoa ON jogador_escalado.jogador=pessoa.id
    LEFT JOIN pais selecao ON jogador_escalado.pais=selecao.id;
    
SELECT
	jogador_escalado.id, partida.copa,
    pais1.nome AS pais1,
    pais2.nome AS pais2,
    pessoa.nome AS jogador,
    selecao.nome AS pais,
    posicao.nome AS posicao,
    numero, titular,
    status.nome AS status
	FROM jogador_escalado
    LEFT JOIN partida ON partida=partida.id
	LEFT JOIN pais pais1 ON partida.pais1=pais1.id
	LEFT JOIN pais pais2 ON partida.pais2=pais2.id
    LEFT JOIN pessoa ON jogador_escalado.jogador=pessoa.id
    LEFT JOIN pais selecao ON pais=selecao.id
    LEFT JOIN posicao ON posicao=posicao.id
    LEFT JOIN status ON status=status.id;

SELECT
	jogador_premiado.id,
    pessoa.nome AS jogador,
    premio.nome AS premio,
    copa
	FROM jogador_premiado
    LEFT JOIN pessoa ON jogador_premiado.jogador=pessoa.id
    LEFT JOIN premio ON premio=premio.id;
    
SELECT * FROM mascote;

SELECT * FROM pais;

SELECT
	partida.id, copa,
    fase.nome AS fase,
    grupo,
    pais1.nome AS pais1,
    pais2.nome AS pais2,
    hora,
    estadio.nome AS estadio,
    estadio.cidade,
    estadio_pais.nome AS pais,
    publico,
    tecnico1.nome AS tecnico1,
    tecnico1_pais.nome AS tecnico1_pais,
    tecnico2.nome AS tecnico2,
    tecnico2_pais.nome AS tecnico2_pais,
    capitao1.nome AS capitao1,
    capitao2.nome AS capitao2,
    mvp.nome AS mvp,
    arbitro.nome AS arbitro,
    arbitro_pais.nome AS arbitro_pais,
    assistente1.nome AS assistente1,
    assistente1_pais.nome AS assistente1_pais,
    assistente2.nome AS assistente2,
    assistente2_pais.nome AS assistente2_pais,
    arbitro4.nome AS arbitro4,
    arbitro4_pais.nome AS arbitro4_pais,
    gols1, gols2, disputa_penaltis1, disputa_penaltis2,
    tempo.nome AS tempo,
    temperatura, vento, umidade, prorrogacao, disputa_penaltis, gol_ouro
	FROM partida
	LEFT JOIN fase ON fase=fase.id
    LEFT JOIN pais pais1 ON pais1=pais1.id
    LEFT JOIN pais pais2 ON pais2=pais2.id
    LEFT JOIN estadio ON estadio=estadio.id
    LEFT JOIN pais estadio_pais ON pais=estadio_pais.id
    LEFT JOIN pessoa tecnico1 ON tecnico1=tecnico1.id
    LEFT JOIN pais tecnico1_pais ON tecnico1.naturalidade=tecnico1_pais.id
    LEFT JOIN pessoa tecnico2 ON tecnico2=tecnico2.id
	LEFT JOIN pais tecnico2_pais ON tecnico2.naturalidade=tecnico2_pais.id
    LEFT JOIN pessoa capitao1 ON capitao1=capitao1.id
    LEFT JOIN pessoa capitao2 ON capitao2=capitao2.id
    LEFT JOIN pessoa mvp ON mvp=mvp.id
    LEFT JOIN pessoa arbitro ON partida.arbitro=arbitro.id
	LEFT JOIN pais arbitro_pais ON arbitro.naturalidade=arbitro_pais.id
    LEFT JOIN pessoa assistente1 ON assistente1=assistente1.id
	LEFT JOIN pais assistente1_pais ON assistente1.naturalidade=assistente1_pais.id
    LEFT JOIN pessoa assistente2 ON assistente2=assistente2.id
	LEFT JOIN pais assistente2_pais ON assistente2.naturalidade=assistente2_pais.id
    LEFT JOIN pessoa arbitro4 ON arbitro4=arbitro4.id
	LEFT JOIN pais arbitro4_pais ON arbitro4.naturalidade=arbitro4_pais.id
    LEFT JOIN tempo ON tempo=tempo.id;
    
SELECT
	penalti.id, partida.copa,
    pais1.nome AS pais1,
    pais2.nome AS pais2,
    pessoa.nome AS cobrador,
    selecao.nome AS pais,
    sequencia, convertido
	FROM penalti
    LEFT JOIN jogador_escalado ON cobrador=jogador_escalado.id
	LEFT JOIN partida ON jogador_escalado.partida=partida.id
	LEFT JOIN pais pais1 ON partida.pais1=pais1.id
	LEFT JOIN pais pais2 ON partida.pais2=pais2.id
    LEFT JOIN pessoa ON jogador_escalado.jogador=pessoa.id
    LEFT JOIN pais selecao ON jogador_escalado.pais=selecao.id;

SELECT
	pessoa.id, pessoa.nome, data_nascimento,
    pais.nome AS naturalidade,
    jogador, tecnico, arbitro
	FROM pessoa
    LEFT JOIN pais ON naturalidade=pais.id;
    
SELECT * FROM posicao;

SELECT * FROM premio;

SELECT
	copa,
    pais.nome AS pais
	FROM sede
    LEFT JOIN pais ON pais=id;
    
SELECT
	selecao.id,
    pais.nome AS pais,
    copa,
    confederacao.acronimo AS confederacao
	FROM selecao
    LEFT JOIN pais ON pais=pais.id
    LEFT JOIN confederacao ON confederacao=confederacao.id;
    
SELECT
	selecao.copa,
	pais.nome AS selecao,
    premio.nome AS premio
	FROM selecao_premiada
    LEFT JOIN selecao ON selecao=selecao.id
    LEFT JOIN pais ON selecao.pais=pais.id
    LEFT JOIN premio ON premio=premio.id;
    
SELECT * FROM status;

SELECT
	substituicao.id, partida.copa,
    pais1.nome AS pais1,
    pais2.nome AS pais2,
    pessoa_substituida.nome AS substituido,
    pessoa_substituta.nome AS substituto,
    selecao.nome AS pais,
    minuto, acrescimo, intervalo
	FROM substituicao
    LEFT JOIN jogador_escalado substituido ON substituido=substituido.id
    LEFT JOIN pessoa pessoa_substituida ON substituido.jogador=pessoa_substituida.id
	LEFT JOIN jogador_escalado substituto ON substituto=substituto.id
	LEFT JOIN pessoa pessoa_substituta ON substituto.jogador=pessoa_substituta.id
    LEFT JOIN partida ON substituido.partida=partida.id
    LEFT JOIN pais pais1 ON partida.pais1=pais1.id
    LEFT JOIN pais pais2 ON partida.pais2=pais2.id
    LEFT JOIN pais selecao ON substituido.pais=selecao.id;

SELECT * FROM tempo;

SELECT * FROM trofeu;