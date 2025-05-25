package kr.co.bestiansoft.ebillservicekg.common.file.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspose.cells.Workbook;
import com.aspose.slides.Presentation;
import com.aspose.words.Document;
import com.aspose.words.PdfFontEmbeddingMode;
import com.aspose.words.PdfImageCompression;
import com.aspose.words.PdfSaveOptions;
import com.aspose.words.SaveFormat;

import kr.co.bestiansoft.ebillservicekg.common.file.service.PdfService;

@Service
public class PdfServiceImpl implements PdfService {

	@Autowired
	private EDVHelper edv;

	@Autowired
	private ExecutorService executorService;

	@Override
	public void convertDocToPdf(String docfilepath, String pdffilepath) throws Exception {
		Document doc = new Document(docfilepath);
		doc.save(pdffilepath, SaveFormat.PDF);
	}

	@Override
	public void convertPptToPdf(String pptfilepath, String pdffilepath) throws Exception {
		Presentation pres = new Presentation(pptfilepath);
		pres.save(pdffilepath, com.aspose.slides.SaveFormat.Pdf);
	}

	@Override
	public void convertXlsToPdf(String xlsfilepath, String pdffilepath) throws Exception {
		Workbook book = new Workbook(xlsfilepath);
		book.save(pdffilepath, com.aspose.cells.SaveFormat.PDF);
	}

	@Override
	public boolean convertToPdf(String filepath, String filename, String pdffilepath) throws Exception {
		if (filename.endsWith(".doc") || filename.endsWith(".docx")) {
			convertDocToPdf(filepath, pdffilepath);
			return true;
		}
		else if(filename.endsWith(".ppt") || filename.endsWith(".pptx")) {
			convertPptToPdf(filepath, pdffilepath);
			return true;
		}
		else if(filename.endsWith(".xls") || filename.endsWith(".xlsx")) {
			convertXlsToPdf(filepath, pdffilepath);
			return true;
		}
		return false;
	}
	
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
	@Override
	public void addImageToPdf(String pdfPath, String imagePath, String outputPath, int pageNumber, float x, float y, float width, float height) throws IOException {
		try (PDDocument document = PDDocument.load(new File(pdfPath))) {
            PDPage page = document.getPage(pageNumber);

            // 이미지 로드
            PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, document);

            // 콘텐츠 스트림을 append 모드로 열어서 기존 콘텐츠 위에 이미지를 추가
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page,
                                                                            PDPageContentStream.AppendMode.APPEND,
                                                                            true, true)) {
                // 이미지 그리기 (x, y, width, height)
                contentStream.drawImage(pdImage, x, y, width, height);
            }
//            document.setAllSecurityToBeRemoved(true);
            document.save(outputPath);
        } 
	}




	public static void main(String args[])  {

		//String docfilepath = "d:/imsi/25MB.doc";
		String docfilepath = "d:/imsi/1717.docx";
		String pdffilepath = "d:/imsi/output_low.pdf";

		Document doc;

		System.out.println("start");

		try {
			doc = new Document(docfilepath);

			// 최적화
//			doc.cleanup();
//			doc.joinRunsWithSameFormatting();
//			doc.updatePageLayout();

//			PdfSaveOptions options = new PdfSaveOptions();
//			options.setImageCompression(PdfImageCompression.JPEG);
//			options.setJpegQuality(60);
//			options.setEmbedFullFonts(false);
//			options.setFontEmbeddingMode(PdfFontEmbeddingMode.EMBED_ALL);
			//options.setTempFolder(new File("/tmp/aspose-temp"));
			doc.save(pdffilepath, SaveFormat.PDF);

			//doc.save(pdffilepath, options);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.toString());
		}




		System.out.println("end");


	}

}