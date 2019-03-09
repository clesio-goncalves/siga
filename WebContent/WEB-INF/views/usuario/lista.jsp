<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>Listar Usuários</title>
<c:import url="../componentes/css_data_table.jsp" />
<c:import url="../componentes/cabecalho.jsp" />

<div class="container">

	<!-- Filtros -->
	<jsp:include page="import_lista/filtro.jsp"></jsp:include>

	<div class="card border-light mb-3">
		<div class="card-header">Listagem de Usuários</div>

		<!-- Table -->
		<div class="card-body" id="tabela">
			<jsp:include page="import_lista/tabela.jsp"></jsp:include>
		</div>
	</div>

	<security:authorize
		access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Psicologia', 'ROLE_Assistência Social', 'ROLE_Enfermagem', 'ROLE_Pedagogia', 'ROLE_Odontologia', 'ROLE_Docente', 'ROLE_Monitor', 'ROLE_Coordenação de Disciplina')">
		<div align="center">
			<a href="<c:url value="/usuario/novo" />"
				class="btn btn-primary btn-lg"><span
				class="glyphicon glyphicon-plus"></span> Cadastrar</a>
		</div>
	</security:authorize>
</div>

<c:import url="../componentes/js_data_table.jsp" />
<script type="text/javascript"
	src="<c:url value="/resources/js/usuario/filtro.js" />"></script>
<c:import url="../componentes/rodape.jsp" />