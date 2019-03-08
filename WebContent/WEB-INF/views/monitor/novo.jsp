<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<title>Cadastrar Monitor</title>
<c:import url="../componentes/cabecalho.jsp" />
<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">Cadastrar Monitor</h1>
		<p class="lead">Preencha o formulário abaixo para realizar o
			cadastro do monitor no sistema.</p>
	</div>
</div>
<div class="container">
	<form action="adiciona" method="POST">

		<!-- NOME -->
		<div class="form-group">
			<label for="nome" class="col-form-label">Nome Completo<span
				class="obrigatorio">*</span></label> <input type="text" class="form-control"
				name="nome" autofocus MAXLENGTH="255" required="required">
		</div>

		<!-- MATRICULA -->
		<div class="form-group">
			<label for="matricula" class="col-form-label">Matricula<span
				class="obrigatorio">*</span></label> <input type="text" class="form-control"
				name="matricula" MAXLENGTH="50" required="required">
		</div>

		<!-- USUÁRIO-->
		<div class="form-group">
			<label for="usuario.id" class="col-form-label">Usuário<span
				class="obrigatorio">*</span></label>
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text">@</span>
				</div>
				<select class="custom-select" name="usuario.id" required="required">
					<c:forEach var="usuario" items="${usuarios}">
						<option value="${usuario.id}">${usuario.email}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<security:csrfInput />

		<!-- OBTIGATÓRIO -->
		<label>(*) Campos obrigatórios</label>
		<div>
			<button type="reset" class="btn btn-secondary btn-lg">
				<span class="glyphicon glyphicon-trash"></span> Limpar
			</button>
			<button type="submit" class="btn btn-primary btn-lg">
				<span class="glyphicon glyphicon-floppy-disk"></span> Salvar
			</button>
		</div>
	</form>
</div>

<c:import url="../componentes/rodape.jsp" />