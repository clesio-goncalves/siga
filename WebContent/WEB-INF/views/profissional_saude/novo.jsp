<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Cadastrar Profissional da Saúde</title>

<c:import url="../componentes/cabecalho.jsp" />
<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">Cadastrar Profissional da Saúde</h1>
		<p class="lead">Preencha o formulário abaixo para realizar o
			cadastro do Profissional de Saúde no sistema.</p>
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
				class="form-control" name=siape MAXLENGTH="10" required
				onkeypress='return SomenteNumero(event)'>
		</div>

		<!-- TIPO PROFISSIONAL -->
		<div class="form-group">
			<label for="tipo_profissional" class="col-form-label">Tipo de
				Profissional*</label>
			<div class="custom-control custom-radio">
				<input type="radio" id="customRadio1" name="tipo_profissional"
					class="custom-control-input" checked="checked" required
					value="Psicólogo"> <label class="custom-control-label"
					for="customRadio1">Psicólogo</label>
			</div>
			<div class="custom-control custom-radio">
				<input type="radio" id="customRadio2" name="tipo_profissional"
					class="custom-control-input" value="Assistente Social"> <label
					class="custom-control-label" for="customRadio2">Assistente
					Social</label>
			</div>
			<div class="custom-control custom-radio">
				<input type="radio" id="customRadio3" name="tipo_profissional"
					class="custom-control-input" value="Enfermeiro"> <label
					class="custom-control-label" for="customRadio3">Enfermeiro</label>
			</div>
			<div class="custom-control custom-radio">
				<input type="radio" id="customRadio4" name="tipo_profissional"
					class="custom-control-input" value="Odontólogo"> <label
					class="custom-control-label" for="customRadio4">Odontólogo</label>
			</div>

		</div>

		<!-- USUÁRIO-->
		<div class="form-group" id="lista_usuarios">
			<jsp:include page="lista_usuarios.jsp"></jsp:include>
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
<script type="text/javascript"
	src="<c:url value="/resources/js/filtro_cadastro/filtroCadProfissional.js" />"></script>
<c:import url="../componentes/rodape.jsp" />