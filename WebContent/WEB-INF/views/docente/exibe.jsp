<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>Exibe os dados do docente</title>
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="card border-light mb-3">
		<div class="card-header">Exibe os dados do docente</div>
		<!-- Table -->
		<div class="card-body">
			<legend style="margin-top: 0;">INFORMA��ES DO DOCENTE</legend>
			<div class="table-responsive">
				<table
					class="table table-striped table-bordered dt-responsive nowrap">
					<tr>
						<th width="30%">ID</th>
						<td>${docente.id}</td>
					</tr>
					<tr>
						<th>Nome Completo</th>
						<td style="font-weight: bold; color: blue;">${docente.nome}</td>
					</tr>
					<tr>
						<th>SIAPE</th>
						<td>${docente.siape}</td>
					</tr>
					<tr>
						<th>Usu�rio</th>
						<td><a
							href="<c:url value="/usuario/exibe?id=${docente.usuario.id}" />">${docente.usuario.email}</a></td>
					</tr>
				</table>
			</div>
			<legend>DISCIPLINAS E TURMAS</legend>
			<div class="table-responsive">
				<table class="table table-hover table-bordered dt-responsive nowrap"
					style="width: 100%; margin-top: 10px;">
					<thead>
						<tr>
							<th width="50%">Disciplina</th>
							<th>Turma</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="disciplina_turma" items="${disciplinas_turma}">
							<tr>
								<td><a
									href="<c:url value="/disciplina/exibe?id=${disciplina_turma.disciplina.id}" />">${disciplina_turma.disciplina.nome}</a></td>
								<td><a
									href="<c:url value="/turma/exibe?id=${disciplina_turma.turma.id}" />">${disciplina_turma.turma.nome}</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<security:authorize
				access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Psicologia', 'ROLE_Assist�ncia Social', 'ROLE_Enfermagem', 'ROLE_Pedagogia', 'ROLE_Odontologia', 'ROLE_Docente', 'ROLE_Coordena��o de Disciplina')">
				<security:authentication property="principal" var="usuario_logado" />
				<c:if
					test="${usuario_logado.perfil.id != 9 or usuario_logado.id == docente.usuario.id}">
					<legend>ATENDIMENTO EXTRACLASSE</legend>
					<div class="table-responsive">
						<table
							class="table table-hover table-bordered dt-responsive nowrap"
							style="width: 100%; margin-top: 10px;">
							<thead>
								<tr>
									<th>Data</th>
									<th>Hor�rio</th>
									<th>Disciplina</th>
									<th>Aluno(s)</th>
									<th>A��es</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="atendimento_extraclasse"
									items="${atendimentos_extraclasse}">
									<tr>
										<td><fmt:formatDate
												value="${atendimento_extraclasse.data}" /></td>
										<td><fmt:formatDate type="time"
												value="${atendimento_extraclasse.horario_inicial}"
												pattern="HH:mm" /> - <fmt:formatDate type="time"
												value="${atendimento_extraclasse.horario_final}"
												pattern="HH:mm" /></td>
										<c:if test="${atendimento_extraclasse.status_atendimento}">
											<td>-</td>
											<td>-</td>
										</c:if>
										<c:if
											test="${atendimento_extraclasse.status_atendimento == false}">
											<td>${atendimento_extraclasse.disciplina.nome}</td>
											<td>${atendimento_extraclasse.alunos}</td>
										</c:if>
										<td>
											<!-- Exibir --> <a
											href="<c:url value="/atendimento/extra-classe/exibe?id=${atendimento_extraclasse.id}"/>"
											class="btn btn-info btn-sm" data-tooltip="tooltip"
											data-placement="bottom" title="Exibir"> <span
												class="glyphicon glyphicon-search"></span></a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
							<tr style="background-color: #fff; font-weight: bold;">
								<td colspan="5" align="center">Total de Atendimentos:
									${fn:length(atendimentos_extraclasse)}</td>
							</tr>
						</table>
					</div>
				</c:if>
			</security:authorize>
		</div>
	</div>
	<security:authorize
		access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Pedagogia', 'ROLE_Coordena��o de Disciplina')">
		<div align="center">
			<!-- Cadastrar -->
			<a href="<c:url value="/docente/novo" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
			<!-- Editar -->
			<a href="<c:url value="/docente/edita?id=${docente.id}" />"
				class="btn btn-warning btn-lg"><span
				class="glyphicon glyphicon-edit"></span> Editar </a>
			<!-- Excluir -->
			<button type="button" class="btn btn-danger btn-lg"
				data-toggle="modal" data-target="#modal${docente.id}">
				<span class="glyphicon glyphicon-trash"></span> Excluir
			</button>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="modal${docente.id}">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Exclus�o do Docente</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<p>
							Deseja realmente excluir o Docente <br>ID (${docente.id}) ->
							${docente.nome}?
						</p>
						<p>
							<b>Aten��o: N�o � recomendado excluir o usu�rio, <br>
								Ent�o, desative-o.<b>
						</p>
					</div>
					<div class="modal-footer">
						<a href="<c:url value="/docente/remove?id=${docente.id}" />"
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
	<a class="btn btn-success" href="<c:url value="/docente/lista" />"><span
		class="glyphicon glyphicon-chevron-left"></span> Voltar</a>
</div>

<script type="text/javascript"
	src="<c:url value="/resources/js/tooltip.js" />"></script>
<c:import url="../componentes/rodape.jsp" />