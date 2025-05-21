package kr.co.bestiansoft.ebillservicekg.common.file.vo;

import kr.co.bestiansoft.ebillservicekg.common.vo.ComDefaultVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
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
	private String regId;
	private LocalDateTime regDt;
	private String modId;
	private LocalDateTime modDt;
}
