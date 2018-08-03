<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Cadastrar Profissional da Saúde</title>

<c:import url="../componentes/cabecalho.jsp" />

<div class="jumbotron">
	<div class="container">
		<h1>Cadastrar Profissional da Saúde</h1>
		<p>Preencha o formulário abaixo para realizar o cadastro do
			Profissional da Saúde no sistema.</p>
	</div>
</div>
<div class="container">
	<form action="adicionaProfissionalSaude" method="POST">

		<!-- NOME -->
		<div class="form-group">
			<label for="nome">Nome Completo*</label> <input type="text"
				class="form-control" name="nome" autofocus MAXLENGTH="255" required>
		</div>

		<!-- SIAPE -->
		<div class="form-group">
			<label for="siape">SIAPE*</label> <input type="number"
				class="form-control" name=siape MAXLENGTH="10" required
				onkeypress='return SomenteNumero(event)'>
		</div>

		<!-- CURSO -->
		<div class="form-group">
			<label for="tipo_profissional">Tipo de Profissional*</label>
			<div class="radio">
				<label><input type="radio" name="tipo_profissional"
					value="Psicólogo" checked="checked" required> Psicólogo </label>
			</div>
			<div class="radio">
				<label><input type="radio" name="tipo_profissional"
					value="Assistente Social"> Assistente Social </label>
			</div>
			<div class="radio">
				<label><input type="radio" name="tipo_profissional"
					value="Enfermeiro"> Enfermeiro </label>
			</div>
			<div class="radio">
				<label><input type="radio" name="tipo_profissional"
					value="Odontologo"> Odontologo </label>
			</div>

		</div>

		<!-- USUÁRIO-->
		<div class="form-group">
			<label for="usuario">Usuário*</label> <select class="form-control"
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


<script type="text/javascript" src="resources/js/SomenteNumero.js"></script>
<c:import url="../componentes/rodape.jsp" />