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
			<th style="vertical-align: middle;">Data</th>
			<th style="vertical-align: middle;">Horário</th>
			<th style="vertical-align: middle;">Tipo de Atendimento</th>
			<th style="vertical-align: middle;">Aluno</th>
			<th>Possui <br>observação?</th>
			<th>Dificulta <br>o aprendizado?</th>
			<th style="vertical-align: middle;">Ações</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="atendimento_saude" items="${atendimentos_saude}">
			<tr>
				<td><fmt:formatDate value="${atendimento_saude.data}" /></td>
				<td><fmt:formatDate type="time"
						value="${atendimento_saude.horario_inicial}" pattern="HH:mm" /> -
					<fmt:formatDate type="time"
						value="${atendimento_saude.horario_final}" pattern="HH:mm" /></td>
				<td>${atendimento_saude.profissional.tipo_atendimento}</td>
				<td>${atendimento_saude.aluno.nome}</td>
				<c:if test="${atendimento_saude.possui_problema eq 'Sim'}">
					<td style="font-weight: bold; color: red;">${atendimento_saude.possui_problema}</td>
					<c:if
						test="${atendimento_saude.esse_problema_dificulta_aprendizado eq 'Sim'}">
						<td style="font-weight: bold; color: red;">${atendimento_saude.esse_problema_dificulta_aprendizado}</td>
					</c:if>
					<c:if
						test="${atendimento_saude.esse_problema_dificulta_aprendizado eq 'Não'}">
						<td>${atendimento_saude.esse_problema_dificulta_aprendizado}</td>
					</c:if>
				</c:if>
				<c:if test="${atendimento_saude.possui_problema eq 'Não'}">
					<td>${atendimento_saude.possui_problema}</td>
					<td>-</td>
				</c:if>
				<td>
					<!-- Exibir --> <a
					href="<c:url value="/atendimento/saude/exibe?id=${atendimento_saude.id}"/>"
					class="btn btn-info btn-sm" data-tooltip="tooltip"
					data-placement="bottom" title="Exibir"> <span
						class="glyphicon glyphicon-search"></span></a> <c:if
						test="${tipo_atendimento != null}">
						<c:if
							test="${tipo_atendimento == atendimento_saude.profissional.tipo_atendimento}">
							<!-- Editar -->
							<a
								href="<c:url value="/atendimento/saude/edita?id=${atendimento_saude.id}" />"
								class="btn btn-warning btn-sm" data-tooltip="tooltip"
								data-placement="bottom" title="Editar"><span
								class="glyphicon glyphicon-pencil"></span> </a>
							<!-- Excluir -->
							<button type="button" class="btn btn-danger btn-sm"
								data-tooltip="tooltip" data-placement="bottom" title="Excluir"
								data-toggle="modal" data-target="#modal${atendimento_saude.id}">
								<span class="glyphicon glyphicon-trash"></span>
							</button>
							<div class="modal fade" id="modal${atendimento_saude.id}">
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
												Deseja realmente excluir o Atendimento de Saúde <br>ID
												(${atendimento_saude.id}) - Aluno:
												${atendimento_saude.aluno.nome}?
											</p>
										</div>
										<div class="modal-footer">
											<a
												href="<c:url value="/atendimento/saude/remove?id=${atendimento_saude.id}" />"
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
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</tbody>
	<tr style="background-color: #fff; font-weight: bold;">
		<td colspan="7" align="center">Total de Atendimentos:
			${fn:length(atendimentos_saude)}</td>
	</tr>
</table>