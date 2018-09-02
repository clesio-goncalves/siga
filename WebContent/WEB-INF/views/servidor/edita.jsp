<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<title>Editar Servidor</title>

<c:import url="../componentes/cabecalho.jsp" />

<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">Editar Servidor</h1>
		<p class="lead">Preencha o formul�rio abaixo para realizar a
			altera��o do Servidor no sistema. � permitida a altera��o somente dos
			campos Nome, SIAPE e Descri��o da Fun��o com o objetivo de mater a
			consist�ncia dos dados.</p>
	</div>
</div>
<div class="container">
	<form action="altera" method="POST">

		<!-- ID -->
		<input type="hidden" name="id" value="${servidor.id}" required/>

		<!-- NOME -->
		<div class="form-group">
			<label for="nome" class="col-form-label">Nome Completo<span
				class="obrigatorio">*</span></label> <input type="text" class="form-control"
				name="nome" autofocus MAXLENGTH="255" required
				value="${servidor.nome}">
		</div>

		<!-- SIAPE -->
		<div class="form-group">
			<label for="siape" class="col-form-label">SIAPE<span
				class="obrigatorio">*</span></label> <input type="text" class="form-control"
				name="siape" MAXLENGTH="11" required value="${servidor.siape}"
				data-mask="99999999999">
		</div>

		<!-- FUNCAO -->
		<input type="hidden" name="funcao" value="${servidor.funcao}" />
		<div class="form-group">
			<label class="col-form-label">Fun��o<span class="obrigatorio">*</span></label>
			<div class="custom-control custom-radio">
				<input type="radio" id="customRadio1" class="custom-control-input"
					checked disabled required value=""> <label
					class="custom-control-label" for="customRadio1">${servidor.funcao}</label>
			</div>
		</div>

		<!-- DESCRICAO FUN��O -->
		<div class="form-group">
			<label for="descricao_funcao" class="col-form-label">Descri��o
				da Fun��o<span class="obrigatorio">*</span>
			</label> <input type="text" class="form-control" name="descricao_funcao"
				MAXLENGTH="255" required value="${servidor.descricao_funcao}">
		</div>

		<!-- USU�RIO -->
		<input type="hidden" name="usuario.id" value="${servidor.usuario.id}" />
		<div class="form-group">
			<label class="col-form-label">Usu�rio<span
				class="obrigatorio">*</span></label> <select class="custom-select" required
				disabled>
				<option value="" selected>${servidor.usuario.email}</option>
			</select>
		</div>

		<security:csrfInput />

		<!-- OBTIGAT�RIO -->
		<label for="obrigatorio">(*) Campos obrigat�rios</label>
		<div>
			<a href="<c:url value="/servidor/lista" />"
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