package siga.capau.relatorio;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

public class CompilaJrxmlParaJasper {

	public static void main(String[] args) throws JRException {
		JasperCompileManager.compileReportToFile(
				"/home/clesio/eclipse-workspace/siga/WebContent/resources/relatorio/indisciplina/atendimento_indisciplina_diretor.jrxml");
	}
}