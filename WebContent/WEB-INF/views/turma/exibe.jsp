<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<title>Exibe os dados do turma</title>
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="card border-light mb-3">
		<div class="card-header">Exibe os dados do turma</div>
		<!-- Table -->
		<div class="card-body">
			<legend style="margin-top: 0;">INFORMAÇÕES DA TURMA</legend>
			<div class="table-responsive">
				<table
					class="table table-striped table-bordered dt-responsive nowrap">
					<tr>
						<th width="30%">ID</th>
						<td>${turma.id}</td>
					</tr>
					<tr>
						<th>Nome</th>
						<td style="font-weight: bold; color: blue;">${turma.nome}</td>
					</tr>
					<tr>
						<th>Curso</th>
						<td><a
							href="<c:url value="/curso/exibe?id=${turma.curso.id}" />">${turma.curso.nome}</a></td>
					</tr>
					<tr>
						<th>Quantidade de Alunos</th>
						<td>${qnt_alunos}</td>
					</tr>
				</table>
			</div>
			<legend>DISCIPLINAS E DOCENTES</legend>
			<div class="table-responsive">
				<table class="table table-hover table-bordered dt-responsive nowrap"
					style="width: 100%; margin-top: 10px;">
					<thead>
						<tr>
							<th width="50%">Disciplina</th>
							<th>Docente</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="disciplina_docente" items="${disciplinas_docente}">
							<tr>
								<td><a
									href="<c:url value="/disciplina/exibe?id=${disciplina_docente.disciplina.id}" />">${disciplina_docente.disciplina.nome}</a></td>
								<td><a
									href="<c:url value="/docente/exibe?id=${disciplina_docente.docente.id}" />">${disciplina_docente.docente.nome}</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<legend>ALUNOS</legend>
			<div class="table-responsive">
				<table class="table table-hover table-bordered dt-responsive nowrap"
					style="width: 100%; margin-top: 10px;">
					<thead>
						<tr>
							<th>ID</th>
							<th>Nome</th>
							<th>Matrícula</th>
							<th>Usuário</th>
							<th>Ações</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="aluno_turma" items="${alunos_turma}">
							<tr>
								<td>${aluno_turma.id}</td>
								<td>${aluno_turma.nome}</td>

								<!-- Matricula -->
								<c:if test="${aluno_turma.matricula eq \"\"}">
									<td>-</td>
								</c:if>
								<c:if test="${aluno_turma.matricula ne \"\"}">
									<td>${aluno_turma.matricula}</td>
								</c:if>

								<!-- Usuário -->
								<c:if test="${aluno_turma.usuario == null}">
									<td>-</td>
								</c:if>
								<c:if test="${aluno_turma.usuario != null}">
									<td>${aluno_turma.usuario.email}</td>
								</c:if>
								<td>
									<!-- Exibir --> <a
									href="<c:url value="/aluno/exibe?id=${aluno_turma.id}"/>"
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
	<security:authorize
		access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Pedagogia', 'ROLE_Coordenação de Disciplina')">
		<div align="center">
			<!-- Cadastrar -->
			<a href="<c:url value="/turma/nova" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
			<!-- Editar -->
			<a href="<c:url value="/turma/edita?id=${turma.id}" />"
				class="btn btn-warning btn-lg"><span
				class="glyphicon glyphicon-edit"></span> Editar </a>
			<!-- Excluir -->
			<button type="button" class="btn btn-danger btn-lg"
				data-toggle="modal" data-target="#modal${turma.id}">
				<span class="glyphicon glyphicon-trash"></span> Excluir
			</button>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="modal${turma.id}">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Exclusão do turma</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<p>
							Deseja realmente excluir o turma ID <br>(${turma.id}) ->
							${turma.nome}?
						</p>
						<p>
							<b>Atenção: não é permitido excluir a turma, caso<br>
								tenha aluno(s) vinculado(s)!
							</b>
						</p>
					</div>
					<div class="modal-footer">
						<a href="<c:url value="/turma/remove?id=${turma.id}" />"
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
	<a class="btn btn-success" href="<c:url value="/turma/lista" />"><span
		class="glyphicon glyphicon-chevron-left"></span> Voltar</a>
</div>
<script type="text/javascript"
	src="<c:url value="/resources/js/tooltip.js" />"></script>
<c:import url="../componentes/rodape.jsp" />