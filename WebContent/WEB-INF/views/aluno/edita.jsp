<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Editar Aluno</title>

<c:import url="../componentes/cabecalho.jsp" />

<div class="jumbotron">
	<div class="container">
		<h1>Editar Aluno</h1>
		<p>Preencha o formulário abaixo para realizar a alteração.</p>
	</div>
</div>
<div class="container">
	<form action="alteraAluno" method="POST">

		<!-- ID -->
		<input type="hidden" name="id" value="${aluno.id}" />

		<!-- NOME -->
		<div class="form-group">
			<label for="nome">Nome Completo*</label> <input type="text"
				class="form-control" name="nome" autofocus MAXLENGTH="255" required
				value="${aluno.nome}">
		</div>

		<!-- MATRICULA -->
		<div class="form-group">
			<label for="matricula">Matricula</label> <input type="text"
				class="form-control" name="matricula" MAXLENGTH="50"
				value="${aluno.matricula}">
		</div>

		<!-- CURSO -->
		<div class="form-group">
			<label for="curso.id">Curso*</label>
			<c:forEach var="curso" items="${cursos}">
				<div class="radio">
					<label> <input type="radio" name="permissao.id"
						value="${curso.id}" ${curso.id == aluno.curso.id ? 'checked' : ''}>
						${curso.nome}
					</label>
				</div>
			</c:forEach>
		</div>

		<!-- USUÁRIO -->
		<div class="form-group">
			<label for="usuario">Usuário</label> <select class="form-control"
				name="usuario.id">
				<!-- percorre usuários montando as linhas da tabela -->
				<option value="">Não informar</option>
				<c:forEach var="usuario" items="${usuarios}">
					<option value="${usuario.id}"
						${usuario.id == aluno.usuario.id ? 'selected' : ''}>${usuario.usuario}</option>
				</c:forEach>
			</select>
		</div>

		<security:csrfInput />

		<!-- OBTIGATÓRIO -->
		<label for="obrigatorio">(*) Campos obrigatórios</label>
		<div>
			<a href="listaAlunos" class="btn btn-default btn-lg"> <span
				class="glyphicon glyphicon-remove"></span> Cancelar
			</a>
			<button type="submit" class="btn btn-primary btn-lg">
				<span class="glyphicon glyphicon-refresh"></span> Atualizar
			</button>
		</div>
	</form>
</div>

<c:import url="../componentes/rodape.jsp" />