<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Login</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<!-- Bootstrap CSS -->
<link rel="stylesheet" type="text/css"
	href="resources/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="resources/css/login.css" />

</head>
<body>
	<div class="container">
		<div class="login-panel panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Login em SIGA CAPAU</h3>
			</div>
			<div class="panel-body">
				<form action="login" method="POST">
					<c:if test="${param.error != null}">
						<div class="alert alert-dismissible alert-danger">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<strong>Erro!</strong> Usuário e/ou senha inválido. Ou usuário
							inativo.
						</div>
					</c:if>

					<c:if test="${param.logout != null}">
						<div class="alert alert-dismissible alert-info">
							<button type="button" class="close" data-dismiss="alert">&times;</button>
							<strong>Volte sempre!</strong> Logout realizado com sucesso.
						</div>
					</c:if>

					<div class="form-group">
						<label for="username">Usuário</label> <input class="form-control"
							id="username" name="username" MAXLENGTH="50" type="text" required
							autofocus>
					</div>
					<div class="form-group">
						<label for="senha">Senha</label> <input class="form-control"
							id="password" name="password" MAXLENGTH="50" type="password"
							required>
					</div>
					<div class="form-group">
						<input type="checkbox" id="remember-me" name="remember-me" /> <label
							for="remember-me">Lembrar-me</label>
					</div>
					<security:csrfInput />
					<button type="submit" class="btn btn-lg btn-success btn-block">Entrar</button>
				</form>

			</div>
		</div>
	</div>

	<script type="text/javascript" src="resources/js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="resources/js/popper.min.js"></script>
	<script type="text/javascript" src="resources/js/bootstrap.min.js"></script>

</body>
</html>
