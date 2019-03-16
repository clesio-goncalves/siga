<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<title>Listar Perfis</title>
<c:import url="../componentes/css_data_table.jsp" />
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="alert alert-dismissible alert-warning">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		<h4 class="alert-heading">Atenção!</h4>
		<p class="mb-0">Não é permitido alterar ou excluir o perfil,
			devido as restrições das rergas de negócio da aplicação.</p>
	</div>
	<div class="card border-light mb-3">
		<div class="card-header">Listagem de Perfis</div>
		<!-- Table -->
		<div class="card-body">
			<table id="tabela_id"
				class="table table-striped table-bordered dt-responsive nowrap"
				style="width: 100%">
				<thead>
					<tr>
						<th>ID</th>
						<th>Nome</th>
						<th>Qnt usuários ativos</th>
						<th>Ações</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="perfil" items="${perfis}">
						<tr>
							<td>${perfil.id}</td>
							<td>${perfil.nome}</td>
							<td>${perfil.qnt_usuarios_ativos}</td>
							<td>
								<!-- Exibir --> <a
								href="<c:url value="/perfil/exibe?id=${perfil.id}"/>"
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
	<div align="center">
		<!-- PDF -->
		<a href="relatorio" class="btn btn-danger btn-lg"><span
			class="glyphicon glyphicon-file"></span> Relatório PDF</a>
	</div>
</div>

<c:import url="../componentes/js_data_table.jsp" />
<c:import url="../componentes/rodape.jsp" />