-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema copa_mundo
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema copa_mundo
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `copa_mundo` ;
USE `copa_mundo` ;

-- -----------------------------------------------------
-- Table `copa_mundo`.`pais`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `copa_mundo`.`pais` (
  `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `copa_mundo`.`estadio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `copa_mundo`.`estadio` (
  `id` SMALLINT UNSIGNED NOT NULL,
  `nome` VARCHAR(100) NOT NULL,
  `cidade` VARCHAR(100) NOT NULL,
  `pais` SMALLINT UNSIGNED NOT NULL,
  `capacidade` INT UNSIGNED NULL,
  `ano_construcao` SMALLINT UNSIGNED NULL,
  PRIMARY KEY (`id`),
  INDEX `pais_idx` (`pais` ASC),
  CONSTRAINT `estadio.pais`
    FOREIGN KEY (`pais`)
    REFERENCES `copa_mundo`.`pais` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `copa_mundo`.`trofeu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `copa_mundo`.`trofeu` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `copa_mundo`.`copa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `copa_mundo`.`copa` (
  `ano` SMALLINT UNSIGNED NOT NULL,
  `musica` VARCHAR(100) NULL,
  `album` VARCHAR(100) NULL,
  `instrumento` VARCHAR(20) NULL,
  `trofeu` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`ano`),
  INDEX `trofeu_idx` (`trofeu` ASC),
  CONSTRAINT `copa.trofeu`
    FOREIGN KEY (`trofeu`)
    REFERENCES `copa_mundo`.`trofeu` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `copa_mundo`.`confederacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `copa_mundo`.`confederacao` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `acronimo` VARCHAR(10) NOT NULL,
  `nome` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `acronimo_UNIQUE` (`acronimo` ASC),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `copa_mundo`.`selecao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `copa_mundo`.`selecao` (
  `id` INT UNSIGNED NOT NULL,
  `pais` SMALLINT UNSIGNED NOT NULL,
  `copa` SMALLINT UNSIGNED NOT NULL,
  `confederacao` TINYINT UNSIGNED NOT NULL,
  INDEX `pais_idx` (`pais` ASC),
  INDEX `copa_idx` (`copa` ASC),
  INDEX `confederacao_idx` (`confederacao` ASC),
  PRIMARY KEY (`id`),
  CONSTRAINT `selecao.pais`
    FOREIGN KEY (`pais`)
    REFERENCES `copa_mundo`.`pais` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `selecao.copa`
    FOREIGN KEY (`copa`)
    REFERENCES `copa_mundo`.`copa` (`ano`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `selecao.confederacao`
    FOREIGN KEY (`confederacao`)
    REFERENCES `copa_mundo`.`confederacao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `copa_mundo`.`fase`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `copa_mundo`.`fase` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `copa_mundo`.`tempo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `copa_mundo`.`tempo` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `copa_mundo`.`pessoa`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `copa_mundo`.`pessoa` (
  `id` INT NOT NULL,
  `nome` VARCHAR(100) NOT NULL,
  `data_nascimento` DATE NULL,
  `naturalidade` SMALLINT UNSIGNED NULL,
  `jogador` TINYINT(1) NOT NULL,
  `tecnico` TINYINT(1) NOT NULL,
  `arbitro` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `nacionalidade_idx` (`naturalidade` ASC),
  CONSTRAINT `pessoa.naturalidade`
    FOREIGN KEY (`naturalidade`)
    REFERENCES `copa_mundo`.`pais` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `copa_mundo`.`partida`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `copa_mundo`.`partida` (
  `id` INT UNSIGNED NOT NULL,
  `copa` SMALLINT UNSIGNED NOT NULL,
  `fase` TINYINT UNSIGNED NOT NULL,
  `grupo` CHAR NULL,
  `pais1` SMALLINT UNSIGNED NOT NULL,
  `pais2` SMALLINT UNSIGNED NOT NULL,
  `hora` DATETIME NOT NULL,
  `estadio` SMALLINT UNSIGNED NOT NULL,
  `publico` INT UNSIGNED NULL,
  `tecnico1` INT NOT NULL,
  `tecnico2` INT NOT NULL,
  `capitao1` INT NULL,
  `capitao2` INT NULL,
  `mvp` INT NULL,
  `arbitro` INT NOT NULL,
  `assistente1` INT NOT NULL,
  `assistente2` INT NOT NULL,
  `arbitro4` INT NULL,
  `gols1` TINYINT UNSIGNED NOT NULL,
  `gols2` TINYINT UNSIGNED NOT NULL,
  `disputa_penaltis1` TINYINT UNSIGNED NULL,
  `disputa_penaltis2` TINYINT UNSIGNED NULL,
  `tempo` TINYINT UNSIGNED NULL,
  `temperatura` TINYINT NULL,
  `vento` SMALLINT UNSIGNED NULL,
  `umidade` TINYINT UNSIGNED NULL,
  `prorrogacao` TINYINT(1) NOT NULL,
  `disputa_penaltis` TINYINT(1) NOT NULL,
  `gol_ouro` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `estadio_idx` (`estadio` ASC),
  INDEX `copa_idx` (`copa` ASC),
  INDEX `fase_idx` (`fase` ASC),
  INDEX `pais1_idx` (`pais1` ASC),
  INDEX `pais2_idx` (`pais2` ASC),
  INDEX `partida.tecnico1_idx` (`tecnico1` ASC),
  INDEX `partida.tecnico2_idx` (`tecnico2` ASC),
  INDEX `partida.capitao1_idx` (`capitao1` ASC),
  INDEX `partida.capitao2_idx` (`capitao2` ASC),
  INDEX `partida.mvp_idx` (`mvp` ASC),
  INDEX `partida.arbitro_idx` (`arbitro` ASC),
  INDEX `partida.assistente1_idx` (`assistente1` ASC),
  INDEX `partida.assistente2_idx` (`assistente2` ASC),
  INDEX `partida.arbitro4_idx` (`arbitro4` ASC),
  INDEX `partida.tempo_idx` (`tempo` ASC),
  CONSTRAINT `partida.estadio`
    FOREIGN KEY (`estadio`)
    REFERENCES `copa_mundo`.`estadio` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `partida.copa`
    FOREIGN KEY (`copa`)
    REFERENCES `copa_mundo`.`copa` (`ano`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `partida.fase`
    FOREIGN KEY (`fase`)
    REFERENCES `copa_mundo`.`fase` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `partida.tempo`
    FOREIGN KEY (`tempo`)
    REFERENCES `copa_mundo`.`tempo` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `partida.pais1`
    FOREIGN KEY (`pais1`)
    REFERENCES `copa_mundo`.`pais` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `partida.pais2`
    FOREIGN KEY (`pais2`)
    REFERENCES `copa_mundo`.`pais` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `partida.tecnico1`
    FOREIGN KEY (`tecnico1`)
    REFERENCES `copa_mundo`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `partida.tecnico2`
    FOREIGN KEY (`tecnico2`)
    REFERENCES `copa_mundo`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `partida.capitao1`
    FOREIGN KEY (`capitao1`)
    REFERENCES `copa_mundo`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `partida.capitao2`
    FOREIGN KEY (`capitao2`)
    REFERENCES `copa_mundo`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `partida.mvp`
    FOREIGN KEY (`mvp`)
    REFERENCES `copa_mundo`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `partida.arbitro`
    FOREIGN KEY (`arbitro`)
    REFERENCES `copa_mundo`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `partida.assistente1`
    FOREIGN KEY (`assistente1`)
    REFERENCES `copa_mundo`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `partida.assistente2`
    FOREIGN KEY (`assistente2`)
    REFERENCES `copa_mundo`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `partida.arbitro4`
    FOREIGN KEY (`arbitro4`)
    REFERENCES `copa_mundo`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `copa_mundo`.`sede`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `copa_mundo`.`sede` (
  `copa` SMALLINT UNSIGNED NOT NULL,
  `pais` SMALLINT UNSIGNED NOT NULL,
  INDEX `ano_idx` (`copa` ASC),
  INDEX `pais_idx` (`pais` ASC),
  PRIMARY KEY (`copa`, `pais`),
  CONSTRAINT `sede.copa`
    FOREIGN KEY (`copa`)
    REFERENCES `copa_mundo`.`copa` (`ano`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `sede.pais`
    FOREIGN KEY (`pais`)
    REFERENCES `copa_mundo`.`pais` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `copa_mundo`.`posicao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `copa_mundo`.`posicao` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `copa_mundo`.`status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `copa_mundo`.`status` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `copa_mundo`.`jogador_escalado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `copa_mundo`.`jogador_escalado` (
  `id` BIGINT UNSIGNED NOT NULL,
  `partida` INT UNSIGNED NOT NULL,
  `jogador` INT NOT NULL,
  `pais` SMALLINT UNSIGNED NOT NULL,
  `posicao` TINYINT UNSIGNED NULL,
  `numero` SMALLINT UNSIGNED NULL,
  `titular` TINYINT(1) NOT NULL,
  `status` TINYINT UNSIGNED NULL,
  INDEX `partida_idx` (`partida` ASC),
  INDEX `posicao_idx` (`posicao` ASC),
  INDEX `status_idx` (`status` ASC),
  PRIMARY KEY (`id`),
  INDEX `pais_idx` (`pais` ASC),
  CONSTRAINT `jogador_escalado.partida`
    FOREIGN KEY (`partida`)
    REFERENCES `copa_mundo`.`partida` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `jogador_escalado.posicao`
    FOREIGN KEY (`posicao`)
    REFERENCES `copa_mundo`.`posicao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `jogador_escalado.status`
    FOREIGN KEY (`status`)
    REFERENCES `copa_mundo`.`status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `jogador_escalado.pais`
    FOREIGN KEY (`pais`)
    REFERENCES `copa_mundo`.`pais` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `copa_mundo`.`substituicao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `copa_mundo`.`substituicao` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `substituido` BIGINT UNSIGNED NULL,
  `substituto` BIGINT UNSIGNED NULL,
  `minuto` TINYINT UNSIGNED NOT NULL,
  `acrescimo` TINYINT UNSIGNED NOT NULL,
  `intervalo` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `substituido_idx` (`substituido` ASC),
  INDEX `substituto_idx` (`substituto` ASC),
  CONSTRAINT `substituicao.substituido`
    FOREIGN KEY (`substituido`)
    REFERENCES `copa_mundo`.`jogador_escalado` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `substituicao.substituto`
    FOREIGN KEY (`substituto`)
    REFERENCES `copa_mundo`.`jogador_escalado` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `copa_mundo`.`gol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `copa_mundo`.`gol` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `autor` BIGINT UNSIGNED NOT NULL,
  `contra` TINYINT(1) NOT NULL,
  `penalti` TINYINT(1) NOT NULL,
  `minuto` TINYINT UNSIGNED NOT NULL,
  `acrescimo` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `autor_idx` (`autor` ASC),
  CONSTRAINT `gol.autor`
    FOREIGN KEY (`autor`)
    REFERENCES `copa_mundo`.`jogador_escalado` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `copa_mundo`.`cartao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `copa_mundo`.`cartao` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `jogador` BIGINT UNSIGNED NOT NULL,
  `vermelho` TINYINT(1) NOT NULL,
  `minuto` TINYINT UNSIGNED NOT NULL,
  `acrescimo` TINYINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `jogador_idx` (`jogador` ASC),
  CONSTRAINT `cartao.jogador`
    FOREIGN KEY (`jogador`)
    REFERENCES `copa_mundo`.`jogador_escalado` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `copa_mundo`.`premio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `copa_mundo`.`premio` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `copa_mundo`.`jogador_premiado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `copa_mundo`.`jogador_premiado` (
  `id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `jogador` INT NOT NULL,
  `premio` TINYINT UNSIGNED NOT NULL,
  `copa` SMALLINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `copa_idx` (`copa` ASC),
  INDEX `premio_idx` (`premio` ASC),
  INDEX `jogador_premiado.jogador_idx` (`jogador` ASC),
  CONSTRAINT `jogador_premiado.copa`
    FOREIGN KEY (`copa`)
    REFERENCES `copa_mundo`.`copa` (`ano`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `jogador_premiado.premio`
    FOREIGN KEY (`premio`)
    REFERENCES `copa_mundo`.`premio` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `jogador_premiado.jogador`
    FOREIGN KEY (`jogador`)
    REFERENCES `copa_mundo`.`pessoa` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `copa_mundo`.`mascote`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `copa_mundo`.`mascote` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  `copa` SMALLINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `copa_idx` (`copa` ASC),
  CONSTRAINT `mascote.copa`
    FOREIGN KEY (`copa`)
    REFERENCES `copa_mundo`.`copa` (`ano`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `copa_mundo`.`bola`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `copa_mundo`.`bola` (
  `id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(50) NOT NULL,
  `copa` SMALLINT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `bola.copa_idx` (`copa` ASC),
  CONSTRAINT `bola.copa`
    FOREIGN KEY (`copa`)
    REFERENCES `copa_mundo`.`copa` (`ano`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `copa_mundo`.`penalti`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `copa_mundo`.`penalti` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `cobrador` BIGINT UNSIGNED NOT NULL,
  `sequencia` TINYINT UNSIGNED NOT NULL,
  `convertido` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `cobrador_idx` (`cobrador` ASC),
  CONSTRAINT `penalti.cobrador`
    FOREIGN KEY (`cobrador`)
    REFERENCES `copa_mundo`.`jogador_escalado` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `copa_mundo`.`selecao_premiada`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `copa_mundo`.`selecao_premiada` (
  `selecao` INT UNSIGNED NOT NULL,
  `premio` TINYINT UNSIGNED NOT NULL,
  INDEX `premio_idx` (`premio` ASC),
  INDEX `selecao_idx` (`selecao` ASC),
  PRIMARY KEY (`premio`, `selecao`),
  CONSTRAINT `selecao_premiada.premio`
    FOREIGN KEY (`premio`)
    REFERENCES `copa_mundo`.`premio` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `selecao_premiada.selecao`
    FOREIGN KEY (`selecao`)
    REFERENCES `copa_mundo`.`selecao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
