<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<title>Cadastrar Docente</title>

<c:import url="../componentes/cabecalho.jsp" />
<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">Cadastrar Docente</h1>
		<p class="lead">Preencha o formulário abaixo para realizar o
			cadastro do Docente no sistema.</p>
	</div>
</div>
<div class="container">
	<form action="adiciona" method="POST">

		<!-- NOME -->
		<div class="form-group">
			<label for="nome" class="col-form-label">Nome Completo<span class="obrigatorio">*</span></label> <input
				type="text" class="form-control" name="nome" autofocus
				MAXLENGTH="255" required>
		</div>

		<!-- SIAPE -->
		<div class="form-group">
			<label for="siape" class="col-form-label">SIAPE<span class="obrigatorio">*</span></label> <input type="text"
				class="form-control" name="siape" MAXLENGTH="11" required data-mask="99999999999">
		</div>

		<!-- USUÁRIO-->
		<div class="form-group" id="lista_usuarios">
			<label for="usuario.id" class="col-form-label">Usuário<span class="obrigatorio">*</span></label> <select
				class="custom-select" name="usuario.id" required>
				<c:forEach var="usuario" items="${usuarios}">
					<option value="${usuario.id}">${usuario.email}</option>
				</c:forEach>
			</select>
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