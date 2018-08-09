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
		<h1 class="display-3">Editar Aluno</h1>
		<p class="lead">Preencha o formulário abaixo para realizar a
			alteração do aluno no sistema.</p>
	</div>
</div>
<div class="container">
	<form action="altera" method="POST">

		<!-- ID -->
		<input type="hidden" name="id" value="${aluno.id}" />

		<!-- NOME -->
		<div class="form-group">
			<label for="nome" class="col-form-label">Nome Completo*</label> <input
				type="text" class="form-control" name="nome" autofocus
				MAXLENGTH="255" required value="${aluno.nome}">
		</div>

		<!-- MATRICULA -->
		<div class="form-group">
			<label for="matricula" class="col-form-label">Matricula</label> <input
				type="text" class="form-control" name="matricula" MAXLENGTH="50"
				value="${aluno.matricula}">
		</div>

		<!-- TURMA -->
		<div class="form-group">
			<label for="turma.id" class="col-form-label">Turma*</label>
			<c:forEach var="turma" items="${turmas}">
				<div class="custom-control custom-radio">
					<input type="radio" id="${turma.id}" name="turma.id"
						class="custom-control-input" required value="${turma.id}"
						${turma.id == aluno.turma.id ? 'checked' : ''}> <label
						class="custom-control-label" for="${turma.id}">${turma.nome}</label>
				</div>
			</c:forEach>
		</div>

		<!-- USUÁRIO-->
		<div class="form-group">
			<label for="usuario.id" class="col-form-label">Usuário</label> <select
				class="custom-select" name="usuario.id">
				<option value="">Não informar</option>
				<c:if test="${aluno.usuario != null}">
					<option value="${aluno.usuario.id}" selected>${aluno.usuario.email}</option>
				</c:if>
				<c:if test="${aluno.usuario == null}">
					<c:forEach var="usuario" items="${usuarios}">
						<option value="${usuario.id}">${usuario.email}</option>
					</c:forEach>
				</c:if>
			</select>
		</div>

		<security:csrfInput />

		<!-- OBTIGATÓRIO -->
		<label>(*) Campos obrigatórios</label>

		<div>
			<a href="<c:url value="/aluno/lista" />"
				class="btn btn-secondary btn-lg"> <span
				class="glyphicon glyphicon-remove"></span> Cancelar
			</a>
			<button type="submit" class="btn btn-primary btn-lg">
				<span class="glyphicon glyphicon-refresh"></span> Atualizar
			</button>
		</div>
	</form>
</div>

<c:import url="../componentes/rodape.jsp" />