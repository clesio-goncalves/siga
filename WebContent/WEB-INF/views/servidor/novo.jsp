<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Cadastrar Servidor</title>

<c:import url="../componentes/cabecalho.jsp" />
<div class="jumbotron">
	<div class="container">
		<h1 class="display-3">Cadastrar Servidor</h1>
		<p class="lead">Preencha o formulário abaixo para realizar o
			cadastro do Servidor no sistema.</p>
	</div>
</div>
<div class="container">
	<form action="adiciona" method="POST">

		<!-- NOME -->
		<div class="form-group">
			<label for="nome" class="col-form-label">Nome Completo<span class="obrigatorio">*</span></label> <input
				type="text" class="form-control" name="nome" autofocus
				MAXLENGTH="255" required>
		</div>

		<!-- SIAPE -->
		<div class="form-group">
			<label for="siape" class="col-form-label">SIAPE<span class="obrigatorio">*</span></label> <input
				type="number" class="form-control" name="siape" MAXLENGTH="10"
				required onkeypress='return SomenteNumero(event)'>
		</div>

		<!-- FUNCAO -->
		<div class="form-group">
			<label for="funcao" class="col-form-label">Função<span class="obrigatorio">*</span></label>
			<div class="custom-control custom-radio">
				<input type="radio" id="customRadio1" name="funcao"
					class="custom-control-input" checked="checked" required
					value="Coordenador"> <label class="custom-control-label"
					for="customRadio1">Coordenador</label>
			</div>
			<div class="custom-control custom-radio">
				<input type="radio" id="customRadio2" name="funcao"
					class="custom-control-input" value="Administrador"> <label
					class="custom-control-label" for="customRadio2">Administrador</label>
			</div>
			<div class="custom-control custom-radio">
				<input type="radio" id="customRadio3" name="funcao"
					class="custom-control-input" value="Diretor"> <label
					class="custom-control-label" for="customRadio3">Diretor</label>
			</div>
		</div>

		<!-- DESCRICAO FUNÇÃO -->
		<div class="form-group">
			<label for="descricao_funcao" class="col-form-label">Descrição
				da Função<span class="obrigatorio">*</span></label> <input type="text" class="form-control"
				name="descricao_funcao" MAXLENGTH="255" required>
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
	src="<c:url value="/resources/js/SomenteNumero.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/filtro_cadastro/filtroCadServidor.js" />"></script>
<c:import url="../componentes/rodape.jsp" />