<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>Exibe os dados do benefício assistencial</title>
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="card border-light mb-3">
		<div class="card-header">Exibe os dados do benefício</div>
		<!-- Table -->
		<div class="card-body">
			<div class="table-responsive">
				<table
					class="table table-striped table-bordered dt-responsive nowrap">
					<tr>
						<th width="30%">ID</th>
						<td>${beneficio.id}</td>
					</tr>
					<tr>
						<th>Nome</th>
						<td style="font-weight: bold; color: blue;">${beneficio.nome}</td>
					</tr>
					<tr>
						<th>Qnt de Aluno</th>
						<td>${fn:length(alunos_beneficio)}</td>
					</tr>
				</table>
			</div>
			<legend>ALUNOS ATIVOS COM ESSE BENEFÍCIO</legend>
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
						<c:forEach var="aluno" items="${alunos_beneficio}">
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
							${fn:length(alunos_beneficio)}</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div align="center">
		<!-- Cadastrar -->
		<a href="<c:url value="/beneficio/novo" />"
			class="btn btn-primary btn-lg"><span
			class="glyphicon glyphicon-plus"></span> Cadastrar</a>
		<!-- Editar -->
		<a href="<c:url value="/beneficio/edita?id=${beneficio.id}" />"
			class="btn btn-warning btn-lg"><span
			class="glyphicon glyphicon-edit"></span> Editar </a>
		<!-- Excluir -->
		<button type="button" class="btn btn-danger btn-lg"
			data-toggle="modal" data-target="#modal${beneficio.id}">
			<span class="glyphicon glyphicon-trash"></span> Excluir
		</button>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="modal${beneficio.id}">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Exclusão do benefício</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<p>
						Deseja realmente excluir o benefício <br>ID (${beneficio.id})
						-> ${beneficio.nome}?
					</p>
					<p>
						<b>Não será permitido excluir o benefício caso haja alunos
							vinculados.</b>
					</p>
				</div>
				<div class="modal-footer">
					<a href="<c:url value="/beneficio/remove?id=${beneficio.id}" />"
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
	<a class="btn btn-success" href="<c:url value="/beneficio/lista" />"><span
		class="glyphicon glyphicon-chevron-left"></span> Voltar</a>
</div>

<c:import url="../componentes/rodape.jsp" />