$(document).ready(function() {
	$('#tabela_id').DataTable({
		"order": [[ 0, "desc" ]],
		"language" : {
			"url" : "../resources/idioma/Portuguese-Brasil.json"
		}
	});
	$('[data-tooltip="tooltip"]').tooltip()
});