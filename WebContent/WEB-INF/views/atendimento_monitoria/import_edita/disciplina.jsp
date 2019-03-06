<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<label for="disciplina.id" class="col-form-label">Disciplina<span
	class="obrigatorio">*</span></label>
<select name="disciplina.id" id="disciplina"
	class="selectpicker show-tick form-control" data-live-search="true"
	multiple data-max-options="1" title="Selecione uma disciplina"
	data-live-search-placeholder="Pesquisar" required
	onchange="alteraDisciplina('edita')"
	${atendimento_monitoria.status_atendimento ? 'disabled' : ''}>
	<c:forEach var="disciplina" items="${disciplinas}">
		<option value="${disciplina.id}"
			${atendimento_monitoria.disciplina.id == disciplina.id ? 'selected' : ''}>${disciplina.nome}</option>
	</c:forEach>
</select>
