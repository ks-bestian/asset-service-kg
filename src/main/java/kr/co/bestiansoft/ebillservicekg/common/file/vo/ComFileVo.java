package kr.co.bestiansoft.ebillservicekg.common.file.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
public class ComFileVo extends ComDefaultVO {
	private String fileId;
	private String fileGroupId;
	private String orgFileNm;
	private Long fileSize;
	private String fileDiv;
	private String uploadYn;
	private String deleteYn;
	private String pdfFileId;
	private String pdfFileNm;
}
