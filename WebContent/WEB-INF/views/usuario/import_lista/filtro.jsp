<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<div class="card border-success mb-3">
	<div class="card-header text-white bg-success" data-toggle="collapse"
		data-target="#accordion">
		<div class="row">
			<div class="col-6">
				<span class="glyphicon glyphicon-filter"></span>Filtros
			</div>
			<div class="col-6" style="text-align: right;">clique para
				expandir</div>
		</div>
	</div>

	<div class="card-body collapse hide" id="accordion">

		<div class="row">
			<div class="col-5">
				<!-- Email -->
				<div class="form-group">
					<label for="email" class="col-form-label">E-mail</label>
					<div class="input-group mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text">@</span>
						</div>
						<input type="email" class="form-control" name="email"
							MAXLENGTH="50">
					</div>
				</div>
			</div>

			<div class="col-4">
				<!-- Perfil -->
				<div class="form-group">
					<label for="perfil" class="col-form-label">Perfil</label> <select
						class="custom-select" name="perfil">
						<security:authorize access="hasRole('ROLE_Administrador')">
							<option value="">Todos</option>
							<option value="1">Administrador</option>
							<option value="3">Diretor</option>
						</security:authorize>
						<security:authorize
							access="hasAnyRole('ROLE_Administrador', 'ROLE_Diretor')">
							<option value="2">Coordenador</option>
							<option value="4">Psicologia</option>
							<option value="5">Assistência Social</option>
							<option value="6">Enfermagem</option>
							<option value="7">Pedagogia</option>
							<option value="8">Odontologia</option>
							<option value="12">Coordenação de Disciplina</option>
						</security:authorize>
						<security:authorize
							access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Pedagogia', 'ROLE_Coordenação de Disciplina')">
							<option value="9">Docente</option>
						</security:authorize>
						<security:authorize
							access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Pedagogia', 'ROLE_Docente')">
							<option value="10">Monitor</option>
						</security:authorize>
						<security:authorize
							access="hasAnyRole('ROLE_Administrador', 'ROLE_Coordenador', 'ROLE_Diretor', 'ROLE_Psicologia', 'ROLE_Assistência Social', 'ROLE_Enfermagem', 'ROLE_Pedagogia', 'ROLE_Odontologia', 'ROLE_Docente', 'ROLE_Monitor', 'ROLE_Coordenação de Disciplina')">
							<option value="11">Aluno</option>
						</security:authorize>
					</select>
				</div>
			</div>

			<div class="col-3">
				<!-- Situação -->
				<div class="form-group">
					<label for="situacao" class="col-form-label">Situação </label> <select
						class="custom-select" name="situacao">
						<option value="">Todos</option>
						<option value="true">Ativos</option>
						<option value="false">Inativos</option>
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-8"></div>

			<div class="col-4">
				<div class="form-group" align="right">
					<br>
					<button type="reset" class="btn btn-secondary btn-lg"
						onclick="limpar()">
						<span class="glyphicon glyphicon-erase"></span> Limpar
					</button>
					<button type="submit" class="btn btn-success btn-lg"
						onclick="filtrar()">
						<span class="glyphicon glyphicon-search"></span> Pesquisar
					</button>
				</div>
			</div>
		</div>
	</div>
</div>