<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Exibe os dados do atendimento de saúde</title>
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="card border-light mb-3">
		<div class="card-header">Exibe os dados do atendimento de saúde</div>
		<!-- Table -->
		<div class="card-body">
			<div class="table-responsive">
				<table
					class="table table-striped table-bordered dt-responsive nowrap">
					<!-- Aluno -->
					<tr>
						<th width="30%">Aluno</th>
						<td><a
							href="<c:url value="/aluno/exibe?id=${atendimento_saude.aluno.id}" />"
							style="font-weight: bold; color: blue;">${atendimento_saude.aluno.nome}</a></td>
					</tr>
					<tr>
						<th>Curso</th>
						<td><a
							href="<c:url value="/curso/exibe?id=${atendimento_saude.aluno.turma.curso.id}" />">${atendimento_saude.aluno.turma.curso.nome}</a></td>
					</tr>
					<tr>
						<th>Turma</th>
						<td><a
							href="<c:url value="/turma/exibe?id=${atendimento_saude.aluno.turma.id}" />">${atendimento_saude.aluno.turma.nome}</a></td>
					</tr>
					<tr>
						<th>Tipo de Atendimento</th>
						<td style="font-weight: bold; color: blue;">${atendimento_saude.profissional.tipo_atendimento}</td>
					</tr>
					<tr>
						<th>Profissional Atendimento</th>
						<td><a
							href="<c:url value="/profissional/exibe?id=${atendimento_saude.profissional.id}" />">${atendimento_saude.profissional.nome}</a></td>
					</tr>
					<tr>
						<th>Data do atendimento</th>
						<td><fmt:formatDate value="${atendimento_saude.data}" /></td>
					</tr>
					<tr>
						<th>Horário</th>
						<td><fmt:formatDate type="time"
								value="${atendimento_saude.horario_inicial}" pattern="HH:mm" />
							- <fmt:formatDate type="time"
								value="${atendimento_saude.horario_final}" pattern="HH:mm" /></td>
					</tr>
					<tr>
						<th>Possui alguma observação?</th>
						<c:if test="${atendimento_saude.possui_problema eq 'Sim'}">
							<td style="font-weight: bold; color: red;">${atendimento_saude.possui_problema}</td>
						</c:if>
						<c:if test="${atendimento_saude.possui_problema eq 'Não'}">
							<td>${atendimento_saude.possui_problema}</td>
						</c:if>
					</tr>
					<tr>
						<th>Essa observação dificulta o aprendizado?</th>
						<c:if
							test="${atendimento_saude.esse_problema_dificulta_aprendizado == null}">
							<td>-</td>
						</c:if>
						<c:if
							test="${atendimento_saude.esse_problema_dificulta_aprendizado != null}">
							<c:if
								test="${atendimento_saude.esse_problema_dificulta_aprendizado eq 'Sim'}">
								<td style="font-weight: bold; color: red;">${atendimento_saude.esse_problema_dificulta_aprendizado}</td>
							</c:if>
							<c:if
								test="${atendimento_saude.esse_problema_dificulta_aprendizado eq 'Não'}">
								<td>${atendimento_saude.esse_problema_dificulta_aprendizado}</td>
							</c:if>
						</c:if>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<security:authorize
		access="hasAnyRole('ROLE_Psicologia', 'ROLE_Assistência Social', 'ROLE_Enfermagem', 'ROLE_Odontologia')">
		<div align="center">
			<!-- Cadastrar -->
			<a href="<c:url value="/atendimento/saude/novo" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
			<!-- Editar -->
			<a
				href="<c:url value="/atendimento/saude/edita?id=${atendimento_saude.id}" />"
				class="btn btn-warning btn-lg"><span
				class="glyphicon glyphicon-edit"></span> Editar </a>
			<!-- Excluir -->
			<button type="button" class="btn btn-danger btn-lg"
				data-toggle="modal" data-target="#modal${atendimento_saude.id}">
				<span class="glyphicon glyphicon-trash"></span> Excluir
			</button>
		</div>
		<div class="modal fade" id="modal${atendimento_saude.id}">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Exclusão do Atendimento de Saúde</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<p>
							Deseja realmente excluir o Atendimento de Saúde <br>ID
							(${atendimento_saude.id}) - Aluno:
							${atendimento_saude.aluno.nome}?
						</p>
					</div>
					<div class="modal-footer">
						<a
							href="<c:url value="/atendimento/saude/remove?id=${atendimento_saude.id}" />"
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
		href="<c:url value="/atendimento/saude/lista" />"><span
		class="glyphicon glyphicon-chevron-left"></span> Voltar</a>
</div>

<c:import url="../componentes/rodape.jsp" />