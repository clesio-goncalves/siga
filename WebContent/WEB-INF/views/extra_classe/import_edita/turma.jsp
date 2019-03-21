<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<label for="turma.id" class="col-form-label">Turma<span
	class="obrigatorio">*</span></label>
<select name="turma.id" id="turma"
	class="selectpicker show-tick form-control" data-live-search="true"
	multiple data-max-options="1" title="Selecione uma turma"
	data-live-search-placeholder="Pesquisar" required
	onchange="alteraTurma('edita')"
	${extra_classe.status_atendimento ? 'disabled' : ''}>
	<c:forEach var="turma" items="${turmas}">
		<option value="${turma.id}"
			${turma_atendimento == turma.id ? 'selected' : ''}>${turma.nome}</option>
	</c:forEach>
</select>