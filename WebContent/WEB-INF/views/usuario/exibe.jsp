<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Exibe os dados do usuário</title>
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="card border-light mb-3">
		<div class="card-header">Exibe os dados do usuário</div>
		<!-- Table -->
		<div class="card-body">
			<div class="table-responsive">
				<table
					class="table table-striped table-bordered dt-responsive nowrap">
					<tr>
						<th width="300">ID</th>
						<td>${usuario.id}</td>
					</tr>

					<tr>
						<th>E-mail</th>
						<td>${usuario.email}</td>
					</tr>

					<tr>
						<th>Ativo</th>
						<c:if test="${usuario.ativo eq true}">
							<td>Sim</td>
						</c:if>
						<c:if test="${usuario.ativo eq false}">
							<td>Não</td>
						</c:if>
					</tr>
					<tr>
						<th>Perfil</th>
						<td>${usuario.perfil.nome}</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<security:authorize access="hasRole('ROLE_Administrador')">
		<div align="center">
			<!-- Cadastrar -->
			<a href="<c:url value="/usuario/novo" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
			<!-- Editar -->
			<a href="<c:url value="/usuario/edita?id=${usuario.id}" />"
				class="btn btn-info btn-lg"><span
				class="glyphicon glyphicon-edit"></span> Editar </a>
			<!-- Excluir -->
			<button type="button" class="btn btn-danger btn-lg"
				data-toggle="modal" data-target="#modal${usuario.id}">
				<span class="glyphicon glyphicon-trash"></span> Excluir
			</button>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="modal${usuario.id}">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Exclusão do usuário</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<p>Deseja realmente excluir o usuário <br>ID (${usuario.id}) ->
							${usuario.email}?</p>
					</div>
					<div class="modal-footer">
						<a href="<c:url value="/usuario/remove?id=${usuario.id}" />"
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
	<a class="btn btn-success" href="<c:url value="/usuario/lista" />"><span
		class="glyphicon glyphicon-chevron-left"></span> Voltar</a>
</div>

<c:import url="../componentes/rodape.jsp" />