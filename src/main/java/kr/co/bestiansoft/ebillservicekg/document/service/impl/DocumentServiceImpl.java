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
    
    @Override
    public List<FolderVo> selectDeptFolderListAll(FolderVo vo) {
    	String userId = new SecurityInfoUtil().getAccountId();
    	String deptCd = new SecurityInfoUtil().getDeptCd();
    	vo.setUserId(userId);
    	vo.setDeptCd(deptCd);
        return documentMapper.selectDeptFolderListAll(vo);
    }
    
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
    
    @Override
    public List<FolderVo> selectMyFolderList(FolderVo vo) {
    	String userId = new SecurityInfoUtil().getAccountId();
    	vo.setUserId(userId);
        return documentMapper.selectMyFolderList(vo);
    }
    
    @Override
    public List<FolderVo> selectShareFolderList(FolderVo vo) {
    	String userId = new SecurityInfoUtil().getAccountId();
    	String deptCd = new SecurityInfoUtil().getDeptCd();
    	vo.setUserId(userId);
    	vo.setDeptCd(deptCd);
        return documentMapper.selectShareFolderList(vo);
    }
    
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
    
    @Transactional
    @Override
    public int insertMyFolder(FolderVo vo) {
    	
    	String userId = new SecurityInfoUtil().getAccountId();
    	
    	vo.setRegId(userId);
    	vo.setUserId(userId);
    	return documentMapper.insertMyFolder(vo);
    }
    
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
    
    @Transactional
    @Override
    public int deleteFoldersAndFiles(List<Long> folderIds, List<String> fileGroupIds) {
    	int ret = deleteFolders(folderIds);
    	ret += deleteFiles(fileGroupIds);
    	return ret;
    }
    
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
    
    @Transactional
    @Override
    public int restoreFoldersAndFiles(List<Long> folderIds, List<String> fileGroupIds) {
    	int ret = restoreFolders(folderIds);
    	ret += restoreFiles(fileGroupIds);
    	return ret;
    }
    
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
    
    @Transactional
    @Override
    public int moveFoldersAndFiles(List<Long> folderIds, List<String> fileGroupIds, Long toFolderId) {
    	int ret = moveFolders(folderIds, toFolderId);
    	ret += moveFiles(fileGroupIds, toFolderId);
    	return ret;
    }
    
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
    
    @Transactional
    @Override
    public void removeFoldersAndFiles(List<Long> folderIds, List<String> fileIds) throws Exception {
    	removeFolders(folderIds);
    	removeFiles(fileIds);
    }
    
    @Transactional
    @Override
    public void removeFolders(List<Long> folderIds) throws Exception {
    	if(folderIds != null) {
    		for(Long folderId : folderIds) {
        		removeFolder(folderId);
        	}	
    	}
    }
    
    @Transactional
    @Override
    public void removeFiles(List<String> fileIds) throws Exception {
    	if(fileIds != null) {
    		for(String fileId : fileIds) {
        		removeFile(fileId);
        	}	
    	}
    }
    
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
    
    @Transactional
    public void removeFile(String fileId) throws Exception {
    	String userId = new SecurityInfoUtil().getAccountId();
    	FileVo fileVo = documentMapper.selectFile2(fileId);
    	documentMapper.addUseCpct(userId, -fileVo.getFileSize()); 
    	documentMapper.removeFile(fileId);
		edv.delete(fileId);
    }
    
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
    
    @Override
    public List<FileVo> selectMyFileList(FileVo vo) {
    	vo.setUserId(new SecurityInfoUtil().getAccountId());
    	return documentMapper.selectMyFileList(vo);
    }
    
    @Override
    public List<FileVo> selectShareFileList(FileVo vo) {
    	vo.setUserId(new SecurityInfoUtil().getAccountId());
    	vo.setDeptCd(new SecurityInfoUtil().getDeptCd());
    	return documentMapper.selectShareFileList(vo);
    }
    
    @Override
    public List<FileVo> selectStarFileList(FileVo vo) {
    	vo.setUserId(new SecurityInfoUtil().getAccountId());
    	vo.setDeptCd(new SecurityInfoUtil().getDeptCd());
    	return documentMapper.selectStarFileList(vo);
    }
    
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
    
    @Override
    public List<FileVo> selectFileGroup(FileVo vo) {
    	vo.setUserId(new SecurityInfoUtil().getAccountId());
    	return documentMapper.selectFileGroup(vo);
    }
    
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
    
    private boolean isAuthorizedShare(FolderVo folder) {
    	String userId = new SecurityInfoUtil().getAccountId();
    	String deptCd = new SecurityInfoUtil().getDeptCd();
    	String deptHeadYn = new SecurityInfoUtil().getDeptHeadYn();
    	
    	if(folder != null && "Y".equals(folder.getDeptFolderYn()) && deptCd.equals(folder.getDeptCd()) && "Y".equals(deptHeadYn)) {
    		return true;
    	}
    	return folder != null && folder.getRegId().equals(userId);
    }
    
    private boolean isAuthorizedShare(FileVo file) {
    	String userId = new SecurityInfoUtil().getAccountId();
    	String deptCd = new SecurityInfoUtil().getDeptCd();
    	String deptHeadYn = new SecurityInfoUtil().getDeptHeadYn();
    	
    	if(file != null && "Y".equals(file.getDeptFileYn()) && deptCd.equals(file.getDeptCd()) && "Y".equals(deptHeadYn)) {
    		return true;
    	}
    	return file != null && file.getRegId().equals(userId);
    }
    
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
    
    @Transactional
    @Override
    public List<FileShareVo> selectShareTargetList(FileShareVo vo) {
    	vo.setOwnerId(new SecurityInfoUtil().getAccountId());
    	return documentMapper.selectShareTargetList(vo);
    }
    
    @Transactional
    @Override
    public List<UserMemberVo> selectListUserMember(HashMap<String, Object> param) {
    	return documentMapper.selectListUserMember(param);
    }
    
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
    
    @Override
    public List<FolderVo> selectShareFolderListByFolderId(FolderVo vo) {
    	
    	if( !isSharedFolder(vo.getUpperFolderId()) ) {
    		throw new ForbiddenException("forbidden");
    	}
    	
    	return documentMapper.selectFolderList(vo);
    }   
 
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
    
    @Override
    public List<UserMemberAuthMappVo> selectListUserAuthMapp(HashMap<String, Object> param) {
    	String deptCd = new SecurityInfoUtil().getDeptCd();
    	Long folderId = Long.valueOf(Objects.toString(param.get("folderId")));
    	param.put("deptCd", deptCd);
    	param.put("folderId", folderId);
    	return documentMapper.selectListUserAuthMapp(param);
    }   
 
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