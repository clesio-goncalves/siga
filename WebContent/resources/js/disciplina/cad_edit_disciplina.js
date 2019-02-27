$(document).ready(function() {
	// Handle form submission event
	$('#disciplina').on('submit', function(e) {

		var form = this;

		// Encode a set of form elements from all pages as an
		// array of names and values
		var params = jQuery.param($("table input[type='checkbox'], table select").serializeArray());

		// Iterate over all form elements
		$.each(params, function() {
			// If element doesn't exist in DOM
			if (!$.contains(document, form[this.name])) {
				// Create a hidden element
				$(form).append($('<input>').attr('type', 'hidden').attr('name', this.name).val(this.value));
			}
		});
	});
});


function selecionaTurma(id){
	if (document.getElementById("turma" + id).checked == true) {
		$('#docente' + id).removeAttr('disabled');
		$('#docente' + id).selectpicker('refresh');
	} else {
		$('#docente' + id).attr('disabled', "disabled");
		$('#docente' + id).selectpicker('refresh');
	}
}