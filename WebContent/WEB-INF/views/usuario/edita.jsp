<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Cadastrar Usuário</title>
<c:import url="../componentes/cabecalho.jsp" />
<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">Editar Usuário</h1>
		<p class="lead">Preencha o formulário abaixo para realizar a
			alteração do usuário no sistema.</p>
	</div>
</div>
<div class="container">
	<form action="alteraUsuario" method="POST">

		<!-- ID -->
		<input type="hidden" name="id" value="${usuario.id}" />

		<!-- USUARIO -->
		<div class="form-group">
			<label for="usuario" class="col-form-label">Usuário*</label> <input
				type="text" class="form-control" name="usuario" MAXLENGTH="50"
				required autofocus value="${usuario.usuario}">
		</div>

		<!-- SENHA -->
		<div class="form-group">
			<label for="senha" class="col-form-label">Senha*</label> <input
				type="password" class="form-control" name="senha" id="senha"
				MAXLENGTH="50" required>
		</div>

		<!-- REPETIR SENHA -->
		<div class="form-group">
			<label for="repetir_senha" class="col-form-label">Repetir
				Senha*</label> <input type="password" class="form-control"
				name="repetir_senha" id="repetir_senha" MAXLENGTH="50" required>
		</div>

		<!-- PERMISSÃO -->
		<div class="form-group">
			<label for="perfil" class="col-form-label">Perfil*</label> <select
				class="custom-select" name="perfil.id" required>
				<!-- percorre perfil montando as linhas da tabela -->
				<c:forEach var="perfil" items="${perfis}">
					<option value="${perfil.id}"
						${perfil.id==usuario.perfil.id ? 'selected' : ''}>${perfil.nome}</option>
				</c:forEach>
			</select>
		</div>

		<!-- ATIVO -->
		<div class="form-group">
			<div class="custom-control custom-checkbox">
				<input type="checkbox" class="custom-control-input"
					id="customCheck1" name="ativo" ${usuario.ativo ? 'checked' : ''}>
				<label class="custom-control-label" for="customCheck1">Ativo</label>
			</div>
		</div>

		<security:csrfInput />

		<!-- OBTIGATÓRIO -->
		<label for="obrigatorio">(*) Campos obrigatórios</label>
		<div>
			<a href="listaUsuarios" class="btn btn-secondary btn-lg"> <span
				class="glyphicon glyphicon-remove"></span> Cancelar
			</a>
			<button type="submit" class="btn btn-primary btn-lg">
				<span class="glyphicon glyphicon-refresh"></span> Atualizar
			</button>
		</div>
	</form>
</div>

<script src="resources/js/confirma_senha.js"></script>

<c:import url="../componentes/rodape.jsp" />