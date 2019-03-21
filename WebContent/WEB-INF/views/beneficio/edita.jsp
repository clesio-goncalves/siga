<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<title>Editar Benef�cio</title>

<c:import url="../componentes/cabecalho.jsp" />

<div class="jumbotron">
	<div class="container">
		<h1>Editar Benef�cio</h1>
		<p class="lead">Preencha o formul�rio abaixo para realizar a
			altera��o do benef�cio assistencial no sistema. Pol�tica de
			Assist�ncia Estudantil (POLAE) - Resolu��o 14/2014, do Conselho
			Superior do IFPI</p>
	</div>
</div>
<div class="container">
	<form action="altera" method="POST">

		<!-- ID -->
		<input type="hidden" name="id" value="${beneficio.id}" required>

		<!-- NOME -->
		<div class="form-group">
			<label for="nome" class="col-form-label">Nome<span
				class="obrigatorio">*</span></label> <input type="text" class="form-control"
				name="nome" autofocus MAXLENGTH="50" required
				value="${beneficio.nome}">
		</div>

		<security:csrfInput />

		<!-- OBTIGAT�RIO -->
		<label for="obrigatorio">(*) Campo obrigat�rio</label>
		<div>
			<a href="<c:url value="/beneficio/lista" />"
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