<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<label for="docente.id" class="col-form-label">Docente<span
	class="obrigatorio">*</span></label>
<select name="docente.id" id="docente"
	class="selectpicker show-tick form-control" data-live-search="true"
	multiple data-max-options="1" title="Selecione um docente"
	data-live-search-placeholder="Pesquisar" required>
	<c:forEach var="docente" items="${docentes}">
		<option value="${docente.id}"
			${extra_classe.docente.id == docente.id ? 'selected' : ''}>${docente.nome}</option>
	</c:forEach>
</select>
