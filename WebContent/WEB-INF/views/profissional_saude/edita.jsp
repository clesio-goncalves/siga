<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<title>Editar Profissional da saúde</title>

<c:import url="../componentes/cabecalho.jsp" />

<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">Editar Profissional da Saúde</h1>
		<p class="lead">Preencha o formulário abaixo para realizar a
			alteração do Profissional da Saúde no sistema. É permitida a
			alteração somente dos campos Nome e SIAPE, com o objetivo de mater a
			consistência dos dados.</p>
	</div>
</div>
<div class="container">
	<form action="altera" method="POST">

		<!-- ID -->
		<input type="hidden" name="id" value="${profissional_saude.id}" />

		<!-- NOME -->
		<div class="form-group">
			<label for="nome" class="col-form-label">Nome Completo<span
				class="obrigatorio">*</span></label> <input type="text" class="form-control"
				name="nome" autofocus MAXLENGTH="255" required
				value="${profissional_saude.nome}">
		</div>

		<!-- SIAPE -->
		<div class="form-group">
			<label for="siape" class="col-form-label">SIAPE<span
				class="obrigatorio">*</span></label> <input type="text" class="form-control"
				name="siape" MAXLENGTH="11" required
				value="${profissional_saude.siape}" data-mask="99999999999">
		</div>

		<!-- TIPO DE PROFISSIONAL -->
		<input type="hidden" name="tipo_profissional"
			value="${profissional_saude.tipo_profissional}" />
		<div class="form-group">
			<label class="col-form-label">Tipo de Profissional<span
				class="obrigatorio">*</span></label>
			<div class="custom-control custom-radio">
				<input type="radio" id="customRadio1" class="custom-control-input"
					checked disabled="disabled" required value=""> <label
					class="custom-control-label" for="customRadio1">${profissional_saude.tipo_profissional}</label>
			</div>
		</div>

		<!-- USUÁRIO -->
		<input type="hidden" name="usuario.id"
			value="${profissional_saude.usuario.id}" />
		<div class="form-group">
			<label class="col-form-label">Usuário<span
				class="obrigatorio">*</span></label> <select class="custom-select" required
				disabled="disabled">
				<option value="" selected>${profissional_saude.usuario.email}</option>
			</select>
		</div>

		<security:csrfInput />

		<!-- OBTIGATÓRIO -->
		<label for="obrigatorio">(*) Campos obrigatórios</label>
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

<c:import url="../componentes/rodape.jsp" />