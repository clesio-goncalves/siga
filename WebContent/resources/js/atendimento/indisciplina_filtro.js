var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

// Reset
function limpar(){
	$("input[name='data_inicial_atendimento']").val(""), 
	$("input[name='data_final_atendimento']").val(""),
	$("input[name='horario_inicial_atendimento']").val(""), 
	$("input[name='horario_final_atendimento']").val(""),
	$("select[name='curso']").val("").selectpicker("refresh"),
	$("select[name='turma']").val("").selectpicker("refresh"),
	$("select[name='advertido']").val(""),
	$("select[name='tipo_advertencia']").val(""),
	$("select[name='aluno']").val("").selectpicker("refresh"),
	$("select[name='profissional']").val("").selectpicker("refresh"),
	$("input[name='descricao']").val("")
}

// Filtro
function filtrar(){
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
			advertido : $("select[name='advertido'] :selected").val(),
			tipo_advertencia : $("select[name='tipo_advertencia'] :selected").val(),
			aluno : $("select[name='aluno'] :selected").val(),
			profissional : $("select[name='profissional'] :selected").val(),
			descricao : $("input[name='descricao']").val()
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
		url : "filtro_turma_lista_atendimento_indisciplina",
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

$(document).ready(function() {
	$('select[name=advertido]').change(function() {
		if (this.value == 'Sim') {
			$("select[name='tipo_advertencia']").removeAttr('disabled');
		} else {
			$("select[name='tipo_advertencia']").attr('disabled', "disabled");
		}
	});
});
