package kr.co.bestiansoft.ebillservicekg.document.repository;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;
import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.FileShareVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.FileVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.FolderVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.UseCpctVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.UserMemberAuthMappVo;

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
    FileVo selectMyFile(String userId, String fileId);
    FileVo selectFile(String fileId);
    FileVo selectFile2(String fileId);
    
    FolderVo selectFolderByFolderId(Long folderId);
    
    int insertFavorite(FileVo vo);
    int updateFavorite(FileVo vo);
    int saveFavorite(FileVo vo);
    int deleteFavorite(FileVo vo);
    
    List<FolderVo> selectMyFolderListAll(FolderVo vo);
    List<FolderVo> selectMyFolderList(FolderVo vo);
    List<FileVo> selectMyFileList(FileVo vo);
    int insertMyFolder(FolderVo vo);
    
    List<FileVo> selectStarFileList(FileVo vo);
    List<FolderVo> selectDeleteFolderList(FolderVo vo);
    List<FolderVo> selectDeletedDeptFolderList(FolderVo vo);
    List<FolderVo> selectDeletedMyFolderList(FolderVo vo);
    List<FileVo> selectDeleteFileList(FileVo vo);
    List<FileVo> selectDeletedDeptFileList(FileVo vo);
    List<FileVo> selectDeletedMyFileList(FileVo vo);
    
    List<Long> selectFolderIdsByUpperFolderId(Long upperFolderId);
    List<String> selectFileIdsByFolderId(Long folderId);
    
    void deleteShare(FileShareVo vo);
    void insertShare(FileShareVo vo);
    List<FileShareVo> selectShareTargetList(FileShareVo vo);
    
    List<UserMemberVo> selectListUserMember(HashMap<String, Object> param);
    
    List<FileVo> selectShareFileList(FileVo vo);
    List<FolderVo> selectShareFolderList(FolderVo vo);
    
    List<FolderVo> selectFolderList(FolderVo vo);
    List<FileVo> selectFileList(FileVo vo);
    
    List<FileShareVo> selectShare(FileShareVo vo);
    
    Long selectTotalUseCpct(String userId);
    void addUseCpct(String userId, Long cpct);
    
    List<UserMemberAuthMappVo> selectListUserAuthMapp(HashMap<String, Object> param);
    void saveFolderAuthMapp(UserMemberAuthMappVo vo);
    
    UserMemberAuthMappVo selectUserAuthMapp(Long folderId, String userId);
    
    List<String> selectListSharingUserId(FileShareVo vo);
}
