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
						data-live-search-placeholder="Pesquisar">
						<c:forEach var="curso" items="${cursos}">
							<option value="${curso.id}">${curso.nome}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="col-4">
				<!-- Ano -->
				<div class="form-group">
					<label class="col-form-label" for="ano_ingresso">Ano</label>
					<div class="form-group">
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text"><span
									class="glyphicon glyphicon-calendar"></span></span>
							</div>
							<select class="custom-select" name="ano_ingresso">
								<option value="">Todos</option>
								<c:forEach var="ano" items="${lista_anos}">
									<option value="${ano}">${ano}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
			</div>

			<div class="col-3">
				<!-- Período -->
				<div class="form-group">
					<label for="periodo_ingresso" class="col-form-label">Período</label>
					<select class="custom-select" name="periodo_ingresso">
						<option value="">Todos</option>
						<option value="1">1º Semestre</option>
						<option value="2">2º Semestre</option>
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-3">
				<!-- Tipo -->
				<div class="form-group">
					<label for="tipo_turma" class="col-form-label">Tipo</label> <select
						class="custom-select" name="tipo_turma">
						<option value="">Todos</option>
						<option value="Única">Única</option>
						<option value="A">A</option>
						<option value="B">B</option>
						<option value="C">C</option>
						<option value="D">D</option>
						<option value="E">E</option>
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

			<div class="col-2"></div>
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