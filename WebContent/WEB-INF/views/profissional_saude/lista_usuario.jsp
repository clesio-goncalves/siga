<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<label for="usuario" class="col-form-label">Usu�rio*</label> 
<select class="custom-select" name="usuario.id" required>
	<c:forEach var="usuario" items="${usuarios}">
		<option value="${usuario.id}">${usuario.usuario}</option>
	</c:forEach>
</select>
