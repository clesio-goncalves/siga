<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Atendimento de Monitoria</title>
<c:import url="../componentes/cabecalho.jsp" />
<c:import url="../componentes/css_atendimento.jsp" />
<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">Editar Atendimento de Monitoria</h1>
		<p class="lead">Preencha o formul�rio abaixo com os dados do
			atendimento de monitoria para realizar a altera��o no sistema.</p>
	</div>
</div>
<div class="container">
	<form action="altera" method="POST">

		<!-- ID -->
		<input type="hidden" name="id" value="${atendimento_monitoria.id}"
			required>

		<div class="row">
			<!-- CURSO-->
			<div class="form-group col-md-6">
				<label for="curso.id" class="col-form-label">Curso<span
					class="obrigatorio">*</span></label> <select name="curso.id" id="curso"
					class="selectpicker show-tick form-control" data-live-search="true"
					multiple data-max-options="1" title="Selecione um curso"
					data-live-search-placeholder="Pesquisar" required
					onchange="alteraCurso('edita')"
					${atendimento_monitoria.status_atendimento ? 'disabled' : ''}>
					<c:forEach var="curso" items="${cursos}">
						<option value="${curso.id}"
							${atendimento_monitoria.aluno.turma.curso.id == curso.id ? 'selected' : ''}>${curso.nome}</option>
					</c:forEach>
				</select>
			</div>

			<!-- TURMA-->
			<div class="form-group col-md-6" id="lista_turmas">
				<jsp:include page="import_edita/turma.jsp"></jsp:include>
			</div>
		</div>

		<!-- ALUNO -->
		<div class="form-group" id="lista_alunos">
			<jsp:include page="import_edita/aluno.jsp"></jsp:include>
		</div>

		<div class="row">
			<!-- DISCIPLINA -->
			<div class="col-md-6 form-group" id="lista_disciplinas">
				<jsp:include page="import_edita/disciplina.jsp"></jsp:include>
			</div>

			<!-- MONITOR -->
			<input type="hidden" name="monitor_id" value="${monitor.id}" />
			<div class="col-md-6 form-group" id="lista_monitores">
				<jsp:include page="import_novo/monitor.jsp"></jsp:include>
			</div>
		</div>

		<div class="row">

			<!-- Data -->
			<div class="form-group col-6">
				<label for="data" class="col-form-label">Data do atendimento<span
					class="obrigatorio">*</span>
				</label> <input type="text" class="form-control maskData" name="data"
					required
					value="<fmt:formatDate value='${atendimento_monitoria.data}' />">
			</div>

			<!-- Hor�rio Inicial -->
			<div class="form-group col-3">
				<label for="horario_inicial" class="col-form-label">Hor�rio
					inicial<span class="obrigatorio">*</span>
				</label> <input type="text" class="form-control maskHorario"
					name="horario_inicial" required
					value="<fmt:formatDate type="time" value='${atendimento_monitoria.horario_inicial}' />">
			</div>

			<!-- Hor�rio Final -->
			<div class="form-group col-3">
				<label for="horario_final" class="col-form-label">Hor�rio
					final<span class="obrigatorio">*</span>
				</label> <input type="text" class="form-control maskHorario"
					name="horario_final" required
					value="<fmt:formatDate type="time" value='${atendimento_monitoria.horario_final}' />">
			</div>
		</div>

		<!-- LOCAL -->
		<div class="form-group">
			<label for="local" class="col-form-label">Local<span
				class="obrigatorio">*</span></label> <input type="text" class="form-control"
				name="local" MAXLENGTH="255" required
				value="${atendimento_monitoria.local}">
		</div>

		<!-- CONTEUDO -->
		<div class="form-group">
			<label for="conteudo">Conte�do<span class="obrigatorio">*</span></label>
			<textarea class="form-control" name="conteudo" rows="2" required
				maxlength="3000"
				${atendimento_monitoria.status_atendimento ? 'readonly' : ''}>${atendimento_monitoria.conteudo}</textarea>
		</div>

		<!-- DIFICULDADES DIAGNOSTICADAS -->
		<div class="form-group">
			<label for="dificuldades_diagnosticadas">Dificuldades
				diagnosticadas<span class="obrigatorio">*</span>
			</label>
			<textarea class="form-control" name="dificuldades_diagnosticadas"
				rows="3" required maxlength="3000"
				${atendimento_monitoria.status_atendimento ? 'readonly' : ''}>${atendimento_monitoria.dificuldades_diagnosticadas}</textarea>
		</div>

		<!-- STATUS ATENDIMENTO -->
		<div class="form-group">
			<div class="custom-control custom-checkbox">
				<input type="checkbox" class="custom-control-input"
					id="status_atendimento" name="status_atendimento"
					onchange="alteraStatusAtendimento('edita')"
					${atendimento_monitoria.status_atendimento ? 'checked' : ''}>
				<label class="custom-control-label" for="status_atendimento">N�o
					houve atendimento</label>
			</div>
		</div>

		<security:csrfInput />

		<!-- OBTIGAT�RIO -->
		<label>(*) Campos obrigat�rios</label>
		<div>
			<a href="<c:url value="/atendimento/monitoria/lista" />"
				class="btn btn-secondary btn-lg"> <span
				class="glyphicon glyphicon-remove"></span> Cancelar
			</a>
			<button type="submit" class="btn btn-primary btn-lg">
				<span class="glyphicon glyphicon-refresh"></span> Atualizar
			</button>
		</div>
	</form>
</div>

<c:import url="../componentes/js_atendimento.jsp" />
<script type="text/javascript"
	src="<c:url value="/resources/js/atendimento/monitoria.js" />"></script>
<c:import url="../componentes/rodape.jsp" />