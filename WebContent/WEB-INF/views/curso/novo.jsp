<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Cadastrar Curso</title>

<c:import url="../componentes/cabecalho.jsp" />

<div class="jumbotron">
	<div class="container">
		<h1>Cadastrar Curso</h1>
		<p>Preencha o formulário abaixo para realizar o cadastro do curso.</p>
	</div>
</div>
<div class="container">
	<form action="adicionaCurso" method="POST">

		<!-- NOME -->
		<div class="form-group">
			<label for="nome">Nome*</label> <input type="text"
				class="form-control" name="nome" autofocus MAXLENGTH="255" required>
		</div>
		
		<security:csrfInput/>

		<!-- OBTIGATÓRIO -->
		<label for="obrigatorio">(*) Campo obrigatório</label>
		<div>
			<button type="reset" class="btn btn-default btn-lg">
				<span class="glyphicon glyphicon-trash"></span> Limpar
			</button>
			<button type="submit" class="btn btn-primary btn-lg">
				<span class="glyphicon glyphicon-saved"></span> Salvar
			</button>
		</div>
	</form>
</div>

<c:import url="../componentes/rodape.jsp" />