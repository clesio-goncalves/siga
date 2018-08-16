<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
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
			<label for="nome" class="col-form-label">Disciplina*</label> <input
				type="text" class="form-control" name="nome" id="nome" autofocus
				MAXLENGTH="255" required value="${disciplina.nome}">
		</div>

		<!-- TURMAS E DOCENTES -->
		<div class="form-group">
			<label for="nome" class="col-form-label">Turma-Docente*</label>
			<table class="table table-bordered  dt-responsive nowrap"
				style="width: 100%" id="table">
				<thead>
					<tr>
						<th style="width: 50%">Turma</th>
						<th>Docente</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="turma" items="${turmas}">
						<tr>
							<td>
								<div class="custom-control custom-checkbox">
									<input type="checkbox" class="custom-control-input"
										id="turma${turma.id}" name="turmas" value="${turma.id}">
									<label class="custom-control-label" for="turma${turma.id}">${turma.nome}</label>
								</div>
							</td>
							<td><select name="docentes"
								class="selectpicker show-tick form-control"
								data-live-search="true" multiple data-max-options="1"
								title="Selecione um docente"
								data-live-search-placeholder="Pesquisar">
									<c:forEach var="docente" items="${docentes}">
										<option value="${docente.id}">${docente.siape}-${docente.nome}</option>
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