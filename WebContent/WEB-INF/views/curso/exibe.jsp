<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Exibe os dados do curso</title>
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="panel panel-default">
		<div class="panel-heading">Exibe os dados do curso</div>
		<!-- Table -->
		<div class="panel-body">
			<div class="table-responsive">
				<table class="table table-striped table-hover">
					<tr>
						<th width="300">ID</th>
						<td>${curso.id}</td>
					</tr>

					<tr>
						<th>Nome</th>
						<td>${curso.nome}</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div align="center">
		<!-- Cadastrar -->
		<a href="novoCurso" class="btn btn-primary btn-lg"><span
			class="glyphicon glyphicon-plus"></span> Cadastrar</a>
		<!-- Editar -->
		<a href="editarCurso?id=${curso.id}" class="btn btn-info btn-lg"><span
			class="glyphicon glyphicon-edit"></span> Editar </a>
		<!-- Excluir -->
		<button class="btn btn-danger btn-lg" data-toggle="modal"
			data-target="#${curso.id}">
			<span class="glyphicon glyphicon-trash"></span> Excluir
		</button>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="${curso.id}" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Fechar</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Exclusão do curso</h4>
				</div>
				<div class="modal-body">Deseja realmente excluir o curso
					(${curso.id}) -> ${curso.nome}?</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<span class="glyphicon glyphicon-log-out"></span> Fechar
					</button>
					<a href="removeCurso?id=${curso.id}" class="btn btn-danger"><span
						class="glyphicon glyphicon-trash"></span> Excluir</a>
				</div>
			</div>
		</div>
	</div>
	<ul class="pager">
		<li class="previous"><a href="listaCursos"><span
				class="glyphicon glyphicon-chevron-left"></span> Voltar</a></li>
	</ul>
</div>

<c:import url="../componentes/rodape.jsp" />