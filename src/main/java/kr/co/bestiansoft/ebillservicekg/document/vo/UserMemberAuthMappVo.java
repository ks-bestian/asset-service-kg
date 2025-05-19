package kr.co.bestiansoft.ebillservicekg.document.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;
import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;

@Data
public class UserMemberAuthMappVo extends UserMemberVo {

	private Long folderId;
	private Boolean searchYn;
	private Boolean createYn;
	private Boolean deleteYn;
	private Boolean updateYn;
	private String regId;
	private String modId;
	
	private Boolean createFolderYn;
	private Boolean createFileYn;
}
