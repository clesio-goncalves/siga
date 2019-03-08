<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<label for="usuario.id" class="col-form-label">Usuário<span
	class="obrigatorio">*</span></label>
<div class="input-group mb-3">
	<div class="input-group-prepend">
		<span class="input-group-text">@</span>
	</div>
	<select class="custom-select" name="usuario.id" required>
		<c:forEach var="usuario" items="${usuarios}">
			<option value="${usuario.id}">${usuario.email}</option>
		</c:forEach>
	</select>
</div>