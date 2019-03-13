<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Ocorrência de Indisciplina</title>
<c:import url="../componentes/cabecalho.jsp" />
<c:import url="../componentes/css_atendimento.jsp" />

<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">Ocorrência de Indisciplina</h1>
		<p class="lead">Preencha o formulário abaixo com os dados da
			ocorrência de indisciplina no sistema.</p>
	</div>
</div>
<div class="container">
	<form action="adiciona" method="POST">

		<!-- Profissional -->
		<input type="hidden" name="profissional.id" value="${profissional.id}"
			required>

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

		<!-- ALUNO -->
		<div class="form-group" id="lista_alunos">
			<jsp:include page="import_novo/aluno.jsp"></jsp:include>
		</div>

		<div class="row">
			<!-- Data -->
			<div class="form-group col-4">
				<label for="data" class="col-form-label">Data da ocorrência<span
					class="obrigatorio">*</span>
				</label> <input type="text" class="form-control maskData" name="data"
					required>
			</div>

			<!-- Horário -->
			<div class="form-group col-3">
				<label for="horario" class="col-form-label">Horário<span
					class="obrigatorio">*</span>
				</label> <input type="text" class="form-control maskHorario" name="horario"
					required>
			</div>

			<!-- Advertido -->
			<div class="form-group col-2">
				<label for="advertido" class="col-form-label">Advertido?<span
					class="obrigatorio">*</span>
				</label> <select class="custom-select" name="advertido" required>
					<option value="Sim">Sim</option>
					<option value="Não" selected="selected">Não</option>
				</select>
			</div>

			<!-- Tipo advertência -->
			<div class="form-group col-3">
				<label for="tipo_advertencia" class="col-form-label">Tipo
					Advertência </label> <select class="custom-select" name="tipo_advertencia"
					disabled="disabled" required="required">
					<option value="Verbal" selected="selected">Verbal</option>
					<option value="Escrita">Escrita</option>
					<option value="Suspensão">Suspensão</option>
				</select>
			</div>
		</div>

		<!-- Descrição -->
		<div class="form-group">
			<label for="descricao">Descrição<span class="obrigatorio">*</span></label>
			<textarea class="form-control" name="descricao" rows="2" required
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

<c:import url="../componentes/js_atendimento.jsp" />
<script type="text/javascript"
	src="<c:url value="/resources/js/atendimento/indisciplina.js" />"></script>

<c:import url="../componentes/rodape.jsp" />