package kr.co.bestiansoft.ebillservicekg.document.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class FileShareVo extends ComDefaultVO {

	private String fileId;
	private String fileGroupId;
	private Long folderId;
	private String folderYn;
	
	private String ownerId;
	private String targetKind;
	private String targetId;
	private List<String> targetIds;
}
