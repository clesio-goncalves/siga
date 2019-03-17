<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<title>Listar Situações dos Alunos</title>
<c:import url="../componentes/css_data_table.jsp" />
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="card border-light mb-3">
		<div class="card-header">Listagem de Situações</div>
		<!-- Table -->
		<div class="card-body">
			<table id="tabela_id"
				class="table table-striped table-bordered dt-responsive nowrap"
				style="width: 100%">
				<thead>
					<tr>
						<th>ID</th>
						<th>Nome</th>
						<th>Qnt alunos Ativos</th>
						<th>Ações</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="situacao" items="${situacoes}">
						<tr>
							<td>${situacao.id}</td>
							<td>${situacao.nome}</td>
							<td>${situacao.qnt_alunos}</td>
							<td>
								<!-- Exibir --> <a
								href="<c:url value="/situacao/exibe?id=${situacao.id}"/>"
								class="btn btn-info btn-sm" data-tooltip="tooltip"
								data-placement="bottom" title="Exibir"> <span
									class="glyphicon glyphicon-search"></span></a> <!-- Editar --> <a
								href="<c:url value="/situacao/edita?id=${situacao.id}" />"
								class="btn btn-warning btn-sm" data-tooltip="tooltip"
								data-placement="bottom" title="Editar"><span
									class="glyphicon glyphicon-pencil"></span> </a> <!-- Excluir -->
								<button type="button" class="btn btn-danger btn-sm"
									data-tooltip="tooltip" data-placement="bottom" title="Excluir"
									data-toggle="modal" data-target="#modal${situacao.id}">
									<span class="glyphicon glyphicon-trash"></span>
								</button>
								<div class="modal fade" id="modal${situacao.id}">
									<div class="modal-dialog" role="document">
										<div class="modal-content">
											<div class="modal-header">
												<h5 class="modal-title">Exclusão da situação</h5>
												<button type="button" class="close" data-dismiss="modal"
													aria-label="Close">
													<span aria-hidden="true">&times;</span>
												</button>
											</div>
											<div class="modal-body">
												<p>
													Deseja realmente excluir a Situação <br>ID
													(${situacao.id}) -> ${situacao.nome}?
												</p>

												<p>
													<b>Não será permitido excluir a situação caso haja
														alunos vinculados.</b>
												</p>
											</div>
											<div class="modal-footer">
												<a href="<c:url value="/situacao/remove?id=${situacao.id}" />"
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
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div align="center">
		<a href="<c:url value="/situacao/nova" />"
			class="btn btn-primary btn-lg"><span
			class="glyphicon glyphicon-plus"></span> Cadastrar</a>
		<!-- PDF -->
		<a href="relatorio" class="btn btn-danger btn-lg"><span
			class="glyphicon glyphicon-file"></span> Relatório PDF</a>
	</div>
</div>

<c:import url="../componentes/js_data_table.jsp" />
<c:import url="../componentes/rodape.jsp" />