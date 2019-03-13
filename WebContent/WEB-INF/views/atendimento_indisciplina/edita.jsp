<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Ocorr�ncia de Indisciplina</title>
<c:import url="../componentes/cabecalho.jsp" />
<c:import url="../componentes/css_atendimento.jsp" />

<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">Ocorr�ncia de Indisciplina</h1>
		<p class="lead">Preencha o formul�rio abaixo com os dados da
			ocorr�ncia de indisciplina para realizar a altera��o no sistema.</p>
	</div>
</div>
<div class="container">
	<form action="altera" method="POST">

		<!-- ID -->
		<input type="hidden" name="id" value="${atendimento_indisciplina.id}"
			required>

		<!-- PROFISSIONAL -->
		<input type="hidden" name="profissional.id"
			value="${atendimento_indisciplina.profissional.id}" required>

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
							${atendimento_indisciplina.aluno.turma.curso.id == curso.id ? 'selected' : ''}>${curso.nome}</option>
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
			<!-- Data -->
			<div class="form-group col-4">
				<label for="data" class="col-form-label">Data da ocorr�ncia<span
					class="obrigatorio">*</span>
				</label> <input type="text" class="form-control maskData" name="data"
					required
					value="<fmt:formatDate value='${atendimento_indisciplina.data}' />">
			</div>

			<!-- Hor�rio -->
			<div class="form-group col-3">
				<label for="horario" class="col-form-label">Hor�rio<span
					class="obrigatorio">*</span>
				</label> <input type="text" class="form-control maskHorario" name="horario"
					required
					value="<fmt:formatDate type="time" value='${atendimento_indisciplina.horario}' />">
			</div>

			<!-- Advertido -->
			<div class="form-group col-2">
				<label for="advertido" class="col-form-label">Advertido?<span
					class="obrigatorio">*</span>
				</label> <select class="custom-select" name="advertido" required>
					<option value="Sim"
						${atendimento_indisciplina.advertido eq 'Sim' ? 'selected' : ''}>Sim</option>
					<option value="N�o"
						${atendimento_indisciplina.advertido eq 'N�o' ? 'selected' : ''}>N�o</option>
				</select>
			</div>

			<!-- Tipo advert�ncia -->
			<div class="form-group col-3">
				<label for="tipo_advertencia" class="col-form-label">Tipo
					Advert�ncia </label> <select class="custom-select" name="tipo_advertencia"
					required="required"
					${atendimento_indisciplina.advertido eq 'N�o' ? 'disabled' : ''}>
					<option value="Verbal"
						${atendimento_indisciplina.tipo_advertencia eq 'Verbal' ? 'selected' : ''}>Verbal</option>
					<option value="Escrita"
						${atendimento_indisciplina.tipo_advertencia eq 'Escrita' ? 'selected' : ''}>Escrita</option>
					<option value="Suspens�o"
						${atendimento_indisciplina.tipo_advertencia eq 'Suspens�o' ? 'selected' : ''}>Suspens�o</option>
				</select>
			</div>
		</div>

		<!-- Descri��o -->
		<div class="form-group">
			<label for="descricao">Descri��o<span class="obrigatorio">*</span></label>
			<textarea class="form-control" name="descricao" rows="2" required
				maxlength="3000">${atendimento_indisciplina.descricao}</textarea>
		</div>


		<security:csrfInput />

		<!-- OBTIGAT�RIO -->
		<label>(*) Campos obrigat�rios</label>
		<div>
			<a href="<c:url value="/atendimento/indisciplina/lista" />"
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
	src="<c:url value="/resources/js/atendimento/indisciplina.js" />"></script>

<c:import url="../componentes/rodape.jsp" />