<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Cadastrar Aluno</title>

<c:import url="../componentes/cabecalho.jsp" />

<div class="jumbotron">
	<div class="container">
		<h1>Cadastrar Aluno</h1>
		<p>Preencha o formulário abaixo para realizar o cadastro do
			aluno no sistema.</p>
	</div>
</div>
<div class="container">
	<form action="adicionaAluno" method="POST">

		<!-- NOME -->
		<div class="form-group">
			<label for="nome">Nome Completo*</label> <input type="text"
				class="form-control" name="nome" autofocus MAXLENGTH="255" required>
		</div>

		<!-- MATRICULA -->
		<div class="form-group">
			<label for="matricula">Matricula</label> <input type="text"
				class="form-control" name="matricula" MAXLENGTH="50">
		</div>
		
		<!-- CURSO -->
		<div class="form-group">
			<label for="curso.id">Curso*</label>
			<c:forEach var="curso" items="${cursos}">
				<div class="radio">
					<label> <input type="radio" name="curso.id"
						value="${curso.id}" checked="checked">
						${curso.nome}
					</label>
				</div>
			</c:forEach>
		</div>
		
		<!-- USUÁRIO-->
		<div class="form-group">
			<label for="usuario">Usuário</label> <select class="form-control"
				name="usuario.id">
				<option value="">Não informar</option>
				<!-- percorre usuarios montando as linhas da tabela -->
				<c:forEach var="usuario" items="${usuarios}">
					<option value="${usuario.id}">${usuario.usuario}</option>
				</c:forEach>
			</select>
		</div> 


		<security:csrfInput />

		<!-- OBTIGATÓRIO -->
		<label for="obrigatorio">(*) Campos obrigatórios</label>
		<div>
			<button type="reset" class="btn btn-default btn-lg">
				<span class="glyphicon glyphicon-trash"></span> Limpar
			</button>
			<button type="submit" class="btn btn-primary btn-lg">
				<span class="glyphicon glyphicon-saved"></span> Salvar
			</button>
		</div>
	</form>
</div>

<c:import url="../componentes/rodape.jsp" />