<div class="modal fade" id="modal_advertencia_escrita">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Termo de Advertência Escrita</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<!-- Motivo da Advertência -->
				<div class="form-group">
					<label for="motivo_advertencia"
						class="col-form-label col-form-label-sm">Motivo da
						Advertência<span class="obrigatorio">*</span>
					</label>
					<textarea class="form-control form-control-sm"
						name="motivo_advertencia" rows="10" required maxlength="3000"></textarea>
				</div>

				<!-- Precedida de Advertência Verbal -->
				<div class="form-group">
					<label for="precedida_advertencia_verbal"
						class="col-form-label col-form-label-sm">Precedida de
						Advertencia Verbal?<span class="obrigatorio">*</span>
					</label> <select class="form-control form-control-sm"
						name="precedida_advertencia_verbal" required>
						<option value="Sim">Sim</option>
						<option value="Não">Não</option>
					</select>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-outline-danger"
					onclick="gerarPDF()">
					<span class="glyphicon glyphicon-file"></span> Gerar PDF
				</button>
				<button type="button" class="btn btn-secondary" data-dismiss="modal">
					<span class="glyphicon glyphicon-log-out"></span> Fechar
				</button>
			</div>
		</div>
	</div>
</div>