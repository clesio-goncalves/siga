var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(document).ready(function() {
	$('input[type=radio]').change(function() {
		$.ajax({
			type : "POST",
			url : "filtro",
			cache : false,
			data : {
				tipo_atendimento : $("input[name='tipo_atendimento']:checked").val()
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
