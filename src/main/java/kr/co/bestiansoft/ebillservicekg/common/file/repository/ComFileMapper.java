package kr.co.bestiansoft.ebillservicekg.common.file.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.bestiansoft.ebillservicekg.common.file.vo.ComFileVo;

@Mapper
public interface ComFileMapper {
	int insertFile(ComFileVo vo);
	List<ComFileVo> findByFileGroupId(String fileGroupId);
	ComFileVo findByFileId(String fileId);
	List<ComFileVo> batchGetDeleteFileList();
	void deleteServerFile(String fileId);
}
