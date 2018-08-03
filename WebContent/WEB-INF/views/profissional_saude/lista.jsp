<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Listar Profissional da Saúde</title>
<link rel="stylesheet" type="text/css"
	href="resources/css/jquery.dataTables.css">

<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="panel panel-default">
		<div class="panel-heading">Listagem de Profissionais da Saúde</div>

		<!-- Table -->
		<div class="panel-body">
			<div class="table-responsive">
				<table class="table table-striped table-hover display" id="table_id">
					<thead>
						<tr>
							<th>ID</th>
							<th>Nome Completo</th>
							<th>SIAPE</th>
							<th>Tipo Profissional</th>
							<th>Usuário</th>
							<th>Ações</th>
						</tr>
					</thead>
					<tbody>
						<!-- percorre profissionais_saude montando as linhas da tabela -->
						<c:forEach var="profissional_saude" items="${profissionais_saude}">
							<tr>
								<td>${profissional_saude.id}</td>
								<td>${profissional_saude.nome}</td>
								<td>${profissional_saude.siape}</td>
								<td>${profissional_saude.tipo_profissional}</td>
								<td>${profissional_saude.usuario.usuario}</td>

								<!-- AÇÕES -->
								<td>
									<!-- Exibir --> <a
									href="exibeProfissionalSaude?id=${profissional_saude.id}"
									class="btn btn-success btn-xs"><span
										class="glyphicon glyphicon-zoom-in"></span> Exibir</a> <security:authorize
										access="hasRole('ROLE_Administrador')">

										<!-- Editar -->
										<a href="editaProfissionalSaude?id=${profissional_saude.id}"
											class="btn btn-info btn-xs"><span
											class="glyphicon glyphicon-edit"></span> Editar </a>
										<!-- Botão exluir -->
										<button class="btn btn-danger btn-xs" data-toggle="modal"
											data-target="#${profissional_saude.id}">
											<span class="glyphicon glyphicon-trash"></span> Excluir
										</button>
										<!-- Modal -->
										<div class="modal fade" id="${profissional_saude.id}"
											tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
											aria-hidden="true">
											<div class="modal-dialog">
												<div class="modal-content">
													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal">
															<span aria-hidden="true">&times;</span><span
																class="sr-only">Fechar</span>
														</button>
														<h4 class="modal-title" id="myModalLabel">Exclusão do
															Profissional da Saúde</h4>
													</div>
													<div class="modal-body">Deseja realmente excluir o
														Profissional da Saúde (${profissional_saude.id}) ->
														${profissional_saude.nome}?</div>
													<div class="modal-footer">
														<button type="button" class="btn btn-default"
															data-dismiss="modal">
															<span class="glyphicon glyphicon-log-out"></span> Fechar
														</button>
														<a
															href="removeProfissionalSaude?id=${profissional_saude.id}"
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
			<a href="novoProfissionalSaude" class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
		</security:authorize>
	</div>
</div>

<script type="text/javascript" src="resources/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="resources/js/data_table.js"></script>

<c:import url="../componentes/rodape.jsp" />