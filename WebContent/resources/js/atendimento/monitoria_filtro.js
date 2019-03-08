var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var monitor_id = $("input[name='monitor_id']").val();

// Reset
function limpar(){
	$("input[name='data_inicial_atendimento']").val(""), 
	$("input[name='data_final_atendimento']").val(""),
	$("input[name='horario_inicial_atendimento']").val(""), 
	$("input[name='horario_final_atendimento']").val(""),
	$("select[name='curso']").val("").selectpicker("refresh"),
	$("select[name='turma']").val("").selectpicker("refresh"),
	$("select[name='disciplina']").val("").selectpicker("refresh"),
	$("select[name='aluno']").val("").selectpicker("refresh"),
	$("select[name='monitor']").val("").selectpicker("refresh"),
	$("input[name='local']").val(""),
	$("input[name='conteudo']").val(""),
	$("input[name='dificuldades_diagnosticadas']").val(""),
	$("select[name='status_atendimento']").val("")
}

// Filtro
function filtrar(){
	var filtro_monitor;
	if (monitor_id != "") {
		filtro_monitor = $("input[name='monitor']").val();
	} else { 
		filtro_monitor = $("select[name='monitor'] :selected").val();
	}
	$.ajax({
		type : "POST",
		url : "filtrar",
		cache : false,
		data : {
			data_inicial_atendimento : $("input[name='data_inicial_atendimento']").val(), 
			data_final_atendimento : $("input[name='data_final_atendimento']").val(),
			horario_inicial_atendimento : $("input[name='horario_inicial_atendimento']").val(), 
			horario_final_atendimento : $("input[name='horario_final_atendimento']").val(),
			curso : $("select[name='curso'] :selected").val(),
			turma : $("select[name='turma'] :selected").val(),
			disciplina : $("select[name='disciplina'] :selected").val(),
			aluno : $("select[name='aluno'] :selected").val(),
			monitor : filtro_monitor,
			local : $("input[name='local']").val(),
			conteudo : $("input[name='conteudo']").val(),
			dificuldades_diagnosticadas : $("input[name='dificuldades_diagnosticadas']").val(),
			status_atendimento : $("select[name='status_atendimento'] :selected").val()
		},
		beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
		success : function(response) {
			$('#tabela').html(response);
			atualizaDataTableAtendimento();
		},
		error : function() {
			alert("Ocorreu um erro");
		}
	});
}

/**
 * Busca as turmas com base no curso selecionado
 * 
 * @returns {undefined}
 */
function alteraCurso() {
	$.ajax({
		type : "POST",
		url : "filtro_turma_lista_atendimento_monitoria",
		cache : false,
		data : {
			curso : $("select[name='curso'] :selected").val()
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
