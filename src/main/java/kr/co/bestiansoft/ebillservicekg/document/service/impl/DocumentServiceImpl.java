package kr.co.bestiansoft.ebillservicekg.document.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.exception.ForbiddenException;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.exception.MaximumCapacityReachedException;
import kr.co.bestiansoft.ebillservicekg.common.file.service.impl.EDVHelper;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import kr.co.bestiansoft.ebillservicekg.document.repository.DocumentMapper;
import kr.co.bestiansoft.ebillservicekg.document.service.DocumentService;
import kr.co.bestiansoft.ebillservicekg.document.service.ThumbnailService;
import kr.co.bestiansoft.ebillservicekg.document.vo.FileShareVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.FileVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.FolderVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.UseCpctVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.UserMemberAuthMappVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class DocumentServiceImpl implements DocumentService {
    private final DocumentMapper documentMapper;
    private final EDVHelper edv;
    private final ThumbnailService thumbnailService;
    
    @Value("${edv.max-use-cpct}")
    private long MAX_USE_CPCT;
    
//    @Value("${edv.datastore-path}")
//    private String DATASTORE_PATH;
    private String DATASTORE_PATH = System.getProperty("user.home");

	/**
	 * Retrieves a list of department folders based on the provided FolderVo object.
	 *
	 * @param vo the FolderVo object containing the user and department information.
	 *           The userId and deptCd fields will be populated based on the current user's information.
	 * @return a list of FolderVo objects representing the department folders.
	 */
	@Override
    public List<FolderVo> selectDeptFolderListAll(FolderVo vo) {
    	String userId = new SecurityInfoUtil().getAccountId();
    	String deptCd = new SecurityInfoUtil().getDeptCd();
    	vo.setUserId(userId);
    	vo.setDeptCd(deptCd);
        return documentMapper.selectDeptFolderListAll(vo);
    }

	/**
	 * Retrieves a list of all folders for the specified user.
	 *
	 * @param vo the FolderVo object containing folder details and the user information
	 * @return a list of FolderVo objects representing all folders for the user
	 */
	@Override
    public List<FolderVo> selectMyFolderListAll(FolderVo vo) {
    	String userId = new SecurityInfoUtil().getAccountId();
    	vo.setUserId(userId);
        return documentMapper.selectMyFolderListAll(vo);
    }
    
//    @Override
//    public List<FolderVo> selectDeptFolderList(FolderVo vo) {
//    	String deptCd = new SecurityInfoUtil().getDeptCd();
//    	vo.setDeptCd(deptCd);
//        return documentMapper.selectDeptFolderList(vo);
//    }

	/**
	 * Retrieves a list of folders for the currently authenticated user based on the provided folder criteria.
	 *
	 * @param vo the folder value object containing the criteria for retrieving the folder list.
	 * @return a list of FolderVo objects representing the folders matching the criteria for the current user.
	 */
	@Override
    public List<FolderVo> selectMyFolderList(FolderVo vo) {
    	String userId = new SecurityInfoUtil().getAccountId();
    	vo.setUserId(userId);
        return documentMapper.selectMyFolderList(vo);
    }

	/**
	 * Retrieves a list of shared folders based on the provided folder information.
	 * Updates the FolderVo object with the current user's ID and department code
	 * before performing the database query.
	 *
	 * @param vo the FolderVo object containing the folder information and
	 *           additional details required for the query
	 * @return a list of FolderVo objects representing the shared folders
	 */
	@Override
    public List<FolderVo> selectShareFolderList(FolderVo vo) {
    	String userId = new SecurityInfoUtil().getAccountId();
    	String deptCd = new SecurityInfoUtil().getDeptCd();
    	vo.setUserId(userId);
    	vo.setDeptCd(deptCd);
        return documentMapper.selectShareFolderList(vo);
    }

	/**
	 * Retrieves a list of deleted folders based on the input FolderVo parameters.
	 * The method considers the user as well as department-level access to determine
	 * if both personal and department folders should be included in the deleted folder list.
	 *
	 * @param vo The FolderVo instance containing filtering parameters. It should include
	 *            details such as user ID and department code. These values are updated
	 *            internally based on the current user's security information.
	 * @return A list of FolderVo objects representing the deleted folders. If the user
	 *         is a department head, the method returns both personal and department-level
	 *         deleted folders. Otherwise, it only returns the user's personal deleted folders.
	 */
	@Override
    public List<FolderVo> selectDeleteFolderList(FolderVo vo) {
    	vo.setUserId(new SecurityInfoUtil().getAccountId());
    	vo.setDeptCd(new SecurityInfoUtil().getDeptCd());
    	
    	String deptHeadYn = new SecurityInfoUtil().getDeptHeadYn();
    	if("Y".equals(deptHeadYn)) {
    		List<FolderVo> deletedDeptFolderList = documentMapper.selectDeletedDeptFolderList(vo);
    		List<FolderVo> deletedMyFolderList = documentMapper.selectDeletedMyFolderList(vo);
    		List<FolderVo> ret = new ArrayList<>();
    		for(FolderVo folder : deletedDeptFolderList) {
    			ret.add(folder);
    		}
       		for(FolderVo folder : deletedMyFolderList) {
    			ret.add(folder);
    		}
       		return ret;
    	}
    	else {
    		return documentMapper.selectDeletedMyFolderList(vo);
    	}
    	
//    	String userId = new SecurityInfoUtil().getAccountId();
//    	vo.setUserId(userId);
//        return documentMapper.selectDeleteFolderList(vo);
    }

	/**
	 * Inserts a department folder into the database.
	 * This method sets the registering user's ID and department code to the folder,
	 * and checks if the user has permission to create the folder under the specified parent folder.
	 *
	 * @param vo the FolderVo object containing the folder details to be inserted.
	 *           Specifies folder information including its name, parent folder ID, etc.
	 * @return the number of rows affected in the database.
	 *         Typically, it should return 1 if the insertion is successful.
	 * @throws ForbiddenException if the user does not have permission to create the folder in the specified location.
	 */
    @Transactional
    @Override
    public int insertDeptFolder(FolderVo vo) {
    	
    	String userId = new SecurityInfoUtil().getAccountId();
    	String deptCd = new SecurityInfoUtil().getDeptCd();
    	
    	if(!isAuthorizedCreateFolder(vo.getUpperFolderId(), userId)) {
    		throw new ForbiddenException("forbidden");
    	}
    	
    	vo.setRegId(userId);
    	vo.setDeptCd(deptCd);
    	return documentMapper.insertDeptFolder(vo);
    }

	/**
	 * Inserts a new folder into the system using the provided folder information.
	 *
	 * @param vo the FolderVo object containing the folder details to be inserted,
	 *           such as folder metadata and user ID information
	 * @return an integer representing the result of the insert operation, typically
	 *         the number of rows affected in the database
	 */
	@Transactional
    @Override
    public int insertMyFolder(FolderVo vo) {
    	
    	String userId = new SecurityInfoUtil().getAccountId();
    	
    	vo.setRegId(userId);
    	vo.setUserId(userId);
    	return documentMapper.insertMyFolder(vo);
    }

	/**
	 * Updates the folder details in the database.
	 * Validates if the user is authorized to perform the update before proceeding.
	 *
	 * @param vo the FolderVo object containing the folder details to be updated
	 * @return the number of rows affected in the database
	 * @throws ForbiddenException if the user is not authorized to update the folder
	 */
	@Transactional
    @Override
    public int updateFolder(FolderVo vo) {
    	String userId = new SecurityInfoUtil().getAccountId();
    	
    	if(!isAuthorizedUpdate(vo.getFolderId(), userId)) {
    		throw new ForbiddenException("forbidden");
    	}
    	
    	vo.setModId(userId);
    	return documentMapper.updateFolder(vo);
    }

	/**
	 * Deletes folders and files based on the provided folder IDs and file group IDs.
	 *
	 * @param folderIds a list of IDs corresponding to the folders to be deleted
	 * @param fileGroupIds a list of file group IDs corresponding to the files to be deleted
	 * @return the total number of folders and files deleted
	 */
    @Transactional
    @Override
    public int deleteFoldersAndFiles(List<Long> folderIds, List<String> fileGroupIds) {
    	int ret = deleteFolders(folderIds);
    	ret += deleteFiles(fileGroupIds);
    	return ret;
    }

	/**
	 * Deletes the specified folders by marking them as deleted in the database.
	 * It verifies if the user has the authorization to delete each folder before proceeding.
	 *
	 * @param folderIds a list of folder IDs to be deleted
	 * @return the number of folders successfully deleted
	 * @throws ForbiddenException if the user is not authorized to delete any of the specified folders
	 */
	@Transactional
    @Override
    public int deleteFolders(List<Long> folderIds) {
    	String userId = new SecurityInfoUtil().getAccountId();
    	
    	int ret = 0;
    	if(folderIds != null) {
    		for(Long folderId : folderIds) {
    			if( !isAuthorizedDeleteRecursive(folderId, userId) ) {
    				throw new ForbiddenException("forbidden");
    			}
        		FolderVo vo = new FolderVo();
            	vo.setFolderId(folderId);
            	vo.setDelYn("Y");
            	vo.setModId(userId);
            	ret += documentMapper.updateFolder(vo);
        	}	
    	}
    	return ret;
    }

	/**
	 * Deletes a list of files specified by their file group IDs. Checks if the user
	 * has the necessary permissions to delete each file group and updates the deletion
	 * status of the files in the database.
	 *
	 * @param fileGroupIds a list of file group IDs to be deleted. If null or empty, no
	 *        files are deleted.
	 * @return the total number of files successfully marked as deleted.
	 * @throws ForbiddenException if the user does not have the required permissions
	 *         to delete a file group.
	 */
	@Transactional
    @Override
    public int deleteFiles(List<String> fileGroupIds) {
    	String userId = new SecurityInfoUtil().getAccountId();
    	
    	int ret = 0;
    	if(fileGroupIds != null) {
    		for(String fileGroupId : fileGroupIds) {
    			FileVo file = documentMapper.selectFile(fileGroupId);
    			if(!isAuthorizedDelete(file.getFolderId(), userId)) {
    				throw new ForbiddenException("forbidden");
    			}
        		FileVo vo = new FileVo();
        		vo.setFileGroupId(fileGroupId);
        		vo.setDelYn("Y");
        		vo.setModId(userId);
        		ret += documentMapper.updateFileByFileGroupId(vo);
        	}	
    	}
    	return ret;
    }

	/**
	 * Restores folders and files identified by their respective IDs.
	 *
	 * @param folderIds a list of folder IDs to be restored
	 * @param fileGroupIds a list of file group IDs to be restored
	 * @return the total number of folders and files successfully restored
	 */
    @Transactional
    @Override
    public int restoreFoldersAndFiles(List<Long> folderIds, List<String> fileGroupIds) {
    	int ret = restoreFolders(folderIds);
    	ret += restoreFiles(fileGroupIds);
    	return ret;
    }

	/**
	 * Restores a list of folders by updating their status to not deleted.
	 *
	 * @param folderIds List of folder IDs to be restored. If the list is null, no action will be performed.
	 * @return The total number of folders successfully restored.
	 */
	@Transactional
    @Override
    public int restoreFolders(List<Long> folderIds) {
    	String userId = new SecurityInfoUtil().getAccountId();
    	int ret = 0;
    	if(folderIds != null) {
    		for(Long folderId : folderIds) {
        		FolderVo vo = new FolderVo();
            	vo.setFolderId(folderId);
            	vo.setDelYn("N");
            	vo.setModId(userId);
            	ret += documentMapper.updateFolder(vo);
        	}	
    	}
    	return ret;
    }

	/**
	 * Restores files by updating their status based on the provided list of file group IDs.
	 *
	 * @param fileGroupIds the list of file group IDs whose associated files are to be restored;
	 *                     if null or empty, no files will be restored
	 * @return the total number of files updated during the restore process
	 */
	@Transactional
    @Override
    public int restoreFiles(List<String> fileGroupIds) {
    	String userId = new SecurityInfoUtil().getAccountId();
    	int ret = 0;
    	if(fileGroupIds != null) {
    		for(String fileGroupId : fileGroupIds) {
        		FileVo vo = new FileVo();
        		vo.setFileGroupId(fileGroupId);
        		vo.setDelYn("N");
        		vo.setModId(userId);
        		ret += documentMapper.updateFileByFileGroupId(vo);
        	}	
    	}
    	return ret;
    }

	/**
	 * Moves the specified folders and files to a target folder.
	 *
	 * @param folderIds the list of folder IDs to be moved
	 * @param fileGroupIds the list of file group IDs to be moved
	 * @param toFolderId the ID of the target folder where the folders and files should be moved
	 * @return the total count of folders and files successfully moved
	 */
    @Transactional
    @Override
    public int moveFoldersAndFiles(List<Long> folderIds, List<String> fileGroupIds, Long toFolderId) {
    	int ret = moveFolders(folderIds, toFolderId);
    	ret += moveFiles(fileGroupIds, toFolderId);
    	return ret;
    }

	/**
	 * Moves the specified list of folders to a new parent folder.
	 *
	 * @param folderIds a list of IDs representing the folders to be moved
	 * @param toFolderId the ID of the destination folder where the folders will be moved
	 * @return the total number of folders successfully moved
	 */
	@Transactional
    @Override
    public int moveFolders(List<Long> folderIds, Long toFolderId) {
    	int ret = 0;
    	if(folderIds != null) {
    		for(Long folderId : folderIds) {
        		FolderVo vo = new FolderVo();
            	vo.setFolderId(folderId);
            	vo.setUpperFolderId(toFolderId);
            	vo.setModId(new SecurityInfoUtil().getAccountId());
            	ret += documentMapper.updateFolder(vo);
        	}	
    	}
    	return ret;
    }

	/**
	 * Moves files associated with the specified file group IDs to a new folder.
	 * Updates the folder ID of the files in the provided file groups and records the modification details.
	 *
	 * @param fileGroupIds a list of file group IDs whose files need to be moved, may be null
	 * @param toFolderId the ID of the destination folder to where the files should be moved
	 * @return the number of files successfully updated
	 */
	@Transactional
    @Override
    public int moveFiles(List<String> fileGroupIds, Long toFolderId) {
    	int ret = 0;
    	if(fileGroupIds != null) {
    		for(String fileGroupId : fileGroupIds) {
        		FileVo vo = new FileVo();
        		vo.setFileGroupId(fileGroupId);
        		vo.setFolderId(toFolderId);
        		vo.setModId(new SecurityInfoUtil().getAccountId());
        		ret += documentMapper.updateFileByFileGroupId(vo);
        	}	
    	}
    	return ret;
    }

	/**
	 * Removes folders and files based on the provided folder and file IDs.
	 *
	 * @param folderIds a list of IDs representing the folders to be removed
	 * @param fileIds a list of IDs (as strings) representing the files to be removed
	 * @throws Exception if an error occurs during the removal process
	 */
	@Transactional
    @Override
    public void removeFoldersAndFiles(List<Long> folderIds, List<String> fileIds) throws Exception {
    	removeFolders(folderIds);
    	removeFiles(fileIds);
    }

	/**
	 * Removes the folders with the specified IDs.
	 *
	 * @param folderIds the list of folder IDs to be removed
	 * @throws Exception if an error occurs while removing the folders
	 */
	@Transactional
    @Override
    public void removeFolders(List<Long> folderIds) throws Exception {
    	if(folderIds != null) {
    		for(Long folderId : folderIds) {
        		removeFolder(folderId);
        	}	
    	}
    }

	/**
	 * Removes files associated with the provided list of file IDs.
	 * Each file ID in the list corresponds to a file that will be removed.
	 *
	 * @param fileIds A list of file IDs to be removed. If the list is null, no action will be taken.
	 * @throws Exception If an error occurs while attempting to remove the files.
	 */
	@Transactional
    @Override
    public void removeFiles(List<String> fileIds) throws Exception {
    	if(fileIds != null) {
    		for(String fileId : fileIds) {
        		removeFile(fileId);
        	}	
    	}
    }

	/**
	 * Removes a folder and its associated contents, including subfolders and files, from the system.
	 * This operation is performed recursively for any nested subfolders and their files.
	 *
	 * @param folderId the unique identifier of the folder to be removed
	 * @throws Exception if an error occurs during the removal process
	 */
	@Transactional
    public void removeFolder(Long folderId) throws Exception {
    	List<Long> folderIds = documentMapper.selectFolderIdsByUpperFolderId(folderId);
    	List<String> fileIds = documentMapper.selectFileIdsByFolderId(folderId);
    	
    	documentMapper.removeFolder(folderId);
    	if(fileIds != null) {
    		for(String id : fileIds) {
        		removeFile(id);
        	}	
    	}
    	if(folderIds != null) {
    		for(Long id : folderIds) {
        		removeFolder(id);
        	}	
    	}
    }

	/**
	 * Removes a file specified by its file ID from the system. This method updates the user's
	 * file usage capacity, deletes the file metadata from the database, and removes the file
	 * from storage.
	 *
	 * @param fileId the unique identifier of the file to be removed
	 * @throws Exception if an error occurs*/
	@Transactional
    public void removeFile(String fileId) throws Exception {
    	String userId = new SecurityInfoUtil().getAccountId();
    	FileVo fileVo = documentMapper.selectFile2(fileId);
    	documentMapper.addUseCpct(userId, -fileVo.getFileSize()); 
    	documentMapper.removeFile(fileId);
		edv.delete(fileId);
    }

	/**
	 * Handles the upload of multiple files and processes related actions such as thumbnail creation.
	 * This method checks the user's authorization, validates storage capacity, saves the files,
	 * and creates thumbnails for the uploaded files as needed.
	 *
	 * @param vo the object containing the file upload details, such as folder ID, file details, and other metadata
	 * @return the number of files successfully uploaded
	 * @throws ForbiddenException if the user is not authorized to upload files in the specified folder
	 * @throws MaximumCapacityReachedException if the total file size exceeds the allowed storage capacity
	 */
	@Transactional
    @Override
    public int uploadFile(FileVo vo) {
    	String userId = new SecurityInfoUtil().getAccountId();
    	
    	if(!isAuthorizedCreateFile(vo.getFolderId(), userId)) {
    		throw new ForbiddenException("forbidden");
    	}
    	
    	int ret = 0;
		
		MultipartFile[] files = vo.getFiles();
		
		
		long useCpct = documentMapper.selectTotalUseCpct(userId);
		long tot = 0;
		for(MultipartFile mpf : files) {
			tot += mpf.getSize();
		}
		if(useCpct + tot > MAX_USE_CPCT) {
			throw new MaximumCapacityReachedException("Maximum Capacity Reached");
		}
		
		Map<String, Long> map = new HashMap<>();
		map.put("", vo.getFolderId());
		List<Map<String, Object>> thumbnailJobs = new ArrayList<>();
		
		for(MultipartFile mpf : files) {
			if (mpf.isEmpty()) break;
			ret += saveFile(vo, mpf, map);

			// Create a thumbnail of each file if it is not a bundle -up load
			if(!"Y".equals(vo.getGroupYn())) {
				try {
					Map<String, Object> thumbnailJob = new HashMap<>();
					File tmpFile = File.createTempFile("tmp", null);
					mpf.transferTo(tmpFile);
					thumbnailJob.put("file", tmpFile);
					thumbnailJob.put("filename", mpf.getOriginalFilename());
					thumbnailJob.put("fileId", vo.getFileId());
					
					thumbnailJobs.add(thumbnailJob);
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}
		}
		
		// Create thumbnail
		for(Map<String, Object> job : thumbnailJobs) {
			File file = (File)job.get("file");
			String filename = (String)job.get("filename");
			String fileId = (String)job.get("fileId");

			thumbnailService.createThumbnailAsync(file, filename, fileId);
		}
		
		return ret;
    }
    
//    @Transactional
//    @Override
//    public int uploadFileGroup(FileVo vo) {
//    	
//    	String fileGroupId = "P_" + StringUtil.getUUUID();
//    	vo.setFileGroupId(fileGroupId);
//    	vo.setRegId(tmpUserId);
//
//    	MultipartFile thumbnailImage = vo.getThumbnailImage();
//    	if(thumbnailImage != null) {
//    		String thumbnailFileId = StringUtil.getUUUID();
//    		try (InputStream edvIs = thumbnailImage.getInputStream()){
//    			edv.save(thumbnailFileId, edvIs);
//    		} catch (Exception edvEx) {
//    			throw new RuntimeException("EDV_NOT_WORK", edvEx);
//    		}
//    		vo.setThumbnail(thumbnailFileId);
//    	}
//    	documentMapper.insertFileGroup(vo);
//    	
//    	int ret = uploadFile(vo);
//    	
//    	//Save importance status.
//		String favoriteYn = vo.getFavoriteYn();
//		if("Y".equals(favoriteYn)) {
//			vo.setUserId(tmpUserId);
//			documentMapper.saveFavorite(vo);	
//		}
//		
//		return ret;
//    }

	/**
	 * Creates a folder based on the given path and adds it to the specified map.
	 * Recursively creates parent folders if they do not exist in the map.
	 *
	 * @param path the path of the folder to be created. If null or empty, the method returns null.
	 * @param map a map that stores folder paths as keys and their corresponding folder IDs as values.
	 * @return the ID of the newly created folder, or null if the provided path is null or empty.
	 */
    // Folder When uploading - Folder generation
    private Long createFolder(String path, Map<String, Long> map) {
		if(path == null || path.isEmpty()) {
			return null;
		}
		int idx = path.lastIndexOf("/");
		String pre_path = idx == -1 ? "" : path.substring(0, idx);
		Long upperFolderId = null;
		if(map.containsKey(pre_path)) {
			upperFolderId = map.get(pre_path);
		}
		else {
			upperFolderId = createFolder(pre_path, map);
		}
		String folderNm = path.substring(idx + 1);
		String userId = new SecurityInfoUtil().getAccountId();
    	String deptCd = new SecurityInfoUtil().getDeptCd();
		
		FolderVo folderVo = new FolderVo();
		folderVo.setFolderNm(folderNm);
		folderVo.setRegId(userId);
		folderVo.setDeptCd(deptCd);
		folderVo.setUpperFolderId(upperFolderId);
		
		insertDeptFolder(folderVo);
		
		map.put(path, folderVo.getFolderId());
		return folderVo.getFolderId();
	}

	/**
	 * Saves a file to the document management system, including its metadata and storage details.
	 * The method handles file and folder creation or mapping if necessary and ensures the file is stored
	 * with its relevant properties.
	 *
	 * @param vo The file metadata object containing details such as folder ID, group information, etc.
	 * @param mpf The multipart file object representing the file to be saved.
	 * @param map A map that links folder paths to their corresponding folder IDs, used for folder organization.
	 * @return An integer indicating the result of the database insertion operation.
	 */
    // Folder - file and Path Folder save
    @Transactional
	private int saveFile(FileVo vo, MultipartFile mpf, Map<String, Long> map) {
		String orgFileNm = mpf.getOriginalFilename();
		
		int idx = orgFileNm.lastIndexOf("/");
		
		String fileNm = null;
		Long folderId = null;
		if(idx == -1 || map == null) {
			fileNm = orgFileNm;
			folderId = vo.getFolderId();
		}
		else {
			String path = orgFileNm.substring(0, idx);
			if(map.containsKey(path)) {
				folderId = map.get(path);
			}
			else {
				folderId = createFolder(path, map);	
			}
			fileNm = orgFileNm.substring(idx + 1);
		}
		
		String fileId = StringUtil.getUUUID();
		vo.setFileId(fileId);
		
		try (InputStream edvIs = mpf.getInputStream()){
			edv.save(fileId, edvIs);
		} catch (Exception edvEx) {
			throw new RuntimeException("EDV_NOT_WORK", edvEx);
		}
		
		long fileSize = mpf.getSize();
		String fileTitle = fileNm, fileType = "";
		idx = fileNm.lastIndexOf('.');
		if(idx != -1) {
			fileTitle = fileNm.substring(0, idx);
			fileType = fileNm.substring(idx + 1);
		}
		String userId = new SecurityInfoUtil().getAccountId();
		String deptCd = new SecurityInfoUtil().getDeptCd();
		String groupYn = vo.getGroupYn();
		String fileGroupId = vo.getFileGroupId();
		String deptFileYn = vo.getDeptFileYn();
		
		String fileHash = null, filePath = null;
		try (InputStream is = mpf.getInputStream()){
			fileHash = edv.getHash(is);
			filePath = edv.getFilePath(fileHash);
		} catch (Exception edvEx) {
			throw new RuntimeException("EDV_NOT_WORK", edvEx);
		}
		
		FileVo fileVo = new FileVo();
		fileVo.setFileId(fileId);
		fileVo.setFileNm(fileNm);
		fileVo.setFileSize(fileSize);
		fileVo.setFileTitle(fileTitle);
		fileVo.setFileType(fileType);
		fileVo.setGroupYn(groupYn);
		fileVo.setRegId(userId);
		fileVo.setFileGroupId("Y".equals(groupYn) ? fileGroupId : fileId);
		fileVo.setEdvHashStr(fileHash);
		fileVo.setEdvPath(filePath);
		fileVo.setFolderId(folderId);
		fileVo.setDeptCd(deptCd);
		fileVo.setDeptFileYn(deptFileYn);
		fileVo.setUserId(userId);
		
		int ret = documentMapper.insertFile(fileVo);
		
		//Save importance status.(If not Bundle)
		if(!"Y".equals(groupYn)) {
			String favoriteYn = vo.getFavoriteYn();
			if("Y".equals(favoriteYn)) {
				fileVo.setUserId(userId);
				fileVo.setFavoriteYn(favoriteYn);
				documentMapper.saveFavorite(fileVo);	
			}	
		}
		
		documentMapper.addUseCpct(userId, fileSize);
		
		return ret;
	}

	/**
	 * Retrieves a list of department files based on the specified criteria in the provided FileVo object.
	 * Ensures that the user has the necessary read permissions for the folder before fetching the files.
	 *
	 * @param vo A FileVo object containing the criteria for selecting department files, such as folder ID.
	 *           This object also gets updated with user and department information.
	 * @return A list of FileVo objects representing the department files that meet the specified criteria.
	 *         Returns an empty list if the user does not have read permissions for the specified folder.
	 */
	@Override
    public List<FileVo> selectDeptFileList(FileVo vo) {
    	
    	String userId = new SecurityInfoUtil().getAccountId();
    	String deptCd = new SecurityInfoUtil().getDeptCd();
    	
    	if(!isAuthorizedRead(vo.getFolderId(), userId)) {
    		return new ArrayList<>();
    	}
    	
    	vo.setUserId(userId);
    	vo.setDeptCd(deptCd);
    	return documentMapper.selectDeptFileList(vo);
    }

	/**
	 * Retrieves a list of files belonging to the current user based on the provided query parameters.
	 *
	 * @param vo an instance of FileVo containing query parameters for filtering the file list
	 * @return a list of FileVo objects representing the files associated with the current user
	 */
	@Override
    public List<FileVo> selectMyFileList(FileVo vo) {
    	vo.setUserId(new SecurityInfoUtil().getAccountId());
    	return documentMapper.selectMyFileList(vo);
    }

	/**
	 * Retrieves a list of shared files based on the given FileVo parameters.
	 *
	 * @param vo the FileVo object containing parameters such as user ID and department code
	 *           that are set internally before querying the shared file list
	 * @return a*/
	@Override
    public List<FileVo> selectShareFileList(FileVo vo) {
    	vo.setUserId(new SecurityInfoUtil().getAccountId());
    	vo.setDeptCd(new SecurityInfoUtil().getDeptCd());
    	return documentMapper.selectShareFileList(vo);
    }

	/**
	 * Retrieves a list of starred files based on the provided file information.
	 *
	 * @param vo an instance of FileVo containing file information, including user and department details
	 * @return a list of FileVo objects representing the starred files
	 */
	@Override
    public List<FileVo> selectStarFileList(FileVo vo) {
    	vo.setUserId(new SecurityInfoUtil().getAccountId());
    	vo.setDeptCd(new SecurityInfoUtil().getDeptCd());
    	return documentMapper.selectStarFileList(vo);
    }

	/**
	 * Retrieves a list of files marked for deletion based on user and department information.
	 * If the user is a department head, the method combines the department's deleted files
	 * with the user's deleted files. Otherwise, only the user's deleted files are retrieved.
	 *
	 * @param vo an instance of FileVo containing information about the user and their department
	 * @return a list of FileVo objects representing the files that are marked for deletion
	 */
	@Override
    public List<FileVo> selectDeleteFileList(FileVo vo) {
    	vo.setUserId(new SecurityInfoUtil().getAccountId());
    	vo.setDeptCd(new SecurityInfoUtil().getDeptCd());
    	
    	String deptHeadYn = new SecurityInfoUtil().getDeptHeadYn();
    	if("Y".equals(deptHeadYn)) {
    		List<FileVo> deletedDeptFileList = documentMapper.selectDeletedDeptFileList(vo);
    		List<FileVo> deletedMyFileList = documentMapper.selectDeletedMyFileList(vo);
    		List<FileVo> ret = new ArrayList<>();
    		for(FileVo file : deletedDeptFileList) {
    			ret.add(file);
    		}
       		for(FileVo file : deletedMyFileList) {
    			ret.add(file);
    		}
       		return ret;
    	}
    	else {
    		return documentMapper.selectDeletedMyFileList(vo);
    	}
    	
//    	vo.setUserId(new SecurityInfoUtil().getAccountId());
//    	return documentMapper.selectDeleteFileList(vo);
    }

	/**
	 * Selects and retrieves a list of file group data based on the provided FileVo instance.
	 *
	 * @param vo the FileVo object containing criteria for the selection,
	 *           including user-specific information which is set*/
	@Override
    public List<FileVo> selectFileGroup(FileVo vo) {
    	vo.setUserId(new SecurityInfoUtil().getAccountId());
    	return documentMapper.selectFileGroup(vo);
    }

	/**
	 * Updates the details of a file in the system. This method retrieves the file by its ID,
	 * checks for update authorization, and then performs the update operation.
	 *
	 * @param vo the file data transfer object containing the details to be updated,
	 *           including the file ID and other updated information
	 * @return the number of records updated in the database
	 * @throws IllegalArgumentException if the file with the specified ID is not found
	 * @throws ForbiddenException if the user is not authorized to update the specified file
	 */
	@Transactional
    @Override
    public int updateFile(FileVo vo) {
    	String userId = new SecurityInfoUtil().getAccountId();
    	
    	FileVo file = documentMapper.selectFile(vo.getFileId());
    	if(file == null) {
    		throw new IllegalArgumentException(vo.getFileId());
    	}
    	if(!isAuthorizedUpdate(file.getFolderId(), userId)) {
    		throw new ForbiddenException("forbidden");
    	}
    	
    	vo.setModId(userId);
    	int ret = documentMapper.updateFileByFileId(vo);

    	saveFavorite(vo);
    	return ret;
    }

	/**
	 * Determines if a user is authorized to create a folder within a specified folder.
	 *
	 * @param folderId the ID of the existing folder where the new folder is to be created; a value of -1 generally
	 *                 implies root-level creation, skipping specific authorization checks
	 * @param userId   the ID of the user requesting folder creation
	 * @return true if the user is authorized to create a folder, false otherwise
	 */
    private boolean isAuthorizedCreateFolder(Long folderId, String userId) {
//    	if(folderId == -1) {
//    		return true;
//    	}
    	FolderVo folder = documentMapper.selectFolderByFolderId(folderId);
//    	if(folder == null) {
//    		return false;
//    	}
    	if(folder != null && "N".equals(folder.getDeptFolderYn())) {
    		return folder.getUserId().equals(userId);
    	}
    	UserMemberAuthMappVo userAuth = documentMapper.selectUserAuthMapp(folderId, userId);
    	if(userAuth == null) {
    		return false;
    	}
    	return userAuth.getCreateFolderYn();    	
    }

	/**
	 * Validates if a user is authorized to create a file within a specified folder.
	 *
	 * @param folderId the unique identifier of the folder where the file is to be created
	 * @param userId the unique identifier of the user attempting to create the file
	 * @return true if the user is authorized to create a file in the specified folder, false otherwise
	 */
    private boolean isAuthorizedCreateFile(Long folderId, String userId) {
//    	if(folderId == -1) {
//    		return true;
//    	}
    	FolderVo folder = documentMapper.selectFolderByFolderId(folderId);
//    	if(folder == null) {
//    		return false;
//    	}
    	if(folder != null && "N".equals(folder.getDeptFolderYn())) {
    		return folder.getUserId().equals(userId);
    	}
    	UserMemberAuthMappVo userAuth = documentMapper.selectUserAuthMapp(folderId, userId);
    	if(userAuth == null) {
    		return false;
    	}
    	return userAuth.getCreateFileYn();    	
    }

	/**
	 * Checks if a user is authorized to read the specified folder.
	 *
	 * @param folderId the ID of the folder to check authorization for
	 * @param userId the ID of the user whose authorization is being checked
	 * @return true if the user is authorized to read the folder, false otherwise
	 */
    private boolean isAuthorizedRead(Long folderId, String userId) {
//    	if(folderId == -1) {
//    		return true;
//    	}
    	FolderVo folder = documentMapper.selectFolderByFolderId(folderId);
//    	if(folder == null) {
//    		return false;
//    	}
    	if(folder != null && "N".equals(folder.getDeptFolderYn())) {
    		return folder.getUserId().equals(userId);
    	}
    	UserMemberAuthMappVo userAuth = documentMapper.selectUserAuthMapp(folderId, userId);
    	if(userAuth == null) {
    		return false;
    	}
    	return userAuth.getSearchYn();
    }

	/**
	 * Determines if the user is authorized to update the specified folder.
	 *
	 * @param folderId the ID of the folder to check for update authorization
	 * @param userId the ID of the user requesting update authorization
	 * @return true if the user is authorized to update the folder, false otherwise
	 */
    private boolean isAuthorizedUpdate(Long folderId, String userId) {
//    	if(folderId == -1) {
//    		return true;
//    	}
    	FolderVo folder = documentMapper.selectFolderByFolderId(folderId);
//    	if(folder == null) {
//    		return false;
//    	}
    	if(folder != null && "N".equals(folder.getDeptFolderYn())) {
    		return folder.getUserId().equals(userId);
    	}
    	UserMemberAuthMappVo userAuth = documentMapper.selectUserAuthMapp(folderId, userId);
    	if(userAuth == null) {
    		return false;
    	}
    	return userAuth.getUpdateYn();
    }

	/**
	 * Determines if the user is authorized to delete a folder based on folder ID and user ID.
	 *
	 * @param folderId the ID of the folder to be checked
	 * @param userId the ID of the user requesting the delete action
	 * @return true if the user is authorized to delete the folder, false otherwise
	 */
    private boolean isAuthorizedDelete(Long folderId, String userId) {
//    	if(folderId == -1) {
//    		return true;
//    	}
    	FolderVo folder = documentMapper.selectFolderByFolderId(folderId);
//    	if(folder == null) {
//    		return false;
//    	}
    	if(folder != null && "N".equals(folder.getDeptFolderYn())) {
    		return folder.getUserId().equals(userId);
    	}
    	UserMemberAuthMappVo userAuth = documentMapper.selectUserAuthMapp(folderId, userId);
    	if(userAuth == null) {
    		return false;
    	}
    	return userAuth.getDeleteYn();
    }

	/**
	 * Determines whether a user is authorized to delete a folder and its subfolders recursively.
	 *
	 * @param folderId the ID of the folder to check for delete authorization
	 * @param userId the ID of the user attempting the delete operation
	 * @return true if the user is authorized to delete the folder recursively, otherwise false
	 */
    private boolean isAuthorizedDeleteRecursive(Long folderId, String userId) {
//    	if(folderId == -1) {
//    		return false;
//    	}
    	FolderVo folder = documentMapper.selectFolderByFolderId(folderId);
//    	if(folder == null) {
//    		return false;
//    	}
    	if(folder != null && "N".equals(folder.getDeptFolderYn())) {
    		return folder.getUserId().equals(userId);
    	}
    	
    	FolderVo vo = new FolderVo();
    	vo.setUserId(userId);
    	vo.setDeptCd(folder.getDeptCd());
    	List<FolderVo> list = documentMapper.selectDeptFolderListAll(vo);
    	
 		ArrayDeque<Long> dq = new ArrayDeque<>();
 		Set<Long> set = new HashSet<>(); // not allowed set
 		Map<Long, FolderVo> map = new HashMap<>();
 		
 		for(FolderVo f : list) {
 			Long id = f.getFolderId();
 			map.put(id, f);
 			if(!f.getDeleteYn()) {
 				dq.add(id);
 				set.add(id);
 			}
 		}
 		
 		while(!dq.isEmpty()) {
 			Long id = dq.pollFirst();
 			FolderVo f = map.get(id);
 			Long upId = f.getUpperFolderId();
 			if(upId == -1) {
 				continue;
 			}
 			if(!map.containsKey(upId)) {
 				continue;
 			}
 			if(set.contains(upId)) {
 				continue;
 			}
 			dq.add(upId);
 			set.add(upId);
 		}
 		return !set.contains(folderId);
    }

	/**
	 * Determines if the current user is authorized to share the specified folder.
	 *
	 * @param folder The folder object to check authorization for. Contains details such as department code and registration ID.
	 * @return true if the user is authorized to share the folder, false otherwise.
	 */
	private boolean isAuthorizedShare(FolderVo folder) {
    	String userId = new SecurityInfoUtil().getAccountId();
    	String deptCd = new SecurityInfoUtil().getDeptCd();
    	String deptHeadYn = new SecurityInfoUtil().getDeptHeadYn();
    	
    	if(folder != null && "Y".equals(folder.getDeptFolderYn()) && deptCd.equals(folder.getDeptCd()) && "Y".equals(deptHeadYn)) {
    		return true;
    	}
    	return folder != null && folder.getRegId().equals(userId);
    }

	/**
	 * Determines if the current user is authorized to share a given file.
	 *
	 * @param file the file to check for sharing authorization; contains details such as
	 *             whether it is a department file and the IDs of its owner and department
	 * @return true if the user is authorized to share the file; otherwise, false
	 */
	private boolean isAuthorizedShare(FileVo file) {
    	String userId = new SecurityInfoUtil().getAccountId();
    	String deptCd = new SecurityInfoUtil().getDeptCd();
    	String deptHeadYn = new SecurityInfoUtil().getDeptHeadYn();
    	
    	if(file != null && "Y".equals(file.getDeptFileYn()) && deptCd.equals(file.getDeptCd()) && "Y".equals(deptHeadYn)) {
    		return true;
    	}
    	return file != null && file.getRegId().equals(userId);
    }

	/**
	 * Saves a file as a favorite for the current user.
	 *
	 * @param vo the file information encapsulated in a FileVo object containing details
	 *           such as file ID and other metadata required for saving the favorite
	 */
	@Transactional
    @Override
    public void saveFavorite(FileVo vo) {
    	String userId = new SecurityInfoUtil().getAccountId();
    	
//    	String fileGroupId = documentMapper.selectFile(vo.getFileId()).getFileGroupId();
    	String fileGroupId = vo.getFileId();
    	vo.setFileGroupId(fileGroupId);
    	vo.setUserId(userId);
    	vo.setRegId(userId);
    	vo.setModId(userId);
    	documentMapper.saveFavorite(vo);
    }
    
//    @Transactional
//    @Override
//    public int updateFileGroup(FileVo vo) throws Exception {
//    	vo.setModId(tmpUserId);
//    	vo.setRegId(tmpUserId);
//
//    	// update thumbnail
//    	MultipartFile thumbnailImage = vo.getThumbnailImage();
//    	if(thumbnailImage != null) {
//    		//TODO 기존 썸네일 삭제 EDV)
//    		
//    		String thumbnailFileId = StringUtil.getUUUID();
//    		try (InputStream edvIs = thumbnailImage.getInputStream()){
//    			edv.save(thumbnailFileId, edvIs);
//    		} catch (Exception edvEx) {
//    			throw new RuntimeException("EDV_NOT_WORK", edvEx);
//    		}
//    		vo.setThumbnail(thumbnailFileId);	
//    	}
//    	
//    	// update file group name
//    	int ret = documentMapper.updateFileGroup(vo);
//    	
//    	// remove files from file group
//    	removeFiles(vo.getDelFileIds());
//    	
//    	// add files to file group
//    	MultipartFile[] files = vo.getAddFiles();
//    	if(files != null) {
//    		List<FileVo> groupFiles = documentMapper.selectFileGroup(vo);
//        	assert(!groupFiles.isEmpty());
//        	Long folderId = groupFiles.get(0).getFolderId();
//        	vo.setFolderId(folderId);
//        	vo.setGroupYn("Y");
//    		for(MultipartFile mpf : files) {
//    			if (mpf.isEmpty()) break;
//    			saveFile(vo, mpf, null);
//    		}	
//    	}
//    	
//    	// save favoriteYn
//		vo.setUserId(tmpUserId);
//    	documentMapper.saveFavorite(vo);
//    	
//    	return ret;
//    }

	/**
	 * Shares a file or folder by saving the share details into the database.
	 * Validates user authorization before proceeding with the sharing operation.
	 *
	 * @param vo the value object containing details about the file or folder to be shared
	 *           including information such as folder ID, file group ID, and folder indication flag
	 */
	@Transactional
    @Override
    public void shareFile(FileShareVo vo) {
    	String userId = new SecurityInfoUtil().getAccountId();
    	
    	if("Y".equals(vo.getFolderYn())) {
    		FolderVo folder = documentMapper.selectFolderByFolderId(vo.getFolderId());
    		if(!isAuthorizedShare(folder)) {
    			throw new ForbiddenException("forbidden");
    		}	
    	}
    	else {
    		FileVo file = documentMapper.selectFile(vo.getFileGroupId());
			if(!isAuthorizedShare(file)) {
				throw new ForbiddenException("forbidden");
			}   		
    	}
    	
    	vo.setOwnerId(userId);
    	vo.setRegId(userId);
    	documentMapper.insertShare(vo);
    }

	/**
	 * Revokes sharing permissions of a specified file or folder for other users.
	 *
	 * @param vo an instance of FileShareVo containing information about the file or
	 *           folder to unshare, as well as sharing details such as the file/folder ID.
	 *           `folderYn` indicates if the item is a folder ("Y" for folder, otherwise it is a file).
	 *           `fileGroupId` or `folderId` corresponds to the item to be unshared.
	 *           `ownerId` is set to the current user's ID to indicate who is revoking the sharing.
	 * @throws ForbiddenException if the current user does not have the necessary permissions
	 *                             to unshare the specified file or folder.
	 */
	@Transactional
    @Override
    public void unshareFile(FileShareVo vo) {
    	String userId = new SecurityInfoUtil().getAccountId();
    	
    	if("Y".equals(vo.getFolderYn())) {
    		FolderVo folder = documentMapper.selectFolderByFolderId(vo.getFolderId());
    		if(!isAuthorizedShare(folder)) {
    			throw new ForbiddenException("forbidden");
    		}	
    	}
    	else {
    		FileVo file = documentMapper.selectFile(vo.getFileGroupId());
			if(!isAuthorizedShare(file)) {
				throw new ForbiddenException("forbidden");
			}   		
    	}
    	
    	vo.setOwnerId(userId);
    	documentMapper.deleteShare(vo);
    }

	/**
	 * Retrieves a list of share target information based on the provided FileShareVo object.
	 *
	 * @param vo an instance of FileShareVo containing the criteria for selecting share targets.
	 *           The owner's account ID is automatically set within the method using SecurityInfoUtil.
	 * @return a list of FileShareVo objects representing share target details that match the provided criteria.
	 */
	@Transactional
    @Override
    public List<FileShareVo> selectShareTargetList(FileShareVo vo) {
    	vo.setOwnerId(new SecurityInfoUtil().getAccountId());
    	return documentMapper.selectShareTargetList(vo);
    }

	/**
	 * Retrieves a list of user members based on the provided parameters.
	 *
	 * @param param a HashMap containing the parameters for querying user members
	 * @return a List of UserMemberVo objects representing the user members
	 */
	@Transactional
    @Override
    public List<UserMemberVo> selectListUserMember(HashMap<String, Object> param) {
    	return documentMapper.selectListUserMember(param);
    }

	/**
	 * Checks if a folder is shared based on the provided folder ID.
	 * This determination is made by checking the sharing information
	 * associated with the user and department.
	 *
	 * @param folderId The ID of the folder to check.
	 * @return true if the folder is shared, false otherwise.
	 */
	private boolean isSharedFolder(Long folderId) {
    	
    	String userId = new SecurityInfoUtil().getAccountId();
    	String deptCd = new SecurityInfoUtil().getDeptCd();
    	
    	FileShareVo vo = new FileShareVo();
    	vo.setFolderId(folderId);
    	vo.setUserId(userId);
    	vo.setDeptCd(deptCd);
    	List<String> list = documentMapper.selectListSharingUserId(vo);
    	
    	return list != null && !list.isEmpty();
    	
//    	if(folderId == null || folderId == -1) {
//    		return false;
//    	}
//    	
//    	String userId = new SecurityInfoUtil().getAccountId();
//    	String deptCd = new SecurityInfoUtil().getDeptCd();
//    	
//    	FileShareVo fileShareVo = new FileShareVo();
//    	fileShareVo.setFolderYn("Y");
//    	fileShareVo.setFolderId(folderId);
//    	fileShareVo.setTargetKind("DEPT");
//    	fileShareVo.setTargetId(deptCd);
//    	List<FileShareVo> share1 = documentMapper.selectShare(fileShareVo);
//    	
//    	if(share1 != null && !share1.isEmpty()) {
//    		return true;
//    	}
//    	
//    	fileShareVo.setTargetKind("INDV");
//    	fileShareVo.setTargetId(userId);
//    	List<FileShareVo> share2 = documentMapper.selectShare(fileShareVo);
//    	
//    	if(share2 != null && !share2.isEmpty()) {
//    		return true;
//    	}
//    	
//    	FolderVo folder = documentMapper.selectFolderByFolderId(folderId);
//    	return isSharedFolder(folder.getUpperFolderId());
    }

	/**
	 * Determines if the specified folder is shared and the current user is authorized to read it.
	 *
	 * @param folderId the ID of the folder to check for shared status and read authorization
	 * @return true if the folder is shared and the current user is authorized to read it, otherwise false
	 */
	private boolean isSharedAndReadAuthorizedFolder(Long folderId) {
    	
    	String userId = new SecurityInfoUtil().getAccountId();
    	String deptCd = new SecurityInfoUtil().getDeptCd();
    	
    	FileShareVo vo = new FileShareVo();
    	vo.setFolderId(folderId);
    	vo.setUserId(userId);
    	vo.setDeptCd(deptCd);
    	List<String> list = documentMapper.selectListSharingUserId(vo);
    	
    	if(list != null) {
    		for(String id : list) {
    			if(isAuthorizedRead(folderId, id)) {
    				return true;
    			}
    		}
    	}
    	return false;
    	
//    	if(folderId == null || folderId == -1) {
//    		return false;
//    	}
//    	
//    	String userId = new SecurityInfoUtil().getAccountId();
//    	String deptCd = new SecurityInfoUtil().getDeptCd();
//    	
//    	FileShareVo fileShareVo = new FileShareVo();
//    	fileShareVo.setFolderYn("Y");
//    	fileShareVo.setFolderId(folderId);
//    	fileShareVo.setTargetKind("DEPT");
//    	fileShareVo.setTargetId(deptCd);
//    	List<FileShareVo> share1 = documentMapper.selectShare(fileShareVo);
//    	
//    	if(share1 != null) {
//    		for(FileShareVo share : share1) {
//    			if(isAuthorizedRead(targetFolderId, share.getOwnerId())) {
//    				return true;
//    			}
//    		}
//    	}
//    	
//    	fileShareVo.setTargetKind("INDV");
//    	fileShareVo.setTargetId(userId);
//    	List<FileShareVo> share2 = documentMapper.selectShare(fileShareVo);
//    	
//    	if(share2 != null) {
//    		for(FileShareVo share : share2) {
//    			if(isAuthorizedRead(targetFolderId, share.getOwnerId())) {
//    				return true;
//    			}
//    		}
//    	}
//    	
//    	FolderVo folder = documentMapper.selectFolderByFolderId(folderId);
//    	if(folder == null) {
//    		return false;
//    	}
//    	return isSharedAndReadAuthorizedFolder(folder.getUpperFolderId(), targetFolderId);
    }

	/**
	 * Retrieves a list of shared folders based on the folder ID provided in the input parameter.
	 * It ensures that the provided folder ID belongs to a shared folder. If the folder ID does not
	 * correspond to a shared folder, a ForbiddenException is thrown.
	 *
	 * @param vo the FolderVo object containing folder details, including the folder ID to look up.
	 * @return a list of FolderVo objects representing the shared folders associated with the given folder ID.
	 */
	@Override
    public List<FolderVo> selectShareFolderListByFolderId(FolderVo vo) {
    	
    	if( !isSharedFolder(vo.getUpperFolderId()) ) {
    		throw new ForbiddenException("forbidden");
    	}
    	
    	return documentMapper.selectFolderList(vo);
    }

	/**
	 * Retrieves a list of shared files associated with the specified folder ID.
	 * If the folder is not shared or the user does not have read authorization,
	 * an empty list is returned.
	 *
	 * @param vo The FileVo object containing folder ID and additional information required
	 *           to fetch the shared file list.
	 * @return A list of FileVo objects representing the files in the shared folder. If the folder
	 *         is not shared or the user does not have proper authorization, an empty list is returned.
	 */
	@Override
    public List<FileVo> selectShareFileListByFolderId(FileVo vo) {
    	if( !isSharedAndReadAuthorizedFolder(vo.getFolderId()) ) {
//    		throw new ForbiddenException("forbidden");
    		return new ArrayList<>();
    	}
    	String userId = new SecurityInfoUtil().getAccountId();
    	vo.setUserId(userId);
    	return documentMapper.selectFileList(vo);
    }

	/**
	 * Retrieves the current usage and maximum capacity information for the user.
	 * This method fetches the total usage capacity (used space) for the user
	 * and sets it alongside the maximum allowable capacity for the current user context.
	 *
	 * @return an instance of UseCpctVo containing total space (maximum capacity)
	 *         and used space (current utilization) for the user.
	 */
	@Override
    public UseCpctVo selectUseCpct() {
//    	Path dir = Paths.get(DATASTORE_PATH);
//		dir = dir.toRealPath();
//		
//		FileStore fs = Files.getFileStore(dir);
//		long freeSpace = fs.getUsableSpace();
//		long totalSpace = fs.getTotalSpace();

    	String userId = new SecurityInfoUtil().getAccountId();
    	
    	long useCpct = documentMapper.selectTotalUseCpct(userId);
		
		UseCpctVo ret = new UseCpctVo();
		ret.setTotalSpace(MAX_USE_CPCT);
		ret.setUsedSpace(useCpct);
		
		return ret;
    }

	/**
	 * Retrieves a list of user-member authorization mappings based on the provided parameters.
	 *
	 * @param param a map containing the parameters required for querying the user-member authorization mappings,
	 *              including folder ID and other relevant information.
	 * @return a list of UserMemberAuthMappVo objects representing the user-member authorization mappings.
	 */
	@Override
    public List<UserMemberAuthMappVo> selectListUserAuthMapp(HashMap<String, Object> param) {
    	String deptCd = new SecurityInfoUtil().getDeptCd();
    	Long folderId = Long.valueOf(Objects.toString(param.get("folderId")));
    	param.put("deptCd", deptCd);
    	param.put("folderId", folderId);
    	return documentMapper.selectListUserAuthMapp(param);
    }

	/**
	 * Saves a list of folder authorization mappings for the specified users.
	 * This method retrieves the current user ID and sets it as the creator and
	 * modifier for each authorization mapping before saving them into the database.
	 *
	 * @param list a list of UserMemberAuthMappVo objects representing the folder
	 * authorization mappings to be saved. If the list is null, the method does nothing.
	 */
	@Transactional
    @Override
    public void saveFolderAuthMapp(List<UserMemberAuthMappVo> list) {
    	String userId = new SecurityInfoUtil().getAccountId();
    	if(list != null) {
    		for(UserMemberAuthMappVo vo : list) {
    			vo.setRegId(userId);
    			vo.setModId(userId);
    			documentMapper.saveFolderAuthMapp(vo);
        	}	
    	}
    }

	/**
	 * Retrieves a list of department folders based on the specified criteria.
	 * Filters folders and only includes those that are accessible based on the
	 * current user's permissions and additional search criteria.
	 *
	 * @param vo the FolderVo object containing search criteria, such as
	 *           user information and department code
	 * @return a list of FolderVo objects representing the department folders
	 *         that match the provided criteria, sorted by folder name
	 */
	@Override
	public List<FolderVo> selectDeptFolderList(FolderVo vo) {
    	String userId = new SecurityInfoUtil().getAccountId();
    	String deptCd = new SecurityInfoUtil().getDeptCd();
//    	vo.setUserId(userId);
//    	vo.setDeptCd(deptCd);
//    	return documentMapper.selectDeptFolderList(vo);

    	
    	vo.setUserId(userId);
    	vo.setDeptCd(deptCd);
        List<FolderVo> list = documentMapper.selectDeptFolderListAll(vo);
        
		ArrayDeque<Long> dq = new ArrayDeque<>();
		Set<Long> set = new HashSet<>();
		Map<Long, FolderVo> map = new HashMap<>();
		
		for(FolderVo folder : list) {
			Long folderId = folder.getFolderId();
			map.put(folderId, folder);
			if(folder.getSearchYn()) {
				dq.add(folderId);
				set.add(folderId);
			}
		}
		
		while(!dq.isEmpty()) {
			Long id = dq.pollFirst();
			FolderVo folder = map.get(id);
			Long upId = folder.getUpperFolderId();
			if(upId == -1) {
				continue;
			}
			if(!map.containsKey(upId)) {
				continue;
			}
			if(set.contains(upId)) {
				continue;
			}
			dq.add(upId);
			set.add(upId);
		}

		List<FolderVo> ret = new ArrayList<>();
		for(FolderVo folder : list) {
			if(folder.getUpperFolderId().equals(vo.getUpperFolderId()) && set.contains(folder.getFolderId())) {
				ret.add(folder);
			}
		}
		Collections.sort(ret, new Comparator<FolderVo>() {
			@Override
			public int compare(FolderVo a, FolderVo b) {
				return a.getFolderNm().compareTo(b.getFolderNm());
			}
		});
		return ret;
	}
    
}