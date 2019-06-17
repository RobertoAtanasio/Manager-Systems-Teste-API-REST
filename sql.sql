DROP TABLE pais;

CREATE TABLE pais (
id INT NOT NULL AUTO_INCREMENT,
nome VARCHAR(30) NULL,
sigla VARCHAR(10) NULL,
gentilico VARCHAR(20) NULL,
primary key (id)
);

DROP TABLE usuario;

CREATE TABLE usuario (
id INT NOT NULL AUTO_INCREMENT,
login VARCHAR(30) NOT NULL,
senha VARCHAR(30) NOT NULL,
nome VARCHAR(40) NOT NULL,
administrador INT NOT NULL,
primary key (id)
);

DROP TABLE token;

CREATE TABLE token (
id INT NOT NULL AUTO_INCREMENT,
token VARCHAR(255) NOT NULL,
login VARCHAR(30) NOT NULL,
expiracao date NOT NULL,
dministrador tinyINT NOT NULL,
primary key (id)
);

DROP TABLE permissao;
DROP TABLE usuario_permissao;

CREATE TABLE permissao (
  id INT NOT NULL,
  descricao VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE usuario_permissao (
  id_usuario INT NOT NULL,
  id_permissao INT NOT NULL,
  PRIMARY KEY (id_usuario, id_permissao),
  KEY codigo_permissao (id_permissao),
  CONSTRAINT usuario_permissao_ibfk_1 FOREIGN KEY (id_usuario) REFERENCES usuario (id),
  CONSTRAINT usuario_permissao_ibfk_2 FOREIGN KEY (id_permissao) REFERENCES permissao (id)
); 

INSERT INTO usuario (login, senha, nome, administrador) VALUES ('admin', 'suporte', 'Gestor', 1);
INSERT INTO usuario (login, senha, nome, administrador) VALUES ('convidado', 'manager', 'Usuário convidado', 0);

INSERT INTO pais (nome, sigla, gentilico) VALUES ('Brasil', 'BR', 'Brasileiro');
INSERT INTO pais (nome, sigla, gentilico) VALUES ('Argentina', 'AR', 'Argentino');
INSERT INTO pais (nome, sigla, gentilico) VALUES ('Alemanha', 'AL', 'Alemão');

INSERT INTO permissao (id, descricao) VALUES (1, 'ROLE_CONSULTAR_PAIS');
INSERT INTO permissao (id, descricao) VALUES (2, 'ROLE_SALVAR_PAIS');
INSERT INTO permissao (id, descricao) VALUES (3, 'ROLE_EXCLUIR_PAIS');
INSERT INTO permissao (id, descricao) VALUES (4, 'ROLE_CONSULTAR_USUARIO');
INSERT INTO permissao (id, descricao) VALUES (5, 'ROLE_SALVAR_USUARIO');
INSERT INTO permissao (id, descricao) VALUES (6, 'ROLE_EXCLUIR_USUARIO');

INSERT usuario_permissao (id_usuario, id_permissao)
	VALUES ((SELECT id FROM usuario WHERE login = 'ADMIN'), 1);
INSERT usuario_permissao (id_usuario, id_permissao)
	VALUES ((SELECT id FROM usuario WHERE login = 'ADMIN'), 2);
INSERT usuario_permissao (id_usuario, id_permissao)
	VALUES ((SELECT id FROM usuario WHERE login = 'ADMIN'), 3);

INSERT usuario_permissao (id_usuario, id_permissao)
	VALUES ((SELECT id FROM usuario WHERE login = 'ADMIN'), 4);
INSERT usuario_permissao (id_usuario, id_permissao)
	VALUES ((SELECT id FROM usuario WHERE login = 'ADMIN'), 5);
INSERT usuario_permissao (id_usuario, id_permissao)
	VALUES ((SELECT id FROM usuario WHERE login = 'ADMIN'), 6);

SELECT * FROM pais;
SELECT * FROM usuario;

select p.descricao as descricao from usuario u
inner join usuario_permissao up on up.id_usuario = u.id
inner join permissao p on p.id = up.id_permissao 
where u.id = 2
;
