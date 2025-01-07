package kr.co.bestiansoft.ebillservicekg.document.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.document.vo.DeptFileVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.DeptFolderVo;

@Mapper
public interface DocumentMapper {
    int insertDeptFolder(DeptFolderVo vo);
    int updateDeptFolder(DeptFolderVo vo);
    void deleteDeptFolder(Long folderId);
    List<DeptFolderVo> selectDeptFolderListAll(DeptFolderVo vo);
    List<DeptFolderVo> selectDeptFolderList(DeptFolderVo vo);

    int insertDeptFile(DeptFileVo vo);
    int insertFileGroup(DeptFileVo vo);
    int updateFileGroup(DeptFileVo vo);
    int updateThumbnail(DeptFileVo vo);
    int updateDeptFileByFileId(DeptFileVo vo);
    int updateDeptFileByFileGroupId(DeptFileVo vo);
    void deleteDeptFile(String fileId);
    List<DeptFileVo> selectDeptFileList(DeptFileVo vo);
    List<DeptFileVo> selectDeptFileGroup(DeptFileVo vo);
    DeptFileVo selectDeptFile(String fileId);
    
    int insertFavorite(DeptFileVo vo);
    int updateFavorite(DeptFileVo vo);
    int saveFavorite(DeptFileVo vo);
}
