var password = document.getElementById("senha"), confirm_password = document
		.getElementById("repetir_senha");

function validatePassword() {
	if (password.value != confirm_password.value) {
		confirm_password.setCustomValidity("Erro: senha incorreta!");
	} else {
		confirm_password.setCustomValidity('');
	}
}

password.onchange = validatePassword;
confirm_password.onkeyup = validatePassword;