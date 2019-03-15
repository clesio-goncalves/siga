<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<title>Cadastrar Usuário</title>
<c:import url="../componentes/cabecalho.jsp" />
<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">Cadastrar Usuário</h1>
		<p class="lead">Preencha o formulário abaixo para realizar o
			cadastro do usuário no sistema.</p>
	</div>
</div>
<div class="container">
	<form action="adiciona" method="POST">

		<!-- EMAIL -->
		<div class="form-group">
			<label for="email" class="col-form-label">E-mail<span
				class="obrigatorio">*</span></label>
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
			<label for="senha" class="col-form-label">Senha<span
				class="obrigatorio">*</span></label> <input type="password"
				class="form-control" name="senha" id="senha" MAXLENGTH="50" required>
		</div>

		<!-- REPETIR SENHA -->
		<div class="form-group">
			<label for="repetir_senha" class="col-form-label">Repetir
				Senha<span class="obrigatorio">*</span>
			</label> <input type="password" class="form-control" name="repetir_senha"
				id="repetir_senha" MAXLENGTH="50" required>
		</div>

		<!-- PERFIL -->
		<div class="form-group">
			<label for="perfil" class="col-form-label">Perfil<span
				class="obrigatorio">*</span></label> <select class="custom-select"
				name="perfil.id" required>
				<security:authorize access="hasRole('ROLE_Administrador')">
					<option value="1">Administrador</option>
					<option value="3">Diretor</option>
				</security:authorize>
				<security:authorize
					access="hasAnyRole('ROLE_Administrador', 'ROLE_Diretor')">
					<option value="2">Coordenador</option>
					<option value="4">Psicologia</option>
					<option value="5">Assistência Social</option>
					<option value="6">Enfermagem</option>
					<option value="7">Pedagogia</option>
					<option value="8">Odontologia</option>
					<option value="12">Coordenação de Disciplina</option>
				</security:authorize>
				<security:authorize
					access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Pedagogia', 'ROLE_Coordenação de Disciplina')">
					<option value="9">Docente</option>
				</security:authorize>
				<security:authorize
					access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Pedagogia', 'ROLE_Docente')">
					<option value="10">Monitor</option>
				</security:authorize>
				<security:authorize
					access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Psicologia', 'ROLE_Assistência Social', 'ROLE_Enfermagem', 'ROLE_Pedagogia', 'ROLE_Odontologia', 'ROLE_Docente', 'ROLE_Monitor', 'ROLE_Coordenação de Disciplina')">
					<option value="11">Aluno</option>
				</security:authorize>
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

		<!-- OBTIGATÓRIO -->
		<label for="obrigatorio">(*) Campos obrigatórios</label>
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