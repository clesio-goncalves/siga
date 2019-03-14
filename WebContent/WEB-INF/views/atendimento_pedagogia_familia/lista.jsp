<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Atendimento de Pedagogia a Família</title>
<c:import url="../componentes/css_data_table.jsp" />
<c:import url="../componentes/css_atendimento.jsp" />
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">
	<!-- Filtros -->
	<jsp:include page="import_lista/filtro.jsp"></jsp:include>
</div>

<div class="container-fluid">

	<div class="card border-light mb-3">
		<div class="card-header">Listagem de Atendimentos da Pedagogia a
			Família</div>

		<!-- Table -->
		<div class="card-body" id="tabela">
			<jsp:include page="import_lista/tabela.jsp"></jsp:include>
		</div>
	</div>

	<div align="center">
		<security:authorize access="hasRole('ROLE_Pedagogia')">
			<a href="<c:url value="/atendimento/pedagogia/familia/novo" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
		</security:authorize>
		<!-- PDF -->
		<a href="relatorio" class="btn btn-danger btn-lg"><span
			class="glyphicon glyphicon-file"></span> Relatório PDF</a>
	</div>
</div>
<c:import url="../componentes/js_atendimento.jsp" />
<c:import url="../componentes/js_data_table_atendimento_pedagogia.jsp" />
<script type="text/javascript"
	src="<c:url value="/resources/js/atendimento/pedagogia_familia_filtro.js" />"></script>
<c:import url="../componentes/rodape.jsp" />