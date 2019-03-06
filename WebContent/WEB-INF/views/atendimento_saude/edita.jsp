<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Atendimento de Servi�os de Sa�de</title>
<c:import url="../componentes/cabecalho.jsp" />
<c:import url="../componentes/css_atendimento.jsp" />

<div class="jumbotron">
	<div class="container">
		<h1>Editar Atendimento de Servi�os de Sa�de</h1>
		<p class="lead">Preencha o formul�rio abaixo com os dados do
			atendimento do servi�o de sa�de para realizar a altera��o no sistema.</p>
	</div>
</div>
<div class="container">
	<form action="altera" method="POST">

		<!-- ID -->
		<input type="hidden" name="id" value="${atendimento_saude.id}"
			required>

		<!-- PROFISSIONAL SAUDE -->
		<input type="hidden" name="profissional.id"
			value="${atendimento_saude.profissional.id}" required>

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
							${atendimento_saude.aluno.turma.curso.id == curso.id ? 'selected' : ''}>${curso.nome}</option>
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
			<div class="form-group col-6">
				<label for="data" class="col-form-label">Data do atendimento<span
					class="obrigatorio">*</span>
				</label> <input type="text" class="form-control maskData" name="data"
					required
					value="<fmt:formatDate value='${atendimento_saude.data}' />">
			</div>

			<!-- Hor�rio Inicial -->
			<div class="form-group col-3">
				<label for="horario_inicial" class="col-form-label">Hor�rio
					inicial<span class="obrigatorio">*</span>
				</label> <input type="text" class="form-control maskHorario"
					name="horario_inicial" required
					value="<fmt:formatDate type="time" value='${atendimento_saude.horario_inicial}' />">
			</div>

			<!-- Hor�rio Final -->
			<div class="form-group col-3">
				<label for="horario_final" class="col-form-label">Hor�rio
					final<span class="obrigatorio">*</span>
				</label> <input type="text" class="form-control maskHorario"
					name="horario_final" required
					value="<fmt:formatDate type="time" value='${atendimento_saude.horario_final}' />">
			</div>
		</div>
		<div class="row">
			<!-- Possui problema -->
			<div class="form-group col-6">
				<div class="form-group">
					<label for="possui_problema" class="col-form-label">Possui
						alguma observa��o?<span class="obrigatorio">*</span>
					</label>
					<div class="custom-control custom-radio">
						<input type="radio" id="customRadio1" name="possui_problema"
							class="custom-control-input" required="required" value="Sim"
							${atendimento_saude.possui_problema == 'Sim' ? 'checked' : ''}>
						<label class="custom-control-label" for="customRadio1">Sim</label>
					</div>
					<div class="custom-control custom-radio">
						<input type="radio" id="customRadio2" name="possui_problema"
							class="custom-control-input" value="N�o"
							${atendimento_saude.possui_problema == 'N�o' ? 'checked' : ''}>
						<label class="custom-control-label" for="customRadio2">N�o</label>
					</div>
				</div>
			</div>

			<!-- esse_problema_dificulta_aprendizado -->
			<div class="form-group col-6">
				<div class="form-group">
					<label for="esse_problema_dificulta_aprendizado"
						class="col-form-label">Essa observa��o dificulta o
						aprendizado? </label>
					<div class="custom-control custom-radio">
						<input type="radio" id="customRadio3"
							name="esse_problema_dificulta_aprendizado"
							class="custom-control-input" value="Sim" required="required"
							${atendimento_saude.possui_problema == 'N�o' ? 'disabled' : ''}
							${atendimento_saude.esse_problema_dificulta_aprendizado == 'Sim' ? 'checked' : ''}>
						<label class="custom-control-label" for="customRadio3">Sim</label>
					</div>
					<div class="custom-control custom-radio">
						<input type="radio" id="customRadio4"
							name="esse_problema_dificulta_aprendizado"
							class="custom-control-input" value="N�o"
							${atendimento_saude.possui_problema == 'N�o' ? 'disabled checked' : ''}
							${atendimento_saude.esse_problema_dificulta_aprendizado == 'N�o' ? 'checked' : ''}>
						<label class="custom-control-label" for="customRadio4">N�o</label>
					</div>
				</div>
			</div>
		</div>


		<security:csrfInput />

		<!-- OBTIGAT�RIO -->
		<label>(*) Campos obrigat�rios</label>
		<div>
			<a href="<c:url value="/atendimento/saude/lista" />"
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
	src="<c:url value="/resources/js/atendimento/saude.js" />"></script>

<c:import url="../componentes/rodape.jsp" />