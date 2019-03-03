<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Exibe os dados do atendimento de monitoria</title>
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="card border-light mb-3">
		<div class="card-header">Exibe os dados do atendimento de
			monitoria</div>
		<!-- Table -->
		<div class="card-body">
			<div class="table-responsive">
				<table
					class="table table-striped table-bordered dt-responsive nowrap">
					<!-- Aluno -->
					<c:if test="${atendimento_monitoria.status_atendimento}">
						<tr>
							<th width="30%">Aluno</th>
							<td>-</td>
						</tr>
						<tr>
							<th>Curso</th>
							<td>-</td>
						</tr>
						<tr>
							<th>Turma</th>
							<td>-</td>
						</tr>
					</c:if>
					<c:if test="${atendimento_monitoria.status_atendimento == false}">
						<tr>
							<th width="30%">Aluno</th>
							<td><a
								href="<c:url value="/aluno/exibe?id=${atendimento_monitoria.aluno.id}" />"
								style="font-weight: bold; color: blue;">${atendimento_monitoria.aluno.nome}</a></td>
						</tr>
						<tr>
							<th>Curso</th>
							<td><a
								href="<c:url value="/curso/exibe?id=${atendimento_monitoria.aluno.turma.curso.id}" />">${atendimento_monitoria.aluno.turma.curso.nome}</a></td>
						</tr>
						<tr>
							<th>Turma</th>
							<td><a
								href="<c:url value="/turma/exibe?id=${atendimento_monitoria.aluno.turma.id}" />">${atendimento_monitoria.aluno.turma.nome}</a></td>
						</tr>
					</c:if>
					<tr>
						<th>Disciplina</th>
						<td><a
							href="<c:url value="/disciplina/exibe?id=${atendimento_monitoria.disciplina.id}" />">${atendimento_monitoria.disciplina.nome}</a></td>
					</tr>
					<tr>
						<th>Monitor</th>
						<td><a
							href="<c:url value="/monitor/exibe?id=${atendimento_monitoria.disciplina.monitor.id}" />">${atendimento_monitoria.disciplina.monitor.nome}</a></td>
					</tr>
					<tr>
						<th>Data do atendimento</th>
						<td><fmt:formatDate value="${atendimento_monitoria.data}" /></td>
					</tr>
					<tr>
						<th>Horário</th>
						<td><fmt:formatDate type="time"
								value="${atendimento_monitoria.horario_inicial}" pattern="HH:mm" />
							- <fmt:formatDate type="time"
								value="${atendimento_monitoria.horario_final}" pattern="HH:mm" /></td>
					</tr>
					<tr>
						<th>Local</th>
						<td>${atendimento_monitoria.local}</td>
					</tr>
					<tr>
						<th>Conteúdo</th>
						<td>${atendimento_monitoria.conteudo}</td>
					</tr>
					<tr>
						<th>Dificuldades diagnosticadas</th>
						<td>${atendimento_monitoria.dificuldades_diagnosticadas}</td>
					</tr>
					<tr>
						<th>Status</th>
						<c:if test="${atendimento_monitoria.status_atendimento}">
							<td>Não houve atendimento</td>
						</c:if>
						<c:if test="${atendimento_monitoria.status_atendimento == false}">
							<td>Atendimento realizado</td>
						</c:if>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<security:authorize
		access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Pedagogia', 'ROLE_Monitor')">
		<div align="center">
			<!-- Cadastrar -->
			<a href="<c:url value="/atendimento/monitoria/novo" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
			<!-- Editar -->
			<a
				href="<c:url value="/atendimento/monitoria/edita?id=${atendimento_monitoria.id}" />"
				class="btn btn-warning btn-lg"><span
				class="glyphicon glyphicon-edit"></span> Editar </a>
			<!-- Excluir -->
			<button type="button" class="btn btn-danger btn-lg"
				data-toggle="modal" data-target="#modal${atendimento_monitoria.id}">
				<span class="glyphicon glyphicon-trash"></span> Excluir
			</button>
		</div>
		<div class="modal fade" id="modal${atendimento_monitoria.id}">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Exclusão do Atendimento de Monitoria</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<p>
							Deseja realmente excluir o Atendimento de Monitoria <br>ID
							(${atendimento_monitoria.id}) - Aluno:
							${atendimento_monitoria.aluno.nome}?
						</p>
					</div>
					<div class="modal-footer">
						<a
							href="<c:url value="/atendimento/monitoria/remove?id=${atendimento_monitoria.id}" />"
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

	<a class="btn btn-success"
		href="<c:url value="/atendimento/monitoria/lista" />"><span
		class="glyphicon glyphicon-chevron-left"></span> Voltar</a>
</div>

<c:import url="../componentes/rodape.jsp" />