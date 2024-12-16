package kr.co.bestiansoft.ebillservicekg.file.service;
import java.io.InputStream;
import java.util.List;

import kr.co.bestiansoft.ebillservicekg.file.vo.DeptFileVo;
import kr.co.bestiansoft.ebillservicekg.file.vo.DeptFolderVo;

public interface FileService {

	int insertDeptFolder(DeptFolderVo vo);
	int updateDeptFolder(DeptFolderVo vo);
	int deleteDeptFolders(List<Long> folderIds);
	int deleteDeptFiles(List<String> fileIds);
    int deleteDeptFoldersAndFiles(List<Long> folderIds, List<String> fileGroupIds);
    int moveDeptFolders(List<Long> folderIds, Long toFolderId);
	int moveDeptFiles(List<String> fileIds, Long toFolderId);
    int moveDeptFoldersAndFiles(List<Long> folderIds, List<String> fileGroupIds, Long toFolderId);
    List<DeptFolderVo> selectDeptFolderListAll(DeptFolderVo vo);
    List<DeptFolderVo> selectDeptFolderList(DeptFolderVo vo);

    int uploadDeptFile(DeptFileVo vo);
    int uploadDeptFileGroup(DeptFileVo vo);
    void removeDeptFiles(List<String> fileIds) throws Exception;
    List<DeptFileVo> selectDeptFileList(DeptFileVo vo);
    List<DeptFileVo> selectDeptFileGroup(DeptFileVo vo);
    
    int updateDeptFile(DeptFileVo vo);
    int updateDeptFileGroup(DeptFileVo vo) throws Exception;
}
