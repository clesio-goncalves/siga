<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Inicio</title>
<c:import url="componentes/cabecalho.jsp" />

<div class="jumbotron">
	<div class="container">
		
		<security:authorize access="isAuthenticated()">
			<security:authentication property="principal" var="usuario"/>
			<h1>Bem-vindo, ${usuario.usuario}</h1>
		</security:authorize>
		<p>Este é o protótipo do sistema SIGA-Capau (Sistema Integrado de Gestão de Alunos do Campus Paulistana)  que acompanha 
		o desenvolvimento dos alunos abaixo da média.</p>
	</div>
</div>

<c:import url="componentes/rodape.jsp" />