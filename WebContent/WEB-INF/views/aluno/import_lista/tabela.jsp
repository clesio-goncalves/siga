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
			<th>ID</th>
			<th>Nome Completo</th>
			<th>Turma</th>
			<th>Matrícula</th>
			<th>Usuário</th>
			<th>Situação</th>
			<th>Ações</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="aluno" items="${alunos}">
			<tr>
				<td>${aluno.id}</td>
				<td>${aluno.nome}</td>
				<td>${aluno.turma.nome}</td>

				<!-- Matricula -->
				<c:if test="${aluno.matricula eq \"\"}">
					<td>-</td>
				</c:if>
				<c:if test="${aluno.matricula ne \"\"}">
					<td>${aluno.matricula}</td>
				</c:if>

				<!-- Usuário -->
				<c:if test="${aluno.usuario == null}">
					<td>-</td>
				</c:if>
				<c:if test="${aluno.usuario != null}">
					<td>${aluno.usuario.email}</td>
				</c:if>
				<td>${aluno.situacao.nome}</td>

				<!-- AÇÕES -->
				<td>
					<!-- Exibir --> <a
					href="<c:url value="/aluno/exibe?id=${aluno.id}"/>"
					class="btn btn-info btn-sm" data-tooltip="tooltip"
					data-placement="bottom" title="Exibir"> <span
						class="glyphicon glyphicon-search"></span></a> <!-- Editar --> <security:authorize
						access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Psicologia', 'ROLE_Assistência Social', 'ROLE_Enfermagem', 'ROLE_Pedagogia', 'ROLE_Odontologia', 'ROLE_Docente', 'ROLE_Monitor', 'ROLE_Coordenação de Disciplina')">
						<a href="<c:url value="/aluno/edita?id=${aluno.id}" />"
							class="btn btn-warning btn-sm" data-tooltip="tooltip"
							data-placement="bottom" title="Editar"><span
							class="glyphicon glyphicon-pencil"></span> </a>
						<security:authorize access="hasRole('ROLE_Administrador')">
							<!-- Excluir -->
							<button type="button" class="btn btn-danger btn-sm"
								data-tooltip="tooltip" data-placement="bottom" title="Excluir"
								data-toggle="modal" data-target="#modal${aluno.id}">
								<span class="glyphicon glyphicon-trash"></span>
							</button>
							<div class="modal fade" id="modal${aluno.id}">
								<div class="modal-dialog" role="document">
									<div class="modal-content">
										<div class="modal-header">
											<h5 class="modal-title">Exclusão do aluno</h5>
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close">
												<span aria-hidden="true">&times;</span>
											</button>
										</div>
										<div class="modal-body">
											<p>
												Deseja realmente excluir o Aluno <br>ID (${aluno.id})
												-> ${aluno.nome}?
											</p>
											<p>
												<b>Atenção: Não é recomendado excluir o usuário, <br>
													Então, desative-o.<b>
											</p>
										</div>
										<div class="modal-footer">
											<a href="<c:url value="/aluno/remove?id=${aluno.id}" />"
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
					</security:authorize>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>