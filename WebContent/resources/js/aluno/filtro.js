var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

function atualizaDataTableAtendimento(){
	$('#tabela_id').DataTable({
		"order": [[ 0, "desc" ]],
		"language" : {
			"url" : "../resources/idioma/Portuguese-Brasil.json"
		}
	});
	$('[data-tooltip="tooltip"]').tooltip()
}

// Reset
function limpar(){
	$("select[name='curso']").val("").selectpicker("refresh"),
	$("select[name='turma']").val("").selectpicker("refresh"),
	$("select[name='situacao']").val(""),
	$("input[name='nome']").val(""),
	$("input[name='matricula']").val(""),
	$("input[name='telefone']").val(""),
	$("input[name='usuario']").val(""),
	$("select[name='atendimentos']").val("")
}

// Filtro
function filtrar(){
	$.ajax({
		type : "POST",
		url : "filtrar",
		cache : false,
		data : {
			curso : $("select[name='curso'] :selected").val(),
			turma : $("select[name='turma'] :selected").val(),
			situacao : $("select[name='situacao'] :selected").val(),
			nome : $("input[name='nome']").val(),
			matricula : $("input[name='matricula']").val(),
			telefone : $("input[name='telefone']").val(),
			usuario : $("input[name='usuario']").val(),
			atendimentos : $("select[name='atendimentos'] :selected").val()
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
		url : "filtro_turma_em_listagem",
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
