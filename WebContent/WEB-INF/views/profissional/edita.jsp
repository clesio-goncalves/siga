<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<title>Editar Profissional</title>

<c:import url="../componentes/cabecalho.jsp" />

<div class="jumbotron">
	<div class="container">
		<h1>Editar Profissional</h1>
		<p class="lead">Preencha o formul�rio abaixo para realizar a
			altera��o do Profissional no sistema. � permitida a altera��o somente
			dos campos Nome e SIAPE, com o objetivo de mater a consist�ncia dos
			dados.</p>
	</div>
</div>
<div class="container">
	<form action="altera" method="POST">

		<!-- ID -->
		<input type="hidden" name="id" value="${profissional.id}" required />

		<!-- NOME -->
		<div class="form-group">
			<label for="nome" class="col-form-label">Nome Completo<span
				class="obrigatorio">*</span></label> <input type="text" class="form-control"
				name="nome" autofocus MAXLENGTH="255" required
				value="${profissional.nome}">
		</div>

		<!-- SIAPE -->
		<div class="form-group">
			<label for="siape" class="col-form-label">SIAPE<span
				class="obrigatorio">*</span></label> <input type="text" class="form-control"
				name="siape" MAXLENGTH="11" required value="${profissional.siape}"
				data-mask="99999999999" autocomplete="off">
		</div>

		<!-- TIPO DE PROFISSIONAL -->
		<input type="hidden" name="tipo_atendimento"
			value="${profissional.tipo_atendimento}" />
		<div class="form-group">
			<label class="col-form-label">Tipo de Atendimento<span
				class="obrigatorio">*</span></label>
			<div class="custom-control custom-radio">
				<input type="radio" id="customRadio1" class="custom-control-input"
					checked disabled="disabled" required value=""> <label
					class="custom-control-label" for="customRadio1">${profissional.tipo_atendimento}</label>
			</div>
		</div>

		<!-- USU�RIO -->
		<input type="hidden" name="usuario.id"
			value="${profissional.usuario.id}" />
		<div class="form-group">
			<label class="col-form-label">Usu�rio<span
				class="obrigatorio">*</span></label>
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text">@</span>
				</div>
				<select class="custom-select" required disabled="disabled">
					<option value="" selected>${profissional.usuario.email}</option>
				</select>
			</div>
		</div>

		<security:csrfInput />

		<!-- OBTIGAT�RIO -->
		<label for="obrigatorio">(*) Campos obrigat�rios</label>
		<div>
			<a href="<c:url value="/profissional/lista" />"
				class="btn btn-secondary btn-lg"> <span
				class="glyphicon glyphicon-remove"></span> Cancelar
			</a>
			<button type="submit" class="btn btn-primary btn-lg">
				<span class="glyphicon glyphicon-refresh"></span> Atualizar
			</button>
		</div>
	</form>
</div>

<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.mask.min.js" />"></script>
<c:import url="../componentes/rodape.jsp" />