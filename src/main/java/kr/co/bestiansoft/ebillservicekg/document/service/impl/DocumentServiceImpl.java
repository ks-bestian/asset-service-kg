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

import kr.co.bestiansoft.ebillservicekg.common.file.service.impl.EDVHelper;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import kr.co.bestiansoft.ebillservicekg.document.repository.DocumentMapper;
import kr.co.bestiansoft.ebillservicekg.document.service.DocumentService;
import kr.co.bestiansoft.ebillservicekg.document.service.ThumbnailService;
import kr.co.bestiansoft.ebillservicekg.document.vo.FileVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.FolderVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class DocumentServiceImpl implements DocumentService {
    private final DocumentMapper fileMapper;
    private final EDVHelper edv;
    private final ThumbnailService thumbnailService;
    
    private final String tmpUserId = "admin";
    private final String tmpDeptCd = "dept1";

    @Override
    public List<FolderVo> selectDeptFolderListAll(FolderVo vo) {
    	vo.setDeptCd(tmpDeptCd);
        return fileMapper.selectDeptFolderListAll(vo);
    }
    
    @Override
    public List<FolderVo> selectDeptFolderList(FolderVo vo) {
        return fileMapper.selectDeptFolderList(vo);
    }

    @Transactional
    @Override
    public int insertFolder(FolderVo vo) {
    	
    	vo.setRegId(tmpUserId);
    	vo.setDeptCd(tmpDeptCd);
    	return fileMapper.insertFolder(vo);
    }
    
    @Transactional
    @Override
    public int updateFolder(FolderVo vo) {
    	vo.setModId(tmpUserId);
    	return fileMapper.updateFolder(vo);
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
    	int ret = 0;
    	for(Long folderId : folderIds) {
    		FolderVo vo = new FolderVo();
        	vo.setFolderId(folderId);
        	vo.setDelYn("Y");
        	vo.setModId(tmpUserId);
        	ret += fileMapper.updateFolder(vo);
    	}
    	return ret;
    }
    
    @Transactional
    @Override
    public int deleteFiles(List<String> fileGroupIds) {
    	int ret = 0;
    	for(String fileGroupId : fileGroupIds) {
    		FileVo vo = new FileVo();
    		vo.setFileGroupId(fileGroupId);
    		vo.setDelYn("Y");
    		vo.setModId(tmpUserId);
    		ret += fileMapper.updateFileByFileGroupId(vo);
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
    	for(Long folderId : folderIds) {
    		FolderVo vo = new FolderVo();
        	vo.setFolderId(folderId);
        	vo.setUpperFolderId(toFolderId);
        	vo.setModId(tmpUserId);
        	ret += fileMapper.updateFolder(vo);
    	}
    	return ret;
    }
    
    @Transactional
    @Override
    public int moveFiles(List<String> fileGroupIds, Long toFolderId) {
    	int ret = 0;
    	for(String fileGroupId : fileGroupIds) {
    		FileVo vo = new FileVo();
    		vo.setFileGroupId(fileGroupId);
    		vo.setFolderId(toFolderId);
    		vo.setModId(tmpUserId);
    		ret += fileMapper.updateFileByFileGroupId(vo);
    	}
    	return ret;
    }
    
    @Transactional
    @Override
    public void removeFiles(List<String> fileIds) throws Exception {
    	for(String fileId : fileIds) {
    		fileMapper.deleteFile(fileId);
    		edv.delete(fileId);
    	}
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
			if("N".equals(vo.getGroupYn())) {
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
//    	fileMapper.insertFileGroup(vo);
//    	
//    	int ret = uploadFile(vo);
//    	
//    	//중요여부 저장
//		String favoriteYn = vo.getFavoriteYn();
//		if("Y".equals(favoriteYn)) {
//			vo.setUserId(tmpUserId);
//			fileMapper.saveFavorite(vo);	
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
		String userId = tmpUserId;
		String deptCd = "1234";
		
		FolderVo folderVo = new FolderVo();
		folderVo.setFolderNm(folderNm);
		folderVo.setRegId(userId);
		folderVo.setDeptCd(deptCd);
		folderVo.setUpperFolderId(upperFolderId);
		
		insertFolder(folderVo);
		
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
		String regId = tmpUserId;
		String groupYn = vo.getGroupYn();
		String fileGroupId = vo.getFileGroupId();
		
		String deptCd = tmpDeptCd;
		
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
		fileVo.setRegId(regId);
		fileVo.setFileGroupId("Y".equals(groupYn) ? fileGroupId : fileId);
		fileVo.setEdvHashStr(fileHash);
		fileVo.setEdvPath(filePath);
		fileVo.setFolderId(folderId);
		fileVo.setDeptCd(deptCd);
		
		int ret = fileMapper.insertFile(fileVo);
		
		//중요여부 저장(묶음업로드 아닌경우)
		if("N".equals(groupYn)) {
			String favoriteYn = vo.getFavoriteYn();
			if("Y".equals(favoriteYn)) {
				fileVo.setUserId(tmpUserId);
				fileVo.setFavoriteYn(favoriteYn);
				fileMapper.saveFavorite(fileVo);	
			}	
		}
		
		return ret;
	}
    
    @Override
    public List<FileVo> selectDeptFileList(FileVo vo) {
    	vo.setUserId(tmpUserId);
    	return fileMapper.selectDeptFileList(vo);
    }
    
    @Override
    public List<FileVo> selectFileGroup(FileVo vo) {
    	vo.setUserId(tmpUserId);
    	return fileMapper.selectFileGroup(vo);
    }
    
    @Transactional
    @Override
    public int updateFile(FileVo vo) {
    	vo.setModId(tmpUserId);
    	int ret = fileMapper.updateFileByFileId(vo);
    	
    	String fileGroupId = fileMapper.selectFile(vo.getFileId()).getFileGroupId();
    	vo.setFileGroupId(fileGroupId);
    	vo.setUserId(tmpUserId);
    	vo.setRegId(tmpUserId);
    	vo.setModId(tmpUserId);
    	fileMapper.saveFavorite(vo);
    	return ret;
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
//    	int ret = fileMapper.updateFileGroup(vo);
//    	
//    	// remove files from file group
//    	removeFiles(vo.getDelFileIds());
//    	
//    	// add files to file group
//    	MultipartFile[] files = vo.getAddFiles();
//    	if(files != null) {
//    		List<FileVo> groupFiles = fileMapper.selectFileGroup(vo);
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
//    	fileMapper.saveFavorite(vo);
//    	
//    	return ret;
//    }
    
}