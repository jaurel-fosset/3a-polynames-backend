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
    `word` VARCHAR(30) NOT NULL,
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
    `returnCode` CHAR(11) NOT NULL,
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

INSERT INTO `Colors` (name)
VALUES ('blue'), ('grey'), ('black') ;

INSERT INTO `Cards` (word, idColor)
VALUES
    ('mix', 1),
    ('field', 1),
    ('activist', 1),
    ('weapon', 1),
    ('tank', 1),
    ('battle', 1),
    ('adequate', 1),
    ('carrier', 1),
    ('gear', 1),
    ('inquiry', 1),
    ('conventional', 1),
    ('personality', 1),
    ('line', 1),
    ('adventure', 1),
    ('impact', 1),
    ('regulate', 1),
    ('live', 2),
    ('treat', 2),
    ('anymore', 2),
    ('frequent', 2),
    ('poetry', 2),
    ('running', 2),
    ('device', 2),
    ('agree', 2),
    ('electric', 2),
    ('lady', 2),
    ('reference', 2),
    ('pet', 2),
    ('reading', 2),
    ('partner', 2),
    ('clear', 2),
    ('would', 2),
    ('golf', 2),
    ('winner', 2),
    ('net', 2),
    ('gift', 2),
    ('confront', 2),
    ('wealthy', 2),
    ('policy', 2),
    ('deputy', 2),
    ('serve', 2),
    ('dog', 2),
    ('virtue', 2),
    ('target', 2),
    ('recogniz', 2),
    ('blue', 2),
    ('ability', 3),
    ('muscle', 3),
    ('egg', 3),
    ('package', 3) ;