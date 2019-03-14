<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Atendimento de Pedagogia ao Aluno</title>
<c:import url="../componentes/cabecalho.jsp" />
<c:import url="../componentes/css_atendimento.jsp" />

<div class="jumbotron">
	<div class="container">
		<h1>Atendimento de Pedagogia ao Aluno</h1>
		<p class="lead">Preencha o formulário abaixo com os dados do
			atendimento de pedagogia no sistema.</p>
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
			<div class="form-group col-6">
				<label for="data" class="col-form-label">Data do atendimento<span
					class="obrigatorio">*</span>
				</label> <input type="text" class="form-control maskData" name="data"
					required>
			</div>

			<!-- Horário Inicial -->
			<div class="form-group col-3">
				<label for="horario_inicial" class="col-form-label">Horário
					inicial<span class="obrigatorio">*</span>
				</label> <input type="text" class="form-control maskHorario"
					name="horario_inicial" required>
			</div>

			<!-- Horário Final -->
			<div class="form-group col-3">
				<label for="horario_final" class="col-form-label">Horário
					final<span class="obrigatorio">*</span>
				</label> <input type="text" class="form-control maskHorario"
					name="horario_final" required>
			</div>
		</div>

		<!-- ASSUNTO -->
		<div class="form-group">
			<label class="col-form-label">Assunto<span
				class="obrigatorio">*</span></label>
			<div class="custom-control custom-checkbox">
				<input type="checkbox" class="custom-control-input"
					id="customCheck1" name="dificuldades_ensino_aprendizagem">
				<label class="custom-control-label" for="customCheck1">Dificuldades
					de ensino/aprendizagem</label>
			</div>
			<div class="custom-control custom-checkbox">
				<input type="checkbox" class="custom-control-input"
					id="customCheck2" name="ausencia_professor"> <label
					class="custom-control-label" for="customCheck2">Ausência de
					professor</label>
			</div>
			<div class="custom-control custom-checkbox">
				<input type="checkbox" class="custom-control-input"
					id="customCheck3" name="relacao_professor_aluno"> <label
					class="custom-control-label" for="customCheck3">Relação
					professor/aluno</label>
			</div>
			<div class="custom-control custom-checkbox">
				<input type="checkbox" class="custom-control-input"
					id="customCheck4" name="indisciplina"> <label
					class="custom-control-label" for="customCheck4">Indisciplina</label>
			</div>
			<div class="row">
				<div class="form-group col-auto">
					<div class="custom-control custom-checkbox">
						<input type="checkbox" class="custom-control-input"
							id="customCheck5" onchange="alteraOutros()"> <label
							class="custom-control-label" for="customCheck5">Outros</label>

					</div>
				</div>
				<div class="form-group col-6">
					<input type="text" class="form-control form-control-sm"
						name="outros" id="outros" MAXLENGTH="255" required
						disabled="disabled">
				</div>
			</div>
		</div>

		<!-- EXPOSIÇÃO DOS MOTIVOS -->
		<div class="form-group">
			<label for="exposicao_motivos">Exposição dos motivos<span
				class="obrigatorio">*</span></label>
			<textarea class="form-control" name="exposicao_motivos" rows="3"
				required maxlength="3000"></textarea>
		</div>

		<!-- ENCAMINHAMENTO -->
		<div class="form-group">
			<label for="encaminhamento">Encaminhamento<span
				class="obrigatorio">*</span>
			</label>
			<textarea class="form-control" name="encaminhamento" rows="2"
				required maxlength="3000"></textarea>
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
	src="<c:url value="/resources/js/atendimento/pedagogia_aluno.js" />"></script>

<c:import url="../componentes/rodape.jsp" />