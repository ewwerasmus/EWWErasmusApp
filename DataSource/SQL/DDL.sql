-- RUN AS ROOT

SET GLOBAL local_infile = 1;

-- CREAZIONE DEL DATABASE
DROP DATABASE IF EXISTS ErasmusApp;
CREATE DATABASE ErasmusApp DEFAULT CHARACTER SET UTF8 COLLATE utf8_general_ci;
USE ErasmusApp;

CREATE TABLE SEGNALAZIONE
(
    id                  BIGINT UNSIGNED  NOT NULL,
    descrizione         VARCHAR(2000)    NOT NULL,
    urgenza             TINYINT UNSIGNED NOT NULL,
    idStatoSegnalazione INT UNSIGNED     NOT NULL,
    idComune            VARCHAR(4)       NOT NULL,
    idCoordinata        BIGINT UNSIGNED  NOT NULL,
    idCategoria         INT UNSIGNED     NOT NULL
) ENGINE = InnoDB;
CREATE TABLE ENUM_STATO_SEGNALAZIONE
(
    id    INT UNSIGNED NOT NULL,
    label VARCHAR(14)  NOT NULL
) ENGINE = InnoDB;

CREATE TABLE COMUNE
(
    codiceCatastale VARCHAR(4)  NOT NULL,
    nome            VARCHAR(60) NOT NULL,
    provincia       VARCHAR(2)  NOT NULL
) ENGINE = InnoDB;

CREATE TABLE COORDINATA
(
    id          BIGINT UNSIGNED NOT NULL,
    latitudine  DOUBLE           NOT NULL,
    longitudine DOUBLE           NOT NULL
) ENGINE = InnoDB;

CREATE TABLE CATEGORIA
(
    id          INT UNSIGNED  NOT NULL,
    nome        VARCHAR(45)   NOT NULL,
    descrizione VARCHAR(2000) NOT NULL
) ENGINE = InnoDB;

CREATE TABLE UTENTE(
    username VARCHAR(80) NOT NULL,
    password VARCHAR(80) NOT NULL
) ENGINE = InnoDB;

CREATE TABLE RUOLO(
    nome VARCHAR(50) NOT NULL
) ENGINE = InnoDB;

CREATE TABLE UTENTE_RUOLO(
    idUtente VARCHAR(80) NOT NULL,
    idRuolo VARCHAR(50) NOT NULL
) ENGINE = InnoDB;

ALTER TABLE COMUNE
    ADD CONSTRAINT PK
        PRIMARY KEY (codiceCatastale),
    ADD CONSTRAINT UK_COMUNE_1_NOME
        UNIQUE KEY (nome);

ALTER TABLE COORDINATA
    MODIFY COLUMN id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    ADD CONSTRAINT PK
        PRIMARY KEY (id),
    ADD CONSTRAINT UK_1_longitudine_latitudine
        UNIQUE KEY (longitudine, latitudine);

ALTER TABLE CATEGORIA
    MODIFY COLUMN id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    ADD CONSTRAINT PK
        PRIMARY KEY (id),
    ADD CONSTRAINT UK_CATEGORIA_1_NOME
        UNIQUE KEY (nome);

ALTER TABLE ENUM_STATO_SEGNALAZIONE
    ADD CONSTRAINT PK
        PRIMARY KEY (id);
INSERT INTO ENUM_STATO_SEGNALAZIONE VALUES
    (0, "DA RISOLVERE"),
    (1, "IN RISOLUZIONE"),
    (2, "RISOLTO");

