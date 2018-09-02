<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Atendimento Extraclasse</title>
<c:import url="../componentes/cabecalho.jsp" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/jquery-ui.min.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/jquery-ui-timepicker-addon.min.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/select/bootstrap-select.min.css" />">
<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">Editar Atendimento Extraclasse</h1>
		<p class="lead">Preencha o formulário abaixo com os dados do
			atendimento extraclasse para realizar a alteração no sistema.</p>
	</div>
</div>
<div class="container">
	<form action="altera" method="POST">

		<!-- ID -->
		<input type="hidden" name="id" value="${extra_classe.id}" required>

		<!-- ALUNO -->
		<div class="form-group">
			<label for="aluno.id" class="col-form-label">Aluno<span
				class="obrigatorio">*</span></label> <select name="aluno.id"
				class="selectpicker show-tick form-control" data-live-search="true"
				multiple data-max-options="1" title="Selecione um aluno"
				data-live-search-placeholder="Pesquisar" required
				onchange="alteraAluno('edita')">
				<c:forEach var="aluno" items="${alunos}">
					<option value="${aluno.id}"
						${extra_classe.aluno.id == aluno.id ? 'selected' : ''}>${aluno.nome}</option>
				</c:forEach>
			</select>
		</div>

		<!-- DISCIPLINA -->
		<div class="form-group" id="lista_disciplinas">
			<jsp:include page="import_edita/disciplina.jsp"></jsp:include>
		</div>

		<!-- DOCENTE -->
		<div class="form-group" id="lista_docentes">
			<jsp:include page="import_edita/docente.jsp"></jsp:include>
		</div>

		<div class="row">

			<!-- Data -->
			<div class="form-group col-6">
				<label for="data" class="col-form-label">Data do atendimento<span
					class="obrigatorio">*</span>
				</label> <input type="text" class="form-control maskData" name="data"
					required value="<fmt:formatDate value='${extra_classe.data}' />">
			</div>

			<!-- Horário -->
			<div class="form-group col-6">
				<label for="horario" class="col-form-label">Horário<span
					class="obrigatorio">*</span>
				</label> <input type="text" class="form-control maskHorario" name="horario"
					required
					value="<fmt:formatDate type="time" value='${extra_classe.horario}' />">
			</div>
		</div>

		<!-- LOCAL -->
		<div class="form-group">
			<label for="local" class="col-form-label">Local<span
				class="obrigatorio">*</span></label> <input type="text" class="form-control"
				name="local" MAXLENGTH="255" required value="${extra_classe.local}">
		</div>

		<!-- CONTEUDO -->
		<div class="form-group">
			<label for="conteudo">Conteúdo<span class="obrigatorio">*</span></label>
			<textarea class="form-control" name="conteudo" rows="3" required
				maxlength="3000">${extra_classe.conteudo}</textarea>
		</div>

		<security:csrfInput />

		<!-- OBTIGATÓRIO -->
		<label>(*) Campos obrigatórios</label>
		<div>
			<a href="<c:url value="/atendimento/extra-classe/lista" />"
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
	src="<c:url value="/resources/js/jquery-ui.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.mask.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-ui-timepicker-addon.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/select/bootstrap-select.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/select/defaults-pt_BR.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/extra_classe/extra_classe.js" />"></script>

<c:import url="../componentes/rodape.jsp" />