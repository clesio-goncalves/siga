$(document).ready(function() {
	$('#tabela_id').DataTable({
		"language" : {
			"url" : "../resources/idioma/Portuguese-Brasil.json"
		},
		"order": [[ 0, "desc" ]]
	});
	$('[data-tooltip="tooltip"]').tooltip()
});