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
			$('#docente option').remove();
			$('#docente').selectpicker('refresh');
		},
		error : function() {
			alert("Ocorreu um erro");
		}
	});
}

/**
 * Busca os docentes com base na turma do aluno e na disciplina selecionada
 * @returns {undefined}
 */
function alteraDisciplina(contexto){
	$.ajax({
		type : "POST",
		url : "filtro_docente",
		cache : false,
		data : {
			contexto : contexto,
			aluno_id : $("select[name='aluno.id'] :selected").val(),
			disciplina_id : $("select[name='disciplina.id'] :selected").val()
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success : function(response) {
			$('#lista_docentes').html(response);
			$('#docente').removeAttr('disabled');
			$('#docente').selectpicker('refresh');
		},
		error : function() {
			alert("Ocorreu um erro");
		}
	});
}