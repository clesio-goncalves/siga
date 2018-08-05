<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Inicio</title>
<c:import url="componentes/cabecalho.jsp" />

<div class="jumbotron">
	<div class="container">
		<security:authorize access="isAuthenticated()">
			<security:authentication property="principal" var="usuario" />
			<h1 class="display-3">Bem-vindo, ${usuario.email}</h1>
		</security:authorize>
		<p class="lead">Este é o protótipo do sistema SIGA-Capau (Sistema
			Integrado de Gestão de Alunos do Campus Paulistana) que acompanha o
			desenvolvimento dos alunos abaixo da média.</p>
	</div>
</div>

<c:import url="componentes/rodape.jsp" />