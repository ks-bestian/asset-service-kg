package kr.co.bestiansoft.ebillservicekg.common.file.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aspose.cells.Workbook;
import com.aspose.slides.Presentation;
import com.aspose.words.Document;
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
}