<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<title>Editar Docente</title>

<c:import url="../componentes/cabecalho.jsp" />

<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">Editar Docente</h1>
		<p class="lead">Preencha o formulário abaixo para realizar a
			alteração do Docente no sistema. Não é permitida a alteração do campo
			Usuário, com o objetivo de mater a consistência dos dados.</p>
	</div>
</div>
<div class="container">
	<form action="altera" method="POST">

		<!-- ID -->
		<input type="hidden" name="id" value="${docente.id}" required />

		<!-- NOME -->
		<div class="form-group">
			<label for="nome" class="col-form-label">Nome Completo<span
				class="obrigatorio">*</span></label> <input type="text" class="form-control"
				name="nome" autofocus MAXLENGTH="255" required
				value="${docente.nome}">
		</div>

		<!-- SIAPE -->
		<div class="form-group">
			<label for="siape" class="col-form-label">SIAPE<span
				class="obrigatorio">*</span></label> <input type="text" class="form-control"
				name="siape" MAXLENGTH="10" required data-mask="99999999999"
				autocomplete="off" value="${docente.siape}">
		</div>

		<!-- USUÁRIO -->
		<input type="hidden" name="usuario.id" value="${docente.usuario.id}" />
		<div class="form-group">
			<label class="col-form-label">Usuário<span
				class="obrigatorio">*</span></label>
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text">@</span>
				</div>
				<select class="custom-select" required disabled>
					<option value="" selected>${docente.usuario.email}</option>
				</select>
			</div>
		</div>

		<security:csrfInput />

		<!-- OBTIGATÓRIO -->
		<label for="obrigatorio">(*) Campos obrigatórios</label>
		<div>
			<a href="<c:url value="/docente/lista" />"
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