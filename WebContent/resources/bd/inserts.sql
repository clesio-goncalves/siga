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
values (7, 'Odontólogo');

insert into Perfil (id, nome)
values (8, 'Docente');

insert into Perfil (id, nome)
values (9, 'Monitor');

insert into Perfil (id, nome)
values (10, 'Aluno');

insert into Usuario(id, email, senha, ativo, perfil_id)
values (1, 'clesio@clesio.com','$2a$10$EWuQ/cYO46g0fCOJDUxrReYT/TjKz2QXVIKTXx34eZlgfvkEZ.KFe', 1, 1);
    
insert into Usuario(id, email, senha, ativo, perfil_id)
values (2, 'aluno@aluno.com','$2a$10$qpt3HctirwaIRedM0ERwXegnlFN0dejoMPBqsv5GDxJj9eAM/scBy', 1, 10);

insert into Curso(id, nome)
values (1,'Técnico em Mineração');

insert into Curso(id, nome)
values (2,'Técnico em Informática');

-- seleciona todos os usuário do tipo aluno que ainda estão sem vinculo com algum aluno
select * from Usuario as u 
inner join Perfil as p on p.id = u.perfil_id
where p.nome = 'Aluno' and u.id not in (
	select a.usuario_id from Aluno as a
	where a.usuario_id is not null);
	
-- seleciona todos os usuário do tipo Psicologo, Assistente Social,
-- Enfermeiro ou Odontologo que ainda estão sem vinculo com algum 
-- desses profissionais da saúde 
select * from Usuario as u 
inner join Perfil as p on p.id = u.perfil_id
where p.nome in ('Psicólogo', 'Assistente Social', 'Enfermeiro', 'Odontólogo') 
	and u.id not in (
	select ps.usuario_id from ProfissionalSaude as ps);

select count(c.id) from Curso c 
inner join CursoDisciplina cd on cd.curso_id = c.id 
inner join Disciplina d on d.id = cd.disciplina_id 
where c.id = 3;




