<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<label for="alunos" class="col-form-label">Aluno<span
	class="obrigatorio">*</span></label>
<select name="alunos" id="aluno"
	class="selectpicker show-tick form-control" data-live-search="true"
	multiple title="Selecione o(s) aluno(s)"
	data-live-search-placeholder="Pesquisar" required disabled="disabled"
	onchange="alteraAluno('novo')">
	<c:forEach var="aluno" items="${alunos}">
		<option value="${aluno.id}">${aluno.nome}</option>
	</c:forEach>
</select>