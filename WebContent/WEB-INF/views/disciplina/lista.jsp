<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Listar Disciplinas</title>
<c:import url="../componentes/css_data_table.jsp" />
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">

	<div class="card border-light mb-3">
		<div class="card-header">Listagem de Disciplinas</div>

		<!-- Table -->
		<div class="card-body">
			<table id="tabela_id"
				class="table table-striped table-bordered dt-responsive nowrap"
				style="width: 100%">
				<thead>
					<tr>
						<th>ID</th>
						<th>Nome</th>
						<th>Ações</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="disciplina" items="${disciplinas}">
						<tr>
							<td>${disciplina.id}</td>
							<td>${disciplina.nome}</td>
							<td>
								<!-- Exibir --> <a
								href="<c:url value="/disciplina/exibe?id=${disciplina.id}" />"
								class="btn btn-secondary btn-sm"><span
									class="glyphicon glyphicon-zoom-in"></span> Exibir</a> <security:authorize
									access="hasRole('ROLE_Administrador')">
									<!-- Editar -->
									<a
										href="<c:url value="/disciplina/edita?id=${disciplina.id}" />"
										class="btn btn-info btn-sm"><span
										class="glyphicon glyphicon-edit"></span> Editar </a>
									<button type="button" class="btn btn-danger btn-sm"
										data-toggle="modal" data-target="#modal${disciplina.id}">
										<span class="glyphicon glyphicon-trash"></span> Excluir
									</button>
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
													<p>Deseja realmente excluir a disciplina ID
														(${disciplina.id}) -> ${disciplina.nome}?</p>
												</div>
												<div class="modal-footer">
													<a
														href="<c:url value="/disciplina/remove?id=${disciplina.id}" />"
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
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<div align="center">
		<security:authorize access="hasRole('ROLE_Administrador')">
			<a href="<c:url value="/disciplina/nova" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
		</security:authorize>
	</div>
</div>

<c:import url="../componentes/js_data_table.jsp" />
<c:import url="../componentes/rodape.jsp" />