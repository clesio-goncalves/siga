var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

// Reset
function limpar(){
	$("input[name='email']").val(""),
	$("select[name='perfil']").val(""),
	$("select[name='situacao']").val("")
}

// Filtro
function filtrar(){
	$.ajax({
		type : "POST",
		url : "filtrar",
		cache : false,
		data : {
			email : $("input[name='email']").val(),
			perfil : $("select[name='perfil'] :selected").val(),
			situacao : $("select[name='situacao'] :selected").val()
		},
		beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
		success : function(response) {
			$('#tabela').html(response);
			atualizaDataTable();
		},
		error : function() {
			alert("Ocorreu um erro");
		}
	});
}
