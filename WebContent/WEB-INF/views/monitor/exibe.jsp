<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>Exibe os dados do monitor</title>
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="card border-light mb-3">
		<div class="card-header">Exibe os dados do monitor</div>
		<!-- Table -->
		<div class="card-body">
			<legend style="margin-top: 0;">INFORMAÇÕES DO MONITOR</legend>
			<div class="table-responsive">
				<table
					class="table table-striped table-bordered dt-responsive nowrap">
					<tr>
						<th width="30%">ID</th>
						<td>${monitor.id}</td>
					</tr>
					<tr>
						<th>Nome Completo</th>
						<td style="font-weight: bold; color: blue;">${monitor.nome}</td>
					</tr>
					<tr>
						<th>Matrícula</th>
						<td>${monitor.matricula}</td>
					</tr>
					<tr>
						<th>Usuário</th>
						<td><a
							href="<c:url value="/usuario/exibe?id=${monitor.usuario.id}" />">${monitor.usuario.email}</a></td>
					</tr>
				</table>
			</div>
			<legend>DISCIPLINAS</legend>
			<div class="table-responsive">
				<table class="table table-hover table-bordered dt-responsive nowrap"
					style="width: 100%; margin-top: 10px;">
					<thead>
						<tr>
							<th>ID</th>
							<th>Nome</th>
							<th>Ações</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="disciplina_monitor" items="${disciplinas_monitor}">
							<tr>
								<td>${disciplina_monitor.id}</td>
								<td>${disciplina_monitor.nome}</td>
								<td>
									<!-- Exibir --> <a
									href="<c:url value="/disciplina/exibe?id=${disciplina_monitor.id}"/>"
									class="btn btn-info btn-sm" data-tooltip="tooltip"
									data-placement="bottom" title="Exibir"> <span
										class="glyphicon glyphicon-search"></span></a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<security:authorize
				access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Psicologia', 'ROLE_Assistência Social', 'ROLE_Enfermagem', 'ROLE_Pedagogia', 'ROLE_Odontologia', 'ROLE_Docente', 'ROLE_Monitor', 'ROLE_Coordenação de Disciplina')">
				<security:authentication property="principal" var="usuario_logado" />
				<c:if
					test="${usuario_logado.perfil.id != 10 or usuario_logado.id == monitor.usuario.id}">
					<legend>ATENDIMENTO DE MONITORIA</legend>
					<div class="table-responsive">
						<table
							class="table table-hover table-bordered dt-responsive nowrap"
							style="width: 100%; margin-top: 10px;">
							<thead>
								<tr>
									<th>Data</th>
									<th>Horário</th>
									<th>Disciplina</th>
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
										<c:if test="${atendimento_monitoria.status_atendimento}">
											<td>-</td>
											<td>-</td>
										</c:if>
										<c:if
											test="${atendimento_monitoria.status_atendimento == false}">
											<td>${atendimento_monitoria.disciplina.nome}</td>
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
				</c:if>
			</security:authorize>
		</div>
	</div>
	<security:authorize
		access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Pedagogia', 'ROLE_Docente')">
		<div align="center">
			<!-- Cadastrar -->
			<a href="<c:url value="/monitor/novo" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
			<!-- Editar -->
			<a href="<c:url value="/monitor/edita?id=${monitor.id}" />"
				class="btn btn-warning btn-lg"><span
				class="glyphicon glyphicon-edit"></span> Editar </a>
			<!-- Excluir -->
			<button type="button" class="btn btn-danger btn-lg"
				data-toggle="modal" data-target="#modal${monitor.id}">
				<span class="glyphicon glyphicon-trash"></span> Excluir
			</button>
		</div>
		<div class="modal fade" id="modal${monitor.id}">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Exclusão do monitor</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<p>
							Deseja realmente excluir o monitor <br>ID (${monitor.id}) ->
							${monitor.nome}?
						</p>
						<p>
							<b>Atenção: Não é recomendado excluir o usuário, <br>
								Então, desative-o.<b>
						</p>
					</div>
					<div class="modal-footer">
						<a href="<c:url value="/monitor/remove?id=${monitor.id}" />"
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

	<a class="btn btn-success" href="<c:url value="/monitor/lista" />"><span
		class="glyphicon glyphicon-chevron-left"></span> Voltar</a>

</div>
<script type="text/javascript"
	src="<c:url value="/resources/js/tooltip.js" />"></script>
<c:import url="../componentes/rodape.jsp" />