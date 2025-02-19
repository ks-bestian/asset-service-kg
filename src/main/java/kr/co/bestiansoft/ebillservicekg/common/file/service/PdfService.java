package kr.co.bestiansoft.ebillservicekg.common.file.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface PdfService {

	void convertDocToPdf(String docfilepath, String pdffilepath) throws Exception;
	void convertPptToPdf(String pptfilepath, String pdffilepath) throws Exception;
	void convertXlsToPdf(String xlsfilepath, String pdffilepath) throws Exception;
	boolean convertToPdf(String filepath, String filename, String pdffilepath) throws Exception;
	
}
