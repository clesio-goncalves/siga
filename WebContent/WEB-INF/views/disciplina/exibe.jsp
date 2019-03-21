<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>Exibe os dados da disciplina</title>
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="card border-light mb-3">
		<security:authentication property="principal" var="usuario_logado" />
		<div class="card-header">Exibe os dados da disciplina</div>
		<!-- Table -->
		<div class="card-body">
			<legend style="margin-top: 0;">INFORMAÇÕES DA DISCIPLINA</legend>
			<div class="table-responsive">
				<table
					class="table table-striped table-bordered dt-responsive nowrap">
					<tr>
						<th width="30%">ID</th>
						<td>${disciplina.id}</td>
					</tr>
					<tr>
						<th>Nome</th>
						<td style="font-weight: bold; color: blue;">${disciplina.nome}</td>
					</tr>
					<tr>
						<th>Monitor</th>
						<c:if test="${disciplina.monitor == null}">
							<td>-</td>
						</c:if>
						<c:if test="${disciplina.monitor != null}">
							<td><a
								href="<c:url value="/monitor/exibe?id=${disciplina.monitor.id}" />">${disciplina.monitor.nome}</a></td>
						</c:if>
					</tr>
				</table>
			</div>
			<legend>TURMAS E DOCENTES</legend>
			<div class="table-responsive">
				<table class="table table-hover table-bordered dt-responsive nowrap"
					style="width: 100%; margin-top: 10px;">
					<thead>
						<tr>
							<th width="50%">Turma</th>
							<th>Docente</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="turma_docente" items="${turmas_docentes}">
							<tr>
								<td><a
									href="<c:url value="/turma/exibe?id=${turma_docente.turma.id}" />">${turma_docente.turma.nome}</a></td>
								<td><a
									href="<c:url value="/docente/exibe?id=${turma_docente.docente.id}" />">${turma_docente.docente.nome}</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<security:authorize
				access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Psicologia', 'ROLE_Assistência Social', 'ROLE_Enfermagem', 'ROLE_Pedagogia', 'ROLE_Odontologia', 'ROLE_Coordenação de Disciplina')">
				<legend>ATENDIMENTO EXTRACLASSE</legend>
				<div class="table-responsive">
					<table
						class="table table-hover table-bordered dt-responsive nowrap"
						style="width: 100%; margin-top: 10px;">
						<thead>
							<tr>
								<th>Data</th>
								<th>Horário</th>
								<th>Docente</th>
								<th>Aluno(s)</th>
								<th>Ações</th>
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
									<td>${atendimento_extraclasse.docente.nome}</td>
									<td>${atendimento_extraclasse.alunos}</td>
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
			</security:authorize>
			<security:authorize
				access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Psicologia', 'ROLE_Assistência Social', 'ROLE_Enfermagem', 'ROLE_Pedagogia', 'ROLE_Odontologia', 'ROLE_Docente', 'ROLE_Coordenação de Disciplina')">
				<legend>ATENDIMENTO DE MONITORIA</legend>
				<div class="table-responsive">
					<table
						class="table table-hover table-bordered dt-responsive nowrap"
						style="width: 100%; margin-top: 10px;">
						<thead>
							<tr>
								<th>Data</th>
								<th>Horário</th>
								<th>Monitor</th>
								<th>Aluno(s)</th>
								<th>Ações</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="atendimento_monitoria"
								items="${atendimentos_monitoria}">
								<tr>
									<td><fmt:formatDate value="${atendimento_monitoria.data}" /></td>
									<td><fmt:formatDate type="time"
											value="${atendimento_monitoria.horario_inicial}"
											pattern="HH:mm" /> - <fmt:formatDate type="time"
											value="${atendimento_monitoria.horario_final}"
											pattern="HH:mm" /></td>
									<td>${atendimento_monitoria.monitor.nome}</td>
									<c:if test="${atendimento_monitoria.status_atendimento}">
										<td>-</td>
									</c:if>
									<c:if
										test="${atendimento_monitoria.status_atendimento == false}">
										<td>${atendimento_monitoria.alunos}</td>
									</c:if>

									<td>
										<!-- Exibir --> <a
										href="<c:url value="/atendimento/monitoria/exibe?id=${atendimento_monitoria.id}"/>"
										class="btn btn-info btn-sm" data-tooltip="tooltip"
										data-placement="bottom" title="Exibir"> <span
											class="glyphicon glyphicon-search"></span></a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
						<tr style="background-color: #fff; font-weight: bold;">
							<td colspan="5" align="center">Total de Atendimentos:
								${fn:length(atendimentos_monitoria)}</td>
						</tr>
					</table>
				</div>
			</security:authorize>
		</div>
	</div>
	<security:authorize
		access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Pedagogia', 'ROLE_Coordenação de Disciplina')">
		<div align="center">
			<!-- Cadastrar -->
			<a href="<c:url value="/disciplina/nova" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
			<!-- Editar -->
			<a href="<c:url value="/disciplina/edita?id=${disciplina.id}" />"
				class="btn btn-warning btn-lg"><span
				class="glyphicon glyphicon-edit"></span> Editar </a>
			<!-- Excluir -->
			<button type="button" class="btn btn-danger btn-lg"
				data-toggle="modal" data-target="#modal${disciplina.id}">
				<span class="glyphicon glyphicon-trash"></span> Excluir
			</button>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="modal${disciplina.id}">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Exclusão da disciplina</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<p>Deseja realmente excluir a disciplina ID (${disciplina.id})
							-> ${disciplina.nome}?</p>
					</div>
					<div class="modal-footer">
						<a href="<c:url value="/disciplina/remove?id=${disciplina.id}" />"
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
	<a class="btn btn-success" href="<c:url value="/disciplina/lista" />"><span
		class="glyphicon glyphicon-chevron-left"></span> Voltar</a>
</div>

<script type="text/javascript"
	src="<c:url value="/resources/js/tooltip.js" />"></script>
<c:import url="../componentes/rodape.jsp" />