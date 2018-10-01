<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Exibe os dados do aluno</title>
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="card border-light mb-3">
		<div class="card-header">Exibe os dados do aluno</div>
		<!-- Table -->
		<div class="card-body">
			<legend style="margin-top: 0;">INFORMAÇÕES DO ALUNO</legend>
			<div class="table-responsive">
				<table
					class="table table-striped table-bordered dt-responsive nowrap">
					<tr>
						<th width="30%">ID</th>
						<td>${aluno.id}</td>
					</tr>

					<tr>
						<th>Nome Completo</th>
						<td style="font-weight: bold; color: blue;">${aluno.nome}</td>
					</tr>

					<tr>
						<th>Matrícula</th>

						<!-- Matricula -->
						<c:if test="${aluno.matricula eq \"\"}">
							<td>-</td>
						</c:if>
						<c:if test="${aluno.matricula ne \"\"}">
							<td>${aluno.matricula}</td>
						</c:if>
					</tr>

					<tr>
						<th>Curso</th>
						<td><a
							href="<c:url value="/curso/exibe?id=${aluno.turma.curso.id}" />">${aluno.turma.curso.nome}</a></td>
					</tr>

					<tr>
						<th>Turma</th>
						<td><a
							href="<c:url value="/turma/exibe?id=${aluno.turma.id}" />">${aluno.turma.nome}</a></td>
					</tr>

					<tr>
						<th>Usuário</th>

						<!-- Usuário -->
						<c:if test="${aluno.usuario == null}">
							<td>-</td>
						</c:if>
						<c:if test="${aluno.usuario != null}">
							<td><a
								href="<c:url value="/usuario/exibe?id=${aluno.usuario.id}" />">${aluno.usuario.email}</a></td>
						</c:if>
					</tr>
				</table>
			</div>
			<legend>ATENDIMENTO EXTRACLASSE</legend>
			<div class="table-responsive">
				<table class="table table-hover table-bordered dt-responsive nowrap"
					style="width: 100%; margin-top: 10px;">
					<thead>
						<tr>
							<th>Data</th>
							<th>Horário</th>
							<th>Disciplina</th>
							<th>Docente</th>
							<th>Ações</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="atendimento_extraclasse"
							items="${atendimentos_extraclasse}">
							<tr>
								<td><fmt:formatDate value="${atendimento_extraclasse.data}" /></td>
								<td><fmt:formatDate type="time"
										value="${atendimento_extraclasse.horario_inicial}" pattern="HH:mm" /> - <fmt:formatDate type="time"
										value="${atendimento_extraclasse.horario_final}" pattern="HH:mm" /></td>
								<td>${atendimento_extraclasse.disciplina.nome}</td>
								<td>${atendimento_extraclasse.docente.nome}</td>
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
				</table>
			</div>
			<legend>ATENDIMENTO DE MONITORIA</legend>
			<div class="table-responsive">
				<table class="table table-hover table-bordered dt-responsive nowrap"
					style="width: 100%; margin-top: 10px;">
					<thead>
						<tr>
							<th>Data</th>
							<th>Horário</th>
							<th>Disciplina</th>
							<th>Monitor</th>
							<th>Ações</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="atendimento_monitoria"
							items="${atendimentos_monitoria}">
							<tr>
								<td><fmt:formatDate value="${atendimento_monitoria.data}" /></td>
								<td><fmt:formatDate type="time"
										value="${atendimento_monitoria.horario_inicial}" pattern="HH:mm" /> - <fmt:formatDate type="time"
										value="${atendimento_monitoria.horario_final}" pattern="HH:mm" /></td>
								<td>${atendimento_monitoria.disciplina.nome}</td>
								<td>${atendimento_monitoria.disciplina.monitor.nome}</td>
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
				</table>
			</div>
		</div>
	</div>
	<security:authorize access="hasRole('ROLE_Administrador')">
		<div align="center">
			<!-- Cadastrar -->
			<a href="<c:url value="/aluno/novo" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
			<!-- Editar -->
			<a href="<c:url value="/aluno/edita?id=${aluno.id}" />"
				class="btn btn-warning btn-lg"><span
				class="glyphicon glyphicon-edit"></span> Editar </a>
			<!-- Excluir -->
			<button type="button" class="btn btn-danger btn-lg"
				data-toggle="modal" data-target="#modal${aluno.id}">
				<span class="glyphicon glyphicon-trash"></span> Excluir
			</button>
		</div>
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
							Deseja realmente excluir o aluno <br>ID (${aluno.id}) ->
							${aluno.nome}?
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

	<a class="btn btn-success" href="<c:url value="/aluno/lista" />"><span
		class="glyphicon glyphicon-chevron-left"></span> Voltar</a>

</div>

<c:import url="../componentes/rodape.jsp" />