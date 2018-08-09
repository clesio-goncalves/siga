<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Exibe os dados do aluno</title>
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="card border-light mb-3">
		<div class="card-header">Exibe os dados do aluno</div>
		<!-- Table -->
		<div class="card-body">
			<div class="table-responsive">
				<table
					class="table table-striped table-bordered dt-responsive nowrap">
					<tr>
						<th width="300">ID</th>
						<td>${aluno.id}</td>
					</tr>

					<tr>
						<th>Nome Completo</th>
						<td>${aluno.nome}</td>
					</tr>

					<tr>
						<th>Matrícula</th>

						<!-- Matricula -->
						<c:if test="${aluno.matricula eq \"\"}">
							<td>Não informada</td>
						</c:if>
						<c:if test="${aluno.matricula ne \"\"}">
							<td>${aluno.matricula}</td>
						</c:if>
					</tr>

					<tr>
						<th>Turma</th>
						<td>${aluno.turma.nome}</td>
					</tr>

					<tr>
						<th>Usuário</th>

						<!-- Usuário -->
						<c:if test="${aluno.usuario == null}">
							<td>Não informado</td>
						</c:if>
						<c:if test="${aluno.usuario != null}">
							<td>${aluno.usuario}</td>
						</c:if>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<security:authorize access="hasRole('ROLE_Administrador')">
		<div align="center">
			<!-- Cadastrar -->
			<a href="<c:url value="/aluno/novo" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
			<!-- Editar -->
			<a href="<c:url value="/aluno/edita?id=${aluno.id}" />"
				class="btn btn-info btn-lg"><span
				class="glyphicon glyphicon-edit"></span> Editar </a>
			<!-- Excluir -->
			<button type="button" class="btn btn-danger btn-lg"
				data-toggle="modal" data-target="#modal${aluno.id}">
				<span class="glyphicon glyphicon-trash"></span> Excluir
			</button>
		</div>
		<div class="modal fade" id="modal${aluno.id}">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Exclusão do aluno</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<p>Deseja realmente excluir o aluno <br>ID (${aluno.id}) ->
							${aluno.nome}?</p>
					</div>
					<div class="modal-footer">
						<a href="<c:url value="/aluno/remove?id=${aluno.id}" />"
							class="btn btn-danger"><span
							class="glyphicon glyphicon-trash"></span> Excluir</a>
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">
							<span class="glyphicon glyphicon-log-out"></span> Fechar
						</button>
					</div>
				</div>
			</div>
		</div>
	</security:authorize>

	<a class="btn btn-success" href="<c:url value="/aluno/lista" />"><span
		class="glyphicon glyphicon-chevron-left"></span> Voltar</a>

</div>

<c:import url="../componentes/rodape.jsp" />