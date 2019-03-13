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
			<th>Docente</th>
			<th>Ações</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="extra_classe" items="${extra_classes}">
			<tr>
				<td><fmt:formatDate value="${extra_classe.data}" /></td>
				<td><fmt:formatDate type="time"
						value="${extra_classe.horario_inicial}" pattern="HH:mm" /> - <fmt:formatDate
						type="time" value="${extra_classe.horario_final}" pattern="HH:mm" /></td>

				<c:if test="${extra_classe.status_atendimento}">
					<td>-</td>
					<td>-</td>
				</c:if>
				<c:if test="${extra_classe.status_atendimento == false}">
					<td>${extra_classe.aluno.nome}</td>
					<td>${extra_classe.disciplina.nome}</td>
				</c:if>
				<td>${extra_classe.docente.nome}</td>
				<td>
					<!-- Exibir --> <a
					href="<c:url value="/atendimento/extra-classe/exibe?id=${extra_classe.id}"/>"
					class="btn btn-info btn-sm" data-tooltip="tooltip"
					data-placement="bottom" title="Exibir"> <span
						class="glyphicon glyphicon-search"></span></a> <!-- Editar --> <security:authorize
						access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Pedagogia', 'ROLE_Docente')">
						<a
							href="<c:url value="/atendimento/extra-classe/edita?id=${extra_classe.id}" />"
							class="btn btn-warning btn-sm" data-tooltip="tooltip"
							data-placement="bottom" title="Editar"><span
							class="glyphicon glyphicon-pencil"></span> </a>
						<!-- Excluir -->
						<button type="button" class="btn btn-danger btn-sm"
							data-tooltip="tooltip" data-placement="bottom" title="Excluir"
							data-toggle="modal" data-target="#modal${extra_classe.id}">
							<span class="glyphicon glyphicon-trash"></span>
						</button>
						<div class="modal fade" id="modal${extra_classe.id}">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title">Exclusão do Atendimento
											Extraclasse</h5>
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
									<div class="modal-body">
										<p>
											Deseja realmente excluir o Atendimento Extraclasse de <br>ID
											nº (${extra_classe.id}) - Docente:
											${extra_classe.docente.nome}?
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
					</security:authorize>
				</td>
			</tr>
		</c:forEach>
	</tbody>
	<tr style="background-color: #fff; font-weight: bold;">
		<td colspan="5" align="center">Total de Atendimentos:
			${fn:length(extra_classes)}</td>
	</tr>
</table>