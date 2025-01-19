package kr.co.bestiansoft.ebillservicekg.document.service;
import java.io.InputStream;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.document.vo.FileVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.FolderVo;

public interface DocumentService {

	int insertDeptFolder(FolderVo vo);
	int updateFolder(FolderVo vo);
	int deleteFolders(List<Long> folderIds);
	int deleteFiles(List<String> fileIds);
    int deleteFoldersAndFiles(List<Long> folderIds, List<String> fileGroupIds);
    int moveFolders(List<Long> folderIds, Long toFolderId);
	int moveFiles(List<String> fileIds, Long toFolderId);
    int moveFoldersAndFiles(List<Long> folderIds, List<String> fileGroupIds, Long toFolderId);
    List<FolderVo> selectDeptFolderListAll(FolderVo vo);
    List<FolderVo> selectDeptFolderList(FolderVo vo);

    int uploadFile(FileVo vo);
//    int uploadFileGroup(FileVo vo);
    List<FileVo> selectDeptFileList(FileVo vo);
    List<FileVo> selectFileGroup(FileVo vo);
    
    int updateFile(FileVo vo);
//    int updateFileGroup(FileVo vo) throws Exception;
    
    List<FolderVo> selectMyFolderListAll(FolderVo vo);
    List<FolderVo> selectMyFolderList(FolderVo vo);
    List<FileVo> selectMyFileList(FileVo vo);
    int insertMyFolder(FolderVo vo);
    
    List<FileVo> selectStarFileList(FileVo vo);
    List<FileVo> selectDeleteFileList(FileVo vo);
    List<FolderVo> selectDeleteFolderList(FolderVo vo);
    
    int restoreFolders(List<Long> folderIds);
	int restoreFiles(List<String> fileIds);
    int restoreFoldersAndFiles(List<Long> folderIds, List<String> fileGroupIds);
    void removeFolders(List<Long> folderIds) throws Exception;
	void removeFiles(List<String> fileIds) throws Exception;
    void removeFoldersAndFiles(List<Long> folderIds, List<String> fileGroupIds) throws Exception;
}
