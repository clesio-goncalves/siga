<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<title>Atendimentos Extra Classes</title>
<c:import url="../componentes/css_data_table.jsp" />
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">

	<div class="card border-light mb-3">
		<div class="card-header">Listagem de Atendimentos do Extra
			Classe</div>

		<!-- Table -->
		<div class="card-body">
			<table id="tabela_id"
				class="table table-striped table-bordered dt-responsive nowrap"
				style="width: 100%">
				<thead>
					<tr>
						<th>ID</th>
						<th>Aluno</th>
						<th>Disciplina</th>
						<th>Docente</th>
						<th>Ações</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="extra_classe" items="${extra_classes}">
						<tr>
							<td>${extra_classe.id}</td>
							<td>${extra_classe.aluno.nome}</td>
							<td>${extra_classe.disciplina.nome}</td>
							<td>${extra_classe.docente.nome}</td>

							<td>
								<!-- Exibir --> <a
								href="<c:url value="/atendimento/extra-classe/exibe?id=${extra_classe.id}"/>"
								class="btn btn-info btn-sm" data-tooltip="tooltip"
								data-placement="bottom" title="Exibir"> <span
									class="glyphicon glyphicon-search"></span></a> <!-- Editar --> <a
								href="<c:url value="/atendimento/extra-classe/edita?id=${extra_classe.id}" />"
								class="btn btn-warning btn-sm" data-tooltip="tooltip"
								data-placement="bottom" title="Editar"><span
									class="glyphicon glyphicon-pencil"></span> </a> <!-- Excluir -->
								<button type="button" class="btn btn-danger btn-sm"
									data-tooltip="tooltip" data-placement="bottom" title="Excluir"
									data-toggle="modal" data-target="#modal${extra_classe.id}">
									<span class="glyphicon glyphicon-trash"></span>
								</button>
								<div class="modal fade" id="modal${extra_classe.id}">
									<div class="modal-dialog" role="document">
										<div class="modal-content">
											<div class="modal-header">
												<h5 class="modal-title">Exclusão do Atendimento Extra
													Classe</h5>
												<button type="button" class="close" data-dismiss="modal"
													aria-label="Close">
													<span aria-hidden="true">&times;</span>
												</button>
											</div>
											<div class="modal-body">
												<p>
													Deseja realmente excluir o Atendimento Extra Classe <br>ID
													(${extra_classe.id}) -> ${extra_classe.aluno.nome}?
												</p>
											</div>
											<div class="modal-footer">
												<a
													href="<c:url value="/atendimento/extra-classe/remove?id=${extra_classe.id}" />"
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
		<security:authorize access="hasRole('ROLE_Administrador')">
			<a href="<c:url value="/atendimento/extra-classe/novo" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
		</security:authorize>
	</div>
</div>
<c:import url="../componentes/js_data_table.jsp" />
<c:import url="../componentes/rodape.jsp" />