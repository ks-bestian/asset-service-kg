package kr.co.bestiansoft.ebillservicekg.document.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;
import kr.co.bestiansoft.ebillservicekg.common.exceptionadvice.exception.ForbiddenException;
import kr.co.bestiansoft.ebillservicekg.common.file.service.impl.EDVHelper;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import kr.co.bestiansoft.ebillservicekg.document.repository.DocumentMapper;
import kr.co.bestiansoft.ebillservicekg.document.service.DocumentService;
import kr.co.bestiansoft.ebillservicekg.document.service.ThumbnailService;
import kr.co.bestiansoft.ebillservicekg.document.vo.FileShareVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.FileVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.FolderVo;
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
    
    @Override
    public List<FolderVo> selectDeptFolderListAll(FolderVo vo) {
    	String deptCd = new SecurityInfoUtil().getDeptCd();
    	vo.setDeptCd(deptCd);
        return documentMapper.selectDeptFolderListAll(vo);
    }
    
    @Override
    public List<FolderVo> selectMyFolderListAll(FolderVo vo) {
    	String userId = new SecurityInfoUtil().getAccountId();
    	vo.setUserId(userId);
        return documentMapper.selectMyFolderListAll(vo);
    }
    
    @Override
    public List<FolderVo> selectDeptFolderList(FolderVo vo) {
    	String deptCd = new SecurityInfoUtil().getDeptCd();
    	vo.setDeptCd(deptCd);
        return documentMapper.selectDeptFolderList(vo);
    }
    
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
    	String userId = new SecurityInfoUtil().getAccountId();
    	vo.setUserId(userId);
        return documentMapper.selectDeleteFolderList(vo);
    }

    @Transactional
    @Override
    public int insertDeptFolder(FolderVo vo) {
    	
    	String userId = new SecurityInfoUtil().getAccountId();
    	String deptCd = new SecurityInfoUtil().getDeptCd();
    	
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
    	
    	FolderVo folder = documentMapper.selectFolderByFolderId(vo.getFolderId());
		if(folder != null && !folder.getRegId().equals(userId)) {
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
    			FolderVo folder = documentMapper.selectFolderByFolderId(folderId);
    			if(folder != null && !folder.getRegId().equals(userId)) {
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
    			if(file != null && !file.getRegId().equals(userId)) {
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
    	documentMapper.removeFile(fileId);
		edv.delete(fileId);
    }
    
    @Transactional
    @Override
    public int uploadFile(FileVo vo) {
    	
    	int ret = 0;
		
		MultipartFile[] files = vo.getFiles();
		
		Map<String, Long> map = new HashMap<>();
		map.put("", vo.getFolderId());
		List<Map<String, Object>> thumbnailJobs = new ArrayList<>();
		
		for(MultipartFile mpf : files) {
			if (mpf.isEmpty()) break;
			ret += saveFile(vo, mpf, map);

			// 묶음업로드 아닌경우 각 파일의 썸네일 생성
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
		
		// 썸네일 생성
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
//    	//중요여부 저장
//		String favoriteYn = vo.getFavoriteYn();
//		if("Y".equals(favoriteYn)) {
//			vo.setUserId(tmpUserId);
//			documentMapper.saveFavorite(vo);	
//		}
//		
//		return ret;
//    }
    
    // 폴더 업로드시 - 폴더 생성
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
	
    // 폴더업로드 - 파일 및 경로상의 폴더 저장
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
		
		//중요여부 저장(묶음업로드 아닌경우)
		if(!"Y".equals(groupYn)) {
			String favoriteYn = vo.getFavoriteYn();
			if("Y".equals(favoriteYn)) {
				fileVo.setUserId(userId);
				fileVo.setFavoriteYn(favoriteYn);
				documentMapper.saveFavorite(fileVo);	
			}	
		}
		
		return ret;
	}
    
    @Override
    public List<FileVo> selectDeptFileList(FileVo vo) {
    	vo.setUserId(new SecurityInfoUtil().getAccountId());
    	vo.setDeptCd(new SecurityInfoUtil().getDeptCd());
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
    	return documentMapper.selectStarFileList(vo);
    }
    
    @Override
    public List<FileVo> selectDeleteFileList(FileVo vo) {
    	vo.setUserId(new SecurityInfoUtil().getAccountId());
    	return documentMapper.selectDeleteFileList(vo);
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
		if(file != null && !file.getRegId().equals(userId)) {
			throw new ForbiddenException("forbidden");
		}
    	
    	vo.setModId(userId);
    	int ret = documentMapper.updateFileByFileId(vo);

    	saveFavorite(vo);
    	return ret;
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
//    		//TODO 기존 썸네일 삭제(EDV)
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
    		if(folder != null && !folder.getRegId().equals(userId)) {
    			throw new ForbiddenException("forbidden");
    		}	
    	}
    	else {
    		FileVo file = documentMapper.selectFile(vo.getFileId());
			if(file != null && !file.getRegId().equals(userId)) {
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
    		if(folder != null && !folder.getRegId().equals(userId)) {
    			throw new ForbiddenException("forbidden");
    		}	
    	}
    	else {
    		FileVo file = documentMapper.selectFile(vo.getFileId());
			if(file != null && !file.getRegId().equals(userId)) {
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
    	
    	if(folderId == null || folderId == -1) {
    		return false;
    	}
    	
    	String userId = new SecurityInfoUtil().getAccountId();
    	String deptCd = new SecurityInfoUtil().getDeptCd();
    	
    	FileShareVo fileShareVo = new FileShareVo();
    	fileShareVo.setFolderYn("Y");
    	fileShareVo.setFolderId(folderId);
    	fileShareVo.setTargetKind("DEPT");
    	fileShareVo.setTargetId(deptCd);
    	FileShareVo share1 = documentMapper.selectShare(fileShareVo);
    	
    	if(share1 != null) {
    		return true;
    	}
    	
    	fileShareVo.setTargetKind("INDV");
    	fileShareVo.setTargetId(userId);
    	FileShareVo share2 = documentMapper.selectShare(fileShareVo);
    	
    	if(share2 != null) {
    		return true;
    	}
    	
    	FolderVo folder = documentMapper.selectFolderByFolderId(folderId);
    	return isSharedFolder(folder.getUpperFolderId());
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
    	if( !isSharedFolder(vo.getFolderId()) ) {
    		throw new ForbiddenException("forbidden");
    	}
    	String userId = new SecurityInfoUtil().getAccountId();
    	vo.setUserId(userId);
    	return documentMapper.selectFileList(vo);
    }
    
}