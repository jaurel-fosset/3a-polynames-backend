CREATE DATABASE PolyNames;
USE PolyNames;


CREATE TABLE `Colors`
(
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(30) NOT NULL,
    PRIMARY KEY(`id`)
);


CREATE TABLE `Cards`
(
    `id` INT NOT NULL AUTO_INCREMENT,
    `word` VARCHAR(30),
    `idColor` INT NOT NULL,
    PRIMARY KEY(`id`)
);

ALTER TABLE `Cards`
ADD CONSTRAINT `fkCardsColors`
FOREIGN KEY (`idColor`)
REFERENCES `Colors` (`id`)
ON UPDATE CASCADE
ON DELETE NO ACTION;


CREATE TABLE `Grids`
(
    `id` INT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY(`id`)
);


CREATE TABLE `Cells`
(
    `id` INT NOT NULL AUTO_INCREMENT,
    `revealed` BIT NOT NULL,
    `idCard` INT NOT NULL,
    `idGrid` INT NOT NULL,
    PRIMARY KEY(`id`)
);

ALTER TABLE `Cells`
ADD CONSTRAINT `fkCellsCards`
FOREIGN KEY (`idCard`)
REFERENCES `Cards` (`id`)
ON UPDATE CASCADE
ON DELETE NO ACTION;

ALTER TABLE `Cells`
ADD CONSTRAINT `fkCellsGrids`
FOREIGN KEY (`idGrid`)
REFERENCES `Grids` (`id`)
ON UPDATE CASCADE
ON DELETE CASCADE;


CREATE TABLE `Players`
(
    `id` INT NOT NULL AUTO_INCREMENT,
    `returnCode` CHAR(10) NOT NULL,
    `pseudo` VARCHAR(30) NOT NULL,
    PRIMARY KEY(`id`)
);


CREATE TABLE `Games`
(
    `id` INT NOT NULL AUTO_INCREMENT,
    `joinCode` CHAR(10) NOT NULL,
    `idGuessMaster` INT,
    `idWordMaster` INT,
    `idGrid` INT,
    PRIMARY KEY(`id`)
);

ALTER TABLE `Games`
ADD CONSTRAINT `fkGamesPlayersGuessMaster`
FOREIGN KEY (`idGuessMaster`)
REFERENCES `Players` (`id`)
ON UPDATE CASCADE
ON DELETE SET NULL;

ALTER TABLE `Games`
ADD CONSTRAINT `fkGamesPlayersWordMaster`
FOREIGN KEY (`idWordMaster`)
REFERENCES `Players` (`id`)
ON UPDATE CASCADE
ON DELETE SET NULL;

ALTER TABLE `Games`
ADD CONSTRAINT `fkGamesGrids`
FOREIGN KEY (`idGrid`)
REFERENCES `Grids` (`id`)
ON UPDATE CASCADE
ON DELETE NO ACTION;
