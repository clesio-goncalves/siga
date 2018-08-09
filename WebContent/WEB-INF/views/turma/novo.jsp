<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Cadastrar Turma</title>

<c:import url="../componentes/cabecalho.jsp" />
<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">Cadastrar Turma</h1>
		<p class="lead">Preencha o formulário abaixo para realizar o
			cadastro do turma no sistema.</p>
	</div>
</div>
<div class="container">
	<form action="adiciona" method="POST">

		<div class="row">
			<!-- ANO INGRESSO -->
			<div class="form-group col-sm-7" class="col-form-label">
				<label for="ano_ingresso">Ano*</label> <input type="text"
					class="form-control" name="ano_ingresso" MAXLENGTH="4" required
					onkeypress='return SomenteNumero(event)' autofocus>
			</div>

			<!-- PERIODO INGRESSO -->
			<div class="form-group col-sm-5" class="col-form-label">
				<label for="periodo_ingresso" class="col-form-label">Período*</label>
				<select class="custom-select" name="periodo_ingresso" required>
					<option value="1">1º Semestre</option>
					<option value="2">2º Semestre</option>
				</select>
			</div>

		</div>

		<!-- CURSO -->
		<div class="form-group">
			<label for="curso.id" class="col-form-label">Curso*</label>
			<c:forEach var="curso" items="${cursos}">
				<div class="custom-control custom-radio">
					<input type="radio" id="${curso.id}" name="curso.id"
						value="${curso.id}" class="custom-control-input" checked required>
					<label class="custom-control-label" for="${curso.id}">${curso.nome}</label>
				</div>
			</c:forEach>
		</div>

		<input type="hidden" name="nome" value="nome">

		<security:csrfInput />

		<!-- OBTIGATÓRIO -->
		<label for="obrigatorio">(*) Campo obrigatório</label>
		<div>
			<button type="reset" class="btn btn-secondary btn-lg">
				<span class="glyphicon glyphicon-trash"></span> Limpar
			</button>
			<button type="submit" class="btn btn-primary btn-lg">
				<span class="glyphicon glyphicon-saved"></span> Salvar
			</button>
		</div>
	</form>
</div>
<script type="text/javascript"
	src="<c:url value="/resources/js/SomenteNumero.js" />"></script>
<c:import url="../componentes/rodape.jsp" />