<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Inicio</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/index.css" />">
<c:import url="componentes/cabecalho.jsp" />

<div class="jumbotron">
	<div class="container">
		<div class="imagem">
			<img src="<c:url value="/resources/imagens/SIGA.png" />"
				class="tamanho_imagem">
		</div>
		<hr class="my-4">
		<security:authorize access="isAuthenticated()">
			<security:authentication property="principal" var="usuario" />
			<c:set var="perfil" value="${usuario.perfil.nome}" />
			<c:set var="nome_perfil" value="${fn:replace(perfil, 'ROLE_', '')}" />
			<h1>Bem-vindo, ${nome_perfil}</h1>
		</security:authorize>
		<p class="lead">Este é o protótipo do sistema SIGA-Capau (Sistema
			Integrado de Gestão de Alunos do Campus Paulistana) que acompanha o
			desenvolvimento dos alunos no campus.</p>
	</div>
</div>

<c:import url="componentes/rodape.jsp" />