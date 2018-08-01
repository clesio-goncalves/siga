insert into Permissao (id, nome)
values (1, 'Administrador');

insert into Permissao (id, nome)
values (2, 'Coordenador');

insert into Permissao (id, nome)
values (3, 'Diretor');

insert into Permissao (id, nome)
values (4, 'Psicologo');

insert into Permissao (id, nome)
values (5, 'AssistenteSocial');

insert into Permissao (id, nome)
values (6, 'Docente');

insert into Permissao (id, nome)
values (7, 'Monitor');

insert into Permissao (id, nome)
values (8, 'Aluno');

insert into Usuario(id, usuario, senha, ativo, permissao_id)
values (1, 'admin','$2a$10$EWuQ/cYO46g0fCOJDUxrReYT/TjKz2QXVIKTXx34eZlgfvkEZ.KFe', 1, 1);
    
insert into Usuario(id, usuario, senha, ativo, permissao_id)
values (2, 'teste','$2a$10$qpt3HctirwaIRedM0ERwXegnlFN0dejoMPBqsv5GDxJj9eAM/scBy', 1, 8);