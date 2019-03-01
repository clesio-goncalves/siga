<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<title>Exibe os dados do servidor da administração</title>
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<div class="card border-light mb-3">
		<div class="card-header">Exibe os dados do servidor da
			administração</div>
		<!-- Table -->
		<div class="card-body">
			<div class="table-responsive">
				<table
					class="table table-striped table-bordered dt-responsive nowrap">
					<tr>
						<th width="30%">ID</th>
						<td>${administracao.id}</td>
					</tr>
					<tr>
						<th>Nome Completo</th>
						<td style="font-weight: bold; color: blue;">${administracao.nome}</td>
					</tr>
					<tr>
						<th>SIAPE</th>
						<td>${administracao.siape}</td>
					</tr>
					<tr>
						<th>Função</th>
						<td>${administracao.funcao}</td>
					</tr>
					<tr>
						<th>Descrição Função</th>
						<td>${administracao.descricao_funcao}</td>
					</tr>
					<tr>
						<th>Usuário</th>
						<td><a
							href="<c:url value="/usuario/exibe?id=${administracao.usuario.id}" />">${administracao.usuario.email}</a></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<security:authorize
		access="hasAnyRole('ROLE_Administrador', 'ROLE_Diretor')">
		<div align="center">
			<!-- Cadastrar -->
			<a href="<c:url value="/administracao/novo" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
			<security:authentication property="principal" var="usuario_logado" />
			<c:if
				test="${usuario_logado.perfil.id == 1 or (usuario_logado.perfil.id == 3 and administracao.funcao eq 'Coordenador')}">
				<!-- Editar -->
				<a
					href="<c:url value="/administracao/edita?id=${administracao.id}" />"
					class="btn btn-warning btn-lg"><span
					class="glyphicon glyphicon-edit"></span> Editar </a>
				<!-- Excluir -->
				<button type="button" class="btn btn-danger btn-lg"
					data-toggle="modal" data-target="#modal${administracao.id}">
					<span class="glyphicon glyphicon-trash"></span> Excluir
				</button>
			</c:if>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="modal${administracao.id}">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title">Exclusão do Servidor</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<p>
							Deseja realmente excluir o Servidor <br>ID
							(${administracao.id}) -> ${administracao.nome}?
						</p>
						<p>
							<b>Atenção: Não é recomendado excluir o usuário, <br>
								Então, desative-o.<b>
						</p>
					</div>
					<div class="modal-footer">
						<a
							href="<c:url value="/administracao/remove?id=${administracao.id}" />"
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
		href="<c:url value="/administracao/lista" />"><span
		class="glyphicon glyphicon-chevron-left"></span> Voltar</a>
</div>

<c:import url="../componentes/rodape.jsp" />