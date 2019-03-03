<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<label for="docente.id" class="col-form-label">Docente<span
	class="obrigatorio">*</span></label>
<c:if test="${docente != null}">
	<select name="" id="docente"
		class="selectpicker show-tick form-control" data-live-search="true"
		multiple data-max-options="1" title="Selecione um docente"
		data-live-search-placeholder="Pesquisar" required disabled="disabled">
		<option value="${docente.id}" selected="selected">${docente.siape}-${docente.nome}</option>
	</select>
	<input type="hidden" name="docente.id" value="${docente.id}" />
</c:if>
<c:if test="${docente == null}">
	<select name="docente.id" id="docente"
		class="selectpicker show-tick form-control" data-live-search="true"
		multiple data-max-options="1" title="Selecione um docente"
		data-live-search-placeholder="Pesquisar" required disabled="disabled">
		<c:forEach var="forDocente" items="${docentes}">
			<option value="${forDocente.id}">${forDocente.siape}-${forDocente.nome}</option>
		</c:forEach>
	</select>
</c:if>
