package kr.co.bestiansoft.ebillservicekg.common.file.vo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class ComFileVo extends ComDefaultVO {

	private String fileId;
	private String fileGroupId;
	private String orgFileNm;
	private Long fileSize;
	private String fileDiv;
	private String uploadYn;
	private String deleteYn;
}
