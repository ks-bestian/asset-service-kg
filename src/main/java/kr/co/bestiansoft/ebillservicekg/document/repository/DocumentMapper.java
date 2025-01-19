package kr.co.bestiansoft.ebillservicekg.document.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.document.vo.FileVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.FolderVo;

@Mapper
public interface DocumentMapper {
    int insertDeptFolder(FolderVo vo);
    int updateFolder(FolderVo vo);
    void removeFolder(Long folderId);
    List<FolderVo> selectDeptFolderListAll(FolderVo vo);
    List<FolderVo> selectDeptFolderList(FolderVo vo);

    int insertFile(FileVo vo);
    int updateThumbnail(FileVo vo);
    int updateFileByFileId(FileVo vo);
    int updateFileByFileGroupId(FileVo vo);
    void removeFile(String fileId);
    List<FileVo> selectDeptFileList(FileVo vo);
    List<FileVo> selectFileGroup(FileVo vo);
    FileVo selectFile(String fileId);
    
    FolderVo selectFolderByFolderId(Long folderId);
    
    int insertFavorite(FileVo vo);
    int updateFavorite(FileVo vo);
    int saveFavorite(FileVo vo);
    
    List<FolderVo> selectMyFolderListAll(FolderVo vo);
    List<FolderVo> selectMyFolderList(FolderVo vo);
    List<FileVo> selectMyFileList(FileVo vo);
    int insertMyFolder(FolderVo vo);
    
    List<FileVo> selectStarFileList(FileVo vo);
    List<FolderVo> selectDeleteFolderList(FolderVo vo);
    List<FileVo> selectDeleteFileList(FileVo vo);
    
    List<Long> selectFolderIdsByUpperFolderId(Long upperFolderId);
    List<String> selectFileIdsByFolderId(Long folderId);
}
