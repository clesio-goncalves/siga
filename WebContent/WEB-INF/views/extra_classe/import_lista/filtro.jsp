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
						data-live-search-placeholder="Pesquisar">
						<c:forEach var="curso" items="${cursos}">
							<option value="${curso.id}">${curso.nome}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="col-4">
				<!-- Turma -->
				<div class="form-group">
					<label for="turma" class="col-form-label">Turma</label> <select
						name="turma" class="selectpicker show-tick form-control"
						data-live-search="true" multiple data-max-options="1"
						title="Selecione uma turma"
						data-live-search-placeholder="Pesquisar">
						<c:forEach var="turma" items="${turmas}">
							<option value="${turma.id}">${turma.nome}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="col-4">
				<!-- Disciplina -->
				<div class="form-group">
					<label for="disciplina" class="col-form-label">Disciplina</label> <select
						name="disciplina" class="selectpicker show-tick form-control"
						data-live-search="true" multiple data-max-options="1"
						title="Selecione uma disciplina"
						data-live-search-placeholder="Pesquisar">
						<c:forEach var="disciplina" items="${disciplinas}">
							<option value="${disciplina.id}">${disciplina.nome}</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-5">
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

			<div class="col-5">
				<!-- Docente -->
				<input type="hidden" name="docente_id" value="${docente.id}" />
				<div class="form-group">
					<label for="docente" class="col-form-label">Docente</label>
					<c:if test="${docente != null}">
						<select name="" class="selectpicker show-tick form-control"
							data-live-search="true" multiple data-max-options="1"
							title="Selecione um docente"
							data-live-search-placeholder="Pesquisar" required
							disabled="disabled">
							<option value="${docente.id}" selected="selected">${docente.siape}-${docente.nome}</option>
						</select>
						<input type="hidden" name="docente" value="${docente.id}" />
					</c:if>
					<c:if test="${docente == null}">
						<select name="docente" class="selectpicker show-tick form-control"
							data-live-search="true" multiple data-max-options="1"
							title="Selecione um docente"
							data-live-search-placeholder="Pesquisar">
							<c:forEach var="docente" items="${docentes}">
								<option value="${docente.id}">${docente.siape}-${docente.nome}</option>
							</c:forEach>
						</select>
					</c:if>
				</div>
			</div>

			<div class="col-2">
				<!-- Status Atendimento -->
				<div class="form-group">
					<label for="status_atendimento" class="col-form-label">Houve
						atendimento?</label> <select class="custom-select"
						name="status_atendimento" required>
						<option value="" selected="selected">Todos</option>
						<option value="sim">Sim</option>
						<option value="nao">Não</option>
					</select>
				</div>
			</div>

		</div>
		<div class="row">
			<div class="col-3">
				<!-- Local -->
				<div class="form-group">
					<label for="local" class="col-form-label">Local</label> <input
						type="text" class="form-control" name="local" MAXLENGTH="255">
				</div>
			</div>
			<div class="col-5">
				<!-- Conteúdo -->
				<div class="form-group">
					<label for="conteudo" class="col-form-label">Conteúdo</label> <input
						type="text" class="form-control" name="conteudo" MAXLENGTH="255">
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
