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

	/**
	 * Converts a Word document to a PDF file.
	 *
	 * @param docfilepath the file path of the input Word document
	 * @param pdffilepath the file path of the output PDF file
	 * @throws Exception if an error occurs during the conversion process
	 */
	@Override
	public void convertDocToPdf(String docfilepath, String pdffilepath) throws Exception {
		Document doc = new Document(docfilepath);
		doc.save(pdffilepath, SaveFormat.PDF);
	}

	/**
	 * Converts a PowerPoint presentation to a PDF file.
	 *
	 * @param pptfilepath the file path of the input PowerPoint presentation
	 * @param pdffilepath the file path of the output PDF file
	 * @throws Exception if an error occurs during the conversion process
	 */
	@Override
	public void convertPptToPdf(String pptfilepath, String pdffilepath) throws Exception {
		Presentation pres = new Presentation(pptfilepath);
		pres.save(pdffilepath, com.aspose.slides.SaveFormat.Pdf);
	}

	/**
	 * Converts an Excel spreadsheet to a PDF file.
	 *
	 * @param xlsfilepath the file path of the input Excel spreadsheet
	 * @param pdffilepath the file path of the output PDF file
	 * @throws Exception if an error occurs during the conversion process
	 */
	@Override
	public void convertXlsToPdf(String xlsfilepath, String pdffilepath) throws Exception {
		Workbook book = new Workbook(xlsfilepath);
		book.save(pdffilepath, com.aspose.cells.SaveFormat.PDF);
	}

	/**
	 * Converts a document file to a PDF file based on its file type.
	 *
	 * @param filepath the file path of the input document
	 * @param filename the name of the input file
	 * @param pdffilepath the file path of the output PDF file
	 * @return true if the conversion is successful, false if the file type is unsupported
	 * @throws Exception if an error occurs during the conversion process
	 */
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
	 * Adds an image to a specified page of a PDF at the given coordinates and dimensions.
	 *
	 * @param pdfPath the file path of the input PDF
	 * @param imagePath the file path of the image to be added
	 * @param outputPath the file path where the updated PDF should be saved
	 * @param pageNumber the page number (zero-based index) where the image should be inserted
	 * @param x the x-coordinate on the page where the image should be placed
	 * @param y the y-coordinate on the page where the image should be placed
	 * @param width the width of the image to be added
	 * @param height the height of the image to be added
	 * @throws IOException if an error occurs while processing the PDF or the image
	 */
	@Override
	public void addImageToPdf(String pdfPath, String imagePath, String outputPath, int pageNumber, float x, float y, float width, float height) throws IOException {
		try (PDDocument document = PDDocument.load(new File(pdfPath))) {
            PDPage page = document.getPage(pageNumber);

            // image Road
            PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, document);

            //Open the content stream in Append mode and add an image on the existing content
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page,
                                                                            PDPageContentStream.AppendMode.APPEND,
                                                                            true, true)) {
                // image Drawing (x, y, width, height)
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

			// Optimization
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