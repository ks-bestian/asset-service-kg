package kr.co.bestiansoft.ebillservicekg.document.service;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.UseCpctVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.UserMemberAuthMappVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.FileShareVo;
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
    
    List<FileVo> selectShareFileList(FileVo vo);
    List<FileVo> selectStarFileList(FileVo vo);
    List<FileVo> selectDeleteFileList(FileVo vo);
    List<FolderVo> selectDeleteFolderList(FolderVo vo);
    
    int restoreFolders(List<Long> folderIds);
	int restoreFiles(List<String> fileIds);
    int restoreFoldersAndFiles(List<Long> folderIds, List<String> fileGroupIds);
    void removeFolders(List<Long> folderIds) throws Exception;
	void removeFiles(List<String> fileIds) throws Exception;
    void removeFoldersAndFiles(List<Long> folderIds, List<String> fileGroupIds) throws Exception;
    
    void shareFile(FileShareVo vo);
    void unshareFile(FileShareVo vo);
    List<FileShareVo> selectShareTargetList(FileShareVo vo);
    
    List<UserMemberVo> selectListUserMember(HashMap<String, Object> param);
    List<FolderVo> selectShareFolderList(FolderVo vo);
    
    List<FolderVo> selectShareFolderListByFolderId(FolderVo vo);
    List<FileVo> selectShareFileListByFolderId(FileVo vo);
    
    void saveFavorite(FileVo vo);
    
    UseCpctVo selectUseCpct();
    
    List<UserMemberAuthMappVo> selectListUserAuthMapp(HashMap<String, Object> param);
    void saveFolderAuthMapp(List<UserMemberAuthMappVo> list);
}
