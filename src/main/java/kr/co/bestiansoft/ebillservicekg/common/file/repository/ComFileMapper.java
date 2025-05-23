package kr.co.bestiansoft.ebillservicekg.common.file.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.common.file.vo.ComFileVo;
import kr.co.bestiansoft.ebillservicekg.common.file.vo.EbsFileVo;

@Mapper
public interface ComFileMapper {
	int insertFile(ComFileVo vo);
	List<ComFileVo> findByFileGroupId(String fileGroupId);
	ComFileVo findByFileId(String fileId);
	List<ComFileVo> batchGetDeleteFileList();
	void deleteServerFile(String fileId);
	
	int insertFileEbs(EbsFileVo vo);
	int updateFileEbs(EbsFileVo vo);
	
	int insertFileEbsMtng(EbsFileVo vo);
	int updateFileEbsMtng(EbsFileVo vo);
	
	EbsFileVo selectEbsFile(String orgFileId);

	void updatePdfFileInfo(ComFileVo updateVo);

}
