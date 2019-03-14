<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Atendimento de Pedagogia a Família</title>
<c:import url="../componentes/cabecalho.jsp" />
<c:import url="../componentes/css_atendimento.jsp" />
<div class="jumbotron">
	<div class="container">
		<h1>Editar Atendimento de Pedagogia a Família</h1>
		<p class="lead">Preencha o formulário abaixo com os dados do
			atendimento de pedagogia a família para realizar a alteração no
			sistema.</p>
	</div>
</div>
<div class="container">
	<form action="altera" method="POST">

		<!-- ID -->
		<input type="hidden" name="id" value="${atendimento_pedagogia.id}"
			required>

		<!-- Profissional -->
		<input type="hidden" name="profissional.id"
			value="${atendimento_pedagogia.profissional.id}" required>

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
							${atendimento_pedagogia.aluno.turma.curso.id == curso.id ? 'selected' : ''}>${curso.nome}</option>
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

		<!-- RESPONSÁVEL -->
		<div class="form-group">
			<label for="responsavel" class="col-form-label">Responsável<span
				class="obrigatorio">*</span></label> <input type="text" class="form-control"
				name="responsavel" MAXLENGTH="255" required
				value="${atendimento_pedagogia.responsavel}">
		</div>

		<div class="row">
			<!-- Data -->
			<div class="form-group col-6">
				<label for="data" class="col-form-label">Data do atendimento<span
					class="obrigatorio">*</span>
				</label> <input type="text" class="form-control maskData" name="data"
					required
					value="<fmt:formatDate value='${atendimento_pedagogia.data}' />">
			</div>

			<!-- Horário Inicial -->
			<div class="form-group col-3">
				<label for="horario_inicial" class="col-form-label">Horário
					inicial<span class="obrigatorio">*</span>
				</label> <input type="text" class="form-control maskHorario"
					name="horario_inicial" required
					value="<fmt:formatDate type="time" value='${atendimento_pedagogia.horario_inicial}' />">
			</div>

			<!-- Horário Final -->
			<div class="form-group col-3">
				<label for="horario_final" class="col-form-label">Horário
					final<span class="obrigatorio">*</span>
				</label> <input type="text" class="form-control maskHorario"
					name="horario_final" required
					value="<fmt:formatDate type="time" value='${atendimento_pedagogia.horario_final}' />">
			</div>
		</div>

		<!-- RELATO DO ATENDIMENTO -->
		<div class="form-group">
			<label for="relato">Relato do atendimento<span
				class="obrigatorio">*</span></label>
			<textarea class="form-control" name="relato" rows="3" required
				maxlength="3000">${atendimento_pedagogia.relato}</textarea>
		</div>

		<!-- ENCAMINHAMENTO -->
		<div class="form-group">
			<label for="encaminhamento">Encaminhamento<span
				class="obrigatorio">*</span></label>
			<textarea class="form-control" name="encaminhamento" rows="2"
				required maxlength="3000">${atendimento_pedagogia.encaminhamento}</textarea>
		</div>

		<security:csrfInput />

		<!-- OBTIGATÓRIO -->
		<label>(*) Campos obrigatórios</label>
		<div>
			<a href="<c:url value="/atendimento/pedagogia/familia/lista" />"
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
	src="<c:url value="/resources/js/atendimento/pedagogia.js" />"></script>
<c:import url="../componentes/rodape.jsp" />