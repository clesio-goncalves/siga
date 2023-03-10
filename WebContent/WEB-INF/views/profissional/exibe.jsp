<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
						<th>Usu?rio</th>
						<td><a
							href="<c:url value="/usuario/exibe?id=${profissional.usuario.id}" />">${profissional.usuario.email}</a></td>
					</tr>
				</table>
			</div>
			<security:authorize
				access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Psicologia', 'ROLE_Assist?ncia Social', 'ROLE_Enfermagem', 'ROLE_Pedagogia', 'ROLE_Odontologia', 'ROLE_Docente', 'ROLE_Coordena??o de Disciplina')">
				<c:if
					test="${profissional.tipo_atendimento eq 'Coordena??o de Disciplina'}">
					<legend>ATENDIMENTOS DE INDISCIPLINA</legend>
					<div class="table-responsive">
						<table
							class="table table-hover table-bordered dt-responsive nowrap"
							style="width: 100%; margin-top: 10px;">
							<thead>
								<tr>
									<th>Data</th>
									<th>Hor?rio</th>
									<th>Aluno</th>
									<th>Advertido</th>
									<th>Tipo advert?ncia</th>
									<th>A??es</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="atendimento_indisciplina"
									items="${atendimentos_indisciplina}">
									<tr>
										<td><fmt:formatDate
												value="${atendimento_indisciplina.data}" /></td>
										<td><fmt:formatDate type="time"
												value="${atendimento_indisciplina.horario}" pattern="HH:mm" /></td>
										<td>${atendimento_indisciplina.aluno.nome}</td>
										<c:if test="${atendimento_indisciplina.advertido eq 'Sim'}">
											<td style="font-weight: bold; color: red;">${atendimento_indisciplina.advertido}</td>
											<td>${atendimento_indisciplina.tipo_advertencia}</td>
										</c:if>
										<c:if test="${atendimento_indisciplina.advertido eq 'N?o'}">
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
								<td colspan="6" align="center">Total de Atendimentos:
									${fn:length(atendimentos_indisciplina)}</td>
							</tr>
						</table>
					</div>
				</c:if>
				<c:if
					test="${profissional.tipo_atendimento eq 'Psicologia' or profissional.tipo_atendimento eq 'Assist?ncia Social' or profissional.tipo_atendimento eq 'Enfermagem' or profissional.tipo_atendimento eq 'Odontologia'}">
					<legend>ATENDIMENTO DE SERVI?OS DE SA?DE</legend>
					<div class="table-responsive">
						<table
							class="table table-hover table-bordered dt-responsive nowrap"
							style="width: 100%; margin-top: 10px;">
							<thead>
								<tr>
									<th>Data</th>
									<th>Hor?rio</th>
									<th>Aluno</th>
									<th>Possui Observa??o</th>
									<th>A??es</th>
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
							<tr style="background-color: #fff; font-weight: bold;">
								<td colspan="5" align="center">Total de Atendimentos:
									${fn:length(atendimentos_saude)}</td>
							</tr>
						</table>
					</div>
				</c:if>
				<c:if test="${profissional.tipo_atendimento eq 'Pedagogia'}">
					<legend>ATENDIMENTOS DE PEDAGOGIA AO ALUNO</legend>
					<div class="table-responsive">
						<table
							class="table table-hover table-bordered dt-responsive nowrap"
							style="width: 100%; margin-top: 10px;">
							<thead>
								<tr>
									<th>Data</th>
									<th>Hor?rio</th>
									<th>Aluno</th>
									<th>A??es</th>
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
										<td>${atendimento_pedagogia.aluno.nome}</td>
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
					<legend>ATENDIMENTOS DE PEDAGOGIA A FAM?LIA</legend>
					<div class="table-responsive">
						<table
							class="table table-hover table-bordered dt-responsive nowrap"
							style="width: 100%; margin-top: 10px;">
							<thead>
								<tr>
									<th>Data</th>
									<th>Hor?rio</th>
									<th>Aluno</th>
									<th>Respons?vel</th>
									<th>A??es</th>
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
										<td>${atendimento_pedagogia.aluno.nome}</td>
										<td>${atendimento_pedagogia.responsavel}</td>
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
						<h5 class="modal-title">Exclus?o do Profissional</h5>
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
							<b>Aten??o: N?o ? recomendado excluir o usu?rio, <br>
								Ent?o, desative-o.<b>
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