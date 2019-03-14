<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<label for="turma" class="col-form-label">Turma</label>
<select name="turma" id="turma"
	class="selectpicker show-tick form-control" data-live-search="true"
	multiple data-max-options="1" title="Selecione uma turma"
	data-live-search-placeholder="Pesquisar" disabled="disabled">
	<c:forEach var="turma" items="${turmas}">
		<option value="${turma.id}">${turma.nome}</option>
	</c:forEach>
</select>