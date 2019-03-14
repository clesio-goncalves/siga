<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Exibe os dados do atendimento da pedagogia a família</title>
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="card border-light mb-3">
		<div class="card-header">Exibe os dados do atendimento da
			pedagogia a família</div>
		<!-- Table -->
		<div class="card-body">
			<div class="table-responsive">
				<table
					class="table table-striped table-bordered dt-responsive nowrap">
					<!-- Aluno -->
					<tr>
						<th width="30%">Aluno</th>
						<td><a
							href="<c:url value="/aluno/exibe?id=${atendimento_pedagogia.aluno.id}" />"
							style="font-weight: bold; color: blue;">${atendimento_pedagogia.aluno.nome}</a></td>
					</tr>
					<tr>
						<th>Curso</th>
						<td><a
							href="<c:url value="/curso/exibe?id=${atendimento_pedagogia.aluno.turma.curso.id}" />">${atendimento_pedagogia.aluno.turma.curso.nome}</a></td>
					</tr>
					<tr>
						<th>Turma</th>
						<td><a
							href="<c:url value="/turma/exibe?id=${atendimento_pedagogia.aluno.turma.id}" />">${atendimento_pedagogia.aluno.turma.nome}</a></td>
					</tr>
					<tr>
						<th>Responsável</th>
						<td>${atendimento_pedagogia.responsavel}</td>
					</tr>
					<tr>
						<th>Servidor</th>
						<td><a
							href="<c:url value="/profissional/exibe?id=${atendimento_pedagogia.profissional.id}" />">${atendimento_pedagogia.profissional.nome}</a></td>
					</tr>
					<tr>
						<th>Data do atendimento</th>
						<td><fmt:formatDate value="${atendimento_pedagogia.data}" /></td>
					</tr>
					<tr>
						<th>Horário</th>
						<td><fmt:formatDate type="time"
								value="${atendimento_pedagogia.horario_inicial}" pattern="HH:mm" />
							- <fmt:formatDate type="time"
								value="${atendimento_pedagogia.horario_final}" pattern="HH:mm" /></td>
					</tr>
					<security:authorize
						access="hasAnyRole('ROLE_Docente', 'ROLE_Monitor')">
						<tr>
							<th>Relato do atendimento</th>
							<td><i>Informação ocultada</i></td>
						</tr>
						<tr>
							<th>Encaminhamento</th>
							<td><i>Informação ocultada</i></td>
						</tr>
					</security:authorize>

					<security:authorize
						access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Psicologia', 'ROLE_Assistência Social', 'ROLE_Enfermagem', 'ROLE_Pedagogia', 'ROLE_Odontologia', 'ROLE_Coordenação de Disciplina')">
						<tr>
							<th>Relato do atendimento</th>
							<td>${atendimento_pedagogia.relato}</td>
						</tr>
						<tr>
							<th>Encaminhamento</th>
							<td>${atendimento_pedagogia.encaminhamento}</td>
						</tr>
					</security:authorize>

				</table>
			</div>
		</div>
	</div>
	<security:authorize access="hasRole('ROLE_Pedagogia')">
		<div align="center">
			<!-- Cadastrar -->
			<a href="<c:url value="/atendimento/pedagogia/familia/novo" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
			<!-- Editar -->
			<a
				href="<c:url value="/atendimento/pedagogia/familia/edita?id=${atendimento_pedagogia.id}" />"
				class="btn btn-warning btn-lg"><span
				class="glyphicon glyphicon-edit"></span> Editar </a>
			<!-- Excluir -->
			<button type="button" class="btn btn-danger btn-lg"
				data-toggle="modal" data-target="#modal${atendimento_pedagogia.id}">
				<span class="glyphicon glyphicon-trash"></span> Excluir
			</button>
		</div>
		<div class="modal fade" id="modal${atendimento_pedagogia.id}">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Exclusão do Atendimento da Pedagogia</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<p>
							Deseja realmente excluir o Atendimento da Pedagogia de <br>ID
							(${atendimento_pedagogia.id}) - Aluno:
							${atendimento_pedagogia.aluno.nome}?
						</p>
					</div>
					<div class="modal-footer">
						<a
							href="<c:url value="/atendimento/pedagogia/familia/remove?id=${atendimento_pedagogia.id}" />"
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
		href="<c:url value="/atendimento/pedagogia/familia/lista" />"><span
		class="glyphicon glyphicon-chevron-left"></span> Voltar</a>

</div>

<c:import url="../componentes/rodape.jsp" />