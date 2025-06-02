package kr.co.bestiansoft.ebillservicekg.common.file.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface PdfService {

	void convertDocToPdf(String docfilepath, String pdffilepath) throws Exception;
	void convertPptToPdf(String pptfilepath, String pdffilepath) throws Exception;
	void convertXlsToPdf(String xlsfilepath, String pdffilepath) throws Exception;
	boolean convertToPdf(String filepath, String filename, String pdffilepath) throws Exception;
	/**
	 * 
	 * @param pdfPath PDF 파일 경로
	 * @param imagePath 삽입할 이미지 파일 경로
	 * @param outputPath 결과 PDF 파일 경로
	 * @param pageNumber 이미지를 삽입할 페이지 번호 (0부터 시작)
	 * @param x 이미지가 삽입될 X 좌표 (페이지 좌측 하단 기준, 포인트 단위)
	 * @param y 이미지가 삽입될 Y 좌표 (페이지 좌측 하단 기준, 포인트 단위)
	 * @param width 삽입될 이미지의 너비 (포인트 단위)
	 * @param height 삽입될 이미지의 높이 (포인트 단위)
	 * @throws IOException 
	 */
	void addImageToPdf(String pdfPath, String imagePath, String outputPath, int pageNumber, float x, float y,
			float width, float height) throws IOException;
	
}

