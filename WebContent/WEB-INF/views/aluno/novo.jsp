<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Cadastrar Aluno</title>
<c:import url="../componentes/cabecalho.jsp" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/select/bootstrap-select.min.css" />">
<div class="jumbotron">
	<div>
		<h1 class="display-3">Cadastrar Aluno</h1>
		<p class="lead">Preencha o formulário abaixo para realizar o
			cadastro do aluno no sistema.</p>
	</div>
</div>
<div class="container">
	<form action="adiciona" method="POST">

		<security:authentication property="principal" var="usuario_logado" />

		<!-- NOME -->
		<div class="form-group">
			<label for="nome" class="col-form-label">Nome Completo<span
				class="obrigatorio">*</span></label> <input type="text" class="form-control"
				name="nome" autofocus MAXLENGTH="255" required>
		</div>

		<div class="row">
			<!-- CURSO-->
			<div class="form-group col-md-6">
				<label for="curso.id" class="col-form-label">Curso<span
					class="obrigatorio">*</span></label> <select name="curso.id" id="curso"
					class="selectpicker show-tick form-control" data-live-search="true"
					multiple data-max-options="1" title="Selecione um curso"
					data-live-search-placeholder="Pesquisar" required
					onchange="alteraCurso('novo')">
					<c:forEach var="curso" items="${cursos}">
						<option value="${curso.id}">${curso.nome}</option>
					</c:forEach>
				</select>
			</div>

			<!-- TURMA-->
			<div class="form-group col-md-6" id="lista_turmas">
				<jsp:include page="import_novo/turma.jsp"></jsp:include>
			</div>
		</div>
		<div class="row">
			<!-- MATRICULA -->
			<div class="form-group col-4">
				<label for="matricula" class="col-form-label">Matricula<span
					class="obrigatorio">*</span></label> <input type="text"
					class="form-control" name="matricula" MAXLENGTH="50"
					required="required">
			</div>

			<!-- TELEFONE -->
			<div class="form-group col-4">
				<label for="telefone" class="col-form-label">Telefone</label>
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text"><span
							class="glyphicon glyphicon-earphone"></span></span>
					</div>
					<input type="text" class="form-control maskTelefone"
						name="telefone" MAXLENGTH="20"
						pattern="\([0-9]{2}\)[\s][0-9]{4}-[0-9]{4,5}">
				</div>
			</div>

			<!-- USUÁRIO-->
			<div class="form-group col-4">
				<label for="usuario.id" class="col-form-label">Usuário</label>
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text">@</span>
					</div>
					<select class="custom-select" name="usuario.id">
						<option value="">Não informar</option>
						<!-- percorre usuarios montando as linhas da tabela -->
						<c:forEach var="usuario" items="${usuarios}">
							<option value="${usuario.id}">${usuario.email}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>

		<div class="row">
			<!-- BENEFÍCIO -->
			<div class="form-group col-6">
				<label for="beneficio.id" class="col-form-label">Benefício
					Assistencial<span class="obrigatorio">**</span>
				</label>
				<c:if test="${usuario_logado.perfil.id != 5}">
					<select class="custom-select" name="beneficio.id"
						disabled="disabled">
						<option value="">Nenhum</option>
					</select>
					<input type="hidden" name="beneficio.id" value="">
				</c:if>
				<c:if test="${usuario_logado.perfil.id == 5}">
					<select class="custom-select" name="beneficio.id">
						<option value="">Nenhum</option>
						<c:forEach var="beneficio" items="${beneficios}">
							<option value="${beneficio.id}">${beneficio.nome}</option>
						</c:forEach>
					</select>
				</c:if>
			</div>

			<!-- SITUAÇÃO-->
			<div class="form-group col-6">
				<label for="situacao.id" class="col-form-label">Situação
					atual<span class="obrigatorio">*</span>
				</label> <select class="custom-select" name="situacao.id"
					required="required">
					<c:forEach var="situacao" items="${situacoes}">
						<option value="${situacao.id}">${situacao.nome}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<security:csrfInput />

		<!-- OBTIGATÓRIO -->
		<label>(*) Campos obrigatórios</label> <br> <label>(**)
			Política de Assistência Estudantil (POLAE) - Resolução 14/2014, do
			Conselho Superior do IFPI</label>
		<div>
			<button type="reset" class="btn btn-secondary btn-lg">
				<span class="glyphicon glyphicon-trash"></span> Limpar
			</button>
			<button type="submit" class="btn btn-primary btn-lg">
				<span class="glyphicon glyphicon-floppy-disk"></span> Salvar
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