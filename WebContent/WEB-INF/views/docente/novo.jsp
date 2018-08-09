<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Cadastrar Docente</title>

<c:import url="../componentes/cabecalho.jsp" />
<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">Cadastrar Docente</h1>
		<p class="lead">Preencha o formulário abaixo para realizar o
			cadastro do Docente no sistema.</p>
	</div>
</div>
<div class="container">
	<form action="adiciona" method="POST">

		<!-- NOME -->
		<div class="form-group">
			<label for="nome" class="col-form-label">Nome Completo*</label> <input
				type="text" class="form-control" name="nome" autofocus
				MAXLENGTH="255" required>
		</div>

		<!-- SIAPE -->
		<div class="form-group" class="col-form-label">
			<label for="siape">SIAPE*</label> <input type="number"
				class="form-control" name="siape" MAXLENGTH="10" required
				onkeypress='return SomenteNumero(event)'>
		</div>

		<!-- USUÁRIO-->
		<div class="form-group" id="lista_usuarios">
			<label for="usuario.id" class="col-form-label">Usuário*</label> <select
				class="custom-select" name="usuario.id" required>
				<c:forEach var="usuario" items="${usuarios}">
					<option value="${usuario.id}">${usuario.email}</option>
				</c:forEach>
			</select>
		</div>

		<security:csrfInput />

		<!-- OBTIGATÓRIO -->
		<label>(*) Campos obrigatórios</label>
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