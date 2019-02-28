<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<title>Listar Usuários</title>
<c:import url="../componentes/css_data_table.jsp" />
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
						<th>E-mail</th>
						<th>Ativo</th>
						<th>Perfil</th>
						<th>Ações</th>
					</tr>
				</thead>
				<tbody>
					<!-- percorre usuarios montando as linhas da tabela -->
					<c:forEach var="usuario" items="${usuarios}">
						<tr>
							<td>${usuario.id}</td>
							<td>${usuario.email}</td>

							<!-- Ativo -->
							<c:if test="${usuario.ativo eq true}">
								<td>Sim</td>
							</c:if>
							<c:if test="${usuario.ativo eq false}">
								<td>Não</td>
							</c:if>

							<td>${usuario.perfil.nome}</td>
							<td>
								<!-- Exibir --> <a
								href="<c:url value="/usuario/exibe?id=${usuario.id}"/>"
								class="btn btn-info btn-sm" data-tooltip="tooltip"
								data-placement="bottom" title="Exibir"> <span
									class="glyphicon glyphicon-search"></span></a> <security:authorize
									access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Psicologia', 'ROLE_Assistência Social', 'ROLE_Enfermagem', 'ROLE_Pedagogia', 'ROLE_Odontologia', 'ROLE_Docente', 'ROLE_Monitor', 'ROLE_Coordenação de Disciplina')">
									<c:if test="${usuario.vinculado eq false}">
										<!-- Editar -->
										<a href="<c:url value="/usuario/edita?id=${usuario.id}" />"
											class="btn btn-warning btn-sm" data-tooltip="tooltip"
											data-placement="bottom" title="Editar"><span
											class="glyphicon glyphicon-pencil"></span> </a>
										<!-- Excluir -->
										<button type="button" class="btn btn-danger btn-sm"
											data-tooltip="tooltip" data-placement="bottom"
											title="Excluir" data-toggle="modal"
											data-target="#modal${usuario.id}">
											<span class="glyphicon glyphicon-trash"></span>
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
														<p>
															Deseja realmente excluir o usuário <br>ID
															(${usuario.id}) -> ${usuario.email}?
														</p>
													</div>
													<div class="modal-footer">
														<a
															href="<c:url value="/usuario/remove?id=${usuario.id}" />"
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
									</c:if>
								</security:authorize>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<security:authorize
		access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Psicologia', 'ROLE_Assistência Social', 'ROLE_Enfermagem', 'ROLE_Pedagogia', 'ROLE_Odontologia', 'ROLE_Docente', 'ROLE_Monitor', 'ROLE_Coordenação de Disciplina')">
		<div align="center">
			<a href="<c:url value="/usuario/novo" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
		</div>
	</security:authorize>
</div>

<c:import url="../componentes/js_data_table.jsp" />
<c:import url="../componentes/rodape.jsp" />