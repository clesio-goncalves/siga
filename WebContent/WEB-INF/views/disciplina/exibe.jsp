<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Exibe os dados da disciplina</title>
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="card border-light mb-3">
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
						<td style="font-weight: bold; color: red;">${disciplina.nome}</td>
					</tr>
					<tr>
						<th>Monitor</th>
						<c:if test="${disciplina.monitor == null}">
							<td>Não informado</td>
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
			<legend>ATENDIMENTO EXTRACLASSE</legend>
			<div class="table-responsive">
				<table class="table table-hover table-bordered dt-responsive nowrap"
					style="width: 100%; margin-top: 10px;">
					<thead>
						<tr>
							<th>Data</th>
							<th>Turma</th>
							<th>Docente</th>
							<th>Aluno</th>
							<th>Ações</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="atendimento_extraclasse"
							items="${atendimentos_extraclasse}">
							<tr>
								<td><fmt:formatDate value="${atendimento_extraclasse.data}" /></td>
								<td>${atendimento_extraclasse.aluno.turma.nome}</td>
								<td>${atendimento_extraclasse.docente.nome}</td>
								<td>${atendimento_extraclasse.aluno.nome}</td>
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
		</div>
	</div>
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
						class="btn btn-danger"><span class="glyphicon glyphicon-trash"></span>
						Excluir</a>
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">
						<span class="glyphicon glyphicon-log-out"></span> Fechar
					</button>
				</div>
			</div>
		</div>
	</div>
	<a class="btn btn-success" href="<c:url value="/disciplina/lista" />"><span
		class="glyphicon glyphicon-chevron-left"></span> Voltar</a>
</div>

<script type="text/javascript"
	src="<c:url value="/resources/js/tooltip.js" />"></script>
<c:import url="../componentes/rodape.jsp" />