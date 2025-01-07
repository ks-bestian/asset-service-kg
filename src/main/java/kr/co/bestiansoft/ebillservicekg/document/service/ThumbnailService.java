package kr.co.bestiansoft.ebillservicekg.document.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

public interface ThumbnailService {
	
	public BufferedImage createThumbnail(InputStream is, String filename, int width, int height);
	public void createThumbnailAsync(File file, String filename, String fileId);
	public void createThumbnailAsync(File file, String filename, String fileId, int width, int height);
}
