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
			<th>Responsável</th>
			<th>Servidor</th>
			<th>Ações</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="atendimento_pedagogia"
			items="${atendimentos_pedagogia}">
			<tr>
				<td><fmt:formatDate value="${atendimento_pedagogia.data}" /></td>
				<td><fmt:formatDate type="time"
						value="${atendimento_pedagogia.horario_inicial}" pattern="HH:mm" />
					- <fmt:formatDate type="time"
						value="${atendimento_pedagogia.horario_final}" pattern="HH:mm" /></td>
				<td>${atendimento_pedagogia.aluno.nome}</td>
				<td>${atendimento_pedagogia.responsavel}</td>
				<td>${atendimento_pedagogia.profissional.nome}</td>

				<td>
					<!-- Exibir --> <a
					href="<c:url value="/atendimento/pedagogia/familia/exibe?id=${atendimento_pedagogia.id}"/>"
					class="btn btn-info btn-sm" data-tooltip="tooltip"
					data-placement="bottom" title="Exibir"> <span
						class="glyphicon glyphicon-search"></span></a> <security:authorize
						access="hasRole('ROLE_Pedagogia')">
						<!-- Editar -->
						<a
							href="<c:url value="/atendimento/pedagogia/familia/edita?id=${atendimento_pedagogia.id}" />"
							class="btn btn-warning btn-sm" data-tooltip="tooltip"
							data-placement="bottom" title="Editar"><span
							class="glyphicon glyphicon-pencil"></span> </a>
						<!-- Excluir -->
						<button type="button" class="btn btn-danger btn-sm"
							data-tooltip="tooltip" data-placement="bottom" title="Excluir"
							data-toggle="modal"
							data-target="#modal${atendimento_pedagogia.id}">
							<span class="glyphicon glyphicon-trash"></span>
						</button>
						<div class="modal fade" id="modal${atendimento_pedagogia.id}">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title">Exclusão de Atendimento da
											Pedagogia</h5>
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
									<div class="modal-body">
										<p>
											Deseja realmente excluir o Atendimento da Pedagogia <br>ID
											(${atendimento_pedagogia.id}) - Aluno:
											${atendimento_pedagogia.aluno.nome}?
										</p>
									</div>
									<div class="modal-footer">
										<a
											href="<c:url value="/atendimento/pedagogia/familia/remove?id=${atendimento_pedagogia.id}" />"
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
			${fn:length(atendimentos_pedagogia)}</td>
	</tr>
</table>