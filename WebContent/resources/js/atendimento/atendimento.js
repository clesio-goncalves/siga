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