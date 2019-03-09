<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
				<!-- Curso -->
				<div class="form-group">
					<label for="curso" class="col-form-label">Curso</label> <select
						name="curso" class="selectpicker show-tick form-control"
						data-live-search="true" multiple data-max-options="1"
						title="Selecione um curso"
						data-live-search-placeholder="Pesquisar" onchange="alteraCurso()">
						<c:forEach var="curso" items="${cursos}">
							<option value="${curso.id}">${curso.nome}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<!-- Turma -->
			<div class="col-5">
				<div class="form-group" id="lista_turmas">
					<jsp:include page="import_filtro/turma.jsp"></jsp:include>
				</div>
			</div>

			<div class="col-2">
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
			<div class="col-6">
				<!-- Nome -->
				<div class="form-group">
					<label for="nome" class="col-form-label">Nome</label> <input
						type="text" class="form-control" name="nome" MAXLENGTH="255">
				</div>
			</div>
			<div class="col-3">
				<!-- Matrícula -->
				<div class="form-group">
					<label for="matricula" class="col-form-label">Matricula</label> <input
						type="text" class="form-control" name="matricula" MAXLENGTH="50">
				</div>
			</div>
			<div class="col-3">
				<!-- Telefone -->
				<div class="form-group">
					<label for="telefone" class="col-form-label">Telefone</label>
					<div class="input-group mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text"><span
								class="glyphicon glyphicon-earphone"></span></span>
						</div>
						<input type="text" class="form-control" name="telefone"
							MAXLENGTH="20" pattern="\([0-9]{2}\)[\s][0-9]{4}-[0-9]{4,5}"
							data-mask="(00) 0000-00009">
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-4">
				<!-- Usuário -->
				<label for="usuario" class="col-form-label">Usuário</label>
				<div class="form-group">
					<div class="input-group mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text">@</span>
						</div>
						<input type="email" class="form-control" name="usuario"
							MAXLENGTH="50">
					</div>
				</div>
			</div>

			<div class="col-4">
				<!-- Atendimentos -->
				<div class="form-group">
					<label for="atendimentos" class="col-form-label">Atendimentos
					</label> <select class="custom-select" name="atendimentos">
						<option value="">Nenhum</option>
						<option value="Extraclasse">Extraclasse</option>
						<option value="Monitoria">Monitoria</option>
						<option value="Saude">Serviços de Saúde</option>
						<option value="Indisciplina">Indisciplina</option>
						<option value="Pedagogia">Pedagogia</option>
						<option value="Todos">Todos</option>
					</select>
				</div>
			</div>

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
