
CREATE TABLE pais (
	id INT NOT NULL AUTO_INCREMENT,
	nome VARCHAR(30) NULL,
	sigla VARCHAR(10) NULL,
	gentilico VARCHAR(20) NULL,
	primary key (id)
);

CREATE TABLE usuario (
	id INT NOT NULL AUTO_INCREMENT,
	login VARCHAR(30) NOT NULL,
	senha VARCHAR(30) NOT NULL,
	nome VARCHAR(40) NOT NULL,
	administrador INT NOT NULL,
	primary key (id)
);

CREATE TABLE token (
	id INT NOT NULL AUTO_INCREMENT,
	token VARCHAR(255) NOT NULL,
	login VARCHAR(30) NOT NULL,
	expiracao datetime NOT NULL,
	administrador tinyINT NOT NULL,
	primary key (id)
);

INSERT INTO usuario (login, senha, nome, administrador) VALUES ('admin', 'suporte', 'Gestor', 1);
INSERT INTO usuario (login, senha, nome, administrador) VALUES ('convidado', 'manager', 'Usuário convidado', 0);

INSERT INTO pais (nome, sigla, gentilico) VALUES ('Brasil', 'BR', 'Brasileiro');
INSERT INTO pais (nome, sigla, gentilico) VALUES ('Argentina', 'AR', 'Argentino');
INSERT INTO pais (nome, sigla, gentilico) VALUES ('Alemanha', 'AL', 'Alemão');
