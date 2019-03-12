<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Listar Alunos</title>
<c:import url="../componentes/css_data_table.jsp" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/select/bootstrap-select.min.css" />">
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<!-- Filtros -->
	<jsp:include page="import_lista/filtro.jsp"></jsp:include>

</div>

<div class="container-fluid">

	<div class="card border-light mb-3">
		<div class="card-header">Listagem de alunos</div>

		<!-- Table -->
		<div class="card-body" id="tabela">
			<jsp:include page="import_lista/tabela.jsp"></jsp:include>
		</div>
	</div>

	<div align="center">
		<security:authorize
			access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Psicologia', 'ROLE_Assistência Social', 'ROLE_Enfermagem', 'ROLE_Pedagogia', 'ROLE_Odontologia', 'ROLE_Docente', 'ROLE_Monitor', 'ROLE_Coordenação de Disciplina')">
			<a href="<c:url value="/aluno/novo" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
		</security:authorize>
		<!-- PDF -->
		<a href="relatorio" class="btn btn-danger btn-lg"><span
			class="glyphicon glyphicon-file"></span> Relatório PDF</a>
	</div>
</div>
<c:import url="../componentes/js_data_table.jsp" />
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.mask.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/select/bootstrap-select.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/select/defaults-pt_BR.min.js" />"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/aluno/filtro.js" />"></script>
<c:import url="../componentes/rodape.jsp" />