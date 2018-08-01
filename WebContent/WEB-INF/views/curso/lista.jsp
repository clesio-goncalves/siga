<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Listar Cursos</title>
<link rel="stylesheet" type="text/css"
	href="resources/css/jquery.dataTables.css">

<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="panel panel-default">
		<div class="panel-heading">Listagem de Cursos</div>

		<!-- Table -->
		<div class="panel-body">
			<div class="table-responsive">
				<table class="table table-striped table-hover display" id="table_id">
					<thead>
						<tr>
							<th>ID</th>
							<th>Nome</th>
							<th>Ações</th>
						</tr>
					</thead>
					<tbody>
						<!-- percorre cursos montando as linhas da tabela -->
						<c:forEach var="curso" items="${cursos}">
							<tr>
								<td>${curso.id}</td>
								<td>${curso.nome}</td>

								<!-- AÇÕES -->
								<td>
									<!-- Exibir --> <a href="exibeCurso?id=${curso.id}"
									class="btn btn-success btn-xs"><span
										class="glyphicon glyphicon-zoom-in"></span> Exibir</a> <!-- Editar -->
									<a href="editaCurso?id=${curso.id}" class="btn btn-info btn-xs"><span
										class="glyphicon glyphicon-edit"></span> Editar </a> <!-- Botão exluir -->
									<button class="btn btn-danger btn-xs" data-toggle="modal"
										data-target="#${curso.id}">
										<span class="glyphicon glyphicon-trash"></span> Excluir
									</button> <!-- Modal -->
									<div class="modal fade" id="${curso.id}" tabindex="-1"
										role="dialog" aria-labelledby="myModalLabel"
										aria-hidden="true">
										<div class="modal-dialog">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal">
														<span aria-hidden="true">&times;</span><span
															class="sr-only">Fechar</span>
													</button>
													<h4 class="modal-title" id="myModalLabel">Exclusão do
														curso</h4>
												</div>
												<div class="modal-body">
													<strong>A exclusão não é permitida caso haja
														aluno vinculado a este curso</strong> <br> Deseja realmente
													excluir o curso (${curso.id}) -> ${curso.nome}?
												</div>
												<div class="modal-footer">
													<button type="button" class="btn btn-default"
														data-dismiss="modal">
														<span class="glyphicon glyphicon-log-out"></span> Fechar
													</button>
													<a href="removeCurso?id=${curso.id}" class="btn btn-danger"><span
														class="glyphicon glyphicon-trash"></span> Excluir</a>
												</div>
											</div>
										</div>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>

				</table>
			</div>
		</div>
	</div>
	<div align="center">
		<a href="novoCurso" class="btn btn-primary btn-lg"><span
			class="glyphicon glyphicon-plus"></span> Cadastrar</a>
	</div>
</div>

<script type="text/javascript" src="resources/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="resources/js/data_table.js"></script>

<c:import url="../componentes/rodape.jsp" />