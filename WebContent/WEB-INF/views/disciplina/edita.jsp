<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<title>Editar Disciplina</title>
<c:import url="../componentes/cabecalho.jsp" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/disciplina/bootstrap-select.min.css" />">

<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">Editar Disciplina</h1>
		<p class="lead">Preencha o formulário abaixo para realizar a
			alteração do disciplina no sistema.</p>
	</div>
</div>
<div class="container">
	<form action="altera" method="POST" id="disciplina">

		<!-- ID -->
		<input type="hidden" name="id" value="${disciplina.id}">

		<!-- NOME -->
		<div class="form-group">
			<label for="nome" class="col-form-label">Disciplina<span
				class="obrigatorio">*</span></label> <input type="text" class="form-control"
				name="nome" id="nome" autofocus MAXLENGTH="255" required
				value="${disciplina.nome}">
		</div>

		<!-- TURMAS E DOCENTES -->
		<div class="form-group">
			<label for="nome" class="col-form-label">Turma-Docente<span
				class="obrigatorio">*</span></label>
			<table class="table table-bordered  dt-responsive nowrap"
				style="width: 100%" id="table">
				<thead>
					<tr>
						<th style="width: 50%">Turma</th>
						<th>Docente</th>
					</tr>
				</thead>
				<tbody>
					<!-- Turmas e Docentes Cadastrados -->
					<c:forEach var="turmas_docente" items="${turmas_docentes}">
						<tr>
							<td>
								<div class="custom-control custom-checkbox">
									<input type="checkbox" class="custom-control-input"
										id="turma${turmas_docente.turma.id}" name="turmas"
										value="${turmas_docente.turma.id}" checked="checked">
									<label class="custom-control-label"
										for="turma${turmas_docente.turma.id}">${turmas_docente.turma.nome}</label>
								</div>
							</td>
							<td><select name="docentes"
								class="selectpicker show-tick form-control"
								data-live-search="true" multiple data-max-options="1"
								title="Selecione um docente"
								data-live-search-placeholder="Pesquisar">
									<option value="${turmas_docente.docente.id}"
										selected="selected">${turmas_docente.docente.siape}-${turmas_docente.docente.nome}</option>
									<c:forEach var="docente_sem_vinculo"
										items="${docentes_sem_vinculo}">
										<option value="${docente_sem_vinculo.id}">${docente_sem_vinculo.siape}-${docente_sem_vinculo.nome}</option>
									</c:forEach>
							</select></td>
						</tr>
					</c:forEach>
					<!-- Demais Turmas e Docentes Sem Viculo -->
					<c:forEach var="turma_sem_vinculo" items="${turmas_sem_vinculo}">
						<tr>
							<td>
								<div class="custom-control custom-checkbox">
									<input type="checkbox" class="custom-control-input"
										id="turma${turma_sem_vinculo.id}" name="turmas"
										value="${turma_sem_vinculo.id}"> <label
										class="custom-control-label"
										for="turma${turma_sem_vinculo.id}">${turma_sem_vinculo.nome}</label>
								</div>
							</td>
							<td><select name="docentes"
								class="selectpicker show-tick form-control"
								data-live-search="true" multiple data-max-options="1"
								title="Selecione um docente"
								data-live-search-placeholder="Pesquisar">
									<c:forEach var="turmas_docente" items="${turmas_docentes}">
										<option value="${turmas_docente.docente.id}">${turmas_docente.docente.siape}-${turmas_docente.docente.nome}</option>
									</c:forEach>
									<c:forEach var="docente_sem_vinculo"
										items="${docentes_sem_vinculo}">
										<option value="${docente_sem_vinculo.id}">${docente_sem_vinculo.siape}-${docente_sem_vinculo.nome}</option>
									</c:forEach>
							</select></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<security:csrfInput />

		<!-- OBTIGATÓRIO -->
		<label for="obrigatorio">(*) Campo obrigatório</label>
		<div>
			<a href="<c:url value="/disciplina/lista" />"
				class="btn btn-secondary btn-lg"> <span
				class="glyphicon glyphicon-remove"></span> Cancelar
			</a>
			<button type="submit" class="btn btn-primary btn-lg">
				<span class="glyphicon glyphicon-refresh"></span> Atualizar
			</button>
		</div>
	</form>
</div>

<script type="text/javascript"
	src="<c:url value="/resources/js/disciplina/bootstrap-select.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/disciplina/defaults-pt_BR.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/disciplina/cad_edit_disciplina.js" />"></script>
<c:import url="../componentes/rodape.jsp" />