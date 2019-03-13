<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<table id="tabela_id"
	class="table table-striped table-bordered dt-responsive nowrap"
	style="width: 100%">
	<thead>
		<tr>
			<th>Data</th>
			<th>Horário</th>
			<th>Aluno</th>
			<th>Disciplina</th>
			<th>Monitor</th>
			<th>Ações</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="atendimento_monitoria"
			items="${atendimento_monitorias}">
			<tr>
				<td><fmt:formatDate value="${atendimento_monitoria.data}" /></td>
				<td><fmt:formatDate type="time"
						value="${atendimento_monitoria.horario_inicial}" pattern="HH:mm" />
					- <fmt:formatDate type="time"
						value="${atendimento_monitoria.horario_final}" pattern="HH:mm" /></td>
				<c:if test="${atendimento_monitoria.status_atendimento}">
					<td>-</td>
					<td>-</td>
				</c:if>
				<c:if test="${atendimento_monitoria.status_atendimento ==  false}">
					<td>${atendimento_monitoria.aluno.nome}</td>
					<td>${atendimento_monitoria.disciplina.nome}</td>
				</c:if>
				<td>${atendimento_monitoria.monitor.nome}</td>
				<td>
					<!-- Exibir --> <a
					href="<c:url value="/atendimento/monitoria/exibe?id=${atendimento_monitoria.id}"/>"
					class="btn btn-info btn-sm" data-tooltip="tooltip"
					data-placement="bottom" title="Exibir"> <span
						class="glyphicon glyphicon-search"></span></a> <!-- Editar --> <security:authorize
						access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Pedagogia', 'ROLE_Monitor')">
						<a
							href="<c:url value="/atendimento/monitoria/edita?id=${atendimento_monitoria.id}" />"
							class="btn btn-warning btn-sm" data-tooltip="tooltip"
							data-placement="bottom" title="Editar"><span
							class="glyphicon glyphicon-pencil"></span> </a>
						<!-- Excluir -->
						<button type="button" class="btn btn-danger btn-sm"
							data-tooltip="tooltip" data-placement="bottom" title="Excluir"
							data-toggle="modal"
							data-target="#modal${atendimento_monitoria.id}">
							<span class="glyphicon glyphicon-trash"></span>
						</button>
						<div class="modal fade" id="modal${atendimento_monitoria.id}">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title">Exclusão do Atendimento de
											Monitoria</h5>
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
									<div class="modal-body">
										<p>
											Deseja realmente excluir o Atendimento de Monitoria <br>ID
											(${atendimento_monitoria.id}) - Aluno:
											${atendimento_monitoria.aluno.nome}?
										</p>
									</div>
									<div class="modal-footer">
										<a
											href="<c:url value="/atendimento/monitoria/remove?id=${atendimento_monitoria.id}" />"
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
	<tr style="background-color: #fff; font-weight: bold;">
		<td colspan="6" align="center">Total de Atendimentos:
			${fn:length(atendimento_monitorias)}</td>
	</tr>
</table>