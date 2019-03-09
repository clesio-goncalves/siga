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
	$("select[name='ano_ingresso']").val(""),
	$("select[name='periodo_ingresso']").val(""),
	$("select[name='tipo_turma']").val(""),
	$("select[name='situacao']").val("")
}

// Filtro
function filtrar(){
	$.ajax({
		type : "POST",
		url : "filtrar",
		cache : false,
		data : {
			curso : $("select[name='curso'] :selected").val(),
			ano_ingresso : $("select[name='ano_ingresso'] :selected").val(),
			periodo_ingresso : $("select[name='periodo_ingresso'] :selected").val(),
			tipo_turma : $("select[name='tipo_turma'] :selected").val(),
			situacao : $("select[name='situacao'] :selected").val()
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
