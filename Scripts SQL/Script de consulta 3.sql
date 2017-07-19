select pessoa.nome as jogador, count(*) as gols
	from gol
	left join jogador_escalado on autor=jogador_escalado.id
	left join partida on jogador_escalado.partida=partida.id
	left join pessoa on jogador_escalado.jogador=pessoa.id
	group by pessoa.id
	order by gols desc