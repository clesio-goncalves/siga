var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(document).ready(function() {
	jqueryMask();
	configDatePicker();
});

/**
 * Função para máscara dos formulários
 * 
 * @returns {undefined}
 */
function jqueryMask() {
	$('.maskData').mask('00/00/0000');
	$('.maskHorario').mask('00:00');
}

/**
 * Seta uma configuração padrão para os datepicker
 * 
 * @returns {undefined}
 */
function configDatePicker() {
	$(".maskData").datepicker(
			{
				dateFormat : 'dd/mm/yy',
				dayNames : [ 'Domingo', 'Segunda', 'Ter&ccedil;a', 'Quarta',
						'Quinta', 'Sexta', 'S&aacute;bado' ],
				dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S', 'S', 'D' ],
				dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex',
						'Sáb', 'Dom' ],
				monthNames : [ 'Janeiro', 'Fevereiro', 'Mar&ccedil;o', 'Abril',
						'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro',
						'Outubro', 'Novembro', 'Dezembro' ],
				monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun',
						'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez' ],
				nextText : 'Pr&oacute;ximo',
				prevText : 'Anterior',
				language : 'pt-BR',
			});
	$(".maskHorario").timepicker({
		timeFormat : 'HH:mm',
		// hourMin : 7,
		// hourMax : 22,
		currentText : "Agora",
		closeText : "Fechar",
		timeOnlyTitle : "Selecionar Hor&aacute;rio",
		timeText : "Hor&aacute;rio",
		hourText : "Hora",
		minuteText : "Minuto"
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