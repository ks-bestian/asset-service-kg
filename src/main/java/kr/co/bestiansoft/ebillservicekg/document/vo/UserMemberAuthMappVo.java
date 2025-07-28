package kr.co.bestiansoft.ebillservicekg.document.vo;

import kr.co.bestiansoft.ebillservicekg.admin.user.vo.UserMemberVo;
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
