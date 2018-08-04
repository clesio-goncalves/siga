<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<label for="usuario">Usuário*</label> 
<select class="form-control" name="usuario.id" required>
	<c:forEach var="usuario" items="${usuarios}">
		<option value="${usuario.id}">${usuario.usuario}</option>
	</c:forEach>
</select>
