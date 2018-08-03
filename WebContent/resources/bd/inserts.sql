insert into Perfil (id, nome)
values (1, 'Administrador');

insert into Perfil (id, nome)
values (2, 'Coordenador');

insert into Perfil (id, nome)
values (3, 'Diretor');

insert into Perfil (id, nome)
values (4, 'Psicólogo');

insert into Perfil (id, nome)
values (5, 'Assistente Social');

insert into Perfil (id, nome)
values (6, 'Enfermeiro');

insert into Perfil (id, nome)
values (7, 'Odontologo');

insert into Perfil (id, nome)
values (8, 'Docente');

insert into Perfil (id, nome)
values (9, 'Monitor');

insert into Perfil (id, nome)
values (10, 'Aluno');

insert into Usuario(id, usuario, senha, ativo, perfil_id)
values (1, 'clesio','$2a$10$EWuQ/cYO46g0fCOJDUxrReYT/TjKz2QXVIKTXx34eZlgfvkEZ.KFe', 1, 1);
    
insert into Usuario(id, usuario, senha, ativo, perfil_id)
values (2, 'teste','$2a$10$qpt3HctirwaIRedM0ERwXegnlFN0dejoMPBqsv5GDxJj9eAM/scBy', 1, 8);

insert into Aluno(id, nome, curso_id, usuario_id)
values (1,'Aluno com Usuario', 1, 2);

-- seleciona todos os usuário do tipo aluno que ainda estão sem vinculo com algum aluno
select * from Usuario as u 
inner join Perfil as p on p.id = u.perfil_id
where p.nome = 'Aluno' and u.id not in (
	select a.usuario_id from Aluno as a
	where a.usuario_id is not null);






