package kr.co.bestiansoft.ebillservicekg.common.file.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import kr.co.bestiansoft.ebillservicekg.bill.review.billMng.vo.BillMngVo;
import kr.co.bestiansoft.ebillservicekg.common.file.repository.ComFileMapper;
import kr.co.bestiansoft.ebillservicekg.common.file.service.ComFileService;
import kr.co.bestiansoft.ebillservicekg.common.file.service.PdfService;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.ComFileVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileUpload;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import kr.co.bestiansoft.ebillservicekg.document.repository.DocumentMapper;
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

	/**
	 * Saves the provided array of files to the storage and persists metadata to the database.
	 * Each file is assigned a unique identifier and stored individually.
	 *
	 * @param files an array of {@code MultipartFile} objects to be saved
	 * @return the unique identifier of the last saved file
	 * @throws RuntimeException if an error occurs during file saving to the storage
	 */
	@Override
	public String saveFile(MultipartFile[] files) {
		String userId = new SecurityInfoUtil().getAccountId();
		String fileGroupId = StringUtil.getUUUID();
        String fileId = null;

		for(MultipartFile file:files) {

			fileId = StringUtil.getUUUID();
			String orgFileNm = file.getOriginalFilename();

    		////////////////////////
			try (InputStream edvIs = file.getInputStream()){
				System.out.println(file.getInputStream());
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
			fileVo.setRegId(userId);

			fileMapper.insertFile(fileVo);
			convertToPdfComFile(file, fileVo.getFileId());
		}
		return fileId;
	}

	/**
	 * Saves a list of files to a storage system and records associated metadata in the database.
	 * Every file is saved with a unique identifier, and its metadata is stored for tracking.
	 * The method returns a unique identifier for the group of files.
	 *
	 * @param files the array of files to be saved; each file is processed, stored, and its metadata recorded
	 * @return a unique file group identifier representing the group of files processed and saved
	 */
	@Override
	public String saveFileList(MultipartFile[] files) {
		String userId = new SecurityInfoUtil().getAccountId();
		String fileGroupId = StringUtil.getUUUID();
		String fileId = null;

		for(MultipartFile file:files) {

			fileId = StringUtil.getUUUID();
			String orgFileNm = file.getOriginalFilename();

			////////////////////////
			try (InputStream edvIs = file.getInputStream()){
				System.out.println(file.getInputStream());
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
			fileVo.setRegId(userId);

			fileMapper.insertFile(fileVo);
			convertToPdfComFile(file, fileVo.getFileId());
		}
		return fileGroupId;
	}

	/**
	 * Converts the given multipart file to a PDF-compatible file using the specified original file ID.
	 *
	 * @param mpf the multipart file to be converted
	 * @param orgFileId the original file ID associated with the file
	 */
	void convertToPdfComFile(MultipartFile mpf, String orgFileId) {
		File tmpFile = null;
		try {
			tmpFile = File.createTempFile("tmp", null);
			mpf.transferTo(tmpFile);
			convertToPdfComFile(tmpFile, mpf.getOriginalFilename(), orgFileId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Converts the given temporary file to a PDF and saves the file details in the database.
	 * The method handles file conversion, saving the PDF to storage, updating file information,
	 * and cleans up the temporary files after processing.
	 *
	 * @param tmpFile    The temporary file to be converted to a PDF.
	 * @param filename   The original filename of the temporary file.
	 * @param orgFileId  The identifier for the original file used for mapping the converted PDF.
	 */
	void convertToPdfComFile(File tmpFile, String filename, String orgFileId) {
			File pdfFile = null;
			try {
				pdfFile = File.createTempFile("tmp", null);
				boolean success = pdfService.convertToPdf(tmpFile.getAbsolutePath(), filename, pdfFile.getAbsolutePath());

				if (success) {
					String pdfFileId = StringUtil.getUUUID();
					try (InputStream edvIs = new FileInputStream(pdfFile)) {
						edv.save(pdfFileId, edvIs);
					} catch (Exception edvEx) {
						throw new RuntimeException("EDV_NOT_WORK", edvEx);
					}

					int idx = filename.lastIndexOf(".");
					String pdfFileNm = filename.substring(0, idx) + ".pdf";

					ComFileVo fileVo = new ComFileVo();
					fileVo.setFileId(orgFileId);
					fileVo.setOrgFileNm(filename);
					fileVo.setPdfFileId(pdfFileId);
					fileVo.setPdfFileNm(pdfFileNm);
					fileMapper.updatePdfFileInfo(fileVo);

				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (tmpFile != null) {
					tmpFile.delete();
				}
				if (pdfFile != null) {
					pdfFile.delete();
				}
			}
	}

	/**
	 * Converts a given temporary file to a PDF and saves the converted file metadata to the database.
	 * The method is executed asynchronously using an executor service.
	 *
	 * @param tmpFile   The temporary file to be converted to PDF. This file should be a valid file object.
	 * @param filename  The original filename of the file. Used to determine the output PDF filename.
	 * @param orgFileId The ID of the original file. This is used to associate the converted PDF file with its original file in the system.
	 */
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

	/**
	 * Converts a given file to a PDF format and processes it for the EBS system.
	 * This method accepts a multipart file, extracts its original file name,
	 * and handles the conversion process by first transferring the file to a temporary file.
	 *
	 * @param mpf      the multipart file to be converted to PDF
	 * @param orgFileId the original file identifier used for tracking and processing
	 */
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

	/**
	 * Converts a temporary file to a PDF, saves it to storage, and updates the database with the associated file information.
	 * The method runs asynchronously, submitting the task to an executor service.
	 *
	 * @param tmpFile   The temporary file to be converted to a PDF.
	 * @param filename  The original filename of the temporary file.
	 * @param orgFileId The unique identifier of the original file in the database.
	 */
	void convertToPdfEbsMtng(File tmpFile, String filename, String orgFileId) {
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
					fileMapper.updateFileEbsMtng(fileVo);
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

	/**
	 * Converts the provided multipart file to a PDF format, associating it with a specific organizational file identifier.
	 * This method handles the file transfer to a temporary file and invokes another method for the conversion process.
	 *
	 * @param mpf The multipart file to be converted to PDF. This contains the file to be processed.
	 * @param orgFileId The identifier of the original file for organizational purposes. It is used to associate
	 *                  the converted file with its corresponding record.
	 */
	void convertToPdfEbsMtng(MultipartFile mpf, String orgFileId) {
		try {
			String filename = mpf.getOriginalFilename();
			File tmpFile = File.createTempFile("tmp", null);
			mpf.transferTo(tmpFile);

			convertToPdfEbsMtng(tmpFile, filename, orgFileId);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves multiple files to EBS (Enterprise Business System) storage.
	 *
	 * @param files         An array of {@link MultipartFile} objects representing the files to be saved.
	 * @param fileKindCdList An array of strings specifying the file kind codes corresponding to each file.
	 * @param opbYnList      An array of strings indicating OPB (On-Premise Backup) flag for each file.
	 * @param billId         A string representing the bill ID associated with the files.
	 */
	@Transactional
	@Override
	public void saveFileEbs(MultipartFile[] files, String[] fileKindCdList, String[] opbYnList, String billId) {
		if(files == null) return;
		
		for(int i = 0; i < files.length; ++i) {
			MultipartFile file = files[i];
			String fileKindCd = fileKindCdList[i];
			String opbYn = opbYnList[i];
			String clsCd = null;
			Long detailSeq = null;
			saveFileEbs(file, fileKindCd, billId, clsCd, opbYn, detailSeq, null);
		}
	}

	/**
	 * Saves files to the EBS (Enterprise Business System) based on the provided file identifiers
	 * and associated metadata.
	 *
	 * @param myFileIds Array of file IDs to be processed for saving to the EBS. May not be null.
	 * @param fileKindCdList Array of file type codes corresponding to each file ID. Must have the same length as {@code myFileIds}.
	 * @param opbYnList Array of flags (e.g., Y/N) indicating some specific condition for each file. Must have the same length as {@code myFileIds}.
	 * @param billId Identifier for the bill associated with the files being saved. Cannot be null or empty.
	 * @throws Exception If an error occurs while saving files to the EBS.
	 */
	@Transactional
	@Override
	public void saveFileEbs(String[] myFileIds, String[] fileKindCdList, String[] opbYnList, String billId) throws Exception {

		if(myFileIds == null) return;

		for(int i = 0; i < myFileIds.length; ++i) {
			String myFileId = myFileIds[i];
			String fileKindCd = fileKindCdList[i];
			String opbYn = opbYnList[i];
			String clsCd = null;
			Long detailSeq = null;
			saveFileEbs(myFileId, fileKindCd, billId, clsCd, opbYn, detailSeq, null);
		}
	}

	/**
	 * Retrieves a list of files associated with the given file group ID.
	 *
	 * @param fileGroupId the identifier of the file group for which to fetch the files
	 * @return a list of ComFileVo objects representing the files in the specified file group
	 */
	@Override
	public List<ComFileVo> getFileList(String fileGroupId) {
		return fileMapper.findByFileGroupId(fileGroupId);
	}

	/**
	 * Retrieves a file based on the provided file ID.
	 *
	 * @param fileId the unique identifier of the file to be retrieved
	 * @return a ComFileVo object containing the file details, or null if no file is found
	 */
	@Override
	public ComFileVo getFile(String fileId) {
		return fileMapper.findByFileId(fileId);
	}

	/**
	 * Deletes a batch of files asynchronously.
	 *
	 * This method retrieves a list of files marked for deletion using the
	 * batchGetDeleteFileList method from the fileMapper. For each file in the list,
	 * a deletion task is submitted to be executed by the executor service.
	 *
	 * The deletion tasks are handled in parallel to improve performance, utilizing
	 * the executor service for managing threads.
	 */
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

	/**
	 * Saves files associated with a meeting in the EBS system. The method processes
	 * the provided files, stores metadata, and converts files to PDF format.
	 *
	 * @param files          an array of {@code MultipartFile} objects representing the uploaded files to be saved.
	 *                        If {@code null}, the method exits without performing any further actions.
	 * @param fileKindCdList an array of {@code String} representing file kind codes, which correspond to the files
	 *                        in the {@code files} array. If {@code null}, file kind codes are not applied.
	 * @param mtngId         the unique identifier of the meeting to associate the saved files with. Required for
	 *                        storing file metadata.
	 */
	@Override
	public void saveFileEbsMtng(MultipartFile[] files, String[] fileKindCdList, Long mtngId) {
		if(files == null) return;
		
		List<Map<String, Object>> pdfJobs = new ArrayList<>();

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
			
			// pdfConversion
			Map<String, Object> pdfJob = new HashMap<>();
			pdfJob.put("file", file);
			pdfJob.put("orgFileId", orgFileId);
			pdfJobs.add(pdfJob);
		}
		
		// pdfconversion
		for(Map<String, Object> job : pdfJobs) {
			MultipartFile file = (MultipartFile)job.get("file");
			String orgFileId = (String)job.get("orgFileId");
			convertToPdfEbsMtng(file, orgFileId);
		}

	}

	/**
	 * Saves files related to EBS meetings by processing the provided file IDs and performing operations
	 * such as downloading, saving, and converting files to PDF.
	 *
	 * @param myFileIds      an array of file IDs to be processed; each ID represents a file that needs
	 *                       to be downloaded and saved
	 * @param fileKindCdList an array of file kind codes corresponding to the files being processed; each
	 *                       code represents the type or category of the file
	 * @param mtngId         the meeting ID to which the files are associated; this is used to associate
	 *                       the saved files with a specific meeting
	 * @throws Exception if any error occurs during the file processing, such as file download, saving, or transformation
	 */
	@Override
	public void saveFileEbsMtng(String[] myFileIds, String[] fileKindCdList, Long mtngId) throws Exception {

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

			String regId = new SecurityInfoUtil().getAccountId();
			EbsFileVo fileVo = new EbsFileVo();
			fileVo.setMtngId(mtngId);
			fileVo.setRegId(regId);
			fileVo.setOrgFileId(orgFileId);
			fileVo.setOrgFileNm(orgFileNm);
			fileVo.setFileSize(myFile.getFileSize());
			fileVo.setDeleteYn("N");
			if(fileKindCds!=null) {
				String fileKindCd = fileKindCds[idx];
				fileVo.setFileKindCd(fileKindCd);
			}

			idx++;
			fileMapper.insertFileEbsMtng(fileVo);
			
			// pdfConversion
			Map<String, Object> pdfJob = new HashMap<>();
			pdfJob.put("tmpFile", tmpFile);
			pdfJob.put("filename", myFile.getFileNm());
			pdfJob.put("orgFileId", orgFileId);
			pdfJobs.add(pdfJob);
	
			myFileIs.close();
		}
		
		// pdfconversion
		for(Map<String, Object> job : pdfJobs) {
			File tmpFile = (File)job.get("tmpFile");
			String filename = (String)job.get("filename");
			String orgFileId = (String)job.get("orgFileId");
			convertToPdfEbsMtng(tmpFile, filename, orgFileId);
		}
	}

//	@Override
//	public void saveFileBillMng(EbsFileVo ebsFileVo) {
//		if(ebsFileVo.getFiles() == null) return;
//
//		for(MultipartFile file : ebsFileVo.getFiles()) {
//			String orgFileId = StringUtil.getUUUID();
//    		String orgFileNm = file.getOriginalFilename();
//
//    		////////////////////////
//			try (InputStream edvIs = file.getInputStream()){
//				edv.save(orgFileId, edvIs);
//			} catch (Exception edvEx) {
//				throw new RuntimeException("EDV_NOT_WORK", edvEx);
//			}
//    		////////////////////////
//
//			String regId = new SecurityInfoUtil().getAccountId();
//			EbsFileVo fileVo = new EbsFileVo();
//			fileVo.setBillId(ebsFileVo.getBillId());
//
//			fileVo.setOrgFileId(orgFileId);
//			fileVo.setOrgFileNm(orgFileNm);
//			fileVo.setFileSize(file.getSize());
//			fileVo.setFileKindCd("200");
//			fileVo.setClsCd("120");
//			fileVo.setOpbYn("Y");
//			fileVo.setRegId(regId);
//			fileVo.setRmk(ebsFileVo.getRmk());
//			fileMapper.insertFileEbs(fileVo);
//		}
//	}

	/**
	 * Saves the details of file-related information associated with a bill management object.
	 * It processes the file uploads if available within the provided {@code billMngVo}.
	 *
	 * @param billMngVo the bill management object containing file-related data to be processed
	 * @throws Exception if an error occurs during the file saving process
	 */
	@Override
	public void saveFileBillDetailMng(BillMngVo billMngVo) throws Exception {
		
		if(billMngVo.getFileUploads() == null) {
			return;
		}
		
		for(EbsFileUpload fileUpload : billMngVo.getFileUploads()) {
			if(fileUpload.getFile() != null) {
				saveFileEbs(fileUpload.getFile(), fileUpload.getFileKindCd(), billMngVo.getBillId(), billMngVo.getClsCd(), fileUpload.getOpbYn(), billMngVo.getSeq(), fileUpload.getLngType());
			}
			else if(fileUpload.getFileId() != null) {
				saveFileEbs(fileUpload.getFileId(), fileUpload.getFileKindCd(), billMngVo.getBillId(), billMngVo.getClsCd(), fileUpload.getOpbYn(), billMngVo.getSeq(), fileUpload.getLngType());
			}
		}

//		if(billMngVo.getFiles() != null) {
//			for(MultipartFile file : billMngVo.getFiles()) {
//				String opbYn = "Y";
//				saveFileEbs(file, billMngVo.getFileKindCd(), billMngVo.getBillId(), billMngVo.getClsCd(), opbYn, billMngVo.getSeq());
//			}	
//		}
//		if(billMngVo.getMyFileIds() != null) {
//			for(String myFileId : billMngVo.getMyFileIds()) {
//				String opbYn = "Y";
//				saveFileEbs(myFileId, billMngVo.getFileKindCd(), billMngVo.getBillId(), billMngVo.getClsCd(), opbYn, billMngVo.getSeq());
//			}
//		}
	}

	/**
	 * Saves a file to the EBS system and records its information in the database.
	 * Additionally, converts the file to PDF format, if applicable.
	 *
	 * @param file the MultipartFile object representing the file to be saved
	 * @param fileKindCd the code representing the type or kind of the file
	 * @param billId the identifier of the bill associated with the file
	 * @param clsCd the class code associated with the file
	 * @param opbYn a flag indicating whether the operation is OPB-related ("Y" for yes, "N" for no)
	 * @param detailSeq a sequence number indicating the detail record associated with the file
	 * @param lngType the language type code associated with the operation
	 */
	@Transactional
	@Override
	public void saveFileEbs(MultipartFile file, String fileKindCd, String billId, String clsCd, String opbYn, Long detailSeq, String lngType) {
		if(file == null) return;

		String orgFileId = StringUtil.getUUUID();
		String orgFileNm = file.getOriginalFilename();

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
		fileVo.setOpbYn(opbYn);
		fileVo.setFileKindCd(fileKindCd);
		fileVo.setDetailSeq(detailSeq);
		fileVo.setClsCd(clsCd);
		fileVo.setRegId(new SecurityInfoUtil().getAccountId());
		fileVo.setLngType(lngType);
		
		fileMapper.insertFileEbs(fileVo);

		// pdf conversion
		convertToPdfEbs(file, orgFileId);
	}

	/**
	 * Saves a file to the EBS (Enterprise Business System).
	 *
	 * This method performs the following steps:
	 * 1. Retrieves the user's file information based on provided parameters.
	 * 2. Downloads the file and temporarily stores it locally.
	 * 3. Saves the file to the EBS storage system.
	 * 4. Inserts file metadata into the database.
	 * 5. Converts the temporary file to a PDF format, if applicable.
	 *
	 * @param myFileId the unique identifier of the file to be saved
	 * @param fileKindCd the code representing the kind of file
	 * @param billId the associated billing identifier for the file
	 * @param clsCd the code for the classification of the file
	 * @param opbYn indicator whether the file should be marked as OPB ("Y" or "N")
	 * @param detailSeq the detail sequence linked to the file
	 * @param lngType the language type of the file
	 * @throws Exception if an error occurs while processing the file
	 */
	@Transactional
	@Override
	public void saveFileEbs(String myFileId, String fileKindCd, String billId, String clsCd, String opbYn, Long detailSeq, String lngType) throws Exception {
		
		if(myFileId == null) return;


		String userId = new SecurityInfoUtil().getAccountId();
		FileVo myFile = documentMapper.selectMyFile(userId, myFileId);

		String orgFileId = StringUtil.getUUUID();
		String orgFileNm = myFile.getFileTitle();
		String fileType = myFile.getFileType();
		if(fileType != null && fileType.length() > 0) {
			orgFileNm += "." + fileType;
		}

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
		fileVo.setOpbYn(opbYn);
		fileVo.setFileKindCd(fileKindCd);
		fileVo.setDetailSeq(detailSeq);
		fileVo.setClsCd(clsCd);
		fileVo.setRegId(new SecurityInfoUtil().getAccountId());
		fileVo.setLngType(lngType);
		
		fileMapper.insertFileEbs(fileVo);

		myFileIs.close();
		
		// pdf conversion
		convertToPdfEbs(tmpFile, myFile.getFileNm(), orgFileId);
	}

	/**
	 * Updates an EBS file with the specified details.
	 *
	 * @param ebsFileVo the object containing the details of the EBS file to be updated
	 */
	@Transactional
	@Override
	public void updateEbsFile(EbsFileVo ebsFileVo) {
		fileMapper.updateFileEbs(ebsFileVo);
	}

	/**
	 * Deletes a file identified by its unique file ID from the server and any associated storage.
	 *
	 * @param fileId the unique identifier of the file to be deleted
	 * @throws RuntimeException if an error occurs during the delete operation
	 */
	public void deleteFile(String fileId){
		try {
			edv.delete(fileId);
		} catch (Exception e) {
            throw new RuntimeException("Failed to delete file:" + e);
        }
		fileMapper.deleteServerFile(fileId);
	}
}