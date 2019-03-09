<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Listar Turmas</title>
<c:import url="../componentes/css_data_table.jsp" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/select/bootstrap-select.min.css" />">
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">

	<!-- Filtros -->
	<jsp:include page="import_lista/filtro.jsp"></jsp:include>

	<div class="card border-light mb-3">
		<div class="card-header">Listagem de Turmas</div>

		<!-- Table -->
		<div class="card-body" id="tabela">
			<jsp:include page="import_lista/tabela.jsp"></jsp:include>
		</div>
	</div>
	<security:authorize
		access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Pedagogia', 'ROLE_Coordenação de Disciplina')">
		<div align="center">
			<a href="<c:url value="/turma/nova" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
		</div>
	</security:authorize>
</div>

<c:import url="../componentes/js_data_table.jsp" />
<script type="text/javascript"
	src="<c:url value="/resources/js/select/bootstrap-select.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/select/defaults-pt_BR.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/turma/filtro.js" />"></script>
<c:import url="../componentes/rodape.jsp" />