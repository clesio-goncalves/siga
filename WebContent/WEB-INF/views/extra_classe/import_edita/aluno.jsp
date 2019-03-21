<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<label for="alunos" class="col-form-label">Aluno<span
	class="obrigatorio">*</span></label>
<select name="alunos" id="aluno"
	class="selectpicker show-tick form-control" data-live-search="true"
	multiple title="Selecione o(s) aluno(s)"
	data-live-search-placeholder="Pesquisar" required
	onchange="alteraAluno('edita')"
	${extra_classe.status_atendimento ? 'disabled' : ''}>
	<c:forEach var="aluno" items="${alunos}">
		<option value="${aluno.id}"
			${fn:contains(extra_classe.alunos, aluno.id) ? 'selected' : ''}>${aluno.nome}</option>
	</c:forEach>
</select>