insert into Perfil (id, nome)
values (1, 'Administrador');

insert into Perfil (id, nome)
values (2, 'Coordenador');

insert into Perfil (id, nome)
values (3, 'Diretor');

insert into Perfil (id, nome)
values (4, 'Psicologia');

insert into Perfil (id, nome)
values (5, 'Assistência Social');

insert into Perfil (id, nome)
values (6, 'Enfermagem');

insert into Perfil (id, nome)
values (7, 'Pedagogia');

insert into Perfil (id, nome)
values (8, 'Odontologia');

insert into Perfil (id, nome)
values (9, 'Docente');

insert into Perfil (id, nome)
values (10, 'Monitor');

insert into Perfil (id, nome)
values (11, 'Aluno');

insert into Perfil (id, nome)
values (12, 'Coordenação de Disciplina');

insert into Usuario(id, email, senha, ativo, perfil_id)
values (1, 'clesio.goncalves@ifpi.edu.br','$2a$10$bRHXvb97c2b4avC31ISmF.4xZkCgUsVMS./xsWvDL83OE8WIVpxX6', 1, 1);

insert into Usuario(id, email, senha, ativo, perfil_id)
values (2, 'flaviaf.bastos@ifpi.edu.br','$2a$10$fjOXnOV0VfR68zoCge0ad.3Pw94NL7WcCnQ7YM33pAMaoFf5LjmBS', 1, 9);

insert into Situacao (id, nome)
values (1, 'Cursando');

insert into Situacao (id, nome)
values (2, 'Aprovado no Conselho');

insert into Situacao (id, nome)
values (3, 'Aprovado no Conselho com Pendência');

insert into Situacao (id, nome)
values (4, 'Retido');

insert into Beneficio (id, nome)
values (1, 'Permanente');

insert into Beneficio (id, nome)
values (2, 'Eventual');
