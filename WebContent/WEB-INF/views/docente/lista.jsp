<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<title>Listar Docentes</title>
<c:import url="../componentes/css_data_table.jsp" />
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">

	<div class="card border-light mb-3">
		<div class="card-header">Listagem de Docentes</div>

		<!-- Table -->
		<div class="card-body">
			<table id="tabela_id"
				class="table table-striped table-bordered dt-responsive nowrap"
				style="width: 100%">
				<thead>
					<tr>
						<th>ID</th>
						<th>Nome Completo</th>
						<th>SIAPE</th>
						<th>Usu?rio</th>
						<th>A??es</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="docente" items="${docentes}">
						<tr>
							<td>${docente.id}</td>
							<td>${docente.nome}</td>
							<td>${docente.siape}</td>
							<td>${docente.usuario.email}</td>

							<!-- A??ES -->
							<td>
								<!-- Exibir --> <a
								href="<c:url value="/docente/exibe?id=${docente.id}"/>"
								class="btn btn-info btn-sm" data-tooltip="tooltip"
								data-placement="bottom" title="Exibir"> <span
									class="glyphicon glyphicon-search"></span></a> <security:authorize
									access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Pedagogia', 'ROLE_Coordena??o de Disciplina')">
									<!-- Editar -->
									<a href="<c:url value="/docente/edita?id=${docente.id}" />"
										class="btn btn-warning btn-sm" data-tooltip="tooltip"
										data-placement="bottom" title="Editar"><span
										class="glyphicon glyphicon-pencil"></span> </a>
									<!-- Excluir -->
									<button type="button" class="btn btn-danger btn-sm"
										data-tooltip="tooltip" data-placement="bottom" title="Excluir"
										data-toggle="modal" data-target="#modal${docente.id}">
										<span class="glyphicon glyphicon-trash"></span>
									</button>
									<div class="modal fade" id="modal${docente.id}">
										<div class="modal-dialog" role="document">
											<div class="modal-content">
												<div class="modal-header">
													<h5 class="modal-title">Exclus?o do docente</h5>
													<button type="button" class="close" data-dismiss="modal"
														aria-label="Close">
														<span aria-hidden="true">&times;</span>
													</button>
												</div>
												<div class="modal-body">
													<p>
														Deseja realmente excluir o Docente <br>ID
														(${docente.id}) -> ${docente.nome}?
													</p>
													<p>
														<b>Aten??o: N?o ? recomendado excluir o usu?rio, <br>
															Ent?o, desative-o.<b>
													</p>
												</div>
												<div class="modal-footer">
													<a
														href="<c:url value="/docente/remove?id=${docente.id}" />"
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
		<security:authorize
			access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Pedagogia', 'ROLE_Coordena??o de Disciplina')">

			<a href="<c:url value="/docente/novo" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>

		</security:authorize>
		<!-- PDF -->
		<a href="relatorio" class="btn btn-danger btn-lg"><span
			class="glyphicon glyphicon-file"></span> Relat?rio PDF</a>
	</div>
</div>
<c:import url="../componentes/js_data_table.jsp" />
<c:import url="../componentes/rodape.jsp" />