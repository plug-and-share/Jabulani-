SELECT
	copa, jogador, pais,
    MAX(gols) AS gols
    FROM
    (
		SELECT 
			partida.copa AS copa,
			pessoa.nome AS jogador,
			pais.nome AS pais,
			COUNT(*) AS gols
		FROM gol
		LEFT JOIN jogador_escalado ON autor=jogador_escalado.id
		LEFT JOIN partida ON jogador_escalado.partida=partida.id
		LEFT JOIN pessoa ON jogador_escalado.jogador=pessoa.id
		LEFT JOIN pais ON pessoa.naturalidade=pais.id
		GROUP BY pessoa.id, partida.copa
		ORDER BY gols DESC
	) gols
	GROUP BY copa;