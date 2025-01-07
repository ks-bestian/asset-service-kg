package kr.co.bestiansoft.ebillservicekg.common.file.service;
import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.co.bestiansoft.ebillservicekg.common.file.vo.ComFileVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.DeptFileVo;
import kr.co.bestiansoft.ebillservicekg.document.vo.DeptFolderVo;

public interface ComFileService {

	String saveFile(MultipartFile[] files);
	List<ComFileVo> getFileList(String fileGroupId);
	ComFileVo getFile(String fileId);
}
