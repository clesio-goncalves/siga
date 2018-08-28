<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<title>Cadastrar Aluno</title>
<c:import url="../componentes/cabecalho.jsp" />
<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">Cadastrar Aluno</h1>
		<p class="lead">Preencha o formulário abaixo para realizar o
			cadastro do aluno no sistema.</p>
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

		<!-- MATRICULA -->
		<div class="form-group">
			<label for="matricula" class="col-form-label">Matricula</label> <input
				type="text" class="form-control" name="matricula" MAXLENGTH="50">
		</div>

		<!-- TURMA -->
		<div class="form-group">
			<label for="turma.id" class="col-form-label">Turma<span class="obrigatorio">*</span></label>
			<c:forEach var="turma" items="${turmas}">
				<div class="custom-control custom-radio">
					<input type="radio" id="${turma.id}" name="turma.id"
						value="${turma.id}" class="custom-control-input" checked required>
					<label class="custom-control-label" for="${turma.id}">${turma.nome}</label>
				</div>
			</c:forEach>
		</div>

		<!-- USUÁRIO-->
		<div class="form-group">
			<label for="usuario.id" class="col-form-label">Usuário</label> <select
				class="custom-select" name="usuario.id">
				<option value="">Não informar</option>
				<!-- percorre usuarios montando as linhas da tabela -->
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