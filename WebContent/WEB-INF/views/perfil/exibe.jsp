<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>Exibe os dados do perfil</title>
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="card border-light mb-3">
		<div class="card-header">Exibe os dados do perfil</div>
		<!-- Table -->
		<div class="card-body">
			<div class="table-responsive">
				<table
					class="table table-striped table-bordered dt-responsive nowrap">
					<tr>
						<th width="30%">ID</th>
						<td>${perfil.id}</td>
					</tr>
					<tr>
						<th>Nome</th>
						<td style="font-weight: bold; color: blue;">${perfil.nome}</td>
					</tr>
					<tr>
						<th>Qnt de usuários ativos</th>
						<td>${fn:length(usuarios)}</td>
					</tr>
				</table>
			</div>
			<legend>USUÁRIOS ATIVOS COM ESSE PERFIL</legend>
			<div class="table-responsive">
				<table class="table table-hover table-bordered dt-responsive nowrap"
					style="width: 100%; margin-top: 10px;">
					<thead>
						<tr>
							<th>ID</th>
							<th>E-mail</th>
							<th>Ações</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="usuario" items="${usuarios}">
							<tr>
								<td>${usuario.id}</td>
								<td>${usuario.email}</td>
								<td>
									<!-- Exibir --> <a
									href="<c:url value="/usuario/exibe?id=${usuario.id}"/>"
									class="btn btn-info btn-sm" data-tooltip="tooltip"
									data-placement="bottom" title="Exibir"> <span
										class="glyphicon glyphicon-search"></span></a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
					<tr style="background-color: #fff; font-weight: bold;">
						<td colspan="3" align="center">Total de Usuários:
							${fn:length(usuarios)}</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<a class="btn btn-success" href="<c:url value="/perfil/lista" />"><span
		class="glyphicon glyphicon-chevron-left"></span> Voltar</a>
</div>

<c:import url="../componentes/rodape.jsp" />