// Reset
$(document).ready(
		function() {
			$('#limpar').click(
					function() {
						$("#data_inicial").val(""), $("#data_final").val(""),
								$("#impressora :selected").text("Qualquer"), $(
										"#estacao").val(""), $(
										"#qnt_impressoes").val(""), $(
										"#usuario").val("")
					});
		});

// Filtro
$(document).ready(function() {
	$('#filtro').click(function() {
		$.ajax({
			type : "post",
			url : "filtrarImpressoes",
			cache : false,
			data : {
				data_inicial : $("#data_inicial").val(),
				data_final : $("#data_final").val(),
				nome_impressora : $("#impressora :selected").text(),
				nome_estacao : $("#estacao").val(),
				qnt_impressoes : $("#qnt_impressoes").val(),
				nome_usuario : $("#usuario").val()
			},
			success : function(response) {
				$('#tabela').html(response);
				atualiza();
			},
			error : function() {
				alert('Ocorreu um erro!');
			}
		});
	});
});

// Atualiza data table
function atualiza() {
	$('#table_id').DataTable({
		"language" : {
			"url" : "resources/idioma/Portuguese-Brasil.json"
		}
	});
}

$(function() {
	$(".data").datepicker(
			{
				dateFormat : 'dd/mm/yy',
				dayNames : [ 'Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta',
						'Sexta', 'Sábado' ],
				dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S', 'S', 'D' ],
				dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex',
						'Sáb', 'Dom' ],
				monthNames : [ 'Janeiro', 'Fevereiro', 'Março', 'Abril',
						'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro',
						'Outubro', 'Novembro', 'Dezembro' ],
				monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun',
						'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez' ],
				nextText : 'Próximo',
				prevText : 'Anterior'
			});
});
