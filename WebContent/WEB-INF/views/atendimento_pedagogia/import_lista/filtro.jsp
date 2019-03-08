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
			<div class="col-3">
				<!-- Data do atendimento -->
				<div class="form-group">
					<label for="data_inicial_atendimento" class="col-form-label">Data
						<strong>inicial</strong> do atendimento
					</label> <input type="text" class="form-control maskData"
						name="data_inicial_atendimento">
				</div>
			</div>
			<div class="col-3">
				<!-- Data final atendimento -->
				<div class="form-group">
					<label for="data_final_atendimento" class="col-form-label">Data
						<strong>final</strong> do atendimento
					</label> <input type="text" class="form-control maskData"
						name="data_final_atendimento">
				</div>
			</div>

			<div class="col-3">
				<!-- Horário inicial atendimento -->
				<div class="form-group">
					<label for="horario_inicial_atendimento" class="col-form-label">Horário
						<strong>inicial</strong> do atendimento
					</label> <input type="text" class="form-control maskHorario"
						name="horario_inicial_atendimento">
				</div>
			</div>

			<div class="col-3">
				<!-- Horário final atendimento -->
				<div class="form-group">
					<label for="horario_final_atendimento" class="col-form-label">Horário
						<strong>final</strong> do atendimento
					</label> <input type="text" class="form-control maskHorario"
						name="horario_final_atendimento">
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-4">
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
			<div class="col-4">
				<div class="form-group" id="lista_turmas">
					<jsp:include page="import_filtro/turma.jsp"></jsp:include>
				</div>
			</div>

			<div class="col-4">
				<!-- Assunto -->
				<div class="form-group">
					<label for="assunto" class="col-form-label">Assunto </label> <select
						class="custom-select" name="assunto">
						<option value="" selected="selected">Todos</option>
						<option value="dificuldades_ensino_aprendizagem">Dificuldades
							de ensino/aprendizagem</option>
						<option value="ausencia_professor">Ausência de professor</option>
						<option value="relacao_professor_aluno">Relação
							professor/aluno</option>
						<option value="indisciplina">Indisciplina</option>
						<option value="outros">Outros</option>
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-6">
				<!-- Aluno -->
				<div class="form-group">
					<label for="aluno" class="col-form-label">Aluno</label> <select
						name="aluno" class="selectpicker show-tick form-control"
						data-live-search="true" multiple data-max-options="1"
						title="Selecione um aluno"
						data-live-search-placeholder="Pesquisar">
						<c:forEach var="aluno" items="${alunos}">
							<option value="${aluno.id}">${aluno.nome}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="col-6">
				<!-- Profissional -->
				<div class="form-group">
					<label for="profissional" class="col-form-label">Profissional</label>
					<select name="profissional"
						class="selectpicker show-tick form-control"
						data-live-search="true" multiple data-max-options="1"
						title="Selecione um profissional"
						data-live-search-placeholder="Pesquisar">
						<c:forEach var="profissional" items="${profissionais}">
							<option value="${profissional.id}">${profissional.siape}-${profissional.nome}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-4">
				<!-- Exposição dos motivos -->
				<div class="form-group">
					<label for="exposicao_motivos" class="col-form-label">Exposição
						dos motivos</label> <input type="text" class="form-control"
						name="exposicao_motivos" MAXLENGTH="255">
				</div>
			</div>

			<div class="col-4">
				<!-- Encaminhamento -->
				<div class="form-group">
					<label for="encaminhamento" class="col-form-label">Encaminhamento</label>
					<input type="text" class="form-control" name="encaminhamento"
						MAXLENGTH="255">
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
