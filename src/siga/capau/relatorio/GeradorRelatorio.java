package siga.capau.relatorio;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class GeradorRelatorio {
	private String nomeRelatorio;
	private String nomeArquivo;
	private Map<String, Object> parametros;
	private JRBeanCollectionDataSource relatorio;

	public GeradorRelatorio(String nomeRelatorio, String nomeArquivo, Map<String, Object> parametros,
			JRBeanCollectionDataSource relatorio) {
		this.nomeRelatorio = nomeRelatorio;
		this.nomeArquivo = nomeArquivo;
		this.parametros = parametros;
		this.relatorio = relatorio;
	}

	public void geraPDFParaOutputStream(HttpServletResponse response) {

		try {

			// preenche relatorio
			JasperPrint jasperPrint = JasperFillManager.fillReport(this.nomeArquivo, this.parametros, this.relatorio);

			response.setContentType("application/pdf");
			response.addHeader("Content-disposition", "attachment; filename=\"" + this.nomeRelatorio + "\"");

			// exporta para pdf
			JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

			ServletOutputStream responseStream = response.getOutputStream();
			responseStream.flush();
			responseStream.close();

		} catch (JRException | IOException e) {
			throw new RuntimeException(e);

		}

	}

}
