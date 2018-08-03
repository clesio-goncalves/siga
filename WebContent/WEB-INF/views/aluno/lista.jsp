<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Listar Alunos</title>
<link rel="stylesheet" type="text/css"
	href="resources/css/jquery.dataTables.css">

<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="panel panel-default">
		<div class="panel-heading">Listagem de Alunos</div>

		<!-- Table -->
		<div class="panel-body">
			<div class="table-responsive">
				<table class="table table-striped table-hover display" id="table_id">
					<thead>
						<tr>
							<th>ID</th>
							<th>Nome Completo</th>
							<th>Matrícula</th>
							<th>Curso</th>
							<th>Usuário</th>
							<th>Ações</th>
						</tr>
					</thead>
					<tbody>
						<!-- percorre alunos montando as linhas da tabela -->
						<c:forEach var="aluno" items="${alunos}">
							<tr>
								<td>${aluno.id}</td>
								<td>${aluno.nome}</td>
								
								<!-- Matricula -->
								<c:if test="${aluno.matricula eq \"\"}">
									<td>Não informada</td>
								</c:if>
								<c:if test="${aluno.matricula ne \"\"}">
									<td>${aluno.matricula}</td>
								</c:if>

								<td>${aluno.curso.nome}</td>
								
								<!-- Usuário -->
								<c:if test="${aluno.usuario == null}">
									<td>Não informado</td>
								</c:if>
								<c:if test="${aluno.usuario != null}">
									<td>${aluno.usuario.usuario}</td>
								</c:if>

								<!-- AÇÕES -->
								<td>
									<!-- Exibir --> <a href="exibeAluno?id=${aluno.id}"
									class="btn btn-success btn-xs"><span
										class="glyphicon glyphicon-zoom-in"></span> Exibir</a> 
										
									<security:authorize access="hasRole('ROLE_Administrador')">
										
										<!-- Editar -->
										<a href="editaAluno?id=${aluno.id}"
											class="btn btn-info btn-xs"><span
											class="glyphicon glyphicon-edit"></span> Editar </a>
										<!-- Botão exluir -->
										<button class="btn btn-danger btn-xs" data-toggle="modal"
											data-target="#${aluno.id}">
											<span class="glyphicon glyphicon-trash"></span> Excluir
										</button>
										<!-- Modal -->
										<div class="modal fade" id="${aluno.id}" tabindex="-1"
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
															aluno</h4>
													</div>
													<div class="modal-body">Deseja realmente excluir o
														aluno (${aluno.id}) -> ${aluno.nome}?</div>
													<div class="modal-footer">
														<button type="button" class="btn btn-default"
															data-dismiss="modal">
															<span class="glyphicon glyphicon-log-out"></span> Fechar
														</button>
														<a href="removeAluno?id=${aluno.id}"
															class="btn btn-danger"><span
															class="glyphicon glyphicon-trash"></span> Excluir</a>
													</div>
												</div>
											</div>
										</div>
									</security:authorize>
								</td>
							</tr>
						</c:forEach>
					</tbody>

				</table>
			</div>
		</div>
	</div>

	<div align="center">
		<security:authorize access="hasRole('ROLE_Administrador')">
			<a href="novoAluno" class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
		</security:authorize>
	</div>
</div>

<script type="text/javascript" src="resources/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="resources/js/data_table.js"></script>

<c:import url="../componentes/rodape.jsp" />