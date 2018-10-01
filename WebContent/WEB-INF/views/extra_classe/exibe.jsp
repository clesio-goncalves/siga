<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Exibe os dados do atendimento extraclasse</title>
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="card border-light mb-3">
		<div class="card-header">Exibe os dados do atendimento
			extraclasse</div>
		<!-- Table -->
		<div class="card-body">
			<div class="table-responsive">
				<table
					class="table table-striped table-bordered dt-responsive nowrap">
					<!-- Aluno -->
					<c:if test="${extra_classe.status_atendimento}">
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
						<tr>
							<th>Disciplina</th>
							<td>-</td>
						</tr>
					</c:if>
					<c:if test="${extra_classe.status_atendimento == false}">
						<tr>
							<th width="30%">Aluno</th>
							<td><a
								href="<c:url value="/aluno/exibe?id=${extra_classe.aluno.id}" />"
								style="font-weight: bold; color: blue;">${extra_classe.aluno.nome}</a></td>
						</tr>
						<tr>
							<th>Curso</th>
							<td><a
								href="<c:url value="/curso/exibe?id=${extra_classe.aluno.turma.curso.id}" />">${extra_classe.aluno.turma.curso.nome}</a></td>
						</tr>
						<tr>
							<th>Turma</th>
							<td><a
								href="<c:url value="/turma/exibe?id=${extra_classe.aluno.turma.id}" />">${extra_classe.aluno.turma.nome}</a></td>
						</tr>
						<tr>
							<th>Disciplina</th>
							<td><a
								href="<c:url value="/disciplina/exibe?id=${extra_classe.disciplina.id}" />">${extra_classe.disciplina.nome}</a></td>
						</tr>
					</c:if>
					<tr>
						<th>Docente</th>
						<td><a
							href="<c:url value="/docente/exibe?id=${extra_classe.docente.id}" />">${extra_classe.docente.nome}</a></td>
					</tr>
					<tr>
						<th>Data do atendimento</th>
						<td><fmt:formatDate value="${extra_classe.data}" /></td>
					</tr>
					<tr>
						<th>Horário</th>
						<td><fmt:formatDate type="time"
								value="${extra_classe.horario_inicial}" pattern="HH:mm" /> - <fmt:formatDate
								type="time" value="${extra_classe.horario_final}"
								pattern="HH:mm" /></td>
					</tr>
					<tr>
						<th>Local</th>
						<td>${extra_classe.local}</td>
					</tr>
					<tr>
						<th>Conteúdo</th>
						<td>${extra_classe.conteudo}</td>
					</tr>
					<tr>
						<th>Status</th>
						<c:if test="${extra_classe.status_atendimento}">
							<td>Não houve atendimento</td>
						</c:if>
						<c:if test="${extra_classe.status_atendimento == false}">
							<td>Atendimento realizado</td>
						</c:if>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<security:authorize access="hasRole('ROLE_Administrador')">
		<div align="center">
			<!-- Cadastrar -->
			<a href="<c:url value="/atendimento/extra-classe/novo" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
			<!-- Editar -->
			<a
				href="<c:url value="/atendimento/extra-classe/edita?id=${extra_classe.id}" />"
				class="btn btn-warning btn-lg"><span
				class="glyphicon glyphicon-edit"></span> Editar </a>
			<!-- Excluir -->
			<button type="button" class="btn btn-danger btn-lg"
				data-toggle="modal" data-target="#modal${extra_classe.id}">
				<span class="glyphicon glyphicon-trash"></span> Excluir
			</button>
		</div>
		<div class="modal fade" id="modal${extra_classe.id}">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Exclusão do Atendimento Extraclasse</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<p>
							Deseja realmente excluir o Atendimento Extraclasse <br>ID
							(${extra_classe.id}) - Aluno: ${extra_classe.aluno.nome}?
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

	<a class="btn btn-success"
		href="<c:url value="/atendimento/extra-classe/lista" />"><span
		class="glyphicon glyphicon-chevron-left"></span> Voltar</a>

</div>

<c:import url="../componentes/rodape.jsp" />