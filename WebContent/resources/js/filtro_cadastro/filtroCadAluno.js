var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(document).ready(function() {
	$('.maskTelefone').mask('(00) 0000-00009');
});

function alteraCurso(contexto) {
	$.ajax({
		type : "POST",
		url : "filtro_turma",
		cache : false,
		data : {
			contexto : contexto,
			curso_id : $("select[name='curso.id'] :selected").val()
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success : function(response) {			
			$('#lista_turmas').html(response);
			$('#turma').removeAttr('disabled');
			$('#turma').selectpicker('refresh');
		},
		error : function() {
			alert("Ocorreu um erro");
		}
	});
}
