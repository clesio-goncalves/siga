<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Exibe os dados do atendimento de indisciplina</title>
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="card border-light mb-3">
		<div class="card-header">Exibe os dados do atendimento de
			indisciplina</div>
		<!-- Table -->
		<div class="card-body">
			<div class="table-responsive">
				<table
					class="table table-striped table-bordered dt-responsive nowrap">
					<!-- Aluno -->
					<tr>
						<th width="30%">Aluno</th>
						<td><a
							href="<c:url value="/aluno/exibe?id=${atendimento_indisciplina.aluno.id}" />"
							style="font-weight: bold; color: blue;">${atendimento_indisciplina.aluno.nome}</a></td>
					</tr>
					<tr>
						<th>Curso</th>
						<td><a
							href="<c:url value="/curso/exibe?id=${atendimento_indisciplina.aluno.turma.curso.id}" />">${atendimento_indisciplina.aluno.turma.curso.nome}</a></td>
					</tr>
					<tr>
						<th>Turma</th>
						<td><a
							href="<c:url value="/turma/exibe?id=${atendimento_indisciplina.aluno.turma.id}" />">${atendimento_indisciplina.aluno.turma.nome}</a></td>
					</tr>
					<tr>
						<th>Profissional</th>
						<td><a
							href="<c:url value="/profissional/exibe?id=${atendimento_indisciplina.profissional.id}" />">${atendimento_indisciplina.profissional.nome}</a></td>
					</tr>
					<tr>
						<th>Data da ocorrência</th>
						<td><fmt:formatDate value="${atendimento_indisciplina.data}" /></td>
					</tr>
					<tr>
						<th>Horário</th>
						<td><fmt:formatDate type="time"
								value="${atendimento_indisciplina.horario}" pattern="HH:mm" />
						</td>
					</tr>
					<c:if test="${atendimento_indisciplina.advertido eq 'Sim'}">
						<tr>
							<th>Advertido?</th>
							<td style="font-weight: bold; color: red;">${atendimento_indisciplina.advertido}</td>
						</tr>
						<tr>
							<th>Tipo advertência</th>
							<td>${atendimento_indisciplina.tipo_advertencia}</td>
						</tr>
					</c:if>
					<c:if test="${atendimento_indisciplina.advertido eq 'Não'}">
						<tr>
							<th>Advertido?</th>
							<td>${atendimento_indisciplina.advertido}</td>
						</tr>
						<tr>
							<th>Tipo advertência</th>
							<td>-</td>
						</tr>
					</c:if>
					<tr>
						<th>Descrição</th>
						<td>${atendimento_indisciplina.descricao}</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<security:authorize access="hasRole('ROLE_Coordenação de Disciplina')">
		<div align="center">
			<!-- PDF -->
			<c:if
				test="${atendimento_indisciplina.tipo_advertencia eq 'Escrita'}">
				<button type="button" class="btn btn-outline-danger btn-lg"
					onclick="advertenciaEscrita()" data-toggle="modal"
					data-target="#modal_advertencia_escrita">
					<span class="glyphicon glyphicon-file"></span> Relatório PDF</a>
				</button>
			</c:if>
			<!-- Cadastrar -->
			<a href="<c:url value="/atendimento/indisciplina/novo" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
			<!-- Editar -->
			<a
				href="<c:url value="/atendimento/indisciplina/edita?id=${atendimento_indisciplina.id}" />"
				class="btn btn-warning btn-lg"><span
				class="glyphicon glyphicon-edit"></span> Editar </a>
			<!-- Excluir -->
			<button type="button" class="btn btn-danger btn-lg"
				data-toggle="modal"
				data-target="#modal${atendimento_indisciplina.id}">
				<span class="glyphicon glyphicon-trash"></span> Excluir
			</button>
		</div>
		<!-- modal_advertencia_escrita  -->
		<c:if test="${atendimento_indisciplina.tipo_advertencia eq 'Escrita'}">
			<jsp:include page="import_exibe/modal_advertencia_escrita.jsp"></jsp:include>
		</c:if>

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

	<a class="btn btn-success"
		href="<c:url value="/atendimento/indisciplina/lista" />"><span
		class="glyphicon glyphicon-chevron-left"></span> Voltar</a>
</div>
<script type="text/javascript"
	src="<c:url value="/resources/js/atendimento/indisciplina_advertencia_escrita.js" />"></script>
<c:import url="../componentes/rodape.jsp" />