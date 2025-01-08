package kr.co.bestiansoft.ebillservicekg.document.service;
import java.io.InputStream;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.document.vo.FileVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.FolderVo;

public interface DocumentService {

	int insertFolder(FolderVo vo);
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
    void removeFiles(List<String> fileIds) throws Exception;
    List<FileVo> selectDeptFileList(FileVo vo);
    List<FileVo> selectFileGroup(FileVo vo);
    
    int updateFile(FileVo vo);
//    int updateFileGroup(FileVo vo) throws Exception;
}
