<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>Login SIGA CAPAU</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/bootstrap.min.css" />">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/login.css" />">
</head>
<body class="my-login-page">
	<section class="h-100">
		<div class="container h-100">
			<div class="imagem">
				<img src="<c:url value="/resources/imagens/SIGA.png" />"
					class="tamanho_imagem">
			</div>
			<div class="row justify-content-md-center h-100">
				<div class="card-wrapper">
					<div class="card fat">
						<div class="card-body">
							<h3 class="card-title" align="left">Login</h3>
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
									<label for="username">E-mail</label> <input
										class="form-control" id="username" name="username"
										MAXLENGTH="50" type="email" required autofocus>
								</div>

								<div class="form-group">
									<label for="password">Senha</label> <input class="form-control"
										id="password" name="password" MAXLENGTH="50" type="password"
										required data-eye>
								</div>

								<div class="form-group">
									<div class="custom-control custom-checkbox">
										<input type="checkbox" class="custom-control-input"
											id="remember-me" name="remember-me"> <label
											class="custom-control-label" for="remember-me">Lembrar-me</label>
									</div>
								</div>

								<security:csrfInput />

								<div class="form-group no-margin">
									<button type="submit" class="btn btn-lg btn-success btn-block">
										Entrar</button>
								</div>
							</form>
						</div>
					</div>
					<div class="footer">2019 - Todos os direitos reservados a CTI
						- Campus Paulistana.</div>
				</div>
			</div>
		</div>
	</section>

	<script type="text/javascript"
		src="<c:url value="/resources/js/jquery-3.3.1.min.js" />"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
	<script type="text/javascript"
		src="<c:url value="/resources/js/login.js"/>"></script>
</body>
</html>