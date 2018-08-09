<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Cadastrar Disciplina</title>

<c:import url="../componentes/cabecalho.jsp" />
<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">Cadastrar Disciplina</h1>
		<p class="lead">Preencha o formulário abaixo para realizar o
			cadastro do disciplina no sistema.</p>
	</div>
</div>
<div class="container">
	<form:form action="adiciona" method="POST" modelAttribute="disciplina">

		<!-- NOME -->
		<div class="form-group">
			<label for="nome" class="col-form-label">Nome*</label> <input
				type="text" class="form-control" name="nome" autofocus
				MAXLENGTH="255" required>
		</div>

		<!-- TURMA -->
		<div class="form-group">
			<label for="lista_turmas" class="col-form-label">Turmas*</label>
			<c:forEach var="turma" items="${disciplina.turma}">
				<div class="custom-control custom-checkbox">
					<form:checkbox path="lista_turmas" value="${turma.id}"
						class="custom-control-input" id="turma${turma.id}" />
					<label class="custom-control-label" for="turma${turma.id}">${turma.nome}</label>
				</div>
			</c:forEach>
		</div>

		<security:csrfInput />

		<!-- OBTIGATÓRIO -->
		<label for="obrigatorio">(*) Campo obrigatório</label>
		<div>
			<button type="reset" class="btn btn-secondary btn-lg">
				<span class="glyphicon glyphicon-trash"></span> Limpar
			</button>
			<button type="submit" class="btn btn-primary btn-lg">
				<span class="glyphicon glyphicon-saved"></span> Salvar
			</button>
		</div>
	</form:form>
</div>

<c:import url="../componentes/rodape.jsp" />