ALTER TABLE SEGNALAZIONE
    MODIFY COLUMN id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    MODIFY COLUMN idStatoSegnalazione INT UNSIGNED NOT NULL DEFAULT 0,
    ADD CONSTRAINT PK
        PRIMARY KEY (id),
    ADD CONSTRAINT FK_SEGNALAZIONE_1_COMUNE
        FOREIGN KEY (idComune)
            REFERENCES COMUNE (codiceCatastale)
            ON DELETE NO ACTION
            ON UPDATE CASCADE,
    ADD CONSTRAINT FK_SEGNALAZIONE_2_COORDINATA
        FOREIGN KEY (idCoordinata)
            REFERENCES COORDINATA (id)
            ON DELETE NO ACTION
            ON UPDATE CASCADE,
    ADD CONSTRAINT FK_SEGNALAZIONE_3_CATEGORIA
        FOREIGN KEY (idCategoria)
            REFERENCES CATEGORIA (id)
            ON DELETE NO ACTION
            ON UPDATE CASCADE,
    ADD CONSTRAINT FK_SEGNALAZIONE_4_ENUMSTATO
        FOREIGN KEY (idStatoSegnalazione)
            REFERENCES ENUM_STATO_SEGNALAZIONE (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;

ALTER TABLE UTENTE
    ADD CONSTRAINT PK
        PRIMARY KEY (username);

ALTER TABLE RUOLO
    ADD CONSTRAINT PK
        PRIMARY KEY (nome);

ALTER TABLE UTENTE_RUOLO
    ADD CONSTRAINT PK
        PRIMARY KEY (idRuolo, idUtente),
    ADD CONSTRAINT FK_1_UTENTE
        FOREIGN KEY (idUtente)
            REFERENCES UTENTE(username)
            ON UPDATE CASCADE
            ON DELETE CASCADE,
    ADD CONSTRAINT FK_2_RUOLO
        FOREIGN KEY (idRuolo)
            REFERENCES RUOLO(nome)
            ON UPDATE CASCADE
            ON DELETE CASCADE;

-- UTENTE MASTER DI TUTTO IL DATABASE
DROP USER IF EXISTS ErasmusAppMaster;
CREATE USER ErasmusAppMaster
    IDENTIFIED BY "ErasmusAppMaster";
GRANT ALL ON ErasmusApp.* TO ErasmusAppMaster;

-- UTENTE PER GESTIONE 'SEGNALAZIONI'
DROP USER IF EXISTS ErasmusAPP_Segnalazioni;
CREATE USER ErasmusAPP_Segnalazioni
    IDENTIFIED BY "ErasmusAPP_Segnalazioni";
GRANT SHOW VIEW ON ErasmusApp.* TO ErasmusAPP_Segnalazioni;
GRANT ALL ON ErasmusApp.SEGNALAZIONE TO ErasmusAPP_Segnalazioni;
GRANT ALL ON ErasmusApp.COMUNE TO ErasmusAPP_Segnalazioni;
GRANT ALL ON ErasmusApp.COORDINATA TO ErasmusAPP_Segnalazioni;
GRANT ALL ON ErasmusApp.CATEGORIA TO ErasmusAPP_Segnalazioni;
GRANT SELECT ON ErasmusApp.ENUM_STATO_SEGNALAZIONE to ErasmusAPP_Segnalazioni;
GRANT SHOW VIEW ON ErasmusApp.ENUM_STATO_SEGNALAZIONE to ErasmusAPP_Segnalazioni;

-- UTENTE PER GESTIONE SISTEMA DI LOGIN
DROP USER IF EXISTS ErasmusAPP_Login;
CREATE USER ErasmusAPP_Login
    IDENTIFIED BY "ErasmusAPP_Login";
GRANT SHOW VIEW ON ErasmusApp.* TO ErasmusAPP_Login;
GRANT ALL ON ErasmusApp.UTENTE TO ErasmusAPP_Login;
GRANT ALL ON ErasmusApp.RUOLO TO ErasmusAPP_Login;
GRANT ALL ON ErasmusApp.UTENTE_RUOLO TO ErasmusAPP_Login;

FLUSH PRIVILEGES;

-- bulk insert of COMUNE
LOAD DATA LOCAL INFILE 'SQL/resources/comune.csv'
INTO TABLE COMUNE
FIELDS TERMINATED BY ','
ENCLOSED BY ''
LINES TERMINATED BY '\n'
IGNORE 1 LINES;
