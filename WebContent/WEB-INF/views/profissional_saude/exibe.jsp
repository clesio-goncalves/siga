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
	<div class="panel panel-default">
		<div class="panel-heading">Exibe os dados do aluno</div>
		<!-- Table -->
		<div class="panel-body">
			<div class="table-responsive">
				<table class="table table-striped table-hover">
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
						<th>Curso</th>
						<td>${aluno.curso.nome}</td>
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
			<a href="novoAluno" class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
			<!-- Editar -->
			<a href="editaAluno?id=${aluno.id}" class="btn btn-info btn-lg"><span
				class="glyphicon glyphicon-edit"></span> Editar </a>
			<!-- Excluir -->
			<button class="btn btn-danger btn-lg" data-toggle="modal"
				data-target="#${aluno.id}">
				<span class="glyphicon glyphicon-trash"></span> Excluir
			</button>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="${aluno.id}" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Fechar</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">Exclusão do usuário</h4>
					</div>
					<div class="modal-body">Deseja realmente excluir o usuário
						(${aluno.id}) -> ${aluno.nome}?</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							<span class="glyphicon glyphicon-log-out"></span> Fechar
						</button>
						<a href="removeAluno?id=${aluno.id}" class="btn btn-danger"><span
							class="glyphicon glyphicon-trash"></span> Excluir</a>
					</div>
				</div>
			</div>
		</div>
	</security:authorize>
	<ul class="pager">
		<li class="previous"><a href="listaAlunos"><span
				class="glyphicon glyphicon-chevron-left"></span> Voltar</a></li>
	</ul>
</div>

<c:import url="../componentes/rodape.jsp" />