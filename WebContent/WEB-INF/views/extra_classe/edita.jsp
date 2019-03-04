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
<c:import url="../componentes/css_atendimento.jsp" />
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

		<div class="row">
			<!-- CURSO-->
			<div class="form-group col-md-6">
				<label for="curso.id" class="col-form-label">Curso<span
					class="obrigatorio">*</span></label> <select name="curso.id" id="curso"
					class="selectpicker show-tick form-control" data-live-search="true"
					multiple data-max-options="1" title="Selecione um curso"
					data-live-search-placeholder="Pesquisar" required
					onchange="alteraCurso('edita')"
					${extra_classe.status_atendimento ? 'disabled' : ''}>
					<c:forEach var="curso" items="${cursos}">
						<option value="${curso.id}"
							${extra_classe.aluno.turma.curso.id == curso.id ? 'selected' : ''}>${curso.nome}</option>
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

			<!-- DOCENTE -->
			<input type="hidden" name="docente_id" value="${docente.id}" />
			<div class="col-md-6 form-group" id="lista_docentes">
				<jsp:include page="import_edita/docente.jsp"></jsp:include>
			</div>
		</div>

		<div class="row">
			<!-- Data -->
			<div class="form-group col-6">
				<label for="data" class="col-form-label">Data do atendimento<span
					class="obrigatorio">*</span>
				</label> <input type="text" class="form-control maskData" name="data"
					required value="<fmt:formatDate value='${extra_classe.data}' />">
			</div>

			<!-- Horário Inicial -->
			<div class="form-group col-3">
				<label for="horario_inicial" class="col-form-label">Horário
					inicial<span class="obrigatorio">*</span>
				</label> <input type="text" class="form-control maskHorario"
					name="horario_inicial" required
					value="<fmt:formatDate type="time" value='${extra_classe.horario_inicial}' />">
			</div>

			<!-- Horário Final -->
			<div class="form-group col-3">
				<label for="horario_final" class="col-form-label">Horário
					final<span class="obrigatorio">*</span>
				</label> <input type="text" class="form-control maskHorario"
					name="horario_final" required
					value="<fmt:formatDate type="time" value='${extra_classe.horario_final}' />">
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
			<textarea class="form-control" name="conteudo" rows="2" required
				maxlength="3000"
				${extra_classe.status_atendimento ? 'readonly' : ''}>${extra_classe.conteudo}</textarea>
		</div>

		<!-- STATUS ATENDIMENTO -->
		<div class="form-group">
			<div class="custom-control custom-checkbox">
				<input type="checkbox" class="custom-control-input"
					id="status_atendimento" name="status_atendimento"
					onchange="alteraStatusAtendimento('edita')"
					${extra_classe.status_atendimento ? 'checked' : ''}> <label
					class="custom-control-label" for="status_atendimento">Não
					houve atendimento</label>
			</div>
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

<c:import url="../componentes/js_atendimento.jsp" />
<script type="text/javascript"
	src="<c:url value="/resources/js/atendimento/extra_classe.js" />"></script>
<c:import url="../componentes/rodape.jsp" />