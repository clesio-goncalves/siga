<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>Exibe os dados do aluno</title>
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="card border-light mb-3">
		<div class="card-header">Exibe os dados do aluno</div>
		<!-- Table -->
		<div class="card-body">
			<legend style="margin-top: 0;">INFORMAÇÕES DO ALUNO</legend>
			<div class="table-responsive">
				<table
					class="table table-striped table-bordered dt-responsive nowrap">
					<tr>
						<th width="30%">ID</th>
						<td>${aluno.id}</td>
					</tr>

					<tr>
						<th>Nome Completo</th>
						<td style="font-weight: bold; color: blue;">${aluno.nome}</td>
					</tr>

					<tr>
						<th>Matrícula</th>

						<!-- Matricula -->
						<c:if test="${aluno.matricula eq \"\"}">
							<td>-</td>
						</c:if>
						<c:if test="${aluno.matricula ne \"\"}">
							<td>${aluno.matricula}</td>
						</c:if>
					</tr>

					<tr>
						<th>Telefone</th>

						<!-- Telefone -->
						<c:if test="${aluno.telefone eq \"\"}">
							<td>-</td>
						</c:if>
						<c:if test="${aluno.telefone ne \"\"}">
							<td>${aluno.telefone}</td>
						</c:if>
					</tr>

					<tr>
						<th>Curso</th>
						<td><a
							href="<c:url value="/curso/exibe?id=${aluno.turma.curso.id}" />">${aluno.turma.curso.nome}</a></td>
					</tr>

					<tr>
						<th>Turma</th>
						<td><a
							href="<c:url value="/turma/exibe?id=${aluno.turma.id}" />">${aluno.turma.nome}</a></td>
					</tr>
					<tr>
						<th>Situação atual</th>
						<td style="font-weight: bold;"><a
							href="<c:url value="/situacao/exibe?id=${aluno.situacao.id}" />">${aluno.situacao.nome}</a></td>
					</tr>
					<tr>
						<th>Benefício assistencial</th>
						<c:if test="${aluno.beneficio == null}">
							<td>Nenhum</td>
						</c:if>
						<c:if test="${aluno.beneficio != null}">
							<td><a
								href="<c:url value="/beneficio/exibe?id=${aluno.beneficio.id}" />">${aluno.beneficio.nome}</a></td>
						</c:if>
					</tr>
					<tr>
						<th>Usuário</th>

						<!-- Usuário -->
						<c:if test="${aluno.usuario == null}">
							<td>-</td>
						</c:if>
						<c:if test="${aluno.usuario != null}">
							<td><a
								href="<c:url value="/usuario/exibe?id=${aluno.usuario.id}" />">${aluno.usuario.email}</a></td>
						</c:if>
					</tr>
				</table>
			</div>
			<security:authentication property="principal" var="usuario_logado" />
			<c:if
				test="${usuario_logado.perfil.id != 11 or usuario_logado.id == aluno.usuario.id}">
				<legend>ATENDIMENTO DE MONITORIA</legend>
				<div class="table-responsive">
					<table
						class="table table-hover table-bordered dt-responsive nowrap"
						style="width: 100%; margin-top: 10px;">
						<thead>
							<tr>
								<th>Data</th>
								<th>Horário</th>
								<th>Disciplina</th>
								<th>Monitor</th>
								<th>Ações</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="atendimento_monitoria"
								items="${atendimentos_monitoria}">
								<tr>
									<td><fmt:formatDate value="${atendimento_monitoria.data}" /></td>
									<td><fmt:formatDate type="time"
											value="${atendimento_monitoria.horario_inicial}"
											pattern="HH:mm" /> - <fmt:formatDate type="time"
											value="${atendimento_monitoria.horario_final}"
											pattern="HH:mm" /></td>
									<td>${atendimento_monitoria.disciplina.nome}</td>
									<td>${atendimento_monitoria.disciplina.monitor.nome}</td>
									<td>
										<!-- Exibir --> <a
										href="<c:url value="/atendimento/monitoria/exibe?id=${atendimento_monitoria.id}"/>"
										class="btn btn-info btn-sm" data-tooltip="tooltip"
										data-placement="bottom" title="Exibir"> <span
											class="glyphicon glyphicon-search"></span></a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
						<tr style="background-color: #fff; font-weight: bold;">
							<td colspan="5" align="center">Total de Atendimentos:
								${fn:length(atendimentos_monitoria)}</td>
						</tr>
					</table>
				</div>
				<security:authorize
					access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Psicologia', 'ROLE_Assistência Social', 'ROLE_Enfermagem', 'ROLE_Pedagogia', 'ROLE_Odontologia', 'ROLE_Docente', 'ROLE_Aluno', 'ROLE_Coordenação de Disciplina')">
					<legend>ATENDIMENTO EXTRACLASSE</legend>
					<div class="table-responsive">
						<table
							class="table table-hover table-bordered dt-responsive nowrap"
							style="width: 100%; margin-top: 10px;">
							<thead>
								<tr>
									<th>Data</th>
									<th>Horário</th>
									<th>Disciplina</th>
									<th>Docente</th>
									<th>Ações</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="atendimento_extraclasse"
									items="${atendimentos_extraclasse}">
									<tr>
										<td><fmt:formatDate
												value="${atendimento_extraclasse.data}" /></td>
										<td><fmt:formatDate type="time"
												value="${atendimento_extraclasse.horario_inicial}"
												pattern="HH:mm" /> - <fmt:formatDate type="time"
												value="${atendimento_extraclasse.horario_final}"
												pattern="HH:mm" /></td>
										<td>${atendimento_extraclasse.disciplina.nome}</td>
										<td>${atendimento_extraclasse.docente.nome}</td>
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
							<tr style="background-color: #fff; font-weight: bold;">
								<td colspan="5" align="center">Total de Atendimentos:
									${fn:length(atendimentos_extraclasse)}</td>
							</tr>
						</table>
					</div>
					<legend>ATENDIMENTO DE SERVIÇOS DE SAÚDE</legend>
					<div class="table-responsive">
						<table
							class="table table-hover table-bordered dt-responsive nowrap"
							style="width: 100%; margin-top: 10px;">
							<thead>
								<tr>
									<th>Data</th>
									<th>Horário</th>
									<th>Tipo de Atendimento</th>
									<th>Profissional</th>
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
										<td>${atendimento_saude.profissional.tipo_atendimento}</td>
										<td>${atendimento_saude.profissional.nome}</td>
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
							<tr style="background-color: #fff; font-weight: bold;">
								<td colspan="6" align="center">Total de Atendimentos:
									${fn:length(atendimentos_saude)}</td>
							</tr>
						</table>
					</div>
					<legend>HISTÓRICO DE INDISCIPLINA</legend>
					<div class="table-responsive">
						<table
							class="table table-hover table-bordered dt-responsive nowrap"
							style="width: 100%; margin-top: 10px;">
							<thead>
								<tr>
									<th>Data</th>
									<th>Horário</th>
									<th>Advertido</th>
									<th>Tipo advertência</th>
									<th>Ações</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="atendimento_indisciplina"
									items="${atendimentos_indisciplina}">
									<tr>
										<td><fmt:formatDate
												value="${atendimento_indisciplina.data}" /></td>
										<td><fmt:formatDate type="time"
												value="${atendimento_indisciplina.horario}" pattern="HH:mm" />
										</td>
										<c:if test="${atendimento_indisciplina.advertido eq 'Sim'}">
											<td style="font-weight: bold; color: red;">${atendimento_indisciplina.advertido}</td>
											<td>${atendimento_indisciplina.tipo_advertencia}</td>
										</c:if>
										<c:if test="${atendimento_indisciplina.advertido eq 'Não'}">
											<td>${atendimento_indisciplina.advertido}</td>
											<td>-</td>
										</c:if>
										<td>
											<!-- Exibir --> <a
											href="<c:url value="/atendimento/indisciplina/exibe?id=${atendimento_indisciplina.id}"/>"
											class="btn btn-info btn-sm" data-tooltip="tooltip"
											data-placement="bottom" title="Exibir"> <span
												class="glyphicon glyphicon-search"></span></a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
							<tr style="background-color: #fff; font-weight: bold;">
								<td colspan="5" align="center">Total de Atendimentos:
									${fn:length(atendimentos_indisciplina)}</td>
							</tr>
						</table>
					</div>
					<legend>ATENDIMENTO DE PEDAGOGIA AO ALUNO</legend>
					<div class="table-responsive">
						<table
							class="table table-hover table-bordered dt-responsive nowrap"
							style="width: 100%; margin-top: 10px;">
							<thead>
								<tr>
									<th>Data</th>
									<th>Horário</th>
									<th>Profissional</th>
									<th>Ações</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="atendimento_pedagogia"
									items="${atendimentos_pedagogia_aluno}">
									<tr>
										<td><fmt:formatDate value="${atendimento_pedagogia.data}" /></td>
										<td><fmt:formatDate type="time"
												value="${atendimento_pedagogia.horario_inicial}"
												pattern="HH:mm" /> - <fmt:formatDate type="time"
												value="${atendimento_pedagogia.horario_final}"
												pattern="HH:mm" /></td>
										<td>${atendimento_pedagogia.profissional.nome}</td>
										<td>
											<!-- Exibir --> <a
											href="<c:url value="/atendimento/pedagogia/aluno/exibe?id=${atendimento_pedagogia.id}"/>"
											class="btn btn-info btn-sm" data-tooltip="tooltip"
											data-placement="bottom" title="Exibir"> <span
												class="glyphicon glyphicon-search"></span></a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
							<tr style="background-color: #fff; font-weight: bold;">
								<td colspan="4" align="center">Total de Atendimentos:
									${fn:length(atendimentos_pedagogia_aluno)}</td>
							</tr>
						</table>
					</div>
					<legend>ATENDIMENTO DE PEDAGOGIA A FAMÍLIA</legend>
					<div class="table-responsive">
						<table
							class="table table-hover table-bordered dt-responsive nowrap"
							style="width: 100%; margin-top: 10px;">
							<thead>
								<tr>
									<th>Data</th>
									<th>Horário</th>
									<th>Responsável</th>
									<th>Profissional</th>
									<th>Ações</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="atendimento_pedagogia"
									items="${atendimentos_pedagogia_familia}">
									<tr>
										<td><fmt:formatDate value="${atendimento_pedagogia.data}" /></td>
										<td><fmt:formatDate type="time"
												value="${atendimento_pedagogia.horario_inicial}"
												pattern="HH:mm" /> - <fmt:formatDate type="time"
												value="${atendimento_pedagogia.horario_final}"
												pattern="HH:mm" /></td>
										<td>${atendimento_pedagogia.responsavel}</td>
										<td>${atendimento_pedagogia.profissional.nome}</td>
										<td>
											<!-- Exibir --> <a
											href="<c:url value="/atendimento/pedagogia/familia/exibe?id=${atendimento_pedagogia.id}"/>"
											class="btn btn-info btn-sm" data-tooltip="tooltip"
											data-placement="bottom" title="Exibir"> <span
												class="glyphicon glyphicon-search"></span></a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
							<tr style="background-color: #fff; font-weight: bold;">
								<td colspan="5" align="center">Total de Atendimentos:
									${fn:length(atendimentos_pedagogia_familia)}</td>
							</tr>
						</table>
					</div>
				</security:authorize>
			</c:if>
		</div>
	</div>
	<security:authorize
		access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Psicologia', 'ROLE_Assistência Social', 'ROLE_Enfermagem', 'ROLE_Pedagogia', 'ROLE_Odontologia', 'ROLE_Docente', 'ROLE_Monitor', 'ROLE_Coordenação de Disciplina')">
		<div align="center">
			<!-- Cadastrar -->
			<a href="<c:url value="/aluno/novo" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
			<!-- Editar -->
			<a href="<c:url value="/aluno/edita?id=${aluno.id}" />"
				class="btn btn-warning btn-lg"><span
				class="glyphicon glyphicon-edit"></span> Editar </a>
			<!-- Excluir -->
			<button type="button" class="btn btn-danger btn-lg"
				data-toggle="modal" data-target="#modal${aluno.id}">
				<span class="glyphicon glyphicon-trash"></span> Excluir
			</button>
		</div>
		<div class="modal fade" id="modal${aluno.id}">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Exclusão do aluno</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<p>
							Deseja realmente excluir o aluno <br>ID (${aluno.id}) ->
							${aluno.nome}?
						</p>
						<p>
							<b>Atenção: Não é recomendado excluir o usuário, <br>
								Então, desative-o.<b>
						</p>
					</div>
					<div class="modal-footer">
						<a href="<c:url value="/aluno/remove?id=${aluno.id}" />"
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

	<a class="btn btn-success" href="<c:url value="/aluno/lista" />"><span
		class="glyphicon glyphicon-chevron-left"></span> Voltar</a>

</div>

<c:import url="../componentes/rodape.jsp" />