<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Exibe os dados da disciplina</title>
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="card border-light mb-3">
		<div class="card-header">Exibe os dados da disciplina</div>
		<!-- Table -->
		<div class="card-body">
			<div class="table-responsive">
				<table
					class="table table-striped table-bordered dt-responsive nowrap">
					<tr>
						<th width="300">ID</th>
						<td>${disciplina.id}</td>
					</tr>
					<tr>
						<th>Nome</th>
						<td>${disciplina.nome}</td>
					</tr>
				</table>
			</div>
			<h3>Cursos vinculados</h3>
			<ul class="list-group">
				<c:forEach var="curso" items="${disciplina.curso}">
					<li
						class="list-group-item d-flex justify-content-between align-items-center">
						<a href="<c:url value="/curso/exibe?id=${curso.id}" />"
						class="alert-link">${curso.nome}</a>
					</li>
				</c:forEach>
			</ul>

		</div>
	</div>
	<div align="center">
		<!-- Cadastrar -->
		<a href="<c:url value="/disciplina/nova" />"
			class="btn btn-primary btn-lg"><span
			class="glyphicon glyphicon-plus"></span> Cadastrar</a>
		<!-- Editar -->
		<a href="<c:url value="/disciplina/edita?id=${disciplina.id}" />"
			class="btn btn-info btn-lg"><span
			class="glyphicon glyphicon-edit"></span> Editar </a>
		<!-- Excluir -->
		<button type="button" class="btn btn-danger btn-lg"
			data-toggle="modal" data-target="#modal${disciplina.id}">
			<span class="glyphicon glyphicon-trash"></span> Excluir
		</button>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="modal${disciplina.id}">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Exclusão da disciplina</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<p>Deseja realmente excluir a disciplina ID (${curso.id}) ->
						${disciplina.nome}?</p>
				</div>
				<div class="modal-footer">
					<a href="<c:url value="/disciplina/remove?id=${disciplina.id}" />"
						class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span>
						Excluir</a>
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">
						<span class="glyphicon glyphicon-log-out"></span> Fechar
					</button>
				</div>
			</div>
		</div>
	</div>
	<a class="btn btn-success" href="<c:url value="/disciplina/lista" />"><span
		class="glyphicon glyphicon-chevron-left"></span> Voltar</a>
</div>

<c:import url="../componentes/rodape.jsp" />