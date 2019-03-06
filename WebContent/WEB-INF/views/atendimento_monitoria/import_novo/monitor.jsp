<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<label for="monitor.id" class="col-form-label">Monitor<span
	class="obrigatorio">*</span></label>
<c:if test="${monitor != null}">
	<select name="" id="monitor"
		class="selectpicker show-tick form-control" data-live-search="true"
		multiple data-max-options="1" title="Selecione um monitor"
		data-live-search-placeholder="Pesquisar" required disabled="disabled">
		<option value="${monitor.id}" selected="selected">${monitor.matricula}-${monitor.nome}</option>
	</select>
	<input type="hidden" name="monitor.id" value="${monitor.id}" />
</c:if>
<c:if test="${monitor == null}">
	<select name="monitor.id" id="monitor"
		class="selectpicker show-tick form-control" data-live-search="true"
		multiple data-max-options="1" title="Selecione um monitor"
		data-live-search-placeholder="Pesquisar" required>
		<c:forEach var="monitor" items="${monitores}">
			<option value="${monitor.id}"
				${atendimento_monitoria.monitor.id == monitor.id ? 'selected' : ''}>${monitor.matricula}-${monitor.nome}</option>
		</c:forEach>
	</select>
</c:if>
