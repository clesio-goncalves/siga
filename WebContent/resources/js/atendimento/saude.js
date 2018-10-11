var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

/**
 * Busca as turmas com base no curso selecionado
 * @returns {undefined}
 */
function alteraCurso(contexto){
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

/**
 * Busca as alunos com base na turma selecionada
 * @returns {undefined}
 */
function alteraTurma(contexto){
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

$(document).ready(function() {
	$('input:radio[name=possui_problema]').change(function() {
		if (this.value == 'Sim') {
			$("input[name='esse_problema_dificulta_aprendizado']").removeAttr('disabled');
		} else {
			$("input[name='esse_problema_dificulta_aprendizado']").attr('disabled', "disabled");
		}
	});
});