<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Editar Usuário</title>

<c:import url="../componentes/cabecalho.jsp" />

<div class="jumbotron">
	<div class="container">
		<h1>Editar Usuário</h1>
		<p>Preencha o formulário abaixo para realizar a alteração.</p>
	</div>
</div>
<div class="container">
	<form action="alteraUsuario" method="POST">

		<!-- ID -->
		<input type="hidden" name="id" value="${usuario.id}" />

		<!-- NOME -->
		<div class="form-group">
			<label for="nome">Nome Completo*</label> <input type="text"
				class="form-control" name="nome" autofocus MAXLENGTH="255" required
				value="${usuario.nome}">
		</div>

		<!-- USUARIO -->
		<div class="form-group">
			<label for="usuario">Usuário*</label> <input type="text"
				class="form-control" name="usuario" MAXLENGTH="20" required
				value="${usuario.usuario}">
		</div>

		<!-- SENHA -->
		<div class="form-group">
			<label for="senha">Senha*</label> <input type="password"
				class="form-control" name="senha" id="senha" MAXLENGTH="20" required>
		</div>

		<!-- REPETIR SENHA -->
		<div class="form-group">
			<label for="repetir_senha">Repetir Senha*</label> <input
				type="password" class="form-control" name="repetir_senha"
				id="repetir_senha" MAXLENGTH="20" required>
		</div>

		<!-- SETOR -->
		<div class="form-group">
			<label for="setor">Setor*</label> <select class="form-control"
				name="setor.id" required>
				<!-- percorre setores montando as linhas da tabela -->
				<c:forEach var="st" items="${setores}">
					<option value="${st.id}"
						${st.id == usuario.setor.id ? 'selected' : ''}>${st.nome}</option>
				</c:forEach>
			</select>
		</div>
		
		<!-- PERMISSÃO -->
		<div class="form-group">
			<label for="permissao.id">Permissão*</label>
			<c:forEach var="permissao" items="${permissoes}">
				<div class="radio">
					<label> <input type="radio" name="permissao.id" value="${permissao.id}" ${permissao.id == usuario.permissao.id ? 'checked' : ''}> ${permissao.nome} </label>
				</div>
			</c:forEach>
		</div>

		<!-- ATIVO -->
		<div class="form-group">
			<div class="checkbox">
				<label> <input type="checkbox" name="ativo"
					${usuario.ativo ? 'checked' : ''}> Ativo
				</label>
			</div>
		</div>
		
		<security:csrfInput />

		<!-- OBTIGATÓRIO -->
		<label for="obrigatorio">(*) Campos obrigatórios</label>
		<div>
			<a href="listaUsuarios" class="btn btn-default btn-lg"> <span
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