var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

// Reset
function limpar(){
	$("input[name='data_inicial_atendimento']").val(""), 
	$("input[name='data_final_atendimento']").val(""),
	$("input[name='horario_inicial_atendimento']").val(""), 
	$("input[name='horario_final_atendimento']").val(""),
	$("select[name='aluno']").val("").selectpicker("refresh"),
	$("select[name='disciplina']").val("").selectpicker("refresh"),
	$("select[name='monitor']").val("").selectpicker("refresh"),
	$("input[name='dificuldades_diagnosticadas']").val(""),
	$("input[name='local']").val(""),
	$("input[name='conteudo']").val("")
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
			aluno : $("select[name='aluno'] :selected").val(),
			disciplina : $("select[name='disciplina'] :selected").val(),
			monitor : $("select[name='monitor'] :selected").val(),
			dificuldades_diagnosticadas : $("input[name='dificuldades_diagnosticadas']").val(),
			local : $("input[name='local']").val(),
			conteudo : $("input[name='conteudo']").val()
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
