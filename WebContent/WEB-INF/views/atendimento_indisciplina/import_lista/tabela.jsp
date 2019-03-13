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
			<th>Advertido</th>
			<th>Tipo advertência</th>
			<th>Descrição</th>
			<th>Ações</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="atendimento_indisciplina"
			items="${atendimentos_indisciplina}">
			<tr>
				<td><fmt:formatDate value="${atendimento_indisciplina.data}" /></td>
				<td><fmt:formatDate type="time"
						value="${atendimento_indisciplina.horario}" pattern="HH:mm" /></td>
				<td>${atendimento_indisciplina.aluno.nome}</td>
				<c:if test="${atendimento_indisciplina.advertido eq 'Sim'}">
					<td style="font-weight: bold; color: red;">${atendimento_indisciplina.advertido}</td>
					<td>${atendimento_indisciplina.tipo_advertencia}</td>
				</c:if>
				<c:if test="${atendimento_indisciplina.advertido eq 'Não'}">
					<td>${atendimento_indisciplina.advertido}</td>
					<td>-</td>
				</c:if>
				<td>${atendimento_indisciplina.descricao}</td>
				<td>
					<!-- Exibir --> <a
					href="<c:url value="/atendimento/indisciplina/exibe?id=${atendimento_indisciplina.id}"/>"
					class="btn btn-info btn-sm" data-tooltip="tooltip"
					data-placement="bottom" title="Exibir"> <span
						class="glyphicon glyphicon-search"></span></a> <security:authorize
						access="hasRole('ROLE_Coordenação de Disciplina')">
						<!-- Editar -->
						<a
							href="<c:url value="/atendimento/indisciplina/edita?id=${atendimento_indisciplina.id}" />"
							class="btn btn-warning btn-sm" data-tooltip="tooltip"
							data-placement="bottom" title="Editar"><span
							class="glyphicon glyphicon-pencil"></span> </a>
						<!-- Excluir -->
						<button type="button" class="btn btn-danger btn-sm"
							data-tooltip="tooltip" data-placement="bottom" title="Excluir"
							data-toggle="modal"
							data-target="#modal${atendimento_indisciplina.id}">
							<span class="glyphicon glyphicon-trash"></span>
						</button>
						<div class="modal fade" id="modal${atendimento_indisciplina.id}">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title">Exclusão de Atendimento de
											Indisciplina</h5>
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
									<div class="modal-body">
										<p>
											Deseja realmente excluir a Ocorrência de Indisciplina <br>ID
											(${atendimento_indisciplina.id}) - Aluno:
											${atendimento_indisciplina.aluno.nome}?
										</p>
									</div>
									<div class="modal-footer">
										<a
											href="<c:url value="/atendimento/indisciplina/remove?id=${atendimento_indisciplina.id}" />"
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
		<td colspan="7" align="center">Total de Ocorrências:
			${fn:length(atendimentos_indisciplina)}</td>
	</tr>
</table>