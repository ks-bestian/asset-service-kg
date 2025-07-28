package kr.co.bestiansoft.ebillservicekg.common.file.service;

import java.io.IOException;

public interface PdfService {

	void convertDocToPdf(String docfilepath, String pdffilepath) throws Exception;
	void convertPptToPdf(String pptfilepath, String pdffilepath) throws Exception;
	void convertXlsToPdf(String xlsfilepath, String pdffilepath) throws Exception;
	boolean convertToPdf(String filepath, String filename, String pdffilepath) throws Exception;
	/**
	 *
	 * @param pdfPath PDF file channel
	 * @param imagePath To insert image file channel
	 * @param outputPath result PDF file channel
	 * @param pageNumber Image To insert page number (0from start)
	 * @param x Image To be inserted X coordinate (page Left side Bottom standard, point unit)
	 * @param y Image To be inserted Y coordinate (page Left side Bottom standard, point unit)
	 * @param width To be inserted Image width (point unit)
	 * @param height To be inserted Image height (point unit)
	 * @throws IOException
	 */
	void addImageToPdf(String pdfPath, String imagePath, String outputPath, int pageNumber, float x, float y,
			float width, float height) throws IOException;

}

