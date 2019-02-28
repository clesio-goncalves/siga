<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<title>Listar Servidores da Administração</title>
<c:import url="../componentes/css_data_table.jsp" />
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">

	<div class="card border-light mb-3">
		<div class="card-header">Listagem de Servidores da Administração</div>

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
						<th>Função</th>
						<th>Descrição da Função</th>
						<th>Usuário</th>
						<th>Ações</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="administracao" items="${lista_administracao}">
						<tr>
							<td>${administracao.id}</td>
							<td>${administracao.nome}</td>
							<td>${administracao.siape}</td>
							<td>${administracao.funcao}</td>
							<td>${administracao.descricao_funcao}</td>
							<td>${administracao.usuario.email}</td>

							<!-- AÇÕES -->
							<td>
								<!-- Exibir --> <a
								href="<c:url value="/administracao/exibe?id=${administracao.id}"/>"
								class="btn btn-info btn-sm" data-tooltip="tooltip"
								data-placement="bottom" title="Exibir"> <span
									class="glyphicon glyphicon-search"></span></a> <!-- Editar --> <security:authorize
									access="hasAnyRole('ROLE_Administrador', 'ROLE_Diretor')">
									<security:authentication property="principal"
										var="usuario_logado" />
									<c:if
										test="${usuario_logado.perfil.id == 1 or (usuario_logado.perfil.id == 3 and administracao.funcao eq 'Coordenador')}">
										<a
											href="<c:url value="/administracao/edita?id=${administracao.id}" />"
											class="btn btn-warning btn-sm" data-tooltip="tooltip"
											data-placement="bottom" title="Editar"><span
											class="glyphicon glyphicon-pencil"></span> </a>
										<!-- Excluir -->
										<button type="button" class="btn btn-danger btn-sm"
											data-tooltip="tooltip" data-placement="bottom"
											title="Excluir" data-toggle="modal"
											data-target="#modal${administracao.id}">
											<span class="glyphicon glyphicon-trash"></span>
										</button>
										<div class="modal fade" id="modal${administracao.id}">
											<div class="modal-dialog" role="document">
												<div class="modal-content">
													<div class="modal-header">
														<h5 class="modal-title">Exclusão do administracao</h5>
														<button type="button" class="close" data-dismiss="modal"
															aria-label="Close">
															<span aria-hidden="true">&times;</span>
														</button>
													</div>
													<div class="modal-body">
														<p>
															Deseja realmente excluir o Servidor <br>ID
															(${administracao.id}) -> ${administracao.nome}?
														</p>
													</div>
													<div class="modal-footer">
														<a
															href="<c:url value="/administracao/remove?id=${administracao.id}" />"
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
		access="hasAnyRole('ROLE_Administrador', 'ROLE_Diretor')">
		<div align="center">
			<a href="<c:url value="/administracao/novo" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
		</div>
	</security:authorize>

</div>
<c:import url="../componentes/js_data_table.jsp" />
<c:import url="../componentes/rodape.jsp" />