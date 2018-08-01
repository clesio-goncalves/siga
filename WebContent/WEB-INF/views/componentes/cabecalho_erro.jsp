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

				<ul class="nav navbar-nav navbar-right">
					<li><a href="logout" class="btn btn-secondary"><span class="glyphicon glyphicon-log-out"></span> Sair</a></li>
				</ul>
			</div>
		</div>
	</nav>