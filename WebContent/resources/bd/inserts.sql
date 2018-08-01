insert into Setor(id, nome)
values (1,'TI');

insert into Setor(id, nome)
values (2,'DAP');

insert into Permissao (id, nome)
values (1, 'Administrador');

insert into Permissao (id, nome)
values (2, 'Gerente');

insert into Permissao (id, nome)
values (3, 'Coordenador');

insert into Usuario(id, nome, usuario, senha, ativo, setor_id, permissao_id)
values (1, 'Clésio de Araújo Gonçalves',
    'admin','$2a$10$EWuQ/cYO46g0fCOJDUxrReYT/TjKz2QXVIKTXx34eZlgfvkEZ.KFe',
    1, 1, 1);
    
insert into Usuario(id, nome, usuario, senha, ativo, setor_id, permissao_id)
values (2, 'Usuario de teste',
    'teste','$2a$10$qpt3HctirwaIRedM0ERwXegnlFN0dejoMPBqsv5GDxJj9eAM/scBy',
    1, 1, 3);