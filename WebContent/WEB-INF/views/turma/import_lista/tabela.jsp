<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<table id="tabela_id"
	class="table table-striped table-bordered dt-responsive nowrap"
	style="width: 100%">
	<thead>
		<tr>
			<th>ID</th>
			<th>Nome</th>
			<th>Ativa</th>
			<th>Qnt Alunos</th>
			<th>Curso</th>
			<th>Ações</th>
		</tr>
	</thead>
	<tbody>
		<!-- percorre turmas montando as linhas da tabela -->
		<c:forEach var="turma" items="${turmas}">
			<tr>
				<td>${turma.id}</td>
				<td>${turma.nome}</td>

				<!-- Ativo -->
				<c:if test="${turma.ativo eq true}">
					<td>Sim</td>
				</c:if>
				<c:if test="${turma.ativo eq false}">
					<td>Não</td>
				</c:if>

				<td>${turma.qnt_alunos}</td>
				<td>${turma.curso.nome}</td>
				<td>
					<!-- Exibir --> <a
					href="<c:url value="/turma/exibe?id=${turma.id}"/>"
					class="btn btn-info btn-sm" data-tooltip="tooltip"
					data-placement="bottom" title="Exibir"> <span
						class="glyphicon glyphicon-search"></span></a> <!-- Editar --> <security:authorize
						access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Pedagogia', 'ROLE_Coordenação de Disciplina')">
						<a href="<c:url value="/turma/edita?id=${turma.id}" />"
							class="btn btn-warning btn-sm" data-tooltip="tooltip"
							data-placement="bottom" title="Editar"><span
							class="glyphicon glyphicon-pencil"></span> </a>
						<!-- Excluir -->
						<button type="button" class="btn btn-danger btn-sm"
							data-tooltip="tooltip" data-placement="bottom" title="Excluir"
							data-toggle="modal" data-target="#modal${turma.id}">
							<span class="glyphicon glyphicon-trash"></span>
						</button>
						<div class="modal fade" id="modal${turma.id}">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title">Exclusão da turma</h5>
										<button type="button" class="close" data-dismiss="modal"
											aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
									<div class="modal-body">
										<p>
											Deseja realmente excluir a turma <br>ID (${turma.id}) ->
											${turma.nome}?
										</p>
										<p>
											<b>Atenção: não é permitido excluir a turma, caso<br>
												tenha aluno(s) vinculado(s)!
											</b>
										</p>
									</div>
									<div class="modal-footer">
										<a href="<c:url value="/turma/remove?id=${turma.id}" />"
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