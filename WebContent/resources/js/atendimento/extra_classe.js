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
			turma_id : $("select[name='turma.id'] :selected").val()
		},
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success : function(response) {
			$('#lista_disciplinas').html(response);
			$('#disciplina').removeAttr('disabled');
			$('#disciplina').selectpicker('refresh');
			//$('#docente option').remove();
			//$('#docente').selectpicker('refresh');
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

/**
 * Desabilita curso, turma, aluno e disciplina
 * @returns {undefined}
 */
function alteraStatusAtendimento(contexto){
	if (document.getElementById("status_atendimento").checked == true) {
		$.ajax({
			type : "POST",
			url : "lista_docente",
			cache : false,
			data : {
				contexto : contexto,
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			success : function(response) {
				$('#lista_docentes').html(response);
				$('#curso').attr('disabled', "disabled");
				$('#curso').selectpicker('refresh');
				$('#turma').attr('disabled', "disabled");
				$('#turma').selectpicker('refresh');
				$('#aluno').attr('disabled', "disabled");
				$('#aluno').selectpicker('refresh');
				$('#disciplina').attr('disabled', "disabled");
				$('#disciplina').selectpicker('refresh');
				$('#docente').removeAttr('disabled');
				$('#docente').selectpicker('refresh');
				$("textarea[name='conteudo']").attr('readonly', "readonly");
				if (contexto == "novo"){
					$("textarea[name='conteudo']").val("-");
				}
			},
			error : function() {
				alert("Ocorreu um erro");
			}
		});
	} else {
		$('#curso').removeAttr('disabled');
		$('#curso').selectpicker('refresh');
		$("textarea[name='conteudo']").removeAttr('readonly');
		$('#docente').attr('disabled', "disabled");
		$('#docente').selectpicker('refresh');
		if (contexto == "novo"){
			$("textarea[name='conteudo']").val("-");
		}
	}
}