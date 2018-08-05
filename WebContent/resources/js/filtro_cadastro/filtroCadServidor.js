var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(document).ready(function() {
	$('input[type=radio]').change(function() {
		$.ajax({
			type : "POST",
			url : "filtrarUsuariosServidor",
			cache : false,
			data : {
				funcao : $("input[name='funcao']:checked").val()
			},
			beforeSend: function(xhr) {
	            xhr.setRequestHeader(header, token);
	        },
			success : function(response) {
				$('#lista_usuarios').html(response);
			},
			error : function() {
				alert("Ocorreu um erro");
			}
		});
	});
});
