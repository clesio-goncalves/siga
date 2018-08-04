<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Editar Profissional da saúde</title>

<c:import url="../componentes/cabecalho.jsp" />

<div class="jumbotron">
	<div class="container">
		<h1>Editar Aluno</h1>
		<p>Preencha o formulário abaixo para realizar a alteração. É
			permitida a alteração somente dos campos Nome e SIAPE, com o objetivo
			de mater a consistência dos dados.</p>
	</div>
</div>
<div class="container">
	<form action="alteraProfissionalSaude" method="POST">

		<!-- ID -->
		<input type="hidden" name="id" value="${profissional_saude.id}" />

		<!-- NOME -->
		<div class="form-group">
			<label for="nome">Nome Completo*</label> <input type="text"
				class="form-control" name="nome" autofocus MAXLENGTH="255" required
				value="${profissional_saude.nome}">
		</div>

		<!-- SIAPE -->
		<div class="form-group">
			<label for="siape">SIAPE*</label> <input type="number"
				class="form-control" name=siape MAXLENGTH="10" required
				onkeypress='return SomenteNumero(event)'
				value="${profissional_saude.siape}">
		</div>

		<!-- CURSO -->
		<div class="form-group">
			<label for="tipo_profissional">Tipo de Profissional*</label>
			<div class="radio">
				<label><input type="radio" name="tipo_profissional"
					value="${profissional_saude.tipo_profissional}" checked="checked"
					required disabled="disabled"> ${profissional_saude.tipo_profissional} </label>
			</div>
		</div>

		<!-- USUÁRIO -->
		<div class="form-group">
			<label for="usuario">Usuário*</label> <select class="form-control"
				name="usuario.id" required disabled="disabled">
				<option value="${profissional_saude.usuario.id}">${profissional_saude.usuario.usuario}</option>
			</select>
		</div>

		<security:csrfInput />

		<!-- OBTIGATÓRIO -->
		<label for="obrigatorio">(*) Campos obrigatórios</label>
		<div>
			<a href="listaProfissionalSaude" class="btn btn-default btn-lg">
				<span class="glyphicon glyphicon-remove"></span> Cancelar
			</a>
			<button type="submit" class="btn btn-primary btn-lg">
				<span class="glyphicon glyphicon-refresh"></span> Atualizar
			</button>
		</div>
	</form>
</div>

<script type="text/javascript" src="resources/js/SomenteNumero.js"></script>
<c:import url="../componentes/rodape.jsp" />