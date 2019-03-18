var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

function advertenciaEscrita() {
	$("textarea[name='motivo_advertencia']").val("");
	$("textarea[name='motivo_advertencia']").removeClass('is-invalid');
}

function gerarPDF() {
	var motivo_advertencia = $("textarea[name='motivo_advertencia']").val();
	var precedida_advertencia_verbal = $(
			"select[name='precedida_advertencia_verbal']").val();

	if (motivo_advertencia != "") {
		$.ajax({
			type : "POST",
			url : "advertencia_escrita",
			cache : false,
			data : {
				motivo_advertencia : motivo_advertencia,
				precedida_advertencia_verbal : precedida_advertencia_verbal
			},
			beforeSend : function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			xhrFields: {
	            responseType: 'blob'
	        },
			success : function (data) {
	            var a = document.createElement('a');
	            var url = window.URL.createObjectURL(data);
	            a.href = url;
	            a.download = 'Termo de Advertencia Escrita.pdf';
	            a.click();
	            window.URL.revokeObjectURL(url);
	            $('#modal_advertencia_escrita').modal('hide');
	        },
			error : function() {
				alert("Ocorreu um erro");
			}
		});
	} else {
		$("textarea[name='motivo_advertencia']").addClass("is-invalid");
	}
}