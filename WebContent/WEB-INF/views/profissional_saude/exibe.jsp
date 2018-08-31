<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
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
						<td>${profissional_saude.id}</td>
					</tr>

					<tr>
						<th>Nome Completo</th>
						<td>${profissional_saude.nome}</td>
					</tr>

					<tr>
						<th>SIAPE</th>
						<td>${profissional_saude.siape}</td>
					</tr>

					<tr>
						<th>Tipo Profissional</th>
						<td>${profissional_saude.tipo_profissional}</td>
					</tr>

					<tr>
						<th>Usuário</th>
						<td>${profissional_saude.usuario.email}</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<security:authorize access="hasRole('ROLE_Administrador')">
		<div align="center">
			<!-- Cadastrar -->
			<a href="<c:url value="/profissional/novo" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
			<!-- Editar -->
			<a
				href="<c:url value="/profissional/edita?id=${profissional_saude.id}" />"
				class="btn btn-warning btn-lg"><span
				class="glyphicon glyphicon-edit"></span> Editar </a>
			<!-- Excluir -->
			<button type="button" class="btn btn-danger btn-lg"
				data-toggle="modal" data-target="#modal${profissional_saude.id}">
				<span class="glyphicon glyphicon-trash"></span> Excluir
			</button>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="modal${profissional_saude.id}">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Exclusão do Profissional da Saúde</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<p>Deseja realmente excluir o Profissional da Saúde <br>ID
							(${profissional_saude.id}) -> ${profissional_saude.nome}?</p>
					</div>
					<div class="modal-footer">
						<a
							href="<c:url value="/profissional/remove?id=${profissional_saude.id}" />"
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