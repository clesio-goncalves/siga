<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<title>Cadastrar Usu�rio</title>
<c:import url="../componentes/cabecalho.jsp" />
<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">Cadastrar Usu�rio</h1>
		<p class="lead">Preencha o formul�rio abaixo para realizar o
			cadastro do usu�rio no sistema.</p>
	</div>
</div>
<div class="container">
	<form action="adiciona" method="POST">

		<!-- EMAIL -->
		<div class="form-group">
			<label for="email" class="col-form-label">E-mail<span class="obrigatorio">*</span></label>
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text">@</span>
				</div>
				<input type="email" class="form-control" name="email" MAXLENGTH="50"
					required autofocus>
			</div>
		</div>

		<!-- SENHA -->
		<div class="form-group">
			<label for="senha" class="col-form-label">Senha<span class="obrigatorio">*</span></label> <input
				type="password" class="form-control" name="senha" id="senha"
				MAXLENGTH="50" required>
		</div>

		<!-- REPETIR SENHA -->
		<div class="form-group">
			<label for="repetir_senha" class="col-form-label">Repetir
				Senha<span class="obrigatorio">*</span></label> <input type="password" class="form-control"
				name="repetir_senha" id="repetir_senha" MAXLENGTH="50" required>
		</div>

		<!-- PERMISS�O -->
		<div class="form-group">
			<label for="perfil" class="col-form-label">Perfil<span class="obrigatorio">*</span></label> <select
				class="custom-select" name="perfil.id" required>
				<!-- percorre perfil montando as linhas da tabela -->
				<c:forEach var="perfil" items="${perfis}">
					<option value="${perfil.id}">${perfil.nome}</option>
				</c:forEach>
			</select>
		</div>

		<!-- ATIVO -->
		<div class="form-group">
			<div class="custom-control custom-checkbox">
				<input type="checkbox" class="custom-control-input"
					id="customCheck1" name="ativo" checked="checked"> <label
					class="custom-control-label" for="customCheck1">Ativo</label>
			</div>
		</div>

		<security:csrfInput />

		<!-- OBTIGAT�RIO -->
		<label for="obrigatorio">(*) Campos obrigat�rios</label>
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

<script src="<c:url value="/resources/js/confirma_senha.js" />"></script>

<c:import url="../componentes/rodape.jsp" />