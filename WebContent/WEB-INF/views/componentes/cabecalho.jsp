<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width">
<link rel="stylesheet" type="text/css"
	href="resources/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="resources/css/cabecalho.css" />
<link rel="stylesheet" type="text/css"
	href="resources/css/jquery-ui.css">
<script type="text/javascript" src="resources/js/jquery.js"></script>
<script type="text/javascript" src="resources/js/bootstrap.js"></script>
</head>
<body>
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar">
					<span class="icon-bar"> </span> <span class="icon-bar"></span> <span
						class="icon-bar"></span> <span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index"> <span
					class="glyphicon glyphicon-home"></span> SIGA CAPAU
				</a>
			</div>

			<div class="collapse navbar-collapse" id="navbar">
				<ul class="nav navbar-nav nav-pills">

					<security:authorize access="hasRole('ROLE_Administrador')">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-expanded="false">Cadastro
								<span class="caret"></span>
						</a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="novoUsuario">Usuário</a></li>
								<li><a href="novoCurso">Curso</a></li>
								<li><a href="">Servidor</a></li>
							</ul></li>
					</security:authorize>
					<!-- <li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-expanded="false">Relatórios
							<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="listaUsuarios">Usuários</a></li>
							<li><a href="listaSetores">Setores</a></li>
							<li><a href="listaImpressoras">Impressoras</a></li>
							<li class="divider"></li>
							<li><a href="listaImpressoes">Impressão</a></li>
						</ul></li> 
					<security:authorize access="hasRole('ROLE_Administrador')">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-expanded="false">Configuração
								<span class="caret"></span>
						</a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="desativacaoIpv6">Desativação IPv6</a></li>
								<li class="divider"></li>
								<li><a href="diretorioLogs">Diretório dos Logs</a></li>
							</ul></li>
					</security:authorize>-->
				</ul>
				<security:authorize access="isAuthenticated()">
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-expanded="false"> <span
								class="glyphicon glyphicon-user"></span> <security:authentication
									property="principal" var="user" /> ${user.usuario} <span
								class="caret"></span>
						</a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="exibeUsuario?id=${user.id}"><span
										class="glyphicon glyphicon-info-sign"></span> Perfil</a></li>
								<li class="divider"></li>
								<li><a href="logout"><span
										class="glyphicon glyphicon-log-out"></span> Sair</a></li>
							</ul></li>
					</ul>
				</security:authorize>
			</div>
		</div>
	</nav>