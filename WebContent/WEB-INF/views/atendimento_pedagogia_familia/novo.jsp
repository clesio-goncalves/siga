<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Atendimento de Pedagogia a Familia</title>
<c:import url="../componentes/cabecalho.jsp" />
<c:import url="../componentes/css_atendimento.jsp" />

<div class="jumbotron">
	<div class="container">
		<h1>Atendimento de Pedagogia a Família</h1>
		<p class="lead">Preencha o formulário abaixo com os dados do
			atendimento de pedagogia a família no sistema.</p>
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
			<!-- RESPONSÁVEL -->
			<div class="form-group col-9">
				<label for="responsavel" class="col-form-label">Responsável<span
					class="obrigatorio">*</span></label> <input type="text"
					class="form-control" name="responsavel" MAXLENGTH="255" required>
			</div>
			<div class="form-group col-3">
				<label for="telefone" class="col-form-label">Telefone</label>
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text"><span
							class="glyphicon glyphicon-earphone"></span></span>
					</div>
					<input type="text" class="form-control maskTelefone"
						name="telefone" MAXLENGTH="20"
						pattern="\([0-9]{2}\)[\s][0-9]{4}-[0-9]{4,5}">
				</div>
			</div>
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

		<!-- RELATO DO ATENDIMENTO -->
		<div class="form-group">
			<label for="relato">Relato do atendimento<span
				class="obrigatorio">*</span></label>
			<textarea class="form-control" name="relato" rows="3" required
				maxlength="3000"></textarea>
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
	src="<c:url value="/resources/js/atendimento/pedagogia.js" />"></script>

<c:import url="../componentes/rodape.jsp" />