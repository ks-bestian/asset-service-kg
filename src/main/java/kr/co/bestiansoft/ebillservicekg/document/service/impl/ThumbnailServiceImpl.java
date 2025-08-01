package kr.co.bestiansoft.ebillservicekg.document.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspose.cells.ImageOrPrintOptions;
import com.aspose.cells.ImageType;
import com.aspose.cells.SheetRender;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.slides.ISlide;
import com.aspose.slides.Presentation;
import com.aspose.words.Document;
import com.aspose.words.SaveFormat;

import kr.co.bestiansoft.ebillservicekg.common.file.service.impl.EDVHelper;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import kr.co.bestiansoft.ebillservicekg.document.repository.DocumentMapper;
import kr.co.bestiansoft.ebillservicekg.document.service.ThumbnailService;
import kr.co.bestiansoft.ebillservicekg.document.vo.FileVo;

@Service
public class ThumbnailServiceImpl implements ThumbnailService {

	@Autowired
	private EDVHelper edv;

	@Autowired
	private ExecutorService executorService;

	@Autowired
	private DocumentMapper fileMapper;

	private int DEFAULT_THUMBNAIL_WIDTH = 300;
	private int DEFAULT_THUMBNAIL_HEIGHT = 200;

	InputStream asInputStream(BufferedImage bi) throws IOException {
		if(bi == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bi, "png", baos);
		return new ByteArrayInputStream(baos.toByteArray());
	}

	BufferedImage thumbnailDoc(InputStream is, int width, int height) throws Exception {
		Document doc = new Document(is);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		doc.save(output, SaveFormat.PNG);

		byte[] data = output.toByteArray();
		ByteArrayInputStream input = new ByteArrayInputStream(data);
		BufferedImage image = ImageIO.read(input);

		return Scalr.resize(image, width, height, null);
	}

	BufferedImage thumbnailPpt(InputStream is, int width, int height) throws Exception {
		Presentation pres = new Presentation(is);

		// User defined dimension
		int desiredX = width;
		int desiredY = height;

		// Getting scaled value of X and Y
		float ScaleX = (float) (1.0 / pres.getSlideSize().getSize().getWidth()) * desiredX;
		float ScaleY = (float) (1.0 / pres.getSlideSize().getSize().getHeight()) * desiredY;

		ISlide slide = pres.getSlides().get_Item(0);
        BufferedImage bi = slide.getThumbnail(ScaleX, ScaleY);

        return bi;
	}

	BufferedImage thumbnailXls(InputStream is, int width, int height) throws Exception {
		// Instantiate and open an Excel file
		Workbook book = new Workbook(is);

		// Define ImageOrPrintOptions
		ImageOrPrintOptions imgOptions = new ImageOrPrintOptions();
		// Set the vertical and horizontal resolution
		imgOptions.setVerticalResolution(200);
		imgOptions.setHorizontalResolution(200);
		// Set the image's format
		imgOptions.setImageType(ImageType.PNG);
		// One page per sheet is enabled
		imgOptions.setOnePagePerSheet(false);

		// Get the first worksheet
		Worksheet sheet = book.getWorksheets().get(0);
		// Render the sheet with respect to specified image/print options
		SheetRender sr = new SheetRender(sheet, imgOptions);

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		sr.toImage(0, output);

		byte[] data = output.toByteArray();
		ByteArrayInputStream input = new ByteArrayInputStream(data);
		BufferedImage image = ImageIO.read(input);
		return Scalr.resize(image, width, height, null);
	}

	BufferedImage thumbnailPdf(InputStream is, int width, int height) throws Exception {
		PDDocument document = PDDocument.load(is);
		PDFRenderer renderer = new PDFRenderer(document);

		BufferedImage image = renderer.renderImage(0);

		document.close();
		return Scalr.resize(image, width, height, null);
	}

	BufferedImage thumbnailImg(InputStream is, int width, int height) throws Exception {
		BufferedImage image = ImageIO.read(is);
		if (image == null) { // not an image
			return null;
		}
		return Scalr.resize(image, width, height, null);
	}


	@Override
	public BufferedImage createThumbnail(InputStream is, String filename, int width, int height) {

		try {
			if (filename.endsWith(".doc") || filename.endsWith(".docx")) {
				return thumbnailDoc(is, width, height);
			}
			else if(filename.endsWith(".ppt") || filename.endsWith(".pptx")) {
				return thumbnailPpt(is, width, height);
			}
			else if(filename.endsWith(".xls") || filename.endsWith(".xlsx")) {
				return thumbnailXls(is, width, height);
			}
			else if(filename.endsWith(".pdf")) {
				return thumbnailPdf(is, width, height);
			}
			else {
				return thumbnailImg(is, width, height);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void createThumbnailAsync(File file, String filename, String fileId) {
		createThumbnailAsync(file, filename, fileId, DEFAULT_THUMBNAIL_WIDTH, DEFAULT_THUMBNAIL_HEIGHT);
	}

	@Override
	public void createThumbnailAsync(File file, String filename, String fileId, int width, int height) {
		executorService.submit(() -> {
			try {
				InputStream is = new FileInputStream(file);
				BufferedImage image = createThumbnail(is, filename, width, height);

				if(image == null) {
					return;
				}

				String thumbnailFileId = StringUtil.getUUUID();

				try (InputStream tis = asInputStream(image)){
					edv.save(thumbnailFileId, tis);
				} catch (Exception edvEx) {
					throw new RuntimeException("EDV_NOT_WORK", edvEx);
				}
				is.close();

				FileVo vo = new FileVo();
				vo.setFileId(fileId);
				vo.setThumbnail(thumbnailFileId);
				fileMapper.updateThumbnail(vo);

				file.delete();

			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

}