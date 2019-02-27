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
values (1, 'clesio@clesio.com','$2a$10$bRHXvb97c2b4avC31ISmF.4xZkCgUsVMS./xsWvDL83OE8WIVpxX6', 1, 1);

insert into Usuario(id, email, senha, ativo, perfil_id)
values (2, 'coordenador@coordenador.com','$2a$10$/tZUwz/PLDMtbAALWNgKFej3cITY55sCct59lXNXLMmnPgYUWg0Ie', 1, 2);

insert into Usuario(id, email, senha, ativo, perfil_id)
values (3, 'diretor@diretor.com','$2a$10$NP/U1b23pPhbwYgB9foQ.u.OSaT1JvKQ.NRXRo4VJs1Uv6advx3AG', 1, 3);

insert into Usuario(id, email, senha, ativo, perfil_id)
values (4, 'psicologo@psicologo.com','$2a$10$03J6yDNF1SwrLPJciIhTeOWomDJJnHIJoqb.u7Nse4irOg969Fq92', 1, 4);

insert into Usuario(id, email, senha, ativo, perfil_id)
values (5, 'assistente@assistente.com','$2a$10$RbrLXGZW.Gv5pxqmgm14puEmvscRIsVtkaIJQocQDrqf.DF4S4CAm', 1, 5);

insert into Usuario(id, email, senha, ativo, perfil_id)
values (6, 'enfermeiro@enfermeiro.com','$2a$10$7DDFvw.HOkhwaj6.zCufheHpoo/YaOD16ZJ6djDsv665b7VzPHLR6', 1, 6);

insert into Usuario(id, email, senha, ativo, perfil_id)
values (7, 'odonto@odonto.com','$2a$10$MWn2zLaHN0nICdbkBzCkGeV24rtUb4vipmbSyXH3DcuybipY1DVRK', 1, 8);

insert into Usuario(id, email, senha, ativo, perfil_id)
values (8, 'docente@docente.com','$2a$10$6SvL56HB0kZa1pDGt6ls/.VQlzCanJbctPyg1iWzoi5T/agOLucOW', 1, 9);

insert into Usuario(id, email, senha, ativo, perfil_id)
values (9, 'monitor@monitor.com','$2a$10$WYXa9SQ.8wLJjYBysLQZcuFXwYexhRzClxbAjrtl8p0ukMkjurvuK', 1, 10);
    
insert into Usuario(id, email, senha, ativo, perfil_id)
values (10, 'aluno@aluno.com','$2a$10$qpt3HctirwaIRedM0ERwXegnlFN0dejoMPBqsv5GDxJj9eAM/scBy', 1, 11);

insert into Curso(id, nome)
values (1, 'Técnico em Mineração');

insert into Curso(id, nome)
values (2, 'Técnico em Informática');

insert into Curso(id, nome)
values (3, 'Técnico em Agropecuária');
