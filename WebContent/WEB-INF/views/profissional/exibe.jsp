<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Exibe os dados do profissional</title>
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="card border-light mb-3">
		<div class="card-header">Exibe os dados do profissional</div>
		<!-- Table -->
		<div class="card-body">
			<div class="table-responsive">
				<table
					class="table table-striped table-bordered dt-responsive nowrap">
					<tr>
						<th width="30%">ID</th>
						<td>${profissional.id}</td>
					</tr>

					<tr>
						<th>Nome Completo</th>
						<td style="font-weight: bold; color: blue;">${profissional.nome}</td>
					</tr>

					<tr>
						<th>SIAPE</th>
						<td>${profissional.siape}</td>
					</tr>

					<tr>
						<th>Tipo de Atendimento</th>
						<td>${profissional.tipo_atendimento}</td>
					</tr>

					<tr>
						<th>Usuário</th>
						<td><a
							href="<c:url value="/usuario/exibe?id=${profissional.usuario.id}" />">${profissional.usuario.email}</a></td>
					</tr>
				</table>
			</div>
			<security:authorize
				access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Psicologia', 'ROLE_Assistência Social', 'ROLE_Enfermagem', 'ROLE_Pedagogia', 'ROLE_Odontologia', 'ROLE_Docente', 'ROLE_Monitor', 'ROLE_Coordenação de Disciplina')">
				<c:if
					test="${profissional.tipo_atendimento eq 'Psicologia' or profissional.tipo_atendimento eq 'Assistência Social' or profissional.tipo_atendimento eq 'Enfermagem' or profissional.tipo_atendimento eq 'Odontologia'}">
					<legend>ATENDIMENTO DE SERVIÇOS DE SAÚDE</legend>
					<div class="table-responsive">
						<table
							class="table table-hover table-bordered dt-responsive nowrap"
							style="width: 100%; margin-top: 10px;">
							<thead>
								<tr>
									<th>Data</th>
									<th>Horário</th>
									<th>Aluno</th>
									<th>Possui Observação</th>
									<th>Ações</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="atendimento_saude" items="${atendimentos_saude}">
									<tr>
										<td><fmt:formatDate value="${atendimento_saude.data}" /></td>
										<td><fmt:formatDate type="time"
												value="${atendimento_saude.horario_inicial}" pattern="HH:mm" />
											- <fmt:formatDate type="time"
												value="${atendimento_saude.horario_final}" pattern="HH:mm" /></td>
										<td>${atendimento_saude.aluno.nome}</td>
										<td>${atendimento_saude.possui_problema}</td>
										<td>
											<!-- Exibir --> <a
											href="<c:url value="/atendimento/saude/exibe?id=${atendimento_saude.id}"/>"
											class="btn btn-info btn-sm" data-tooltip="tooltip"
											data-placement="bottom" title="Exibir"> <span
												class="glyphicon glyphicon-search"></span></a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
			</security:authorize>
		</div>
	</div>
	<security:authorize
		access="hasAnyRole('ROLE_Administrador', 'ROLE_Diretor')">
		<div align="center">
			<!-- Cadastrar -->
			<a href="<c:url value="/profissional/novo" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
			<!-- Editar -->
			<a href="<c:url value="/profissional/edita?id=${profissional.id}" />"
				class="btn btn-warning btn-lg"><span
				class="glyphicon glyphicon-edit"></span> Editar </a>
			<!-- Excluir -->
			<button type="button" class="btn btn-danger btn-lg"
				data-toggle="modal" data-target="#modal${profissional.id}">
				<span class="glyphicon glyphicon-trash"></span> Excluir
			</button>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="modal${profissional.id}">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Exclusão do Profissional</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<p>
							Deseja realmente excluir o Profissional <br>ID
							(${profissional.id}) -> ${profissional.nome}?
						</p>
						<p>
							<b>Atenção: Não é recomendado excluir o usuário, <br>
								Então, desative-o.<b>
						</p>
					</div>
					<div class="modal-footer">
						<a
							href="<c:url value="/profissional/remove?id=${profissional.id}" />"
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
	<a class="btn btn-success" href="<c:url value="/profissional/lista" />"><span
		class="glyphicon glyphicon-chevron-left"></span> Voltar</a>
</div>

<c:import url="../componentes/rodape.jsp" />