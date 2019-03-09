<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
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
		<c:forEach var="usuario" items="${usuarios_manipulaveis}">
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
						class="glyphicon glyphicon-search"></span></a> <!-- Editar --> <a
					href="<c:url value="/usuario/edita?id=${usuario.id}" />"
					class="btn btn-warning btn-sm" data-tooltip="tooltip"
					data-placement="bottom" title="Editar"><span
						class="glyphicon glyphicon-pencil"></span> </a> <!-- Excluir -->
					<button type="button" class="btn btn-danger btn-sm"
						data-tooltip="tooltip" data-placement="bottom" title="Excluir"
						data-toggle="modal" data-target="#modal${usuario.id}">
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
										Deseja realmente excluir o usuário <br>ID (${usuario.id})
										-> ${usuario.email}?
									</p>
									<p>
										<b>Atenção: Não é recomendado excluir o usuário, <br>
											Então, desative-o.<b>
									</p>
								</div>
								<div class="modal-footer">
									<a href="<c:url value="/usuario/remove?id=${usuario.id}" />"
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