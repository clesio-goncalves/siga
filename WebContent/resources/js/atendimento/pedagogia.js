var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(document).ready(function() {
	$('.maskTelefone').mask('(00) 0000-00009');
});

/**
 * Busca as turmas com base no curso selecionado
 * 
 * @returns {undefined}
 */
function alteraCurso(contexto) {
	$.ajax({
		type : "POST",
		url : "filtro_turma",
		cache : false,
		data : {
			contexto : contexto,
			curso_id : $("select[name='curso.id'] :selected").val(),
			monitor_id : $("input[name='monitor_id']").val()
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

/**
 * Busca as alunos com base na turma selecionada
 * 
 * @returns {undefined}
 */
function alteraTurma(contexto) {
	$.ajax({
		type : "POST",
		url : "filtro_aluno",
		cache : false,
		data : {
			contexto : contexto,
			turma_id : $("select[name='turma.id'] :selected").val()
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success : function(response) {
			$('#lista_alunos').html(response);
			$('#aluno').removeAttr('disabled');
			$('#aluno').selectpicker('refresh');
		},
		error : function() {
			alert("Ocorreu um erro");
		}
	});
}

function alteraOutros() {
	if (document.getElementById("customCheck5").checked == true) {
		$('#outros').removeAttr('disabled');
	} else {
		$('#outros').attr('disabled', "disabled");
	}
}