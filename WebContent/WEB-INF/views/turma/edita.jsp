<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<title>Editar Turma</title>

<c:import url="../componentes/cabecalho.jsp" />

<div class="jumbotron">
	<div class="container">
		<h1>Editar Turma</h1>
		<p class="lead">Preencha o formulário abaixo para realizar a
			alteração do turma no sistema.</p>
	</div>
</div>
<div class="container">
	<form action="altera" method="POST">

		<!-- ID -->
		<input type="hidden" name="id" value="${turma.id}" required />

		<div class="form-row">
			<!-- ANO INGRESSO -->
			<div class="col-md-6 form-group">
				<label class="col-form-label" for="ano_ingresso">Ano<span
					class="obrigatorio">*</span></label>
				<div class="form-group">
					<div class="input-group mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text"><span
								class="glyphicon glyphicon-calendar"></span></span>
						</div>
						<select class="custom-select" name="ano_ingresso" required
							autofocus>
							<c:forEach var="ano" items="${lista_anos}">
								<option value="${ano}"
									${turma.ano_ingresso==ano ? 'selected' : ''}>${ano}</option>
							</c:forEach>
						</select>
					</div>
				</div>
			</div>

			<!-- PERIODO-->
			<div class="col-md-3 form-group">
				<label for="periodo_ingresso" class="col-form-label">Período<span
					class="obrigatorio">*</span></label> <select class="custom-select"
					name="periodo_ingresso" required>
					<option value="1" ${turma.periodo_ingresso==1 ? 'selected' : ''}>1º
						Semestre</option>
					<option value="2" ${turma.periodo_ingresso==2 ? 'selected' : ''}>2º
						Semestre</option>
				</select>
			</div>

			<!-- TIPO TURMA -->
			<div class="col-md-3 form-group">
				<label for="tipo_turma" class="col-form-label">Tipo<span
					class="obrigatorio">*</span></label> <select class="custom-select"
					name="tipo_turma" required>
					<option value="Única"
						${turma.tipo_turma eq 'Única' ? 'selected' : ''}>Única</option>
					<option value="A" ${turma.tipo_turma eq 'A' ? 'selected' : ''}>A</option>
					<option value="B" ${turma.tipo_turma eq 'B' ? 'selected' : ''}>B</option>
					<option value="C" ${turma.tipo_turma eq 'C' ? 'selected' : ''}>C</option>
					<option value="D" ${turma.tipo_turma eq 'D' ? 'selected' : ''}>D</option>
					<option value="E" ${turma.tipo_turma eq 'E' ? 'selected' : ''}>E</option>
				</select>
			</div>

		</div>

		<!-- CURSO -->
		<div class="form-group">
			<label for="curso.id" class="col-form-label">Curso<span
				class="obrigatorio">*</span></label>
			<c:forEach var="curso" items="${cursos}">
				<div class="custom-control custom-radio">
					<input type="radio" id="${curso.id}" name="curso.id"
						class="custom-control-input" value="${curso.id}"
						${curso.id==turma.curso.id ? 'checked' : ''}> <label
						class="custom-control-label" for="${curso.id}">${curso.nome}</label>
				</div>
			</c:forEach>
		</div>

		<input type="hidden" name="nome" value="nome">

		<!-- ATIVO -->
		<div class="form-group">
			<div class="custom-control custom-checkbox">
				<input type="checkbox" class="custom-control-input"
					id="customCheck1" name="ativo" ${turma.ativo ? 'checked' : ''}>
				<label class="custom-control-label" for="customCheck1">Ativa</label>
			</div>
		</div>

		<security:csrfInput />

		<!-- OBTIGATÓRIO -->
		<label for="obrigatorio">(*) Campo obrigatório</label>
		<div>
			<a href="<c:url value="/turma/lista" />"
				class="btn btn-secondary btn-lg"> <span
				class="glyphicon glyphicon-remove"></span> Cancelar
			</a>
			<button type="submit" class="btn btn-primary btn-lg">
				<span class="glyphicon glyphicon-refresh"></span> Atualizar
			</button>
		</div>
	</form>
</div>

<c:import url="../componentes/rodape.jsp" />