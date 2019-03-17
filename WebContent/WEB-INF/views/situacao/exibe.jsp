<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>Exibe os dados da situação do aluno</title>
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="card border-light mb-3">
		<div class="card-header">Exibe os dados da situação</div>
		<!-- Table -->
		<div class="card-body">
			<div class="table-responsive">
				<table
					class="table table-striped table-bordered dt-responsive nowrap">
					<tr>
						<th width="30%">ID</th>
						<td>${situacao.id}</td>
					</tr>
					<tr>
						<th>Nome</th>
						<td style="font-weight: bold; color: blue;">${situacao.nome}</td>
					</tr>
					<tr>
						<th>Qnt de Aluno</th>
						<td>${fn:length(alunos_situacao)}</td>
					</tr>
				</table>
			</div>
			<legend>ALUNOS ATIVOS COM ESSA SITUACÃO</legend>
			<div class="table-responsive">
				<table class="table table-hover table-bordered dt-responsive nowrap"
					style="width: 100%; margin-top: 10px;">
					<thead>
						<tr>
							<th>ID</th>
							<th>Nome</th>
							<th>Matrícula</th>
							<th>Usuário</th>
							<th>Situação</th>
							<th>Ações</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="aluno" items="${alunos_situacao}">
							<tr>
								<td>${aluno.id}</td>
								<td>${aluno.nome}</td>

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
								<td>
									<!-- Exibir --> <a
									href="<c:url value="/aluno/exibe?id=${aluno.id}"/>"
									class="btn btn-info btn-sm" data-tooltip="tooltip"
									data-placement="bottom" title="Exibir"> <span
										class="glyphicon glyphicon-search"></span></a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
					<tr style="background-color: #fff; font-weight: bold;">
						<td colspan="6" align="center">Total de Alunos:
							${fn:length(alunos_situacao)}</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div align="center">
		<!-- Cadastrar -->
		<a href="<c:url value="/situacao/nova" />"
			class="btn btn-primary btn-lg"><span
			class="glyphicon glyphicon-plus"></span> Cadastrar</a>
		<!-- Editar -->
		<a href="<c:url value="/situacao/edita?id=${situacao.id}" />"
			class="btn btn-warning btn-lg"><span
			class="glyphicon glyphicon-edit"></span> Editar </a>
		<!-- Excluir -->
		<button type="button" class="btn btn-danger btn-lg"
			data-toggle="modal" data-target="#modal${situacao.id}">
			<span class="glyphicon glyphicon-trash"></span> Excluir
		</button>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="modal${situacao.id}">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Exclusão da situacão</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<p>
						Deseja realmente excluir a situação <br>ID (${situacao.id})
						-> ${situacao.nome}?
					</p>
					<p>
						<b>Não será permitido excluir a situação caso haja alunos
							vinculados.</b>
					</p>
				</div>
				<div class="modal-footer">
					<a href="<c:url value="/situacao/remove?id=${situacao.id}" />"
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
	<a class="btn btn-success" href="<c:url value="/situacao/lista" />"><span
		class="glyphicon glyphicon-chevron-left"></span> Voltar</a>
</div>

<c:import url="../componentes/rodape.jsp" />