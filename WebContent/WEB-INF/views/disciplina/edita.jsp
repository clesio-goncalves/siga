<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Editar Disciplina</title>

<c:import url="../componentes/cabecalho.jsp" />
<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">Editar Disciplina</h1>
		<p class="lead">Preencha o formulário abaixo para realizar a
			alteração da disciplina no sistema.</p>
	</div>
</div>
<div class="container">
	<form:form action="alteraDisciplina" method="POST"
		modelAttribute="disciplina">

		<!-- ID -->
		<input type="hidden" name="id" value="${disciplina.id}">

		<!-- NOME -->
		<div class="form-group">
			<label for="nome" class="col-form-label">Nome*</label> <input
				type="text" class="form-control" name="nome" autofocus
				MAXLENGTH="255" required value="${disciplina.nome}">
		</div>

		<!-- CURSO -->
		<div class="form-group">
			<label for="lista_cursos" class="col-form-label">Cursos*</label>
			<c:set var="checked" value="" />
			<c:forEach var="curso" items="${lista_todos_cursos}">
				<div class="custom-control custom-checkbox">
					<form:checkbox path="lista_cursos" value="${curso.nome}"
						class="custom-control-input" id="curso${curso.id}" />
					<label class="custom-control-label" for="curso${curso.id}">${curso.nome}</label>
				</div>
			</c:forEach>
		</div>

		<security:csrfInput />

		<!-- OBTIGATÓRIO -->
		<label for="obrigatorio">(*) Campo obrigatório</label>
		<div>
			<a href="listaDisciplinas" class="btn btn-secondary btn-lg"> <span
				class="glyphicon glyphicon-remove"></span> Cancelar
			</a>
			<button type="submit" class="btn btn-primary btn-lg">
				<span class="glyphicon glyphicon-refresh"></span> Atualizar
			</button>
		</div>
	</form:form>
</div>

<c:import url="../componentes/rodape.jsp" />