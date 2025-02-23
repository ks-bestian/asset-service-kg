package kr.co.bestiansoft.ebillservicekg.common.file.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.bestiansoft.ebillservicekg.common.file.repository.ComFileMapper;
import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import kr.co.bestiansoft.ebillservicekg.common.file.service.PdfService;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.ComFileVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.FileUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import kr.co.bestiansoft.ebillservicekg.document.repository.DocumentMapper;
import kr.co.bestiansoft.ebillservicekg.document.service.DocumentService;
import kr.co.bestiansoft.ebillservicekg.document.vo.FileVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ComFileServiceImpl implements ComFileService {

	private static final Logger logger = LoggerFactory.getLogger(ComFileServiceImpl.class);
    private final EDVHelper edv;
    private final ComFileMapper fileMapper;
	private final ExecutorService executorService;
	private final PdfService pdfService;
	private final DocumentMapper documentMapper;
	
	@Override
	public String saveFile(MultipartFile[] files) {
		String fileGroupId = StringUtil.getUUUID();

		for(MultipartFile file:files) {

			String fileId = StringUtil.getUUUID();
    		String orgFileNm = file.getOriginalFilename();

    		////////////////////////
			try (InputStream edvIs = file.getInputStream()){
				edv.save(fileId, edvIs);
			} catch (Exception edvEx) {
				throw new RuntimeException("EDV_NOT_WORK", edvEx);
			}
    		////////////////////////

			ComFileVo fileVo = new ComFileVo();
			fileVo.setFileGroupId(fileGroupId);
			fileVo.setFileId(fileId);
			fileVo.setOrgFileNm(orgFileNm);
			fileVo.setFileSize(file.getSize());
			fileVo.setUploadYn("Y");
			fileVo.setDeleteYn("N");

			fileMapper.insertFile(fileVo);
		}
		
		return fileGroupId;
	}
	
	void convertToPdfEbs(File tmpFile, String filename, String orgFileId) {
		executorService.submit(() -> {
			File pdfFile = null;
			try {
				pdfFile = File.createTempFile("tmp", null);
				boolean success = pdfService.convertToPdf(tmpFile.getAbsolutePath(), filename, pdfFile.getAbsolutePath());
				
				if(success) {
					String pdfFileId = StringUtil.getUUUID();
					try (InputStream edvIs = new FileInputStream(pdfFile)) {
						edv.save(pdfFileId, edvIs);
					} catch (Exception edvEx) {
						throw new RuntimeException("EDV_NOT_WORK", edvEx);
					}

					int idx = filename.lastIndexOf(".");
					String pdfFileNm = filename.substring(0, idx) + ".pdf";
					
					EbsFileVo fileVo = new EbsFileVo();
					fileVo.setOrgFileId(orgFileId);
					fileVo.setPdfFileId(pdfFileId);
					fileVo.setPdfFileNm(pdfFileNm);
					fileMapper.updateFileEbs(fileVo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(tmpFile != null) {
					tmpFile.delete();
				}
				if(pdfFile != null) {
					pdfFile.delete();
				}
			}
		});	
	}
	
	void convertToPdfEbs(MultipartFile mpf, String orgFileId) {
		try {
			String filename = mpf.getOriginalFilename();
			File tmpFile = File.createTempFile("tmp", null);
			mpf.transferTo(tmpFile);
			
			convertToPdfEbs(tmpFile, filename, orgFileId);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveFileEbs(MultipartFile[] files, String[] fileKindCdList, String billId) {

		if(files == null) return;
		
		List<Map<String, Object>> pdfJobs = new ArrayList<>();

		String[] fileKindCds = fileKindCdList;
		int idx = 0;
		for(MultipartFile file:files) {

			String orgFileId = StringUtil.getUUUID();
    		String orgFileNm = file.getOriginalFilename();
    		String fileKindCd = fileKindCds[idx];

    		////////////////////////
			try (InputStream edvIs = file.getInputStream()){
				edv.save(orgFileId, edvIs);
			} catch (Exception edvEx) {
				throw new RuntimeException("EDV_NOT_WORK", edvEx);
			}
    		////////////////////////

			EbsFileVo fileVo = new EbsFileVo();
			fileVo.setBillId(billId);

			fileVo.setOrgFileId(orgFileId);
			fileVo.setOrgFileNm(orgFileNm);
			fileVo.setFileSize(file.getSize());
			fileVo.setDeleteYn("N");
			fileVo.setOpbYn("N");
			fileVo.setFileKindCd(fileKindCd);
			idx++;
			fileMapper.insertFileEbs(fileVo);
			
			// pdf변환작업
			Map<String, Object> pdfJob = new HashMap<>();
			pdfJob.put("file", file);
			pdfJob.put("orgFileId", orgFileId);
			pdfJobs.add(pdfJob);
		}
		
		// pdf변환
		for(Map<String, Object> job : pdfJobs) {
			MultipartFile file = (MultipartFile)job.get("file");
			String orgFileId = (String)job.get("orgFileId");
			convertToPdfEbs(file, orgFileId);
		}
	}
	
	@Override
	public void saveFileEbs(String[] myFileIds, String[] fileKindCdList, String billId) throws Exception {
		
		if(myFileIds == null) return;

		List<Map<String, Object>> pdfJobs = new ArrayList<>();

		String[] fileKindCds = fileKindCdList;
		int idx = 0;
		for(String myFileId : myFileIds) {
			
			String userId = new SecurityInfoUtil().getAccountId();
			FileVo myFile = documentMapper.selectMyFile(userId, myFileId);

			String orgFileId = StringUtil.getUUUID();
    		String orgFileNm = myFile.getFileTitle();
    		String fileType = myFile.getFileType();
    		if(fileType != null && fileType.length() > 0) {
    			orgFileNm += "." + fileType;
    		}
    		String fileKindCd = fileKindCds[idx];
    		
    		InputStream myFileIs = edv.download(myFileId);
    		File tmpFile = File.createTempFile("tmp", null);
			IOUtils.copy(myFileIs, new FileOutputStream(tmpFile));
			
    		////////////////////////
			try (InputStream edvIs = new FileInputStream(tmpFile)){
				edv.save(orgFileId, edvIs);
			} catch (Exception edvEx) {
				throw new RuntimeException("EDV_NOT_WORK", edvEx);
			}
    		////////////////////////

			EbsFileVo fileVo = new EbsFileVo();
			fileVo.setBillId(billId);

			fileVo.setOrgFileId(orgFileId);
			fileVo.setOrgFileNm(orgFileNm);
			fileVo.setFileSize(myFile.getFileSize());
			fileVo.setDeleteYn("N");
			fileVo.setOpbYn("N");
			fileVo.setFileKindCd(fileKindCd);
			idx++;
			fileMapper.insertFileEbs(fileVo);
			
			// pdf변환작업
			Map<String, Object> pdfJob = new HashMap<>();
			pdfJob.put("tmpFile", tmpFile);
			pdfJob.put("filename", myFile.getFileNm());
			pdfJob.put("orgFileId", orgFileId);
			pdfJobs.add(pdfJob);
			
			myFileIs.close();
		}
		
		// pdf변환
		for(Map<String, Object> job : pdfJobs) {
			File tmpFile = (File)job.get("tmpFile");
			String filename = (String)job.get("filename");
			String orgFileId = (String)job.get("orgFileId");
			convertToPdfEbs(tmpFile, filename, orgFileId);
		}
	}
	
	@Override
	public List<ComFileVo> getFileList(String fileGroupId) {
		return fileMapper.findByFileGroupId(fileGroupId);
	}

	@Override
	public ComFileVo getFile(String fileId) {
		return fileMapper.findByFileId(fileId);
	}

	@Override
	public void batchFileDelete() {
		List<ComFileVo> fileList = fileMapper.batchGetDeleteFileList();
		for(ComFileVo file : fileList) {
			executorService.submit(new FileDeleteTask(file.getFileId()));
		}
	}


	private class FileDeleteTask implements Callable<String> {

        private String fileId;

        public FileDeleteTask(String fileId) {
            this.fileId = fileId;
        }

        @Override
        public String call() {

    		try {
            	edv.delete(this.fileId);

            	fileMapper.deleteServerFile(this.fileId);
            	logger.info("Success to delete file edv: {}", this.fileId);
                return "SUCCESS";
            } catch (Exception e) {
            	logger.error("Failed to delete file edv: {}", this.fileId , e);
            	return "FAIL";
            }
        }

    }

	@Override
	public void saveFileEbsMtng(MultipartFile[] files, String[] fileKindCdList, Long mtngId) {
		if(files == null) return;

		String[] fileKindCds = fileKindCdList;
		int idx = 0;
		for(MultipartFile file:files) {

			String orgFileId = StringUtil.getUUUID();
    		String orgFileNm = file.getOriginalFilename();
    		

    		////////////////////////
			try (InputStream edvIs = file.getInputStream()){
				edv.save(orgFileId, edvIs);
			} catch (Exception edvEx) {
				throw new RuntimeException("EDV_NOT_WORK", edvEx);
			}
    		////////////////////////
			String regId = new SecurityInfoUtil().getAccountId();
			EbsFileVo fileVo = new EbsFileVo();
			fileVo.setMtngId(mtngId);
			fileVo.setRegId(regId);
			fileVo.setOrgFileId(orgFileId);
			fileVo.setOrgFileNm(orgFileNm);
			fileVo.setFileSize(file.getSize());
			fileVo.setDeleteYn("N");
			if(fileKindCds!=null) {
				String fileKindCd = fileKindCds[idx];
				fileVo.setFileKindCd(fileKindCd);
			}
			
			idx++;
			fileMapper.insertFileEbsMtng(fileVo);
		}
		
	}

	@Override
	public void saveFileBillMng(EbsFileVo ebsFileVo) {
		if(ebsFileVo.getFiles() == null) return;
		
		for(MultipartFile file : ebsFileVo.getFiles()) {
			String orgFileId = StringUtil.getUUUID();
    		String orgFileNm = file.getOriginalFilename();
    		
    		////////////////////////
			try (InputStream edvIs = file.getInputStream()){
				edv.save(orgFileId, edvIs);
			} catch (Exception edvEx) {
				throw new RuntimeException("EDV_NOT_WORK", edvEx);
			}
    		////////////////////////
			
			String regId = new SecurityInfoUtil().getAccountId();
			EbsFileVo fileVo = new EbsFileVo();
			fileVo.setBillId(ebsFileVo.getBillId());

			fileVo.setOrgFileId(orgFileId);
			fileVo.setOrgFileNm(orgFileNm);
			fileVo.setFileSize(file.getSize());
			fileVo.setFileKindCd("200");
			fileVo.setClsCd("120");
			fileVo.setOpbYn("N");
			fileVo.setRegId(regId);
			fileVo.setRmk(ebsFileVo.getRmk());
			fileMapper.insertFileEbs(fileVo);
		}
	}


}