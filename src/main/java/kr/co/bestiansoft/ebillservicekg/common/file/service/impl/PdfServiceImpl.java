package kr.co.bestiansoft.ebillservicekg.common.file.service.impl;

import java.io.File;
import java.util.concurrent.ExecutorService;

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