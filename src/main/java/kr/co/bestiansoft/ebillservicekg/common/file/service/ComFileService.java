package kr.co.bestiansoft.ebillservicekg.common.file.service;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.co.bestiansoft.ebillservicekg.common.file.vo.ComFileVo;

public interface ComFileService {

	String saveFile(MultipartFile[] files);
	List<ComFileVo> getFileList(String fileGroupId);
	ComFileVo getFile(String fileId);
	void batchFileDelete();
	
//	void saveFileEbs(MultipartFile[] files, String billId);
	void saveFileEbs(MultipartFile[] files, String[] fileKindCd, String billId);
	void saveFileEbsMtng(MultipartFile[] files, String[] fileKindCd, String billId);
}
