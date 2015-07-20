package com.myCompany.apps.jasper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import com.myCompany.apps.utils.JSONUtils;
import com.myCompany.apps.utils.JasperUtils;
import com.myCompany.apps.vo.JasperVo;

public class MyJasperReport {
	private static final JasperUtils JASPER_UTILS = new JasperUtils();

	private static final MyJasperReport JASPER_REPORT = new MyJasperReport();

	Connection connection;

	public static void main(String args[]) throws Exception {
		// Example for paramter substitutions
		parameterSubstitution();
		// Example for database connections
		queryPDF();
		// Example for collection object usage in jasper reports
		collectionSubstitution();
	}

	@SuppressWarnings("unchecked")
	public static void parameterSubstitution() throws Exception {

		String jrxmlString = JASPER_UTILS.readFromFile("PRM_ITEM_LABEL.jrxml");

		String jsonString = JASPER_UTILS.readFromFile("PRM_ITEM_LABEL.json");
		Map<String, Object> map = new HashMap<>();
		map = JSONUtils.getObjectFromJSONString(jsonString, map.getClass());
		byte[] pdfArray = JASPER_REPORT.generatePDF(jrxmlString, map);

		JASPER_UTILS.generatePDFFromFile(pdfArray, "output/PDF_PRM_ITEM_LABEL_"
				+ JASPER_UTILS.getDateString() + ".pdf");
	}

	public static void queryPDF() throws Exception {

		String jrxmlString = JASPER_UTILS
				.readFromFile("readfromdbsample.jrxml");

		byte[] pdfArray = JASPER_REPORT.generatePDF(jrxmlString);

		JASPER_UTILS.generatePDFFromFile(pdfArray,
				"output/PDF_readfromdbsample_" + JASPER_UTILS.getDateString()
						+ ".pdf");
	}

	public static void collectionSubstitution() throws Exception {

		String jrxmlString = JASPER_UTILS
				.readFromFile("RTV_INVOICE_TEMPLATE.jrxml");

		String jsonString = JASPER_UTILS
				.readFromFile("RTV_INVOICE_TEMPLATE.json");

		JasperVo jasperVo = JSONUtils.getObjectFromJSONString(jsonString,
				JasperVo.class);

		byte[] pdfArray = JASPER_REPORT.generatePDF(jrxmlString,
				jasperVo.getTemplateAttributeMap(),
				jasperVo.getJasperCollection());

		JASPER_UTILS.generatePDFFromFile(
				pdfArray,
				"output/PDF_RTV_INVOICE_TEMPLATE_"
						+ JASPER_UTILS.getDateString() + ".pdf");
	}

	public byte[] generatePDF(String jrxmlString) throws Exception {

		byte[] pdfByteArray = null;
		try {

			Class.forName("com.mysql.jdbc.Driver");

			connection = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/notification?user=root&password=root");

			InputStream inputStream = new ByteArrayInputStream(
					jrxmlString.getBytes("UTF-8"));
			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			JasperReport jasperReport = JasperCompileManager
					.compileReport(jasperDesign);

			pdfByteArray = JasperRunManager.runReportToPdf(jasperReport,
					new HashMap<String, Object>(), connection);

		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException " + e);
		} catch (JRException e) {
			System.out.println("JRException " + e);
		}
		return pdfByteArray;

	}

	public byte[] generatePDF(String jrxmlString, Map<String, Object> map)
			throws Exception {
		byte[] pdfByteArray = null;
		try {
			InputStream inputStream = new ByteArrayInputStream(
					jrxmlString.getBytes("UTF-8"));
			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			JasperReport jasperReport = JasperCompileManager
					.compileReport(jasperDesign);

			pdfByteArray = JasperRunManager.runReportToPdf(jasperReport, map,
					new JREmptyDataSource());

		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException " + e);
		} catch (JRException e) {
			System.out.println("JRException " + e);
		}
		return pdfByteArray;
	}

	public byte[] generatePDF(String jrxmlString, Map<String, Object> map,
			Object jasperCollection) throws Exception {
		byte[] pdfByteArray = null;
		try {
			InputStream inputStream = new ByteArrayInputStream(
					jrxmlString.getBytes("UTF-8"));
			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			JasperReport jasperReport = JasperCompileManager
					.compileReport(jasperDesign);

			pdfByteArray = JasperRunManager.runReportToPdf(jasperReport, map,
					new JREmptyDataSource());

			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(
					(Collection<?>) jasperCollection);
			pdfByteArray = JasperRunManager.runReportToPdf(jasperReport, map,
					beanColDataSource);

		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException " + e);
		} catch (JRException e) {
			System.out.println("JRException " + e);
		}
		return pdfByteArray;
	}

}
