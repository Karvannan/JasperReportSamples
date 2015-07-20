package com.myCompany.apps.utils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JasperUtils {

	public final InputStream readStream(String fileName) throws Exception {
		return this.getClass().getClassLoader().getResourceAsStream(fileName);
	}

	public final String getDateString() {
		Date date = new Date();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"YYYY MM dd ss");
		simpleDateFormat.format(date);
		return simpleDateFormat.format(date);
	}

	public final String readFromFile(String fileName) throws Exception {
		return getStringFromStream(readStream(fileName));
	}

	public final void generatePDFFromFile(byte[] pdfByteArray, String fileName)
			throws IOException {
		FileOutputStream fos = new FileOutputStream(fileName);
		fos.write(pdfByteArray);
		fos.close();
	}

	public final String getStringFromStream(InputStream is) throws Exception {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}

}
