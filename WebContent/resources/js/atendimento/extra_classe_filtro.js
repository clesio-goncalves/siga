var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
var docente_id = $("input[name='docente_id']").val();

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
	$("select[name='docente']").val("").selectpicker("refresh"),
	$("select[name='status_atendimento']").val(""),
	$("input[name='local']").val(""),
	$("input[name='conteudo']").val("")
}

// Filtro
function filtrar(){
	var filtro_docente;
	if (docente_id != "") {
		filtro_docente = $("input[name='docente']").val();
	} else { 
		filtro_docente = $("select[name='docente'] :selected").val();
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
			docente : filtro_docente,
			status_atendimento : $("select[name='status_atendimento'] :selected").val(),
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
