var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

/**
 * Busca as disciplinas com base na turma do aluno
 * @returns {undefined}
 */
function alteraAluno(contexto){
	$.ajax({
		type : "POST",
		url : "filtro_disciplina",
		cache : false,
		data : {
			contexto : contexto,
			aluno_id : $("select[name='aluno.id'] :selected").val()
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success : function(response) {
			$('#lista_disciplinas').html(response);
			$('#disciplina').removeAttr('disabled');
			$('#disciplina').selectpicker('refresh');
		},
		error : function() {
			alert("Ocorreu um erro");
		}
	});
}