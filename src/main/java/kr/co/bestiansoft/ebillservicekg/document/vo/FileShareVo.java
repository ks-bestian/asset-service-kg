package kr.co.bestiansoft.ebillservicekg.document.vo;

import java.util.List;

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

	private String userId;
	private String deptCd;
}
