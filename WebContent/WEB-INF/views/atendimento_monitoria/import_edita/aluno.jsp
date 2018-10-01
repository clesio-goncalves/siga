<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<label for="aluno.id" class="col-form-label">Aluno<span
	class="obrigatorio">*</span></label>
<select name="aluno.id" id="aluno"
	class="selectpicker show-tick form-control" data-live-search="true"
	multiple data-max-options="1" title="Selecione um aluno"
	data-live-search-placeholder="Pesquisar" required
	onchange="alteraAluno('edita')"
	${atendimento_monitoria.status_atendimento ? 'disabled' : ''}>
	<c:forEach var="aluno" items="${alunos}">
		<option value="${aluno.id}"
			${atendimento_monitoria.aluno.id == aluno.id ? 'selected' : ''}>${aluno.nome}</option>
	</c:forEach>
</select>