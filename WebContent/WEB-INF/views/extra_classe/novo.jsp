<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
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
		<h1 class="display-3">Atendimento Extraclasse</h1>
		<p class="lead">Preencha o formulário abaixo com os dados do
			atendimento extraclasse no sistema.</p>
	</div>
</div>
<div class="container">
	<form action="adiciona" method="POST">

		<!-- ALUNO -->
		<div class="form-group">
			<label for="aluno.id" class="col-form-label">Aluno<span
				class="obrigatorio">*</span></label> <select name="aluno.id"
				class="selectpicker show-tick form-control" data-live-search="true"
				multiple data-max-options="1" title="Selecione um aluno"
				data-live-search-placeholder="Pesquisar" required
				autofocus="autofocus">
				<c:forEach var="aluno" items="${alunos}">
					<option value="${aluno.id}">${aluno.nome}</option>
				</c:forEach>
			</select>
		</div>

		<!-- DISCIPLINA -->
		<div class="form-group">
			<label for="disciplina.id" class="col-form-label">Disciplina<span
				class="obrigatorio">*</span></label> <select name="disciplina.id"
				class="selectpicker show-tick form-control" data-live-search="true"
				multiple data-max-options="1" title="Selecione uma disciplina"
				data-live-search-placeholder="Pesquisar" required>
				<c:forEach var="disciplina" items="${disciplinas}">
					<option value="${disciplina.id}">${disciplina.nome}</option>
				</c:forEach>
			</select>
		</div>

		<!-- DOCENTE -->
		<div class="form-group">
			<label for="docente.id" class="col-form-label">Docente<span
				class="obrigatorio">*</span></label> <select name="docente.id"
				class="selectpicker show-tick form-control" data-live-search="true"
				multiple data-max-options="1" title="Selecione um docente"
				data-live-search-placeholder="Pesquisar" required>
				<c:forEach var="docente" items="${docentes}">
					<option value="${docente.id}">${docente.nome}</option>
				</c:forEach>
			</select>
		</div>


		<div class="row">

			<!-- Data -->
			<div class="form-group col-6">
				<label for="data" class="col-form-label">Data do atendimento<span
					class="obrigatorio">*</span>
				</label> <input type="text" class="form-control maskData" name="data"
					required>
			</div>

			<!-- Horário -->
			<div class="form-group col-6">
				<label for="horario" class="col-form-label">Horário<span
					class="obrigatorio">*</span>
				</label> <input type="text" class="form-control maskHorario" name="horario"
					required>
			</div>
		</div>

		<!-- LOCAL -->
		<div class="form-group">
			<label for="local" class="col-form-label">Local<span
				class="obrigatorio">*</span></label> <input type="text" class="form-control"
				name="local" MAXLENGTH="255" required>
		</div>

		<!-- CONTEUDO -->
		<div class="form-group">
			<label for="conteudo">Conteúdo<span class="obrigatorio">*</span></label>
			<textarea class="form-control" name="conteudo" rows="3" required
				maxlength="3000"></textarea>
		</div>

		<security:csrfInput />

		<!-- OBTIGATÓRIO -->
		<label>(*) Campos obrigatórios</label>
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
	src="<c:url value="/resources/js/jquery-ui.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.mask.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-ui-timepicker-addon.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/extra_classe/extra_classe.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/select/bootstrap-select.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/select/defaults-pt_BR.min.js" />"></script>
<c:import url="../componentes/rodape.jsp" />