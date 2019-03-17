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
values (4, 'psicologia@psicologia.com','$2a$10$09Cq8M99qOo7KdBUveqdBOh78paYZUwyykE.XAJNxto47kmaVuP4.', 1, 4);

insert into Usuario(id, email, senha, ativo, perfil_id)
values (5, 'as@as.com','$2a$10$nHeIl7dPX2j9jACwLTqra.3f.3C7LMdyKJ42tsYUGhUQm98pHkPZG', 1, 5);

insert into Usuario(id, email, senha, ativo, perfil_id)
values (6, 'enfermagem@enfermagem.com','$2a$10$C0VoDrsnPXO2FHf1dhj3Z.IeGGhtuUcdfy5304arpmXt4QQXtfsQO', 1, 6);

insert into Usuario(id, email, senha, ativo, perfil_id)
values (7, 'pedagogia@pedagogia.com','$2a$10$nXD1lk8SjQmO6KBXKvBrFOT8odQNdUOv6k0PR6Mse5stccQHxDdNm', 1, 7);

insert into Usuario(id, email, senha, ativo,  perfil_id)
values (8, 'odontologia@odontologia.com','$2a$10$0joNG6nMLyT.JsqzQ6rjde3yRejgUPvCLQ4zFWgaxvKwPSZhJ4Fzq', 1, 8);

insert into Usuario(id, email, senha, ativo, perfil_id)
values (9, 'docente@docente.com','$2a$10$n/VecX2RV/TY3xfHbW0Jo.bpg2MAoy0yKGHSa.vuZ.ZuGFiB2QYha', 1, 9);

insert into Usuario(id, email, senha, ativo, perfil_id)
values (10, 'monitor@monitor.com','$2a$10$9p2tu15zJKOOLIn.HTx76.Jo/QnHRzO7NYtHtL9Fjv1urtYQIFqWy', 1, 10);

insert into Usuario(id, email, senha, ativo, perfil_id)
values (11, 'aluno@aluno.com','$2a$10$Z032yir9pQ4FSrG/WQxo7eWm4tstNtfspS3srxaholypPXj4FK.a.', 1, 11);

insert into Usuario(id, email, senha, ativo, perfil_id)
values (12, 'cd@cd.com','$2a$10$blE.bIaeXORY9jDiiMq2COivr.osQmn2y0AIvqFRpaVI3X/Ehc3wi', 1, 12);

insert into Situacao (id, nome)
values (1, 'Aprovado sem pendências');

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

insert into Curso(id, nome)
values (1, 'Técnico em Mineração');

insert into Curso(id, nome)
values (2, 'Técnico em Informática');

insert into Curso(id, nome)
values (3, 'Técnico em Agropecuária');

insert into Curso(id, nome)
values (4, 'Técnico em Administração');
