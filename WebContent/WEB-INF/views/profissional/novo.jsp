<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Cadastrar Profissional da Saúde</title>

<c:import url="../componentes/cabecalho.jsp" />
<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">Cadastrar Profissional da Saúde</h1>
		<p class="lead">Preencha o formulário abaixo para realizar o
			cadastro do Profissional de Saúde no sistema.</p>
	</div>
</div>
<div class="container">
	<form action="adiciona" method="POST">

		<!-- NOME -->
		<div class="form-group">
			<label for="nome" class="col-form-label">Nome Completo<span
				class="obrigatorio">*</span></label> <input type="text" class="form-control"
				name="nome" autofocus MAXLENGTH="255" required>
		</div>

		<!-- SIAPE -->
		<div class="form-group">
			<label for="siape" class="col-form-label">SIAPE<span
				class="obrigatorio">*</span></label> <input type="text" class="form-control"
				name="siape" MAXLENGTH="11" required data-mask="99999999999">
		</div>

		<!-- TIPO PROFISSIONAL -->
		<div class="form-group">
			<label for="tipo_atendimento" class="col-form-label">Tipo de
				Atendimento<span class="obrigatorio">*</span>
			</label>
			<div class="custom-control custom-radio">
				<input type="radio" id="customRadio1" name="tipo_atendimento"
					class="custom-control-input" checked="checked" required
					value="Psicologia"> <label class="custom-control-label"
					for="customRadio1">Psicologia</label>
			</div>
			<div class="custom-control custom-radio">
				<input type="radio" id="customRadio2" name="tipo_atendimento"
					class="custom-control-input" value="Assistência Social"> <label
					class="custom-control-label" for="customRadio2">Assistência
					Social</label>
			</div>
			<div class="custom-control custom-radio">
				<input type="radio" id="customRadio3" name="tipo_atendimento"
					class="custom-control-input" value="Enfermagem"> <label
					class="custom-control-label" for="customRadio3">Enfermagem</label>
			</div>
			<div class="custom-control custom-radio">
				<input type="radio" id="customRadio4" name="tipo_atendimento"
					class="custom-control-input" value="Odontologia"> <label
					class="custom-control-label" for="customRadio4">Odontologia</label>
			</div>
			<div class="custom-control custom-radio">
				<input type="radio" id="customRadio5" name="tipo_atendimento"
					class="custom-control-input" value="Pedagogia"> <label
					class="custom-control-label" for="customRadio5">Pedagogia</label>
			</div>

		</div>

		<!-- USUÁRIO-->
		<div class="form-group" id="lista_usuarios">
			<jsp:include page="lista_usuarios.jsp"></jsp:include>
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

<script type="text/javascript"
	src="<c:url value="/resources/js/filtro_cadastro/filtroCadProfissional.js" />"></script>
<c:import url="../componentes/rodape.jsp" />