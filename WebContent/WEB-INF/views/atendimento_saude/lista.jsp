<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@	taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Atendimento Serviços de Saúde</title>
<c:import url="../componentes/css_data_table.jsp" />
<c:import url="../componentes/css_atendimento.jsp" />
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">

	<!-- Filtros -->
	<jsp:include page="import_lista/filtro.jsp"></jsp:include>

	<div class="card border-light mb-3">
		<div class="card-header">Listagem de Atendimentos de Serviços de
			Saúde</div>

		<!-- Table -->
		<div class="card-body" id="tabela">
			<jsp:include page="import_lista/tabela.jsp"></jsp:include>
		</div>
	</div>

	<div align="center">
		<security:authorize
			access="hasAnyRole('ROLE_Psicologia', 'ROLE_Assistência Social', 'ROLE_Enfermagem', 'ROLE_Odontologia')">
			<a href="<c:url value="/atendimento/saude/novo" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
		</security:authorize>
	</div>
</div>
<c:import url="../componentes/js_atendimento.jsp" />
<c:import url="../componentes/js_data_table_atendimento.jsp" />
<script type="text/javascript"
	src="<c:url value="/resources/js/atendimento/saude_filtro.js" />"></script>
<c:import url="../componentes/rodape.jsp" />