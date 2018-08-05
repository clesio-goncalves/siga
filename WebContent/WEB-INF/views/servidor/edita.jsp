<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Editar Servidor</title>

<c:import url="../componentes/cabecalho.jsp" />

<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">Editar Servidor</h1>
		<p class="lead">Preencha o formulário abaixo para realizar a
			alteração do Profissional da Saúde no sistema. É permitida a
			alteração somente dos campos Nome, SIAPE e Descrição da Função com o
			objetivo de mater a consistência dos dados.</p>
	</div>
</div>
<div class="container">
	<form action="alteraServidor" method="POST">

		<!-- ID -->
		<input type="hidden" name="id" value="${servidor.id}" />

		<!-- NOME -->
		<div class="form-group">
			<label for="nome" class="col-form-label">Nome Completo*</label> <input
				type="text" class="form-control" name="nome" autofocus
				MAXLENGTH="255" required value="${servidor.nome}">
		</div>

		<!-- SIAPE -->
		<div class="form-group" class="col-form-label">
			<label for="siape">SIAPE*</label> <input type="number"
				class="form-control" name=siape MAXLENGTH="10" required
				onkeypress='return SomenteNumero(event)' value="${servidor.siape}">
		</div>

		<!-- FUNCAO -->
		<input type="hidden" name="funcao" value="${servidor.funcao}" />
		<div class="form-group">
			<label class="col-form-label">Função*</label>
			<div class="custom-control custom-radio">
				<input type="radio" id="customRadio1" class="custom-control-input"
					checked disabled required value=""> <label
					class="custom-control-label" for="customRadio1">${servidor.funcao}</label>
			</div>
		</div>

		<!-- DESCRICAO FUNÇÃO -->
		<div class="form-group">
			<label for="descricao_funcao" class="col-form-label">Descrição
				da Função*</label> <input type="text" class="form-control"
				name="descricao_funcao" MAXLENGTH="255" required
				value="${servidor.descricao_funcao}">
		</div>

		<!-- USUÁRIO -->
		<input type="hidden" name="usuario.id" value="${servidor.usuario.id}" />
		<div class="form-group">
			<label class="col-form-label">Usuário*</label> <select
				class="custom-select" required disabled>
				<option value="" selected>${servidor.usuario.email}</option>
			</select>
		</div>

		<security:csrfInput />

		<!-- OBTIGATÓRIO -->
		<label for="obrigatorio">(*) Campos obrigatórios</label>
		<div>
			<a href="listaServidores" class="btn btn-secondary btn-lg"> <span
				class="glyphicon glyphicon-remove"></span> Cancelar
			</a>
			<button type="submit" class="btn btn-primary btn-lg">
				<span class="glyphicon glyphicon-refresh"></span> Atualizar
			</button>
		</div>
	</form>
</div>

<script type="text/javascript" src="resources/js/SomenteNumero.js"></script>
<c:import url="../componentes/rodape.jsp" />