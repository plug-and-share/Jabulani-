-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`pais`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`pais` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  `participacoes` TINYINT UNSIGNED NOT NULL,
  `titulos` TINYINT UNSIGNED NOT NULL,
  `sedes` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`pessoa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`pessoa` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `data_nascimento` DATE NOT NULL,
  `naturalidade` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `nacionalidade_idx` (`naturalidade` ASC),
  CONSTRAINT `pessoa.naturalidade`
    FOREIGN KEY (`naturalidade`)
    REFERENCES `mydb`.`pais` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`jogador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`jogador` (
  `id` INT UNSIGNED NOT NULL,
  `copas` TINYINT UNSIGNED NOT NULL,
  `titulos` TINYINT UNSIGNED NOT NULL,
  `partidas` SMALLINT UNSIGNED NOT NULL,
  `gols` SMALLINT UNSIGNED NOT NULL,
  `cartoes_amarelos` SMALLINT UNSIGNED NOT NULL,
  `cartoes_vermelhos` SMALLINT UNSIGNED NOT NULL,
  `altura` TINYINT UNSIGNED NOT NULL,
  `pe_direito_dominante` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `jogador.id`
    FOREIGN KEY (`id`)
    REFERENCES `mydb`.`pessoa` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`estadio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`estadio` (
  `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `cidade` VARCHAR(100) NOT NULL,
  `estado` VARCHAR(100) NULL,
  `pais` TINYINT UNSIGNED NOT NULL,
  `capacidade` INT UNSIGNED NOT NULL,
  `ano_construcao` SMALLINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `pais_idx` (`pais` ASC),
  CONSTRAINT `estadio.pais`
    FOREIGN KEY (`pais`)
    REFERENCES `mydb`.`pais` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`trofeu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`trofeu` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`copa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`copa` (
  `ano` SMALLINT UNSIGNED NOT NULL,
  `data_inicio` DATE NOT NULL,
  `data_fim` DATE NOT NULL,
  `musica` VARCHAR(50) NULL,
  `album` VARCHAR(100) NULL,
  `instrumento` VARCHAR(20) NULL,
  `trofeu` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`ano`),
  INDEX `trofeu_idx` (`trofeu` ASC),
  CONSTRAINT `copa.trofeu`
    FOREIGN KEY (`trofeu`)
    REFERENCES `mydb`.`trofeu` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`confederacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`confederacao` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `acronimo` VARCHAR(10) NOT NULL,
  `nome` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `acronimo_UNIQUE` (`acronimo` ASC),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`selecao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`selecao` (
  `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `pais` TINYINT UNSIGNED NOT NULL,
  `copa` SMALLINT UNSIGNED NOT NULL,
  `confederacao` TINYINT UNSIGNED NOT NULL,
  `colocacao` TINYINT UNSIGNED NOT NULL,
  `jogos` TINYINT UNSIGNED NOT NULL,
  `vitorias` TINYINT UNSIGNED NOT NULL,
  `empates` TINYINT UNSIGNED NOT NULL,
  `derrotas` TINYINT UNSIGNED NOT NULL,
  `gols_pro` TINYINT UNSIGNED NOT NULL,
  `gols_contra` TINYINT UNSIGNED NOT NULL,
  `saldo_gols` TINYINT NOT NULL,
  INDEX `pais_idx` (`pais` ASC),
  INDEX `copa_idx` (`copa` ASC),
  INDEX `confederacao_idx` (`confederacao` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `selecao.pais`
    FOREIGN KEY (`pais`)
    REFERENCES `mydb`.`pais` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `selecao.copa`
    FOREIGN KEY (`copa`)
    REFERENCES `mydb`.`copa` (`ano`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `selecao.confederacao`
    FOREIGN KEY (`confederacao`)
    REFERENCES `mydb`.`confederacao` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`bola`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`bola` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tecnico`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tecnico` (
  `id` INT UNSIGNED NOT NULL,
  `copas` TINYINT UNSIGNED NOT NULL,
  `partidas` SMALLINT UNSIGNED NOT NULL,
  `titulos` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `tecnico.id`
    FOREIGN KEY (`id`)
    REFERENCES `mydb`.`pessoa` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`fase`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`fase` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`tempo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`tempo` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`arbitro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`arbitro` (
  `id` INT UNSIGNED NOT NULL,
  `copas` TINYINT UNSIGNED NOT NULL,
  `partidas` SMALLINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `arbitro.id`
    FOREIGN KEY (`id`)
    REFERENCES `mydb`.`pessoa` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`partida`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`partida` (
  `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `copa` SMALLINT UNSIGNED NOT NULL,
  `fase` TINYINT UNSIGNED NOT NULL,
  `grupo` CHAR NOT NULL,
  `sequencia` TINYINT UNSIGNED NOT NULL,
  `pais1` TINYINT UNSIGNED NOT NULL,
  `pais2` TINYINT UNSIGNED NOT NULL,
  `hora` DATETIME NOT NULL,
  `estadio` SMALLINT UNSIGNED NOT NULL,
  `publico` INT UNSIGNED NOT NULL,
  `bola` TINYINT UNSIGNED NOT NULL,
  `acrescimo1` TINYINT UNSIGNED NOT NULL,
  `acrescimo2` TINYINT UNSIGNED NOT NULL,
  `acrescimo3` TINYINT UNSIGNED NOT NULL,
  `acrescimo4` TINYINT UNSIGNED NOT NULL,
  `tecnico1` INT UNSIGNED NOT NULL,
  `tecnico2` INT UNSIGNED NOT NULL,
  `capitao1` INT UNSIGNED NOT NULL,
  `capitao2` INT UNSIGNED NOT NULL,
  `mvp` INT UNSIGNED NULL,
  `arbitro` INT UNSIGNED NOT NULL,
  `assistente1` INT UNSIGNED NOT NULL,
  `assistente2` INT UNSIGNED NOT NULL,
  `arbitro4` INT UNSIGNED NULL,
  `comissario_partida` INT UNSIGNED NULL,
  `coordenador_geral` INT UNSIGNED NULL,
  `assistente_reserva` INT UNSIGNED NULL,
  `gols1` TINYINT UNSIGNED NOT NULL,
  `gols2` TINYINT UNSIGNED NOT NULL,
  `disputa_penaltis1` TINYINT UNSIGNED NOT NULL,
  `disputa_penaltis2` TINYINT UNSIGNED NOT NULL,
  `posse1` TINYINT UNSIGNED NOT NULL,
  `posse2` TINYINT UNSIGNED NOT NULL,
  `tempo_posse1` SMALLINT UNSIGNED NOT NULL,
  `tempo_posse2` SMALLINT UNSIGNED NOT NULL,
  `chutes1` TINYINT UNSIGNED NOT NULL,
  `chutes2` TINYINT UNSIGNED NOT NULL,
  `chutes_gol1` TINYINT UNSIGNED NOT NULL,
  `chutes_gol2` TINYINT UNSIGNED NOT NULL,
  `faltas_cometidas1` TINYINT UNSIGNED NOT NULL,
  `faltas_cometidas2` TINYINT UNSIGNED NOT NULL,
  `faltas_sofridas1` TINYINT UNSIGNED NOT NULL,
  `faltas_sofridas2` TINYINT UNSIGNED NOT NULL,
  `escanteios1` TINYINT UNSIGNED NOT NULL,
  `escanteios2` TINYINT UNSIGNED NOT NULL,
  `tiros_diretos1` TINYINT UNSIGNED NOT NULL,
  `tiros_diretos2` TINYINT UNSIGNED NOT NULL,
  `tiros_indiretos1` TINYINT UNSIGNED NOT NULL,
  `tiros_indiretos2` TINYINT UNSIGNED NOT NULL,
  `penaltis_cobrados1` TINYINT UNSIGNED NOT NULL,
  `penaltis_cobrados2` TINYINT UNSIGNED NOT NULL,
  `penaltis_convertidos1` TINYINT UNSIGNED NOT NULL,
  `penaltis_convertidos2` TINYINT UNSIGNED NOT NULL,
  `impedimentos1` TINYINT UNSIGNED NOT NULL,
  `impedimentos2` TINYINT UNSIGNED NOT NULL,
  `gols_contra1` TINYINT UNSIGNED NOT NULL,
  `gols_contra2` TINYINT UNSIGNED NOT NULL,
  `advertencias1` TINYINT UNSIGNED NOT NULL,
  `advertencias2` TINYINT UNSIGNED NOT NULL,
  `expulsoes_indiretas1` TINYINT UNSIGNED NOT NULL,
  `expulsoes_indiretas2` TINYINT UNSIGNED NOT NULL,
  `expulsoes_diretas1` TINYINT UNSIGNED NOT NULL,
  `expulsoes_diretas2` TINYINT UNSIGNED NOT NULL,
  `tempo` TINYINT UNSIGNED NULL,
  `temperatura` TINYINT UNSIGNED NOT NULL,
  `vento` TINYINT UNSIGNED NOT NULL,
  `umidade` TINYINT UNSIGNED NOT NULL,
  `prorrogacao` TINYINT(1) NOT NULL,
  `disputa_penaltis` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `estadio_idx` (`estadio` ASC),
  INDEX `copa_idx` (`copa` ASC),
  INDEX `bola_idx` (`bola` ASC),
  INDEX `comissario_partida_idx` (`comissario_partida` ASC),
  INDEX `coordenador_geral_idx` (`coordenador_geral` ASC),
  INDEX `mvp_idx` (`mvp` ASC),
  INDEX `capitao1_idx` (`capitao1` ASC),
  INDEX `capitao2_idx` (`capitao2` ASC),
  INDEX `fase_idx` (`fase` ASC),
  INDEX `tempo_idx` (`tempo` ASC),
  INDEX `pais1_idx` (`pais1` ASC),
  INDEX `pais2_idx` (`pais2` ASC),
  INDEX `arbitro_idx` (`arbitro` ASC),
  INDEX `assistente1_idx` (`assistente1` ASC),
  INDEX `assistente2_idx` (`assistente2` ASC),
  INDEX `arbitro4_idx` (`arbitro4` ASC),
  INDEX `assistente_reserva_idx` (`assistente_reserva` ASC),
  INDEX `tecnico1_idx` (`tecnico1` ASC),
  INDEX `tecnico2_idx` (`tecnico2` ASC),
  CONSTRAINT `partida.estadio`
    FOREIGN KEY (`estadio`)
    REFERENCES `mydb`.`estadio` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `partida.copa`
    FOREIGN KEY (`copa`)
    REFERENCES `mydb`.`copa` (`ano`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `partida.bola`
    FOREIGN KEY (`bola`)
    REFERENCES `mydb`.`bola` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `partida.comissario_partida`
    FOREIGN KEY (`comissario_partida`)
    REFERENCES `mydb`.`pessoa` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `partida.coordenador_geral`
    FOREIGN KEY (`coordenador_geral`)
    REFERENCES `mydb`.`pessoa` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `partida.mvp`
    FOREIGN KEY (`mvp`)
    REFERENCES `mydb`.`jogador` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `partida.capitao1`
    FOREIGN KEY (`capitao1`)
    REFERENCES `mydb`.`jogador` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `partida.capitao2`
    FOREIGN KEY (`capitao2`)
    REFERENCES `mydb`.`jogador` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `partida.tecnico1`
    FOREIGN KEY (`tecnico1`)
    REFERENCES `mydb`.`tecnico` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `partida.tecnico2`
    FOREIGN KEY (`tecnico2`)
    REFERENCES `mydb`.`tecnico` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `partida.fase`
    FOREIGN KEY (`fase`)
    REFERENCES `mydb`.`fase` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `partida.tempo`
    FOREIGN KEY (`tempo`)
    REFERENCES `mydb`.`tempo` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `partida.pais1`
    FOREIGN KEY (`pais1`)
    REFERENCES `mydb`.`pais` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `partida.pais2`
    FOREIGN KEY (`pais2`)
    REFERENCES `mydb`.`pais` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `partida.arbitro`
    FOREIGN KEY (`arbitro`)
    REFERENCES `mydb`.`arbitro` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `partida.assistente1`
    FOREIGN KEY (`assistente1`)
    REFERENCES `mydb`.`arbitro` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `partida.assistente2`
    FOREIGN KEY (`assistente2`)
    REFERENCES `mydb`.`arbitro` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `partida.arbitro4`
    FOREIGN KEY (`arbitro4`)
    REFERENCES `mydb`.`arbitro` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `partida.assistente_reserva`
    FOREIGN KEY (`assistente_reserva`)
    REFERENCES `mydb`.`arbitro` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`sede`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`sede` (
  `copa` SMALLINT UNSIGNED NOT NULL,
  `pais` TINYINT UNSIGNED NOT NULL,
  INDEX `ano_idx` (`copa` ASC),
  INDEX `pais_idx` (`pais` ASC),
  PRIMARY KEY (`copa`, `pais`),
  CONSTRAINT `sede.copa`
    FOREIGN KEY (`copa`)
    REFERENCES `mydb`.`copa` (`ano`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `sede.pais`
    FOREIGN KEY (`pais`)
    REFERENCES `mydb`.`pais` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`posicao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`posicao` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`status` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`jogador_escalado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`jogador_escalado` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `partida` SMALLINT UNSIGNED NOT NULL,
  `jogador` INT UNSIGNED NOT NULL,
  `pais` TINYINT UNSIGNED NOT NULL,
  `posicao` TINYINT UNSIGNED NOT NULL,
  `numero` SMALLINT UNSIGNED NOT NULL,
  `titular` TINYINT(1) NOT NULL,
  `status` TINYINT UNSIGNED NOT NULL,
  `tempo_jogo` SMALLINT UNSIGNED NOT NULL,
  `passes_completos` SMALLINT UNSIGNED NOT NULL,
  `passes_tentados` SMALLINT UNSIGNED NOT NULL,
  `precisao_passes` TINYINT UNSIGNED NOT NULL,
  `passes_recebidos` SMALLINT UNSIGNED NOT NULL,
  `gols_marcados` TINYINT UNSIGNED NOT NULL,
  `gols_sofridos` TINYINT UNSIGNED NOT NULL,
  `penaltis_convertidos` TINYINT UNSIGNED NOT NULL,
  `penaltis_perdidos` TINYINT UNSIGNED NOT NULL,
  `chutes` TINYINT UNSIGNED NOT NULL,
  `chutes_gol` TINYINT UNSIGNED NOT NULL,
  `assistencias` TINYINT UNSIGNED NOT NULL,
  `impedimentos` TINYINT UNSIGNED NOT NULL,
  `defesas` TINYINT UNSIGNED NOT NULL,
  `advertencias` TINYINT UNSIGNED NOT NULL,
  `expulsoes_indiretas` TINYINT UNSIGNED NOT NULL,
  `expulsoes_diretas` TINYINT UNSIGNED NOT NULL,
  `faltas_cometidas` TINYINT UNSIGNED NOT NULL,
  `faltas_sofridas` TINYINT UNSIGNED NOT NULL,
  `distancia_percorrida` SMALLINT UNSIGNED NOT NULL,
  `velocidade_maxima` SMALLINT UNSIGNED NOT NULL,
  `arrancadas` TINYINT UNSIGNED NOT NULL,
  INDEX `jogador_idx` (`jogador` ASC),
  INDEX `partida_idx` (`partida` ASC),
  INDEX `posicao_idx` (`posicao` ASC),
  INDEX `status_idx` (`status` ASC),
  PRIMARY KEY (`id`),
  INDEX `pais_idx` (`pais` ASC),
  CONSTRAINT `jogador_escalado.jogador`
    FOREIGN KEY (`jogador`)
    REFERENCES `mydb`.`jogador` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `jogador_escalado.partida`
    FOREIGN KEY (`partida`)
    REFERENCES `mydb`.`partida` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `jogador_escalado.posicao`
    FOREIGN KEY (`posicao`)
    REFERENCES `mydb`.`posicao` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `jogador_escalado.status`
    FOREIGN KEY (`status`)
    REFERENCES `mydb`.`status` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `jogador_escalado.pais`
    FOREIGN KEY (`pais`)
    REFERENCES `mydb`.`pais` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`substituicao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`substituicao` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `substituido` INT UNSIGNED NOT NULL,
  `substituto` INT UNSIGNED NOT NULL,
  `minuto` TINYINT UNSIGNED NOT NULL,
  `acrescimo` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `substituido_idx` (`substituido` ASC),
  INDEX `substituto_idx` (`substituto` ASC),
  CONSTRAINT `substituicao.substituido`
    FOREIGN KEY (`substituido`)
    REFERENCES `mydb`.`jogador_escalado` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `substituicao.substituto`
    FOREIGN KEY (`substituto`)
    REFERENCES `mydb`.`jogador_escalado` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`gol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`gol` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `autor` INT UNSIGNED NOT NULL,
  `contra` TINYINT(1) NOT NULL,
  `penalti` TINYINT(1) NOT NULL,
  `minuto` TINYINT UNSIGNED NOT NULL,
  `acrescimo` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `autor_idx` (`autor` ASC),
  CONSTRAINT `gol.autor`
    FOREIGN KEY (`autor`)
    REFERENCES `mydb`.`jogador_escalado` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`cartao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`cartao` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `jogador` INT UNSIGNED NOT NULL,
  `vermelho` TINYINT(1) NOT NULL,
  `minuto` TINYINT UNSIGNED NOT NULL,
  `acrescimo` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `jogador_idx` (`jogador` ASC),
  CONSTRAINT `cartao.jogador`
    FOREIGN KEY (`jogador`)
    REFERENCES `mydb`.`jogador_escalado` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`premio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`premio` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`jogador_premiado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`jogador_premiado` (
  `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `jogador` INT UNSIGNED NOT NULL,
  `premio` TINYINT UNSIGNED NOT NULL,
  `copa` SMALLINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `vencedor_idx` (`jogador` ASC),
  INDEX `copa_idx` (`copa` ASC),
  INDEX `premio_idx` (`premio` ASC),
  CONSTRAINT `jogador_premiado.jogador`
    FOREIGN KEY (`jogador`)
    REFERENCES `mydb`.`jogador` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `jogador_premiado.copa`
    FOREIGN KEY (`copa`)
    REFERENCES `mydb`.`copa` (`ano`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `jogador_premiado.premio`
    FOREIGN KEY (`premio`)
    REFERENCES `mydb`.`premio` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`mascote`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`mascote` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  `copa` SMALLINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `copa_idx` (`copa` ASC),
  CONSTRAINT `mascote.copa`
    FOREIGN KEY (`copa`)
    REFERENCES `mydb`.`copa` (`ano`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`passes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`passes` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `origem` INT UNSIGNED NOT NULL,
  `destino` INT UNSIGNED NOT NULL,
  `quantidade` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `origem_idx` (`origem` ASC),
  INDEX `destino_idx` (`destino` ASC),
  CONSTRAINT `passes.origem`
    FOREIGN KEY (`origem`)
    REFERENCES `mydb`.`jogador_escalado` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `passes.destino`
    FOREIGN KEY (`destino`)
    REFERENCES `mydb`.`jogador_escalado` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`penalti`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`penalti` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `cobrador` INT UNSIGNED NOT NULL,
  `sequencia` TINYINT UNSIGNED NOT NULL,
  `convertido` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `cobrador_idx` (`cobrador` ASC),
  CONSTRAINT `penalti.cobrador`
    FOREIGN KEY (`cobrador`)
    REFERENCES `mydb`.`jogador_escalado` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`selecao_premiada`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`selecao_premiada` (
  `selecao` SMALLINT UNSIGNED NOT NULL,
  `premio` TINYINT UNSIGNED NOT NULL,
  INDEX `premio_idx` (`premio` ASC),
  INDEX `selecao_idx` (`selecao` ASC),
  PRIMARY KEY (`premio`, `selecao`),
  CONSTRAINT `selecao_premiada.premio`
    FOREIGN KEY (`premio`)
    REFERENCES `mydb`.`premio` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `selecao_premiada.selecao`
    FOREIGN KEY (`selecao`)
    REFERENCES `mydb`.`selecao` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
