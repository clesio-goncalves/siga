<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Listar Usuários</title>
<link rel="stylesheet" type="text/css"
	href="resources/css/data_table/dataTables.bootstrap4.min.css">
<link rel="stylesheet" type="text/css"
	href="resources/css/data_table/responsive.bootstrap4.min.css">
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">

	<div class="card border-light mb-3">
		<div class="card-header">Listagem de Usuários</div>

		<!-- Table -->
		<div class="card-body">
			<table id="tabela_id"
				class="table table-striped table-bordered dt-responsive nowrap"
				style="width: 100%">
				<thead>
					<tr>
						<th>ID</th>
						<th>Usuário</th>
						<th>Ativo</th>
						<th>Perfil</th>
						<th>Ações</th>
					</tr>
				</thead>
				<tbody>
					<!-- percorre usuarios montando as linhas da tabela -->
					<c:forEach var="usuario" items="${usuarios}">
						<tr>
							<th>${usuario.id}</th>
							<th>${usuario.usuario}</th>

							<!-- Ativo -->
							<c:if test="${usuario.ativo eq true}">
								<td>Sim</td>
							</c:if>
							<c:if test="${usuario.ativo eq false}">
								<td>Não</td>
							</c:if>

							<td>${usuario.perfil.nome}</td>
							<th>
								<!-- Exibir --> <a href="exibeUsuario?id=${usuario.id}"
								class="btn btn-secondary btn-sm"><span
									class="glyphicon glyphicon-zoom-in"></span> Exibir</a> <security:authorize
									access="hasRole('ROLE_Administrador')">
									<!-- Editar -->
									<a href="editaUsuario?id=${usuario.id}"
										class="btn btn-info btn-sm"><span
										class="glyphicon glyphicon-edit"></span> Editar </a>
									<!-- Button to Open the Modal -->
									<button type="button" class="btn btn-danger btn-sm"
										data-toggle="modal" data-target="#modal${usuario.id}">
										<span class="glyphicon glyphicon-trash"></span> Excluir
									</button>
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
													<p>Deseja realmente excluir o usuário (${usuario.id})
														-> ${usuario.usuario}?</p>
												</div>
												<div class="modal-footer">
													<a href="removeUsuario?id=${usuario.id}"
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
							</th>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<div align="center">
		<security:authorize access="hasRole('ROLE_Administrador')">
			<a href="novoUsuario" class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
		</security:authorize>
	</div>
</div>

<script type="text/javascript"
	src="resources/js/data_table/jquery.dataTables.min.js"></script>
<script type="text/javascript"
	src="resources/js/data_table/dataTables.bootstrap4.min.js"></script>
<script type="text/javascript"
	src="resources/js/data_table/dataTables.responsive.min.js"></script>
<script type="text/javascript"
	src="resources/js/data_table/responsive.bootstrap4.min.js"></script>
<script type="text/javascript"
	src="resources/js/data_table/data_table.js"></script>

<c:import url="../componentes/rodape.jsp" />