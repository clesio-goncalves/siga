<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<title>Editar Monitor</title>
<c:import url="../componentes/cabecalho.jsp" />
<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">Editar Monitor</h1>
		<p class="lead">Preencha o formulário abaixo para realizar a
			alteração do monitor no sistema. Não é permitida a alteração do campo
			Usuário, com o objetivo de mater a consistência dos dados.</p>
	</div>
</div>
<div class="container">
	<form action="altera" method="POST">

		<!-- ID -->
		<input type="hidden" name="id" value="${monitor.id}" required/>

		<!-- NOME -->
		<div class="form-group">
			<label for="nome" class="col-form-label">Nome Completo<span class="obrigatorio">*</span></label> <input
				type="text" class="form-control" name="nome" autofocus
				MAXLENGTH="255" required="required" value="${monitor.nome}">
		</div>

		<!-- MATRICULA -->
		<div class="form-group">
			<label for="matricula" class="col-form-label">Matricula</label> <input
				type="text" class="form-control" name="matricula" MAXLENGTH="50"
				required="required" value="${monitor.matricula}">
		</div>

		<!-- DISCIPLINA -->
		<div class="form-group">
			<label for="disciplina.id" class="col-form-label">Disciplina<span class="obrigatorio">*</span></label>
			<select class="custom-select" name="disciplina.id"
				required="required">
				<c:forEach var="disciplina" items="${disciplinas}">
					<option value="${disciplina.id}"
						${disciplina.id==monitor.disciplina.id ? 'selected' : ''}>${disciplina.nome}</option>
				</c:forEach>
			</select>
		</div>

		<!-- USUÁRIO-->
		<input type="hidden" name="usuario.id" value="${monitor.usuario.id}" />
		<div class="form-group">
			<label class="col-form-label">Usuário<span class="obrigatorio">*</span></label> <select
				class="custom-select" required="required" disabled="disabled">
				<option value="" selected>${monitor.usuario.email}</option>
			</select>
		</div>

		<security:csrfInput />

		<!-- OBTIGATÓRIO -->
		<label>(*) Campos obrigatórios</label>

		<div>
			<a href="<c:url value="/monitor/lista" />"
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