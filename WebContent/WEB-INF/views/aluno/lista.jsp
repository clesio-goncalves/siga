<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Listar Alunos</title>
<c:import url="../componentes/css_data_table.jsp" />
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">

	<div class="card border-light mb-3">
		<div class="card-header">Listagem de Alunos</div>

		<!-- Table -->
		<div class="card-body">
			<table id="tabela_id"
				class="table table-striped table-bordered dt-responsive nowrap"
				style="width: 100%">
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
								<td>${aluno.usuario.email}</td>
							</c:if>

							<!-- AÇÕES -->
							<th>
								<!-- Exibir --> <a href="exibeAluno?id=${aluno.id}"
								class="btn btn-secondary btn-sm"><span
									class="glyphicon glyphicon-zoom-in"></span> Exibir</a> <security:authorize
									access="hasRole('ROLE_Administrador')">
									<!-- Editar -->
									<a href="editaAluno?id=${aluno.id}" class="btn btn-info btn-sm"><span
										class="glyphicon glyphicon-edit"></span> Editar </a>
									<button type="button" class="btn btn-danger btn-sm"
										data-toggle="modal" data-target="#modal${aluno.id}">
										<span class="glyphicon glyphicon-trash"></span> Excluir
									</button>
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
													<p>Deseja realmente excluir o aluno (${aluno.id}) ->
														${aluno.nome}?</p>
												</div>
												<div class="modal-footer">
													<a href="removeAluno?id=${aluno.id}" class="btn btn-danger"><span
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
							</th>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<div align="center">
		<security:authorize access="hasRole('ROLE_Administrador')">
			<a href="novoAluno" class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
		</security:authorize>
	</div>
</div>
<c:import url="../componentes/js_data_table.jsp" />
<c:import url="../componentes/rodape.jsp" />