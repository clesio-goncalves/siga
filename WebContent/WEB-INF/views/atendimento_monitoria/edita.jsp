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
		<p class="lead">Preencha o formulário abaixo com os dados do
			atendimento de monitoria para realizar a alteração no sistema.</p>
	</div>
</div>
<div class="container">
	<form action="altera" method="POST">

		<!-- ID -->
		<input type="hidden" name="id" value="${atendimento_monitoria.id}"
			required>

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
						${atendimento_monitoria.aluno.id == aluno.id ? 'selected' : ''}>${aluno.nome}</option>
				</c:forEach>
			</select>
		</div>

		<!-- DISCIPLINA -->
		<div class="form-group" id="lista_disciplinas">
			<jsp:include page="import_edita/disciplina.jsp"></jsp:include>
		</div>

		<!-- MONITOR -->
		<div class="form-group" id="monitor_disciplina">
			<jsp:include page="import_novo/monitor.jsp"></jsp:include>
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

			<!-- Horário -->
			<div class="form-group col-6">
				<label for="horario" class="col-form-label">Horário<span
					class="obrigatorio">*</span>
				</label> <input type="text" class="form-control maskHorario" name="horario"
					required
					value="<fmt:formatDate type="time" value='${atendimento_monitoria.horario}' />">
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
			<label for="conteudo">Conteúdo<span class="obrigatorio">*</span></label>
			<textarea class="form-control" name="conteudo" rows="2" required
				maxlength="3000">${atendimento_monitoria.conteudo}</textarea>
		</div>

		<!-- DIFICULDADES DIAGNOSTICADAS -->
		<div class="form-group">
			<label for="dificuldades_diagnosticadas">Dificuldades
				diagnosticadas<span class="obrigatorio">*</span>
			</label>
			<textarea class="form-control" name="dificuldades_diagnosticadas"
				rows="3" required maxlength="3000">${atendimento_monitoria.dificuldades_diagnosticadas}</textarea>
		</div>

		<security:csrfInput />

		<!-- OBTIGATÓRIO -->
		<label>(*) Campos obrigatórios</label>
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