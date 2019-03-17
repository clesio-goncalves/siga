<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Editar Aluno</title>
<c:import url="../componentes/cabecalho.jsp" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/select/bootstrap-select.min.css" />">
<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">Editar Aluno</h1>
		<p class="lead">Preencha o formulário abaixo para realizar a
			alteração do aluno no sistema. Não é permitida a alteração do campo
			Usuário (anteriormente vinculado), com o objetivo de mater a
			consistência dos dados.</p>
	</div>
</div>
<div class="container">
	<form action="altera" method="POST">

		<!-- ID -->
		<input type="hidden" name="id" value="${aluno.id}" required />

		<!-- NOME -->
		<div class="form-group">
			<label for="nome" class="col-form-label">Nome Completo<span
				class="obrigatorio">*</span></label> <input type="text" class="form-control"
				name="nome" autofocus MAXLENGTH="255" required value="${aluno.nome}">
		</div>

		<div class="row">
			<!-- CURSO-->
			<div class="form-group col-md-6">
				<label for="curso.id" class="col-form-label">Curso<span
					class="obrigatorio">*</span></label> <select name="curso.id" id="curso"
					class="selectpicker show-tick form-control" data-live-search="true"
					multiple data-max-options="1" title="Selecione um curso"
					data-live-search-placeholder="Pesquisar" required
					onchange="alteraCurso('edita')">
					<c:forEach var="curso" items="${cursos}">
						<option value="${curso.id}"
							${aluno.turma.curso.id == curso.id ? 'selected' : ''}>${curso.nome}</option>
					</c:forEach>
				</select>
			</div>

			<!-- TURMA-->
			<div class="form-group col-md-6" id="lista_turmas">
				<jsp:include page="import_edita/turma.jsp"></jsp:include>
			</div>
		</div>

		<div class="row">
			<!-- MATRICULA -->
			<div class="form-group col-6">
				<label for="matricula" class="col-form-label">Matricula</label> <input
					type="text" class="form-control" name="matricula" MAXLENGTH="50"
					value="${aluno.matricula}">
			</div>

			<!-- TELEFONE -->
			<div class="form-group col-6">
				<label for="telefone" class="col-form-label">Telefone</label>
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text"><span
							class="glyphicon glyphicon-earphone"></span></span>
					</div>
					<input type="text" class="form-control maskTelefone"
						name="telefone" MAXLENGTH="20"
						pattern="\([0-9]{2}\)[\s][0-9]{4}-[0-9]{4,5}"
						value="${aluno.telefone}">
				</div>
			</div>
		</div>

		<div class="row">
			<!-- USUÁRIO-->
			<div class="form-group col-6">
				<label for="usuario.id" class="col-form-label">Usuário</label>
				<c:if test="${aluno.usuario != null}">
					<input type="hidden" name="usuario.id" value="${aluno.usuario.id}" />
					<div class="input-group mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text">@</span>
						</div>
						<select class="custom-select" name="usuario.id"
							disabled="disabled">
							<option value="" selected>${aluno.usuario.email}</option>
						</select>
					</div>
				</c:if>
				<c:if test="${aluno.usuario == null}">
					<div class="input-group mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text">@</span>
						</div>
						<select class="custom-select" name="usuario.id">
							<option value="">Não informar</option>
							<c:forEach var="usuario" items="${usuarios}">
								<option value="${usuario.id}">${usuario.email}</option>
							</c:forEach>
						</select>
					</div>
				</c:if>
			</div>
			<!-- SITUAÇÃO-->
			<div class="form-group col-6">
				<label for="situacao.id" class="col-form-label">Situação
					atual<span class="obrigatorio">*</span>
				</label> <select class="custom-select" name="situacao.id"
					required="required">
					<c:forEach var="forsituacao" items="${situacoes}">
						<option value="${forsituacao.id}"
							${aluno.situacao.id == forsituacao.id ? 'selected' : '' }>${forsituacao.nome}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<security:csrfInput />

		<!-- OBTIGATÓRIO -->
		<label>(*) Campos obrigatórios</label>

		<div>
			<a href="<c:url value="/aluno/lista" />"
				class="btn btn-secondary btn-lg"> <span
				class="glyphicon glyphicon-remove"></span> Cancelar
			</a>
			<button type="submit" class="btn btn-primary btn-lg">
				<span class="glyphicon glyphicon-refresh"></span> Atualizar
			</button>
		</div>
	</form>
</div>

<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.mask.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/select/bootstrap-select.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/select/defaults-pt_BR.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/filtro_cadastro/filtroCadAluno.js" />"></script>
<c:import url="../componentes/rodape.jsp" />